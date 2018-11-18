package pl.balazinski.jakub.simplecalc.calculations

import pl.balazinski.jakub.simplecalc.R
import pl.balazinski.jakub.simplecalc.isNumber
import pl.balazinski.jakub.simplecalc.provideNegativeNumbers
import pl.balazinski.jakub.simplecalc.trimZerosAndComa
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList

class Algorithm {

    private val invalidResult = EvaluationResult(Result.ERROR, R.string.expression_invalid, null)
    private val dividingByZeroResult = EvaluationResult(Result.ERROR, R.string.dividing_by_zero, null)

    //Implementation of Shunting-yard_algorithm https://en.wikipedia.org/wiki/Shunting-yard_algorithm (conversion to rpn)
    fun shuntingYard(expression: String): EvaluationResult {
        val modifiedExpression = expression.provideNegativeNumbers()
        val tokens = splitExpressionToTokens(modifiedExpression)

        val operatorStack = Stack<String>()
        val output = ArrayList<String>()

        for (token in tokens) {
            when {
                token.isNumber() -> output.add(token)
                token == "(" -> operatorStack.push(token)
                token == ")" -> {
                    val result: EvaluationResult? = onTokenClosedBracket(operatorStack, output)
                    if (result != null)
                        return result
                }
                else -> {
                    onTokenOperator(operatorStack, output, token)
                }
            }
        }

        while (operatorStack.isNotEmpty())
            output.add(operatorStack.pop())

        return evaluateValue(output)
    }

    private fun splitExpressionToTokens(expression: String): ArrayList<String> {
        val expressionWithNegatives = expression.provideNegativeNumbers()

        val allTokens = ArrayList<String>()
        val m = Pattern.compile("[-+/*()]|-?\\d+(\\.\\d+)?|ó-?\\.\\d+")
            .matcher(expressionWithNegatives)

        while (m.find()) {
            allTokens.add(m.group())
        }

        return allTokens
    }


    private fun onTokenClosedBracket(operatorStack: Stack<String>, output: ArrayList<String>): EvaluationResult? {
        if (operatorStack.isEmpty())
            return invalidResult
        while (operatorStack.peek() != "(") {
            output.add(operatorStack.pop())
            if (operatorStack.isEmpty())
                return invalidResult
        }
        if (operatorStack.isNotEmpty())
            operatorStack.pop()
        return null
    }

    private fun onTokenOperator(operatorStack: Stack<String>, output: ArrayList<String>, token: String) {
        while (operatorStack.isNotEmpty() && (getPriority(operatorStack.peek()) >= getPriority(token)))
            output.add(operatorStack.pop())
        operatorStack.push(token)
    }

    private fun getPriority(operation: String): Int {
        return when {
            operation == "(" -> 0
            (operation == "+") or (operation == "-") -> 1
            else -> 2
        }
    }

    //evaluation of postfix expression
    private fun evaluateValue(output: List<String>): EvaluationResult {

        val resultStack = Stack<String>()
        for (item in output) {
            if (item.isNumber())
                resultStack.push(item)
            else {
                if (resultStack.size >= 2) {
                    val first = BigDecimal(resultStack.pop())
                    val second = BigDecimal(resultStack.pop())
                    when (item) {
                        "-" -> {
                            resultStack.push(second.subtract(first).toString())
                        }
                        "+" -> {
                            resultStack.push(second.add(first).toString())
                        }
                        "/" -> {
                            if (first == BigDecimal(0))
                                return dividingByZeroResult
                            resultStack.push(second.divide(first, 5, RoundingMode.HALF_EVEN).toString())
                        }
                        "*" -> {
                            resultStack.push(second.multiply(first).toString())
                        }
                    }
                } else
                    return invalidResult
            }
        }

        return if (resultStack.empty())
            invalidResult
        else
            EvaluationResult(Result.VALID, null, resultStack[0].trimZerosAndComa())
    }

}