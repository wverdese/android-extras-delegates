package me.eugeniomarletti.extras.intent.base

import me.eugeniomarletti.extras.TypeReader
import me.eugeniomarletti.extras.TypeWriter
import me.eugeniomarletti.extras.intent.IntentExtra
import me.eugeniomarletti.extras.intent.IntentPropertyDelegate
import me.eugeniomarletti.extras.intent.Generic

inline fun <reified T : Enum<*>> IntentExtra.Enum(
    name: String? = null,
    customPrefix: String? = null
): IntentPropertyDelegate<T?> =
    Enum({ intToEnum<T>(it) }, { enumToInt(it) }, name, customPrefix)

inline fun <reified T : Enum<*>> IntentExtra.Enum(
    defaultValue: T,
    name: String? = null,
    customPrefix: String? = null
): IntentPropertyDelegate<T> =
    Enum({ intToEnum(it) ?: defaultValue }, { enumToInt(it) }, name, customPrefix)

fun <T> IntentExtra.Enum(
    reader: TypeReader<T, Int?>,
    writer: TypeWriter<T, Int?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        { key -> getIntExtra(key, -1).takeUnless { it == -1 } },
        { key, value -> value?.let { putExtra(key, it) } },
        reader,
        writer,
        name,
        customPrefix
    )
