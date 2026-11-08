package io.github.nakulov.kotestkit.serialization

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(val id: Int, val name: String, val nickname: String?, val role: Role = Role.USER, val profile: Profile? = null)

@Serializable
data class Profile(val city: String)

@Serializable
enum class Role { USER, ADMIN }
