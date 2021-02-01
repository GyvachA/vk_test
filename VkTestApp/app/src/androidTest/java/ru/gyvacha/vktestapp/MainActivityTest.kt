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
        * Замена текста в лейбле текстом из поля ввода
        * */

        inputAndClick(INPUT_STRING)

        onView(withId(R.id.showChangedTextView))
            .check(matches(withText(INPUT_STRING)))
    }

    @Test
    fun emptyEditTextAfterInput() {

        /*
        * Очищение поля ввода после вставки
        * */

        inputAndClick(INPUT_STRING)

        onView(withId(R.id.inputEditText))
            .check(matches(withText("")))
    }

    @Test
    fun changeWithZeroText() {

        /*
        * Вставка пустого текста
        * После вставки текст лейбла не изменится
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
        * Смена ориентации экрана
        * После смены ориентации экрана текст лейбла не изменится
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
        * Вставка текста с пробелами в начале и в конце
        * При вставке удалятся ненужные пробелы
        * */

        inputAndClick("             $INPUT_STRING          ")

        onView(withId(R.id.showChangedTextView))
            .check(matches(withText(INPUT_STRING)))
    }

    @Test
    fun changeWithLargeText() {

        /*
        * Вставка текста с большим количеством символов в лейбл
        * После вставки кнопка отображается корректно
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