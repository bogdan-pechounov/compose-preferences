package com.uzential.compose_preferences.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.uzential.compose_preferences.ui.PreferenceItem
import org.junit.Rule
import org.junit.Test

class PreferenceItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysTitle() {
        // Start the app
        composeTestRule.setContent {
            PreferenceItem(title = "Title")
        }

        composeTestRule.onNodeWithText("Title").assertExists()
    }

}