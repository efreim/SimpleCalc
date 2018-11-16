package pl.balazinski.jakub.simplecalc

import java.util.regex.Pattern

fun String.isNumber(): Boolean {
    val m = Pattern.compile("-?\\d+(\\.\\d+)?|-?\\.\\d+").matcher(this)
    return m.matches()
}

fun String.provideNegativeNumbers(): String {
    val newExpression = this.replace("(-", "(0-")
    if (newExpression.startsWith("-"))
        return "0$newExpression"
    return newExpression
}

fun String.trimZerosAndComa(): String {
    var newValue = this
    if (newValue.contains(".")) {
        while (newValue.endsWith("0")) {
            newValue = newValue.substring(0, newValue.length - 1)
        }
        if (newValue.endsWith("."))
            newValue = newValue.substring(0, newValue.length - 1)

    }
    return newValue
}
