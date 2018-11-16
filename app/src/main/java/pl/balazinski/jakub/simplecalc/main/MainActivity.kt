package pl.balazinski.jakub.simplecalc.main

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import pl.balazinski.jakub.simplecalc.R

class MainActivity : Activity(), MainContract.View<MainContract.Presenter> {
    companion object {
        private const val EXPRESSION_KEY = "EXPRESSION_KEY"
        private const val OPEN_BRACKETS_KEY = "OPEN_BRACKETS_KEY"
        private const val CLOSED_BRACKETS_KEY = "CLOSED_BRACKETS_KEY"
    }

    override lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MainPresenter(this)
    }

    private fun getCalculationText(): String {
        return expression_edit_text.text.toString()
    }

    override fun removeLastFromView() {
        expression_edit_text.setText(
            expression_edit_text.text.toString().substring(
                0,
                expression_edit_text.text.length - 1
            )
        )
    }

    override fun loadView() {
        expression_edit_text.addTextChangedListener(MyTextWatcher(this, expression_edit_text))
        expression_edit_text.showSoftInputOnFocus = false

        button_zero.setOnClickListener { presenter.numberClick(getCalculationText(), "0") }
        button_one.setOnClickListener { presenter.numberClick(getCalculationText(), "1") }
        button_two.setOnClickListener { presenter.numberClick(getCalculationText(), "2") }
        button_three.setOnClickListener { presenter.numberClick(getCalculationText(), "3") }
        button_four.setOnClickListener { presenter.numberClick(getCalculationText(), "4") }
        button_five.setOnClickListener { presenter.numberClick(getCalculationText(), "5") }
        button_six.setOnClickListener { presenter.numberClick(getCalculationText(), "6") }
        button_seven.setOnClickListener { presenter.numberClick(getCalculationText(), "7") }
        button_eight.setOnClickListener { presenter.numberClick(getCalculationText(), "8") }
        button_nine.setOnClickListener { presenter.numberClick(getCalculationText(), "9") }
        button_dot.setOnClickListener { presenter.dotClick(getCalculationText()) }

        button_add.setOnClickListener { presenter.addClick(getCalculationText()) }
        button_subtract.setOnClickListener { presenter.subtractClick(getCalculationText()) }
        button_multiply.setOnClickListener { presenter.multiplyClick(getCalculationText()) }
        button_divide.setOnClickListener { presenter.divideClick(getCalculationText()) }
        button_open_bracket.setOnClickListener { presenter.openBracketsClick() }
        button_close_bracket.setOnClickListener { presenter.closeBracketsClick() }
        button_remove_last_char.setOnClickListener { presenter.removeLastCharacterClick(getCalculationText()) }
        button_clear.setOnClickListener { presenter.clearClick() }
        button_equals.setOnClickListener { evaluateExpression(expression_edit_text.text.toString()) }
    }

    override fun updateCalculationView(number: String) {
        expression_edit_text.append(number)
    }

    override fun updateResultView(value: String) {
        result_text_view.text = value
    }

    override fun clearViews() {
        result_text_view.text = ""
        expression_edit_text.text.clear()
        error_image.visibility = View.GONE
    }

    override fun updateBracketsView(open: Int, closed: Int) {
        when {
            open == closed -> {
                open_brackets_info.visibility = View.GONE
                closed_brackets_info.visibility = View.GONE
            }
            open > closed -> {
                open_brackets_info.visibility = View.VISIBLE
                open_brackets_info.text = (open - closed).toString()
                closed_brackets_info.visibility = View.GONE
            }
            else -> {
                open_brackets_info.visibility = View.GONE
                closed_brackets_info.visibility = View.VISIBLE
                closed_brackets_info.text = (closed - open).toString()
            }
        }
    }

    override fun showError(message: Int) {
        result_text_view.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_dark))
        error_image.visibility = View.VISIBLE
        error_image.setOnClickListener { Toast.makeText(this, getString(message), Toast.LENGTH_SHORT).show() }
    }

    override fun hideError() {
        result_text_view.setTextColor(ContextCompat.getColor(this, android.R.color.black))
        error_image.visibility = View.GONE
    }

    class MyTextWatcher(private val activity: MainActivity, private val editText: EditText) : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            editText.setSelection(editText.text.length)
            if (p0 != null)
                activity.evaluateExpression(p0.toString())
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.stopCalculation()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        if (outState != null) {
            if (expression_edit_text.text.isNotEmpty())
                outState.putString(EXPRESSION_KEY, expression_edit_text.text.toString())
            outState.putInt(OPEN_BRACKETS_KEY, presenter.getOpenBracketsCount())
            outState.putInt(CLOSED_BRACKETS_KEY, presenter.getClosedBracketsCount())
        }

        super.onSaveInstanceState(outState)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            expression_edit_text.setText(savedInstanceState.getString(EXPRESSION_KEY, ""))
            val open = savedInstanceState.getInt(OPEN_BRACKETS_KEY, 0)
            val closed = savedInstanceState.getInt(CLOSED_BRACKETS_KEY, 0)
            presenter.setOpenBracketsCount(open)
            presenter.setClosedBracketsCount(closed)
            updateBracketsView(open, closed)
        }
    }

    private fun evaluateExpression(expression: String) {
        presenter.evaluateExpression(expression)
    }

}
