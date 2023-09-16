package com.caseyjbrooks.onepagewar

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val AppLightColorScheme = lightColorScheme(
    // M3 light Color parameters
)
private val AppDarkColorScheme = darkColorScheme(
    // M3 dark Color parameters
)

@Composable
internal fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    MaterialTheme(
        colorScheme = if (useDarkTheme) AppDarkColorScheme else AppLightColorScheme,
        content = { Surface(content = content) }
    )
}

/*

From that description, a sealed class probably is still appropriate, but rather than setting a property of the environment, give the base class an abstract function which returns the URL. Each environment subclass can then override it however is necessary, whether returning a fixed or dynamic value.

```
// if the custom URL lookup is synchronous/blocking,
// providing a custom `get()` function works just fine
sealed interface Environment {
    abstract val baseUrl: String

    object Prod : Environment {
        override val baseUrl: String = "https://prod.example.com"
    }
    object Staging : Environment {
        override val baseUrl: String = "https://staging.example.com"
    }
    object Dev : Environment {
        override val baseUrl: String = "https://dev.example.com"
    }
    class Custom(private val extraParameter: String) : State {
        override val baseUrl: String
            get() {
                return blockingFetchBaseUrl(extraParameter)
            }
    }
}
```

```
// if the custom URL comes from making an async
// network/DB call (or something like that), it's a
// bit more boilerplate, but still not too bad.
sealed interface Environment {
    abstract suspend fun getBaseUrl(): String

    object Prod : Environment {
        override suspend fun getBaseUrl(): String = "https://prod.example.com"
    }
    object Staging : Environment {
        override suspend fun getBaseUrl(): String = "https://staging.example.com"
    }
    object Dev : Environment {
        override suspend fun getBaseUrl(): String = "https://dev.example.com"
    }
    class Custom(private val extraParameter: String) : State {
        override suspend fun getBaseUrl(): String {
            return suspendingFetchBaseUrl(extraParameter)
        }
    }
}
```


 */
