package io.github.nakulov.kotestkit.readme

import io.github.nakulov.kotestkit.serialization.DefaultTestJson
import io.github.nakulov.kotestkit.serialization.withJson
import io.kotest.core.spec.style.BehaviorSpec
import java.math.BigDecimal
import kotlinx.serialization.Contextual
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule

@Serializable
data class UserDto(val id: Long, val name: String, val nickname: String?)

class UserDtoTest : BehaviorSpec({
    withJson {
        shouldDecodeTest("dto/user/full.json", UserDto(1, "Аня", "anya"))
        shouldDecodeTest("dto/user/without_nickname.json", UserDto(1, "Аня", null))
        shouldEncodeTest(UserDto(1, "Аня", "anya"), "dto/user/full.json")
    }
})

class UserSamplesTest : BehaviorSpec({
    withJson {
        shouldRoundTripTest<UserDto>("dto/user/full.json")
        shouldRoundTripTest<UserDto>("dto/user/without_nickname.json")
    }
})

@Serializable
data class PaymentDto(val id: Long, @Contextual val amount: BigDecimal)

object BigDecimalSerializer : KSerializer<BigDecimal> {
    override val descriptor = PrimitiveSerialDescriptor("BigDecimal", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: BigDecimal) = encoder.encodeString(value.toPlainString())
    override fun deserialize(decoder: Decoder): BigDecimal = BigDecimal(decoder.decodeString())
}

val appModule = SerializersModule { contextual(BigDecimal::class, BigDecimalSerializer) }

class PaymentDtoTest : BehaviorSpec({
    withJson(Json(DefaultTestJson) { serializersModule = appModule }) {
        shouldDecodeTest("dto/payment/card.json", PaymentDto(7, BigDecimal("199.90")))
        shouldEncodeTest(PaymentDto(7, BigDecimal("199.90")), "dto/payment/card.json")
    }
})
