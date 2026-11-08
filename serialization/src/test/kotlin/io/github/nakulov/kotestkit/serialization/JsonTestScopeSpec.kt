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
})

/** Номер строки, следующей за вызовом. Кадры: сама функция, её синтетический `access$`, вызвавшая лямбда. */
private fun nextLine(): Int = Throwable().stackTrace[2].lineNumber + 1

private fun RanTest.errorMessage(): String? = result.errorOrNull?.message

private fun RanTest.firstFrame(): String? =
    result.errorOrNull?.stackTrace?.first()?.let { "${it.fileName}:${it.lineNumber}" }
