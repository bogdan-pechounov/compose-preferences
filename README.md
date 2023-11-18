# Compose Preferences

## Usage

## Behind the scenes

A `Preference` is simply an interface with a `flow` and `edit` method.

```kotlin
interface Preference<V> {

    fun flow(dataStore: DataStore<Preferences>): Flow<V?>
    suspend fun edit(dataStore: DataStore<Preferences>, transform: (currentValue: V?) -> V?)
    
    val value: V?
        @Composable
        get() {
            if (inPreviewMode) {
                return previewDefaultValue
            }
            return flow(LocalDataStore.current)
                .collectAsStateWithLifecycle(initialValue = null)
                .value
        }

    @Composable
    fun setValue(): (newValue: V?) -> Unit {
        if (inPreviewMode) {
            return {}
        }

        val scope = rememberCoroutineScope()
        val dataStore = LocalDataStore.current
        return { newValue ->
            scope.launch {
                edit(dataStore) {
                    newValue
                }
            }
        }
    }

    @get:Composable
    val previewDefaultValue: V?
}
```

To distinguish between `collectAsStateWithLifecycle(initialValue = null)` and `dataStore.data.map { preferences -> preferences[key] }` returning `null`, there is a `Result` class.

```kotlin
  fun flowResult(dataStore: DataStore<Preferences>) = flow(dataStore).map { Result.Loaded(it) }

    val result: Result<V?>
        @Composable
        get() {
            if (inPreviewMode) {
                return Result.Loaded(previewDefaultValue)
            }
            return flowResult(LocalDataStore.current)
                .collectAsStateWithLifecycle(initialValue = Result.Loading)
                .value
        }
```

When the `Context` is needed to determine the default value, override the `@Composable` functions.

```kotlin
class DarkThemePreference(
    name: String = "dark_theme",
) : SingleKeyPreference<Boolean>(booleanPreferencesKey(name)) {
    override val value: Boolean?
        @Composable
        get() = when (val result = result) {
            is Result.Loaded -> result.value ?: isSystemInDarkTheme()
            Result.Loading -> null
        }

    override val previewDefaultValue: Boolean
        @Composable
        get() = isSystemInDarkTheme()
}
```