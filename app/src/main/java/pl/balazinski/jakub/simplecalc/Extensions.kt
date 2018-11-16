package pl.balazinski.jakub.simplecalc

import android.text.InputType
import android.widget.EditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.regex.Pattern

fun String.isNumber(): Boolean{
    val m = Pattern.compile("-?\\d+(\\.\\d+)?|-?\\.\\d+").matcher(this)
    return m.matches()
}

fun EditText.disableShowingKeyboard() {
    setRawInputType(InputType.TYPE_CLASS_TEXT)
    setTextIsSelectable(true)
}

suspend fun CoroutineScope.launchInIO() = launch(Dispatchers.IO) {
    // Launched in the scope of the caller, but with IO dispatcher
}