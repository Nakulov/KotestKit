# KotestKit

Обёртки над Kotest: тест одним вызовом, параметризованные случаи через `withGivens`, тесты соответствия JSON и DTO
на kotlinx.serialization и единый стандарт формы unit-тестов.

## Подключение

Требования: JVM 17, Kotlin не ниже 2.3. Kotest 6.2.6 приходит транзитивно, отдельно его подключать не нужно.

`settings.gradle.kts`. `mavenCentral()` нужен, иначе не найдётся Kotest:

```kts
dependencyResolutionManagement {
    repositories {
        mavenCentral()

        maven {
            url = uri("https://maven.pkg.github.com/Nakulov/KotestKit")
            credentials {
                username = providers.gradleProperty("gpr.user").get()
                password = providers.gradleProperty("gpr.key").get()
            }
        }
    }
}
```

`~/.gradle/gradle.properties`. Нужен токен classic с `read:packages`, без него GitHub Packages не отдаёт и публичный
пакет:

```properties
gpr.user=<логин GitHub>
gpr.key=<токен>
```

`build.gradle.kts`. `mockk` подключается отдельно, он нужен для третьей формы тестов:

```kts
dependencies {
    testImplementation("io.github.nakulov:kotestkit:2.1.0")
    testImplementation("io.mockk:mockk:1.14.9")
}

tasks.test {
    useJUnitPlatform()
}
```

Тесты JSON — отдельный артефакт `kotestkit-serialization`, проекту без JSON он не нужен. kotlinx.serialization он не
приносит: `kotlinx-serialization-json` подключает сам проект, не ниже 1.9.0. `kotlinx-serialization-core` приходит с
Kotest (сейчас 1.9.0). Плагин serialization нужен для `@Serializable` в DTO:

```kts
plugins {
    kotlin("plugin.serialization") version "<версия Kotlin проекта>"
}

dependencies {
    testImplementation("io.github.nakulov:kotestkit-serialization:2.1.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
}
```

Первый тест, файл можно скопировать и запустить:

```kotlin
import io.github.nakulov.kotestkit.shouldReturnTest
import io.kotest.core.spec.style.BehaviorSpec

class Greeter {
    fun greet(name: String): String = "Привет, $name!"
}

class GreeterTest : BehaviorSpec({
    val greeter = Greeter()

    shouldReturnTest("Привет, Аня!") { greeter.greet("Аня") }
})
```

