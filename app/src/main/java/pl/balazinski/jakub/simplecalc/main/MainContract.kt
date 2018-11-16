package pl.balazinski.jakub.simplecalc.main

interface MainContract {
    interface View<T> {
        var presenter: T

        fun loadView()
        fun updateCalculationView(number: String)
        fun updateResultView(result: String)
        fun clearViews()
        fun updateBracketsView(open: Int, closed: Int)
        fun showError()
        fun removeLastFromView()
    }

    interface Presenter {
        fun numberClick(currentText: String, number: String)
        fun dotClick(currentText: String)
        fun multiplyClick(currentText: String)
        fun divideClick(currentText: String)
        fun addClick(currentText: String)
        fun subtractClick(currentText: String)
        fun clearClick()
        fun removeLastCharacterClick(currentText: String)
        fun openBracketsClick()
        fun closeBracketsClick()
        fun evaluateExpression(expression: String)
        fun stopCalculation()
        fun getOpenBracketsCount(): Int
        fun getClosedBracketsCount(): Int
        fun setOpenBracketsCount(count: Int)
        fun setClosedBracketsCount(count: Int)
    }
}
