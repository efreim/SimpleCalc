package pl.balazinski.jakub.simplecalc.calculations

data class EvaluationResult(val result: Result, val message: Int?, val value: String?)

enum class Result {
    VALID, ERROR
}

