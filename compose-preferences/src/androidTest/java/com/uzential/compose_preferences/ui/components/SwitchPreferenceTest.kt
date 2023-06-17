package com.uzential.compose_preferences.ui.components

import android.content.Context
import android.util.Log
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.core.app.ApplicationProvider
import com.uzential.compose_preferences.data.compose.state
import com.uzential.compose_preferences.data.preferences.BooleanPreference
import com.uzential.compose_preferences.ui.components.preferences.SwitchPreference
import com.uzential.compose_preferences.ui.providers.DataStoreProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineExceptionHandler
import kotlinx.coroutines.test.createTestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class SwitchPreferenceTest {

    private val testContext: Context = ApplicationProvider.getApplicationContext()
    @OptIn(ExperimentalCoroutinesApi::class)
    private val testCoroutineDispatcher: TestCoroutineDispatcher =
        TestCoroutineDispatcher()
    @OptIn(ExperimentalCoroutinesApi::class)
    private val testCoroutineScope =
        createTestCoroutineScope(TestCoroutineDispatcher() + TestCoroutineExceptionHandler() + (testCoroutineDispatcher + Job()))
    private val testDataStore: DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            scope = testCoroutineScope,
            produceFile =
            { testContext.preferencesDataStoreFile("TEST_DATASTORE_NAME") }
        )

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun togglesOnClick() = runBlocking{
        val preference = BooleanPreference("test2")
        composeTestRule.setContent {
            DataStoreProvider {
                SwitchPreference(title = "Title", preference = preference)

                val state = preference.state()
                Log.d(TAG, "state: ${state.value}")
            }
        }
        composeTestRule.onNodeWithText("Title").performClick() // toggles the state
        delay(5000) // don't know how to properly wait yet
    }

    private val TAG = "provider-test"
    @Test
    fun updates(){
        val preference = BooleanPreference("test2")
        testCoroutineScope.runBlockingTest {
            Log.d(TAG, preference.flow(testDataStore).first().toString())
            // preference.edit(testDataStore){ false }
            Log.d(TAG, preference.flow(testDataStore).first().toString())
        }
    }
}