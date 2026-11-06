package io.github.nakulov.kotestkit.readme

import io.github.nakulov.kotestkit.TestOptions
import io.github.nakulov.kotestkit.case
import io.github.nakulov.kotestkit.shouldReturnTest
import io.github.nakulov.kotestkit.shouldThrowExactlyTest
import io.github.nakulov.kotestkit.shouldThrowTest
import io.github.nakulov.kotestkit.shouldThrowWithMessageTest
import io.github.nakulov.kotestkit.withGivens
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.core.test.config.TestConfig
import io.kotest.datatest.withContexts
import io.kotest.matchers.doubles.plusOrMinus
import io.mockk.every
import io.mockk.mockk
import kotlin.time.Duration.Companion.seconds

interface TaxRates {
    fun rateFor(country: String): Int
}

class TaxCalculator(private val rates: TaxRates) {
    fun tax(price: Int, country: String): Int = price * rates.rateFor(country) / 100
}

class TaskQueue {
    private val tasks = mutableListOf<String>()
    val size: Int get() = tasks.size

    fun add(task: String) {
        tasks.add(task)
    }
}

class Discount(val percent: Int) {
    init {
        require(percent in 0..100) { "Скидка должна быть от 0 до 100: $percent" }
    }

    fun apply(price: Int): Int = price - price * percent / 100
}

fun pricePerKilo(price: Double, kilos: Double): Double = price / kilos

class FragmentsExample : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerRoot

    val rates = mockk<TaxRates>()
    val calculator = TaxCalculator(rates)
    val queue = TaskQueue()
    val task = "отправить письмо"
    val discount = Discount(percent = 10)

    // значение, с заглушкой мока
    shouldReturnTest(20, options = TestOptions(name = "налог по ставке страны")) {
        every { rates.rateFor("RU") } returns 20
        calculator.tax(100, "RU")
    }

    // состояние, которое можно вернуть из блока
    shouldReturnTest(1, options = TestOptions(name = "размер после добавления")) {
        queue.add(task)
        queue.size
    }

    // значение, вход показан в имени теста
    shouldReturnTest(90, 100) { price -> discount.apply(price) }

    // настройки теста
    shouldReturnTest(
        180,
        options = TestOptions(name = "цена со скидкой", config = TestConfig(timeout = 1.seconds)),
    ) {
        discount.apply(200)
    }

    // исключение
    shouldThrowTest<RuntimeException> { Discount(percent = 150) }

    shouldThrowExactlyTest<IllegalArgumentException> { Discount(percent = 150) }

    shouldThrowWithMessageTest<IllegalArgumentException>("Скидка должна быть от 0 до 100: 150") {
        Discount(percent = 150)
    }

    // набор случаев
    withGivens(
        nameFn = { "цена: ${it.arg2}" },
        case(arg1 = 270, arg2 = 300),
        case(arg1 = 360, arg2 = 400),
    ) { expected, price ->
        shouldReturnTest(expected) { discount.apply(price) }
    }

    // общая подготовка
    given("скидка 50%") {
        val half = Discount(percent = 50)
        shouldReturnTest(50) { half.apply(100) }
        shouldReturnTest(0) { half.apply(0) }
    }

    // матчер: в наборе случаев имя даёт nameFn
    withGivens(
        nameFn = { "цена: ${it.arg2}, вес: ${it.arg3}" },
        case(2.5, 5.0, 2.0),
    ) { expected, price, kilos ->
        shouldReturnTest(expected plusOrMinus 1e-12) { pricePerKilo(price, kilos) }
    }

    // матчер в корне спеки: имя задаём сами
    shouldReturnTest(0.333 plusOrMinus 1e-3, options = TestOptions(name = "цена за килограмм")) {
        pricePerKilo(1.0, 3.0)
    }

    // случай вне стандарта
    // Вне стандарта: один набор данных на разные given, см. #NN
    withContexts(1, 2) { n ->
        given("пустой кэш") {
            shouldReturnTest(n) { n }
        }
        given("заполненный кэш") {
            shouldReturnTest(n) { n }
        }
    }
})
