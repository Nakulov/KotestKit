package io.github.nakulov.kotestkit.serialization

import io.github.nakulov.kotestkit.TestOptions
import io.github.nakulov.kotestkit.testing.RanTest
import io.github.nakulov.kotestkit.testing.runBehaviorSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.config.TestConfig
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.string.shouldStartWith
import kotlinx.serialization.SerializationException

private val ANYA = UserDto(1, "Аня", "anya", Role.ADMIN, Profile("Москва"))

class JsonTestScopeSpec : StringSpec({
    "shouldDecodeTest и shouldEncodeTest: каждый вызов регистрирует один успешный тест с именем по умолчанию" {
        val run = runBehaviorSpec {
            withJson {
                shouldDecodeTest("dto/user/full.json", ANYA)
                shouldEncodeTest(ANYA, "dto/user/full.json")
            }
        }

        run.tests.map { it.name } shouldBe listOf("dto/user/full.json -> UserDto", "UserDto -> dto/user/full.json")
        run.tests.map { it.path.size } shouldBe listOf(1, 1)
        run.tests.map { it.result.isSuccess } shouldBe listOf(true, true)
    }

    "shouldEncodeTest: порядок ключей и пробелы в образце не важны" {
        val run = runBehaviorSpec {
            withJson { shouldEncodeTest(ANYA, "dto/user/reordered.json") }
        }

        run.tests.single().result.isSuccess shouldBe true
    }

    "ведущий / в пути ресурса допускается" {
        val run = runBehaviorSpec {
            withJson { shouldDecodeTest("/dto/user/full.json", ANYA) }
        }

        run.tests.single().result.isSuccess shouldBe true
    }

    "DefaultTestJson: новое поле, новое значение enum и отсутствующее nullable-поле читаются" {
        val run = runBehaviorSpec {
            withJson { shouldDecodeTest("dto/user/new_server_fields.json", UserDto(1, "Аня", null, Role.USER)) }
        }

        run.tests.single().result.isSuccess shouldBe true
    }

    "shouldDecodeTest: несовпадение поля падает с именем поля, первым в стеке место вызова" {
        var line = 0
        val run = runBehaviorSpec {
            withJson {
                line = nextLine()
                shouldDecodeTest("dto/user/full.json", ANYA.copy(name = "Оля"))
            }
        }

        val test = run.tests.single()
        test.errorMessage() shouldContain "name: expected:<Оля> but was:<Аня>"
        test.firstFrame() shouldBe "JsonTestScopeSpec.kt:$line"
    }

    "shouldEncodeTest: несовпадение поля падает с путём поля, первым в стеке место вызова" {
        var line = 0
        val run = runBehaviorSpec {
            withJson {
                line = nextLine()
                shouldEncodeTest(ANYA.copy(profile = Profile("Казань")), "dto/user/full.json")
            }
        }

        val test = run.tests.single()
        test.errorMessage() shouldContain "At 'profile.city' expected 'Москва' but was 'Казань'"
        test.firstFrame() shouldBe "JsonTestScopeSpec.kt:$line"
    }

    "shouldDecodeTest: неразбираемый JSON падает с исключением kotlinx и путём поля" {
        val run = runBehaviorSpec {
            withJson { shouldDecodeTest("dto/user/broken.json", ANYA) }
        }

        val error = run.tests.single().result.errorOrNull
        (error is SerializationException) shouldBe true
        error?.message shouldContain "\$.id"
    }

    "отсутствующий ресурс: тест падает с путём ресурса" {
        val run = runBehaviorSpec {
            withJson {
                shouldDecodeTest("dto/missing.json", ANYA)
                shouldEncodeTest(ANYA, "dto/missing.json")
            }
        }

        run.tests.map { it.errorMessage() } shouldBe List(2) { "resource dto/missing.json not found" }
    }

    "options: имя и настройки доходят до Kotest" {
        val run = runBehaviorSpec {
            withJson {
                shouldDecodeTest("dto/user/full.json", ANYA, TestOptions("decode", TestConfig(enabled = false)))
                shouldEncodeTest(ANYA, "dto/user/full.json", TestOptions("encode", TestConfig(enabled = false)))
            }
        }

        run.tests.map { it.name } shouldBe listOf("decode", "encode")
        run.tests.map { it.config?.enabled } shouldBe listOf(false, false)
    }

    "тип без сериализатора: тест падает, спека регистрируется" {
        val run = runBehaviorSpec {
            withJson { shouldDecodeTest("dto/user/full.json", Any()) }
        }

        run.specError shouldBe null
        run.tests.single().errorMessage() shouldStartWith "Serializer for class 'Any' is not found"
    }

    "shouldRoundTripTest: значения по умолчанию, явный null, целое для Float — каждый образец проходит" {
        val run = runBehaviorSpec {
            withJson {
                shouldRoundTripTest<UserDto>("samples/user/defaults_omitted.json")
                shouldRoundTripTest<UserDto>("samples/user/defaults_explicit.json")
                shouldRoundTripTest<UserDto>("samples/user/explicit_null.json")
                shouldRoundTripTest<SizeDto>("samples/size.json")
            }
        }

        run.tests.map { it.name } shouldBe listOf(
            "samples/user/defaults_omitted.json <-> UserDto",
            "samples/user/defaults_explicit.json <-> UserDto",
            "samples/user/explicit_null.json <-> UserDto",
            "samples/size.json <-> SizeDto",
        )
        run.tests.map { it.path.size } shouldBe List(4) { 1 }
        run.tests.map { it.result.isSuccess } shouldBe List(4) { true }
    }

    "shouldRoundTripTest: полиморфный корень через serializersModule и явный сериализатор" {
        val run = runBehaviorSpec {
            withJson(ComponentJson) {
                shouldRoundTripTest<Component>("samples/component/text.json")
                shouldRoundTripTest("samples/point.json", PointAsArray)
            }
        }

        run.tests.map { it.name } shouldBe listOf("samples/component/text.json <-> Component", "samples/point.json <-> Point")
        run.tests.map { it.result.isSuccess } shouldBe listOf(true, true)
    }

    "shouldRoundTripTest: потерянное поле падает с путём, первым в стеке место вызова" {
        var line = 0
        val run = runBehaviorSpec {
            withJson {
                line = nextLine()
                shouldRoundTripTest<UserDto>("samples/user/lost_field.json")
            }
        }

        val test = run.tests.single()
        test.errorMessage() shouldContain "At 'role' expected 'OWNER' but was 'USER'"
        test.firstFrame() shouldBe "JsonTestScopeSpec.kt:$line"
    }

    "shouldRoundTripTest: другой вариант и разная длина массива падают с путём" {
        val run = runBehaviorSpec {
            withJson(ComponentJson) {
                shouldRoundTripTest<Component>("samples/component/video.json")
                shouldRoundTripTest<TagsDto>("samples/tags.json")
            }
        }

        run.tests.map { it.errorMessage() }.let { (variant, length) ->
            variant shouldContain "At 'type' expected 'Video' but was 'Unknown'"
            length shouldContain "At 'tags' expected array length 3 but was 2"
        }
    }

    "shouldRoundTripTest с сериализатором: ошибка разбора — исключение kotlinx, первым в стеке место вызова" {
        var line = 0
        val run = runBehaviorSpec {
            withJson {
                line = nextLine()
                shouldRoundTripTest("dto/user/broken.json", UserDto.serializer())
            }
        }

        val error = run.tests.single().result.errorOrNull
        (error is SerializationException) shouldBe true
        error?.message shouldContain "\$.id"
        run.tests.single().firstFrame() shouldBe "JsonTestScopeSpec.kt:$line"
    }

    "shouldRoundTripTest: нет ресурса — падение с путём, упавший образец не останавливает остальные" {
        val run = runBehaviorSpec {
            withJson {
                shouldRoundTripTest<UserDto>("samples/missing.json")
                shouldRoundTripTest("samples/missing.json", UserDto.serializer())
                shouldRoundTripTest<UserDto>("samples/user/defaults_omitted.json")
            }
        }

        run.tests.map { it.errorMessage() } shouldBe List(2) { "resource samples/missing.json not found" } + null
        run.tests.last().result.isSuccess shouldBe true
    }

    "shouldRoundTripTest: options доходят до Kotest" {
        val run = runBehaviorSpec {
            withJson {
                shouldRoundTripTest<UserDto>("samples/size.json", TestOptions("reified", TestConfig(enabled = false)))
                shouldRoundTripTest("samples/point.json", PointAsArray, TestOptions("explicit", TestConfig(enabled = false)))
            }
        }

        run.tests.map { it.name } shouldBe listOf("reified", "explicit")
        run.tests.map { it.config?.enabled } shouldBe listOf(false, false)
    }
})

/** Номер строки, следующей за вызовом. Кадры: сама функция, её синтетический `access$`, вызвавшая лямбда. */
private fun nextLine(): Int = Throwable().stackTrace[2].lineNumber + 1

private fun RanTest.errorMessage(): String? = result.errorOrNull?.message

private fun RanTest.firstFrame(): String? =
    result.errorOrNull?.stackTrace?.first()?.let { "${it.fileName}:${it.lineNumber}" }
