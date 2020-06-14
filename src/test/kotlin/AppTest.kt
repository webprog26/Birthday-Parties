package test.kotlin

import main.kotlin.*

import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals


class AppTest {

    private val inputData = arrayOf("1995-03-29 Alice", "1988-04-01 Bob", "2005-07-06 Carol", "1979-07-10 Dave")
    private val formatter = SimpleDateFormat("yyy-MM-dd")

    @Test
    fun validateInput() {
        for (value in inputData) {
            assert(isValidInput(value))
        }
    }

    @Test
    fun testBirthdaysInTheNextMonth() {
        val monthOfBirth = parseInputToBirthdayData(inputData[3], formatter).birthdayDate.get(Calendar.MONTH)
        assert(hasBirthdayNextMonth(monthOfBirth))
    }

    @Test
    fun testSundayCalculatingCorrect() {
        assert(getSundayPartyDate(getInitializedCalendarInstance(8, 3)).get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
    }

    @Test
    fun testSaturdayCalculatingCorrect() {
        assertEquals(
            getSaturdayPartyDate(getInitializedCalendarInstance(11, 30)).get(Calendar.DAY_OF_WEEK),
            Calendar.SATURDAY
        )
    }

    @Test
    fun testOutputData() {

        val birthdayData = parseInputToBirthdayData(inputData[3], formatter)
        val upcomingMonthBirthdays = arrayListOf(birthdayData, birthdayData, birthdayData, birthdayData)
        for (birthdayData in upcomingMonthBirthdays) {
            birthdayData.birthdayPartyDate = formatter.format(getBirthdayPartyDate(birthdayData.birthdayDate).time)
        }
        println(outputDataToMap(upcomingMonthBirthdays))

        assertFalse { outputDataToMap(upcomingMonthBirthdays).isEmpty() }
    }

    @Test
    fun testMonthLastDateCalculation() {
        assertEquals(31, getMonthLastDate(0, 2020))
        assertNotEquals(31, getMonthLastDate(1, 2020))
    }
}
