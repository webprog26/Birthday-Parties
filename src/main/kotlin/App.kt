package main.kotlin

import java.text.SimpleDateFormat
import java.util.*

const val DATE_FORMAT_PATTERN = "yyyy-MM-dd"

fun main() {

    val inputDataList = mutableListOf<String>()

    with(Scanner(System.`in`)) {
        var readString = nextLine()
        while (readString != null) {
            if (isValidInput(readString)) {
                inputDataList.add(readString)
            }

            if (readString.isEmpty()) {
                if (!inputDataList.isEmpty()) {
                    proceedWithDataHandling(inputDataList) {
                        System.exit(0)
                    }
                } else {
                    println("No valid input!")
                    System.exit(0)
                }
            }

            readString = if (hasNextLine()) {
                nextLine()
            } else {
                null
            }
        }
    }
}

fun proceedWithDataHandling(inputData: List<String>, onFinishedProcessing: () -> Unit) {
    val formatter = SimpleDateFormat(DATE_FORMAT_PATTERN)

    val upcomingMonthBirthdays =
        inputData.map { parseInputToBirthdayData(it, formatter) }
            .filter { hasBirthdayNextMonth(it.birthdayDate.get(Calendar.MONTH)) }

    if (!upcomingMonthBirthdays.isEmpty()) {
        for (birthdayData in upcomingMonthBirthdays) {
            birthdayData.birthdayPartyDate = formatter.format(getBirthdayPartyDate(birthdayData.birthdayDate).time)
        }

        printOutputData(outputDataToMap(upcomingMonthBirthdays))
    } else {
        println("No birthdays in upcoming month found")
    }
    onFinishedProcessing()
}

fun isValidInput(input: String): Boolean {
    val inputValidatorPattern = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))\\s[A-Z][a-z]*".toRegex()
    return input.matches(inputValidatorPattern)
}

fun parseInputToBirthdayData(input: String, formatter: SimpleDateFormat): BirthdayData {
    val dividerIndex = input.indexOf(' ')
    val birthdayDate = Calendar.getInstance()
    birthdayDate.time = formatter.parse(input.substring(0, dividerIndex))
    val personName = input.substring(dividerIndex + 1)
    return BirthdayData(birthdayDate, personName)
}

fun hasBirthdayNextMonth(birthdayMonth: Int): Boolean {
    val calendar: Calendar = Calendar.getInstance()
    calendar.time = Date(System.currentTimeMillis())
    val currentMonth = calendar.get(Calendar.MONTH)
    return birthdayMonth - currentMonth == 1
}

fun outputDataToMap(outputDataList: List<BirthdayData>): Map<String, StringBuilder> {
    val dataMap = hashMapOf<String, StringBuilder>()
    for (birthdayData in outputDataList) {
        val birthdayPartyDate = birthdayData.birthdayPartyDate
        birthdayPartyDate?.let {
            if (dataMap.containsKey(it)) {
                val personsNames: StringBuilder? = dataMap[it]
                personsNames?.append(
                    if (personsNames.isEmpty()) {
                        birthdayData.personName
                    } else {
                        ", ${birthdayData.personName}"
                    }
                )
            } else {
                val personsNames = StringBuilder()
                personsNames.append(
                    birthdayData.personName

                )
                dataMap.put(it, personsNames)
            }
        }
    }
    return dataMap
}

private fun printOutputData(outputDataMap: Map<String, StringBuilder?>) {
    for (key in outputDataMap.keys) {
        val personsNames = outputDataMap[key]
        personsNames?.let {
            println("$key $personsNames")
        }
    }
}