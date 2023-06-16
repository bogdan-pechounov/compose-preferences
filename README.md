# Compose Preferences

(in progress)

## Usage

### Step 1 - Provide a DataStore

```kotlin
setContent {
    DataStoreProvider {
        Theme {
            ...
        }
    }
}
```

The `DataStoreProvider` is necessary to read values from anywhere. By default, it uses `preferencesDataStore(name = "settings")`.

### Step 2 - Define Preferences

```kotlin
val SHOW = BooleanPreference("show") // defaultValue = false
```

A `Preference` simply contains a `Preferences.Key<V>` and a default value.

### Step 3 - Read Preference

```kotlin
@Composable
fun Example() {
    val show by SHOW.stateOrDefault()

    if (show) Text(text = "Shown")
    else Text(text = "Not shown")
}
```

Behind the scenes, a `flow` is created with `LocalDataStore.current`:

```kotlin
fun value(preferences: Preferences) = preferences[key]
fun flow(dataStore: DataStore<Preferences>) = dataStore.data.map { value(it) }.distinctUntilChanged()
```

### Step 4 - Add a Preferences Screen

```kotlin
PreferenceScreen {
    item {
        SwitchPreference(title = "Show", preference = SHOW)
    }
}
```

### Dark Theme

```kotlin
val DARK_THEME = DarkThemePreference()
```

In your theme replace `darkTheme: Boolean = isSystemInDarkTheme()` with:

```kotlin
darkTheme: Boolean = DARK_THEME.stateOrDefault().value,
```

The `DarkThemePreference` works by overriding the `defaultValue` and getting a value from the context.

```kotlin
class DarkThemePreference(keyName: String = "dark_theme") : BooleanPreference(keyName) {
    override val defaultValue: Context.() -> Boolean
        get() = {
            isSystemInDarkTheme(resources.configuration)
        }
}

fun isSystemInDarkTheme(configuration: Configuration): Boolean {
    return (configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
}
```

## References

[ComposePrefs3](https://github.com/JamalMulla/ComposePrefs3/tree/master)

[CompositionLocal](https://developer.android.com/jetpack/compose/compositionlocal)

[DataStore](https://developer.android.com/topic/libraries/architecture/datastore)
