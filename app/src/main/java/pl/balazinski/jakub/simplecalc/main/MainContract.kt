package pl.balazinski.jakub.simplecalc.main

interface MainContract {
    interface View {
        fun loadView()
        fun updateCalculationView()
        fun updateResultView()
        fun clearViews()
        fun updateOpenBracketsView()
        fun updateCloseBracketsView()
    }

    interface Presenter {
        fun numberClick()
        fun dotClick()
        fun multiplyClick()
        fun divideClick()
        fun addClick()
        fun subtractClick()
        fun clearClick()
        fun removeLastCharacterClick()
        fun openBracketsClick()
        fun closeBracketsClick()
    }
}