Устройство самого Kotest описано в [его документации](https://kotest.io/docs/framework/framework.html).

## Как писать тесты

Стандарт описывает форму unit-тестов: как писать, а не что покрывать. Стиль спек — `BehaviorSpec`. Стандарт действует
для проектов-потребителей. Собственные спеки библиотеки живут по [`TESTING.md`](TESTING.md).

Форма выбирается по тому, что проверяет тест, а не по типу класса:

| Что проверяет тест | Форма |
|---|---|
| Значение, в том числе состояние, которое можно вернуть из блока | `shouldReturnTest` |
| Только брошенное исключение | `shouldThrow*Test` |
| Вызовы зависимостей, возможно вместе со значением или исключением | `given`/`when`/`then` с `verifySequence` или `coVerifySequence` |
| Соответствие JSON и DTO | `shouldDecodeTest` / `shouldEncodeTest` внутри `withJson` |

### Правила

1. В третьей форме 1 given = 1 when = 1 then. Случай `withGivens` считается за один `given`, внутри него допустимы `when` и `then`.
2. `shouldReturnTest` и `shouldThrow*Test` — самостоятельные тесты, основная форма в корне спеки.
3. Контейнер вокруг них нужен только для набора случаев (`withGivens`) или общей подготовки (`given`). В `given` с общей подготовкой их может быть несколько.
4. Параметризованные тесты пишутся только через `withGivens`.
5. Спека с моками ставит `isolationMode = IsolationMode.InstancePerRoot`. Ручного сброса через `clearMocks` и `beforeTest` нет.
6. Неточные значения проверяются матчером. Внутри контейнера случай описывает имя контейнера, отдельное имя тесту не нужно. В корне спеки или когда таких тестов в контейнере несколько, имя задаётся через `options`: иначе тест называется `expected ToleranceMatcher`.
7. Случай вне стандарта пишется прямым вызовом Kotest и фиксируется комментарием `// Вне стандарта: <причина>` прямо над тестом. Если нужно решение в библиотеке, заводится задача в KotestKit, её номер указывается в комментарии. Ассистент сначала сообщает о таком случае владельцу.

### Примеры

Значение, с заглушкой мока:

```kotlin
shouldReturnTest(20, options = TestOptions(name = "налог по ставке страны")) {
    every { rates.rateFor("RU") } returns 20
    calculator.tax(100, "RU")
}
```

Состояние, которое можно вернуть из блока:

```kotlin
shouldReturnTest(1, options = TestOptions(name = "размер после добавления")) {
    queue.add(task)
    queue.size
}
```

Значение со входом. Вход передаётся в блок и попадает в имя теста, здесь `100 -> 90`:

```kotlin
shouldReturnTest(90, 100) { price -> discount.apply(price) }
```

Настройки теста. `TestOptions` задаёт имя и `TestConfig` из Kotest:

```kotlin
shouldReturnTest(
    180,
    options = TestOptions(name = "цена со скидкой", config = TestConfig(timeout = 1.seconds)),
) {
    discount.apply(200)
}
```

Исключение. `shouldThrowTest` принимает и наследников указанного типа, `shouldThrowExactlyTest` только сам тип,
`shouldThrowWithMessageTest` дополнительно сверяет сообщение:

```kotlin
shouldThrowTest<RuntimeException> { Discount(percent = 150) }

shouldThrowExactlyTest<IllegalArgumentException> { Discount(percent = 150) }

shouldThrowWithMessageTest<IllegalArgumentException>("Скидка должна быть от 0 до 100: 150") {
    Discount(percent = 150)
}
```

Вызовы зависимостей, целый файл:

```kotlin
import io.github.nakulov.kotestkit.then
import io.github.nakulov.kotestkit.`when`
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.coJustRun
import io.mockk.coVerifySequence
import io.mockk.mockk

data class Order(val id: Long, val total: Int)

interface OrderRepository {
    suspend fun save(order: Order)
}

class OrderService(private val repository: OrderRepository) {
    suspend fun place(order: Order) = repository.save(order)
}

class OrderServiceTest : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerRoot

    val repository = mockk<OrderRepository>()
    val service = OrderService(repository)

    given("новый заказ") {
        val order = Order(id = 1000L, total = 20)
        coJustRun { repository.save(order) }
        `when` {
            service.place(order)
            then {
                coVerifySequence { repository.save(order) }
            }
        }
    }
})
```

Набор случаев:

```kotlin
withGivens(
    nameFn = { "цена: ${it.arg2}" },
    case(arg1 = 270, arg2 = 300),
    case(arg1 = 360, arg2 = 400),
) { expected, price ->
    shouldReturnTest(expected) { discount.apply(price) }
}
```

Общая подготовка:

```kotlin
given("скидка 50%") {
    val half = Discount(percent = 50)
    shouldReturnTest(50) { half.apply(100) }
    shouldReturnTest(0) { half.apply(0) }
}
```

Матчер: в наборе случаев имя даёт `nameFn`:

```kotlin
withGivens(
    nameFn = { "цена: ${it.arg2}, вес: ${it.arg3}" },
    case(2.5, 5.0, 2.0),
) { expected, price, kilos ->
    shouldReturnTest(expected plusOrMinus 1e-12) { pricePerKilo(price, kilos) }
}
```

Матчер в корне спеки: имя задаём сами:

```kotlin
shouldReturnTest(0.333 plusOrMinus 1e-3, options = TestOptions(name = "цена за килограмм")) {
    pricePerKilo(1.0, 3.0)
}
```

Случай вне стандарта:

```kotlin
// Вне стандарта: один набор данных на разные given, см. #NN
withContexts(1, 2) { n ->
    given("пустой кэш") {
        shouldReturnTest(n) { n }
    }
    given("заполненный кэш") {
        shouldReturnTest(n) { n }
    }
}
```

### JSON и DTO

Тест ловит расхождение DTO с форматом сервера до запуска приложения: образец ответа → ожидаемый DTO и обратно.
Образцы — ресурсы `.json` в `src/test/resources`, путь задаётся от этого корня. `shouldDecodeTest` разбирает образец и
сравнивает с DTO через `shouldBe`, при несовпадении Kotest называет поле. `shouldEncodeTest` кодирует DTO и сравнивает
с образцом через `shouldEqualJson`: порядок ключей и пробелы не важны, отчёт называет путь поля. Имя теста — путь
образца и тип, например `dto/user/full.json -> UserDto`. Своё имя и `TestConfig` задаются через `options`, как у
`shouldReturnTest`.

Целый файл, образцы лежат в `src/test/resources/dto/user/`:

```kotlin
@Serializable
data class UserDto(val id: Long, val name: String, val nickname: String?)

class UserDtoTest : BehaviorSpec({
    withJson {
        shouldDecodeTest("dto/user/full.json", UserDto(1, "Аня", "anya"))
        shouldDecodeTest("dto/user/without_nickname.json", UserDto(1, "Аня", null))
        shouldEncodeTest(UserDto(1, "Аня", "anya"), "dto/user/full.json")
    }
})
```

По умолчанию `withJson` берёт `DefaultTestJson`. Его флаги — требования к `Json` приложения: старый DTO читает ответ
нового сервера.

| Флаг | Зачем |
|---|---|
| `ignoreUnknownKeys = true` | Сервер добавил поле — старый DTO читается |
| `explicitNulls = false` | Сервер не прислал необязательное поле — `null` |
| `coerceInputValues = true` | Новое значение enum или `null` — значение поля по умолчанию |
| `encodeDefaults = true` | В запрос идут все поля, без надежды на значения по умолчанию сервера |
| `prettyPrint = true` | Читаемый JSON в отчёте о падении, на сравнение не влияет |

Если у `Json` приложения другие флаги или нужен `SerializersModule` (для `@Contextual` и полиморфизма), передайте свой
экземпляр. `Json(DefaultTestJson) { ... }` сохраняет флаги по умолчанию:

```kotlin
withJson(Json(DefaultTestJson) { serializersModule = appModule }) {
    shouldDecodeTest("dto/payment/card.json", PaymentDto(7, BigDecimal("199.90")))
    shouldEncodeTest(PaymentDto(7, BigDecimal("199.90")), "dto/payment/card.json")
}
```

## Ограничения

- `shouldReturnTest` принимает до 8 входов, `expected` не считается. `case` у `withGivens` принимает до 8 аргументов,
  `expected` считается, если передаётся через `case`.
- `withGivens` работает только в корне `BehaviorSpec`.
- `withJson` работает только в корне `BehaviorSpec`. Внутри него тесты создают только `shouldDecodeTest` и
  `shouldEncodeTest`, другие вызовы — ошибка компиляции. `withJson` сам тест не создаёт: тесты группы лежат в корне
  спеки.
- Образцы JSON только в ресурсах, строки с JSON в коде не поддерживаются.
- Вызов внутри теста — ошибка компиляции, в том числе `then` внутри `then`.
- Типы `expected` и результата блока при компиляции не сверяются: `Output` выводится как общий супертип, поэтому
  `shouldReturnTest(1) { "x" }` компилируется и падает при запуске. С матчером то же самое.
- `io.kotest.datatest.withData` остаётся доступен из Kotest, автоматической проверки стандарта нет.
