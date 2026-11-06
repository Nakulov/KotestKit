package io.github.nakulov.kotestkit.testing

import io.kotest.core.spec.style.StringSpec
import io.kotest.engine.test.TestResult
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class RunSpecSpec : StringSpec({
    "спека без тестов даёт пустой отчёт без ошибки" {
        val run = runStringSpec { }

        run.tests.shouldBeEmpty()
        run.specError.shouldBeNull()
    }

    "успешный тест попадает в отчёт с именем" {
        val run = runStringSpec { "t" { } }

        val test = run.tests.single()
        test.name shouldBe "t"
        test.path shouldBe listOf("t")
        test.result.isSuccess shouldBe true
    }

    "упавший тест виден в отчёте и не роняет внешний" {
        val run = runStringSpec {
            "t" { throw AssertionError("boom") }
        }

        val result = run.tests.single().result
        result.isFailure shouldBe true
        result.errorOrNull?.message shouldBe "boom"
    }

    "отключённый тест приходит как Ignored с настройками" {
        val run = runStringSpec {
            "t".config(enabled = false) { }
        }

        val test = run.tests.single()
        test.result.shouldBeInstanceOf<TestResult.Ignored>()
        test.config?.enabled shouldBe false
    }

    "дерево Given/When/Then: полные пути, родители остаются Success при упавшем Then" {
        val run = runBehaviorSpec {
            given("g") {
                `when`("w") {
                    then("t") { throw AssertionError("boom") }
                }
            }
        }

        run.tests.map { it.path } shouldBe listOf(
            listOf("g", "w", "t"),
            listOf("g", "w"),
            listOf("g"),
        )
        run.tests.map { it.result.isSuccess } shouldBe listOf(false, true, true)
    }

    "исключение в теле спеки: тестов нет, ошибка в specError" {
        val run = runStringSpec { error("boom") }

        run.tests.shouldBeEmpty()
        run.specError?.message shouldBe "boom"
    }
})
