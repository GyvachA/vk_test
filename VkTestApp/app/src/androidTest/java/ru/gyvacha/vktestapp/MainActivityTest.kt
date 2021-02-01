package ru.gyvacha.vktestapp

import android.content.pm.ActivityInfo
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    companion object {
        const val INPUT_STRING = "My name is Egor!"
        val BIG_INPUT_STRING = (1..250).toList().toString()
    }

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun changeWithNormalText() {

        /*
        * Замена текста в лейбле (текстом из поля ввода)
        * */

        inputAndClick(INPUT_STRING)

        onView(withId(R.id.showChangedTextView))
            .check(matches(withText(INPUT_STRING)))
    }

    @Test
    fun emptyEditTextAfterInput() {

        /*
        * После вставки поле для ввода автоматически очищается
        * */

        inputAndClick(INPUT_STRING)

        onView(withId(R.id.inputEditText))
            .check(matches(withText("")))
    }

    @Test
    fun changeWithZeroText() {

        /*
        * (предварительно в лейбле был заменен текст)
        *
        * Замена текста в лейбле с пустым полем ввода, при этом текст лейбла не должен измениться
        * + появляется всплывающее сообщение, которое сообщает о некорректности ввода
        * */

        inputAndClick(INPUT_STRING)

        onView(withId(R.id.inputEditText))
            .perform(clearText(), closeSoftKeyboard())
        onView(withId(R.id.changeTextButton))
            .perform(click())

        onView(withId(R.id.showChangedTextView))
            .check(matches(withText(INPUT_STRING)))
    }

    @Test
    fun textSavedAfterChangingOrientation() {

        /*
        * При смене ориентации экрана текст лейбла сохраняется
        * */

        inputAndClick(INPUT_STRING)

        activityRule.scenario.onActivity {
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        onView(withId(R.id.showChangedTextView))
            .check(matches(withText(INPUT_STRING)))
    }

    @Test
    fun changeWithUnnecessarySpaces() {

        /*
        * Попытка замены текста лейбла из поля ввода, где существуют ненужные пробелы -
        * в итоге в лейбле должен обработанный текст (без пробелов)
        * */

        inputAndClick("             $INPUT_STRING          ")

        onView(withId(R.id.showChangedTextView))
            .check(matches(withText(INPUT_STRING)))
    }

    @Test
    fun changeWithLargeText() {

        /*
        * Замена текста в лейбле из поля ввода, где находится большой текст,
        * в результате кнопка не должна вылезти за пределы экрана
        * */

        inputAndClick(BIG_INPUT_STRING)

        onView(withId(R.id.changeTextButton))
            .check(matches(isDisplayed()))
    }

    private fun inputAndClick(inputStr: String) {

        /*
        * Вспомогательная функция, которая вводит текст в поле ввода и нажимает на кнопку
        * */

        onView(withId(R.id.inputEditText))
            .perform(typeText(inputStr), closeSoftKeyboard())
        onView(withId(R.id.changeTextButton))
            .perform(click())
    }

}