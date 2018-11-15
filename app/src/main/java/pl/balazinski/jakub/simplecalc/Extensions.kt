package pl.balazinski.jakub.simplecalc

import java.util.regex.Pattern

fun String.isNumber(): Boolean{
    val m = Pattern.compile("-?\\d+(\\.\\d+)?|-?\\.\\d+").matcher(this)
    return m.matches()
}