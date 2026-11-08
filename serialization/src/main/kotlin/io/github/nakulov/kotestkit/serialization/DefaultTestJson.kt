package io.github.nakulov.kotestkit.serialization

import kotlinx.serialization.json.Json

/** Json по умолчанию: каждый флаг — требование совместимости с сервером. */
val DefaultTestJson: Json = Json {
    prettyPrint = true          // читаемый JSON в отчёте о падении
    ignoreUnknownKeys = true    // сервер добавил поле — старый DTO читается
    explicitNulls = false       // сервер не прислал необязательное поле — null
    encodeDefaults = true       // в запрос идут все поля, без надежды на значения по умолчанию сервера
    coerceInputValues = true    // новое значение enum или null — значение по умолчанию поля
}
