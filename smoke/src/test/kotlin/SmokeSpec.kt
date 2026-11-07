import io.github.nakulov.kotestkit.shouldReturnTest
import io.kotest.core.spec.style.BehaviorSpec

class SmokeSpec : BehaviorSpec({
    shouldReturnTest(2) { 1 + 1 }
})
