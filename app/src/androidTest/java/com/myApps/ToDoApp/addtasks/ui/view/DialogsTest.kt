package com.myApps.ToDoApp.addtasks.ui.view

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test


class DialogsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun addTaskDialog_isDisplayedWhenShowIsTrue() {
        composeTestRule.setContent {
            AddTaskDialog(
                show = true,
                onDismiss = { },
                onTaskAdd = {}
            )
        }
        composeTestRule.onNodeWithTag("AddDialog").assertIsDisplayed()
        composeTestRule.onNodeWithTag("textField").assertIsDisplayed()
        composeTestRule.onNodeWithTag("ButtonCancel1").assertIsDisplayed()
        composeTestRule.onNodeWithTag("ButtonAdd").assertIsDisplayed()

    }

    @Test
    fun addTaskDialog_isNotExistsWhenShowIsFalse() {
        composeTestRule.setContent {
            AddTaskDialog(
                show = false,
                onDismiss = { },
                onTaskAdd = {}
            )
        }
        composeTestRule.onNodeWithTag("AddDialog").assertDoesNotExist()
    }

    @Test
    fun addButtonIsDisplayed_whenTextFieldIsEmpty() {
        composeTestRule.setContent {
            AddTaskDialog(
                show = true,
                onDismiss = { },
                onTaskAdd = {}
            )
        }
        composeTestRule.onNodeWithTag("ButtonAdd").assertIsNotEnabled()
    }

    @Test
    fun addButtonIsEnabled_WhenTextFieldContainsText() {
        composeTestRule.setContent {
            AddTaskDialog(
                show = true,
                onDismiss = { },
                onTaskAdd = {}
            )
        }
        composeTestRule.onNodeWithTag("textField").performTextInput("Tarea de prueba")
        composeTestRule.onNodeWithTag("ButtonAdd").assertIsEnabled()
    }

    @Test
    fun addButtonClearsTextField() {
        composeTestRule.setContent {
            AddTaskDialog(
                show = true,
                onDismiss = { },
                onTaskAdd = {}
            )
        }
        composeTestRule.onNodeWithTag("textField").performTextInput("Tarea de prueba")
        composeTestRule.onNodeWithTag("ButtonAdd").performClick()
        composeTestRule.onNodeWithTag("textField").assertTextEquals("")
    }

    @Test
    fun cancelButtonCloseAddDialog() {
        var show = true
        composeTestRule.setContent {
            AddTaskDialog(
                show = show,
                onDismiss = { show = false },
                onTaskAdd = {}
            )
        }
        composeTestRule.onNodeWithTag("ButtonCancel1").performClick()
        assert(!show)
    }

    @Test
    fun deleteDialog_isDisplayedWhenShowIsTrue() {
        composeTestRule.setContent {
            DeleteDialog(
                show = true,
                onDismiss = {},
                onDeleteTask = {},
                isVisible = true,
                onVisibilityChange = {},
            )
        }
        composeTestRule.onNodeWithTag("DeleteDialog").assertIsDisplayed()
        composeTestRule.onNodeWithTag("ButtonCancel2").assertIsDisplayed()
        composeTestRule.onNodeWithTag("ButtonDelete").assertIsDisplayed()
    }

    @Test
    fun deleteDialog_isNotExistsWhenShowIsFalse() {
        composeTestRule.setContent {
            DeleteDialog(
                show = false,
                onDismiss = {},
                onDeleteTask = {},
                isVisible = true,
                onVisibilityChange = {},
            )
        }
        composeTestRule.onNodeWithTag("DeleteDialog").assertIsNotDisplayed()
    }

    @Test
    fun cancelButtonCloseDeleteDialog() {
        var show = true
        composeTestRule.setContent {
            DeleteDialog(
                show = show,
                onDismiss = { show = false },
                onDeleteTask = {},
                isVisible = true,
                onVisibilityChange = {},
            )
        }
        composeTestRule.onNodeWithTag("ButtonCancel2").performClick()
        assert(!show)
    }

    @Test
    fun testDeleteButton() {
        var taskDelete = false
        var visibility = false
        var show = false
        composeTestRule.setContent {
            DeleteDialog(
                show = true,
                onDismiss = { show = true },
                onDeleteTask = { taskDelete = true },
                isVisible = true,
                onVisibilityChange = { visibility = it },
            )
        }

        composeTestRule.onNodeWithTag("ButtonDelete").performClick()
        assert(taskDelete)
        assert(show)
        assert(visibility == false)
    }
}