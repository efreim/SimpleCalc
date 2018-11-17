package pl.balazinski.jakub.simplecalc.main

interface MainContract {
    interface View {
        var presenter: Presenter

        fun loadView()
        fun updateCalculationView(number: String)
        fun updateResultView(value: String)
        fun clearViews()
        fun updateBracketsView(open: Int, closed: Int)
        fun showError(message: Int)
        fun hideError()
        fun removeLastFromView()
    }

    interface Presenter {
        fun numberClick(number: String)
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
