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
                    proceedWithDataHandling(inputDataList)
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

fun proceedWithDataHandling(inputData: List<String>) {
    val formatter = SimpleDateFormat(DATE_FORMAT_PATTERN)

    val nextMonthBithdays =
        inputData.map { parseInputToBirthdayData(it, formatter) }
            .filter { hasBirthdayNextMonth(it.birthdayDate.get(Calendar.MONTH)) }

    if (!nextMonthBithdays.isEmpty()) {
        for (bd in nextMonthBithdays) {
            bd.birthdayPartyDate = formatter.format(getBirthdayPartyDate(bd.birthdayDate).time)
        }

        printOutputData(outputDataToMap(nextMonthBithdays))
    } else {
        println("No birthdays in upcoming month found")
    }
    System.exit(0)
}

private fun isValidInput(input: String): Boolean {
    val inputValidatorPattern = "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))\\s[A-Z][a-z]*".toRegex()
    return input.matches(inputValidatorPattern)
}

private fun parseInputToBirthdayData(input: String, formatter: SimpleDateFormat): BirthdayData {
    val dividerIndex = input.indexOf(' ')
    val birthdayDate = Calendar.getInstance()
    birthdayDate.time = formatter.parse(input.substring(0, dividerIndex))
    val personName = input.substring(dividerIndex + 1)
    return BirthdayData(birthdayDate, personName)
}

private fun hasBirthdayNextMonth(birthdayMonth: Int): Boolean {
    val calendar: Calendar = Calendar.getInstance()
    calendar.time = Date(System.currentTimeMillis())
    val currentMonth = calendar.get(Calendar.MONTH)
    return birthdayMonth - currentMonth == 1
}

private fun outputDataToMap(outputDataList: List<BirthdayData>): Map<String, StringBuilder> {
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
                    if (personsNames.isEmpty()) {
                        birthdayData.personName
                    } else {
                        ", ${birthdayData.personName}"
                    }
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