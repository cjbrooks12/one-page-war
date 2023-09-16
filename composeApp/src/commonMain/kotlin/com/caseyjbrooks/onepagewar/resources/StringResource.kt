@file:Suppress("LocalVariableName")

package com.caseyjbrooks.onepagewar.resources

public interface StringResource {
    public operator fun invoke(locale: Locale = Locale.en_us): String

    public companion object {
        public operator fun invoke(en_us: String): StringResource {
            return StringResourceImpl(
                en_us = en_us,
            )
        }
    }
}

private class StringResourceImpl(
    val en_us: String,
) : StringResource {
    override fun invoke(locale: Locale): String {
        return when (locale) {
            Locale.en_us -> en_us
        }
    }
}
