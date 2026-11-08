package io.github.nakulov.kotestkit.serialization

import io.github.nakulov.kotestkit.testing.runBehaviorSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import kotlinx.serialization.json.Json

class WithJsonSpec : StringSpec({
    "withJson сам тест не создаёт: тесты группы регистрируются в корне спеки" {
        val run = runBehaviorSpec {
            withJson { }
            withJson { shouldDecodeTest("dto/user/new_server_fields.json", UserDto(1, "Аня", null)) }
        }

        run.tests.single().path shouldBe listOf("dto/user/new_server_fields.json -> UserDto")
    }

    "withJson(json): тесты группы используют переданный Json" {
        val run = runBehaviorSpec {
            withJson(Json) { shouldDecodeTest("dto/user/new_server_fields.json", UserDto(1, "Аня", null)) }
        }

        run.tests.single().result.errorOrNull?.message shouldContain "does not contain element with name 'OWNER' at path $.role"
    }

    "Json(DefaultTestJson) { ... } сохраняет флаги по умолчанию" {
        val run = runBehaviorSpec {
            withJson(Json(DefaultTestJson) { }) {
                shouldDecodeTest("dto/user/new_server_fields.json", UserDto(1, "Аня", null))
            }
        }

        run.tests.single().result.isSuccess shouldBe true
    }
})
