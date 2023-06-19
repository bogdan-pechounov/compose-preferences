# Compose Preferences

<img src="https://github-production-user-asset-6210df.s3.amazonaws.com/119967588/246880046-6be45205-d2c9-424d-9a81-5738bb8c717b.jpg" height="500"/>

(in progress)

## Usage

### Step 1 - Provide a DataStore

```kotlin
setContent {
    DataStoreProvider {
        AppTheme {
            // ...
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

### Step 3 - Read the Preference

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

To read the preference from a **ViewModel**, use dependency injection to have access to a `DataStore`.

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.settingsDataStore
    }

}
```

```kotlin
@HiltViewModel
class ExampleViewModel @Inject constructor(dataStore: DataStore<Preferences>) : ViewModel(){
    val settingFlow = SHOW.flowOrDefault(dataStore)
}
```

### Step 4 - Add a PreferenceScreen

```kotlin
PreferenceScreen {
    item {
        SwitchPreference(title = "Show", preference = SHOW)
    }
    // or ...
    header(title ="General")
    switchPreference(title = "Show", description = "Description", icon = {
        Icon(imageVector = Icons.Default.Home, contentDescription = null)
    }, preference = SHOW)
    preferenceItem(title = "Send Feedback", onClick = {})
}
```

### Dark Theme

```kotlin
val DARK_THEME = DarkThemePreference()
```

Pass a value for `darkTheme` in your `AppTheme` manually.

```kotlin
AppTheme(darkTheme = DARK_THEME.stateOrDefault().value) { }
```

The `DarkThemePreference` works by overriding the `defaultValue` and getting a `Configuration` from the context.

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

## Custom colors with dark theme preference

Be careful not to use extension functions as they do not use the saved value.

```kotlin
val ColorScheme.myColor: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) Color.Red else Color.Yellow
```

When using `stateOrDefault()`, the initial value might be different from the saved value, which will cause some flickering. I recommend using a `CompositionLocal` next to the `MaterialTheme`.

```kotlin
data class MyColors(
    val green: Color = MaterialColor.LightGreenA400,
    val red: Color = MaterialColor.DeepOrange400,
    val yellow: Color = MaterialColor.YellowA200
)

val lightMyColors = MyColors(
    green = MaterialColor.LightGreen800,
    red = MaterialColor.Red800,
    yellow = MaterialColor.Yellow800
)

val LocalMyColors = staticCompositionLocalOf { MyColors() }
val MaterialTheme.myColors @Composable get() = LocalMyColors.current
```

```kotlin
    val myColors = if (darkTheme) MyColors() else lightMyColors

    CompositionLocalProvider(LocalMyColors provides myColors) {
        MaterialTheme(
            colorScheme = colorScheme, typography = Typography, content = content
        )
    }
```

Optionally, you can wait a little to get the saved or default value.

```kotlin
@Composable
fun <V> Preference<V>.stateOrDefault() =
    flowOrDefault(LocalDataStore.current, LocalContext.current)
        .collectAsStateWithLifecycle(initialValue = defaultValue())

@Composable
fun <V> Preference<V>.waitForValue() = // nullable
    flowOrDefault(LocalDataStore.current, LocalContext.current)
        .collectAsStateWithLifecycle(initialValue = null).value
```

```kotlin
DataStoreProvider {
    val darkTheme = Settings.DARK_THEME.waitForValue() ?: return@DataStoreProvider
    StopwatchTheme(darkTheme = darkTheme) {
        // ...
    }
}
```

## Download

For now, you can clone the repository and add a module to your project in `settings.gradle`:

```groovy
include ':compose-preferences'
project(':compose-preferences').projectDir = new File('repoPath/compose-preferences')
```

Then, add the module to your app in `build.gradle`:

```groovy
implementation project(path: ':compose-preferences')

// datastore
implementation "androidx.datastore:datastore-preferences:1.0.0"
```

## References

[ComposePrefs3](https://github.com/JamalMulla/ComposePrefs3/tree/master)

[CompositionLocal](https://developer.android.com/jetpack/compose/compositionlocal)

[DataStore](https://developer.android.com/topic/libraries/architecture/datastore)
