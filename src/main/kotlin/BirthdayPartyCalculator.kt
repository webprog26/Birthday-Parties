package main.kotlin

import java.util.Calendar

fun getBirthdayPartyDate(birthdayCalendar: Calendar): Calendar {
    val monthOfBirth = birthdayCalendar.get(Calendar.MONTH)
    val dayOfBirth = birthdayCalendar.get(Calendar.DAY_OF_MONTH)
    var birthdayPartyCalendar =
        getSundayPartyDate(getInitializedCalendarInstance(monthOfBirth, dayOfBirth))

    val birthdayMonth = birthdayCalendar.get(Calendar.MONTH)
    val sundayPartyMonth = birthdayPartyCalendar.get(Calendar.MONTH)

    if (birthdayMonth < sundayPartyMonth) {
        val saturdayPartyDate =
            getSaturdayPartyDate(getInitializedCalendarInstance(monthOfBirth, dayOfBirth))

        val saturdayPartyMonth = saturdayPartyDate.get(Calendar.MONTH)
        if (birthdayMonth == saturdayPartyMonth) {
            birthdayPartyCalendar = saturdayPartyDate
        }
    }
    return birthdayPartyCalendar
}

fun getInitializedCalendarInstance(monthOfBirth: Int, dayOfBirth: Int): Calendar {
    val currentCalendar = Calendar.getInstance()
    currentCalendar.set(Calendar.MONTH, monthOfBirth)
    currentCalendar.set(Calendar.DAY_OF_MONTH, dayOfBirth)
    return currentCalendar
}

fun getSundayPartyDate(birthdayCalendar: Calendar): Calendar {
    return getNextSunday(birthdayCalendar)
}

fun getSaturdayPartyDate(birthdayCalendar: Calendar): Calendar {
    return getNextSaturday(birthdayCalendar)
}


private fun getNextSunday(calendar: Calendar): Calendar {
    return getPartyDate(calendar, Calendar.SUNDAY)
}

private fun getNextSaturday(calendar: Calendar): Calendar {
    return getPartyDate(calendar, Calendar.SATURDAY)
}

private fun getPartyDate(calendar: Calendar, dayPreferred: Int): Calendar {
    var dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

    while (dayOfWeek != dayPreferred) {
        var date = calendar.get(Calendar.DATE)

        var month = calendar.get(Calendar.MONTH)

        val year = calendar.get(Calendar.YEAR)

        if (date == getMonthLastDate(month, year)) {

            if (month == Calendar.DECEMBER) {
                month = Calendar.JANUARY

                calendar.set(Calendar.YEAR, year + 1)
            } else {
                month++
            }

            calendar.set(Calendar.MONTH, month)

            date = 1
        } else {
            date++
        }

        calendar.set(Calendar.DATE, date)

        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    }

    return calendar
}

fun getMonthLastDate(month: Int, year: Int): Int {
    return when (month) {
        Calendar.JANUARY,
        Calendar.MARCH,
        Calendar.MAY,
        Calendar.JULY,
        Calendar.AUGUST,
        Calendar.OCTOBER,
        Calendar.DECEMBER -> 31
        Calendar.APRIL,
        Calendar.JUNE,
        Calendar.SEPTEMBER,
        Calendar.NOVEMBER -> 30
        else -> if (year % 4 == 0) 29 else 28
    }
}