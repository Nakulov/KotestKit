package io.github.nakulov.kotestkit.serialization

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

@Serializable
data class UserDto(val id: Int, val name: String, val nickname: String?, val role: Role = Role.USER, val profile: Profile? = null)

@Serializable
data class Profile(val city: String)

@Serializable
enum class Role { USER, ADMIN }

@Serializable
data class SizeDto(val width: Float)

@Serializable
data class TagsDto(val tags: Set<String>)

interface Component

@Serializable
@SerialName("Text")
data class Text(val text: String, val bold: Boolean = false) : Component

@Serializable
@SerialName("Unknown")
data object UnknownComponent : Component

/** Полиморфный корень через модуль; незнакомый `type` читается как [UnknownComponent]. */
val ComponentJson: Json = Json(from = DefaultTestJson) {
    serializersModule = SerializersModule {
        polymorphic(Component::class) {
            subclass(Text::class)
            subclass(UnknownComponent::class)
            defaultDeserializer { UnknownComponent.serializer() }
        }
    }
}

/** Класс без `@Serializable`: правило записи — только явный сериализатор. */
data class Point(val x: Int, val y: Int)

/** `Point(1, 2)` <-> `[1, 2]`. */
object PointAsArray : KSerializer<Point> {
    private val list = ListSerializer(Int.serializer())

    @OptIn(ExperimentalSerializationApi::class)
    override val descriptor: SerialDescriptor = SerialDescriptor("Point", list.descriptor)

    override fun serialize(encoder: Encoder, value: Point) = list.serialize(encoder, listOf(value.x, value.y))

    override fun deserialize(decoder: Decoder): Point = list.deserialize(decoder).let { (x, y) -> Point(x, y) }
}
