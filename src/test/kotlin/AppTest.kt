package test.kotlin

import main.kotlin.*

import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


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
        assertEquals(getSundayPartyDate(getInitializedCalendarInstance(30, 3)).get(Calendar.DAY_OF_WEEK),  Calendar.SUNDAY)
    }

    @Test
    fun testOutputData() {
        assertNotNull(outputDataToMap(listOf(parseInputToBirthdayData(inputData[3], formatter))))
    }
}
