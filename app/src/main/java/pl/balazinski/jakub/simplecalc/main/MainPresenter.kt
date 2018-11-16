package pl.balazinski.jakub.simplecalc.main

import android.util.Log
import kotlinx.coroutines.*
import pl.balazinski.jakub.simplecalc.Algorithm
import kotlin.coroutines.CoroutineContext

class MainPresenter(private val view: MainContract.View<MainContract.Presenter>) : MainContract.Presenter,
    CoroutineScope {

    var job: Job

    init {
        job = Job()
        view.presenter = this
        view.loadView()
    }


    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job

    private val algorithm = Algorithm()
    private var numberOfOpenBrackets = 0
    private var numberOfClosedBrackets = 0

    override fun evaluateExpression(expression: String) {
        job = launch(coroutineContext) {
            Log.d("dupa", "launch")
            val result = async {
                Log.d("dupa", "async")
                algorithm.shuntingYard(expression)
            }.await()

            Log.d("dupa", "updateResultView")
            view.updateResultView(result)
        }

    }


    override fun numberClick(currentText: String, number: String) {
        view.updateCalculationView(number)
    }

    override fun dotClick(currentText: String) {
        if (currentText.isNotEmpty()) {
            if (currentText[currentText.lastIndex].isDigit())
                view.updateCalculationView(".")
        }
    }

    override fun multiplyClick(currentText: String) {
        if (currentText.isNotEmpty()) {
            if (currentText[currentText.lastIndex].isDigit())
                view.updateCalculationView("*")
        }
    }

    override fun divideClick(currentText: String) {
        if (currentText.isNotEmpty()) {
            if (currentText[currentText.lastIndex].isDigit())
                view.updateCalculationView("/")
        }
    }

    override fun addClick(currentText: String) {
        if (currentText.isNotEmpty()) {
            if (currentText[currentText.lastIndex].isDigit())
                view.updateCalculationView("+")
        }
    }

    override fun subtractClick(currentText: String) {
        if (currentText.isNotEmpty()) {
            if (currentText[currentText.lastIndex].isDigit())
                view.updateCalculationView("-")
        }
    }

    override fun clearClick() {
        view.clearViews()
    }

    override fun removeLastCharacterClick(currentText: String) {
        if (currentText.isNotEmpty()) {
            if (currentText[currentText.lastIndex] == '(') {
                numberOfOpenBrackets--
                view.updateBracketsView(numberOfOpenBrackets, numberOfClosedBrackets)
            }
            if (currentText[currentText.lastIndex] == ')') {
                numberOfClosedBrackets--
                view.updateBracketsView(numberOfOpenBrackets, numberOfClosedBrackets)
            }

            if (currentText.length == 1)
                view.clearViews()
            else
                view.removeLastFromView()
        }
    }

    override fun openBracketsClick() {
        numberOfOpenBrackets++
        view.updateBracketsView(numberOfOpenBrackets, numberOfClosedBrackets)
        view.updateCalculationView("(")
    }

    override fun closeBracketsClick() {
        numberOfClosedBrackets++
        view.updateBracketsView(numberOfOpenBrackets, numberOfClosedBrackets)
        view.updateCalculationView(")")

    }

    override fun getOpenBracketsCount(): Int = numberOfOpenBrackets

    override fun getClosedBracketsCount(): Int = numberOfClosedBrackets

    override fun setOpenBracketsCount(count: Int) {
        numberOfOpenBrackets = count
    }

    override fun setClosedBracketsCount(count: Int) {
        numberOfClosedBrackets = count
    }

    override fun stopCalculation() {
        job.cancel()
    }


}