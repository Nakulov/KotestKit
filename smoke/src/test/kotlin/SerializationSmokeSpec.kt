import io.github.nakulov.kotestkit.serialization.withJson
import io.kotest.core.spec.style.BehaviorSpec
import kotlinx.serialization.Serializable

@Serializable
data class SmokeUser(val id: Int, val name: String)

class SerializationSmokeSpec : BehaviorSpec({
    withJson {
        shouldDecodeTest("user.json", SmokeUser(1, "Аня"))
    }
})
