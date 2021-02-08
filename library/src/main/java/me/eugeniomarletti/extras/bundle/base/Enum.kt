package me.eugeniomarletti.extras.bundle.base

import me.eugeniomarletti.extras.TypeReader
import me.eugeniomarletti.extras.TypeWriter
import me.eugeniomarletti.extras.bundle.BundleExtra
import me.eugeniomarletti.extras.bundle.BundlePropertyDelegate
import me.eugeniomarletti.extras.bundle.Generic

inline fun <reified T : Enum<*>> BundleExtra.Enum(
    name: String? = null,
    customPrefix: String? = null
): BundlePropertyDelegate<T?> =
    Enum({ intToEnum<T>(it) }, { enumToInt(it) }, name, customPrefix)

inline fun <reified T : Enum<*>> BundleExtra.Enum(
    defaultValue: T,
    name: String? = null,
    customPrefix: String? = null
): BundlePropertyDelegate<T> =
    Enum({ intToEnum(it) ?: defaultValue }, { enumToInt(it) }, name, customPrefix)

fun <T> BundleExtra.Enum(
    reader: TypeReader<T, Int?>,
    writer: TypeWriter<T, Int?>,
    name: String? = null,
    customPrefix: String? = null
) =
    Generic(
        { key -> getInt(key, -1).takeUnless { it == -1 } },
        { key, value -> value?.let { putInt(key, it) } },
        reader,
        writer,
        name,
        customPrefix
    )
