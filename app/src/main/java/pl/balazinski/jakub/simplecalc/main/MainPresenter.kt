package pl.balazinski.jakub.simplecalc.main

import kotlinx.coroutines.*
import pl.balazinski.jakub.simplecalc.calculations.Algorithm
import pl.balazinski.jakub.simplecalc.calculations.EvaluationResult
import pl.balazinski.jakub.simplecalc.calculations.Result
import kotlin.coroutines.CoroutineContext

class MainPresenter(private val view: MainContract.View<MainContract.Presenter>) : MainContract.Presenter,
    CoroutineScope {

    private val job = Job()

    lateinit var deferred: Deferred<String>

    init {
        view.presenter = this
        view.loadView()
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    private val algorithm = Algorithm()
    private var numberOfOpenBrackets = 0
    private var numberOfClosedBrackets = 0

    override fun evaluateExpression(expression: String) {
        if (expression.isNotEmpty()) {
            launch(coroutineContext) {
                val evaluationResult: EvaluationResult = async(Dispatchers.IO) {
                    algorithm.shuntingYard(expression)
                }.await()

                if (evaluationResult.result == Result.VALID) {
                    view.updateResultView(evaluationResult.value!!)
                    view.hideError()
                } else
                    view.showError(evaluationResult.message!!)
            }
        }
    }


    override fun numberClick(currentText: String, number: String) {
        view.updateCalculationView(number)
    }

    override fun dotClick(currentText: String) {
        if (currentText.isNotEmpty()) {
            if (currentText[currentText.lastIndex].isDigit()) {
                for (i in currentText.length - 1 downTo 0) {
                    if (!currentText[i].isDigit() and (currentText[i] != '.')) {
                        break
                    }
                    if (currentText[i] == '.')
                        return
                }
                view.updateCalculationView(".")
            }
        }
    }

    override fun multiplyClick(currentText: String) {
        if (currentText.isNotEmpty()) {
            if (currentText[currentText.lastIndex].isDigit() or currentText.endsWith(")"))
                view.updateCalculationView("*")
        }
    }

    override fun divideClick(currentText: String) {
        if (currentText.isNotEmpty()) {
            if (currentText[currentText.lastIndex].isDigit() or currentText.endsWith(")"))
                view.updateCalculationView("/")
        }
    }

    override fun addClick(currentText: String) {
        if (currentText.isNotEmpty()) {
            if (currentText[currentText.lastIndex].isDigit() or currentText.endsWith(")"))
                view.updateCalculationView("+")
        }
    }

    override fun subtractClick(currentText: String) {
        if (currentText.isNotEmpty()) {
            if (currentText[currentText.lastIndex].isDigit() or currentText.endsWith(")") or currentText.endsWith("("))
                view.updateCalculationView("-")
        } else
            view.updateCalculationView("-")
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