package pl.balazinski.jakub.simplecalc.calculations

import pl.balazinski.jakub.simplecalc.R
import pl.balazinski.jakub.simplecalc.isNumber
import pl.balazinski.jakub.simplecalc.provideNegativeNumbers
import pl.balazinski.jakub.simplecalc.trimZerosAndComa
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*
import java.util.regex.Pattern

class Algorithm {

    //Implementation of Shunting-yard_algorithm https://en.wikipedia.org/wiki/Shunting-yard_algorithm (conversion to rpn)
    fun shuntingYard(expression: String): EvaluationResult {
        val expressionWithNegatives = expression.provideNegativeNumbers()

        val allTokens = ArrayList<String>()
        val m = Pattern.compile("[-+/*()]|-?\\d+(\\.\\d+)?|ó-?\\.\\d+")
            .matcher(expressionWithNegatives)

        while (m.find()) {
            allTokens.add(m.group())
        }

        val operationsMap = HashMap<String, Int>()
        operationsMap["-"] = 1
        operationsMap["+"] = 1
        operationsMap["*"] = 2
        operationsMap["/"] = 2

        val operatorStack = Stack<String>()
        val outputStack = Stack<String>()

        for (token in allTokens) {
            if (token.isNumber()) {
                outputStack.push(token)
            } else if (operationsMap.containsKey(token)) {

                while (operatorStack.size > 0) {
                    val prev = operatorStack.peek()
                    var i: Int? = operationsMap[prev]
                    if (i == null)
                        i = -2

                    if (operationsMap.containsKey(token)) {
                        if (prev == "(")
                            break
                        else if (operationsMap[token]!! <= i)
                            outputStack.push(operatorStack.pop())
                        else break
                    }
                }
                operatorStack.push(token)

            } else if (token == "(") {
                operatorStack.push("(")
            } else if (token == ")") {
                //todo tutaj wywala błąd
                if (operatorStack.isEmpty())
                    return EvaluationResult(Result.ERROR, R.string.expression_invalid, null)
                while (operatorStack.peek() != "(") {
                    outputStack.push(operatorStack.pop())
                }
                operatorStack.pop()

            }
        }
        while (!operatorStack.isEmpty())
            outputStack.push(operatorStack.pop())

        return evaluateValue(outputStack.toList())
    }

    //evaluation of postfix expression
    private fun evaluateValue(outputStack: List<String>): EvaluationResult {

        val resultStack = Stack<String>()
        for (item in outputStack) {
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
                                return EvaluationResult(
                                    Result.ERROR,
                                    R.string.dividing_by_zero,
                                    null
                                )
                            resultStack.push(second.divide(first, 5, RoundingMode.HALF_EVEN).toString())
                        }
                        "*" -> {
                            resultStack.push(second.multiply(first).toString())
                        }
                    }
                } else
                    return EvaluationResult(
                        Result.ERROR,
                        R.string.expression_not_complete,
                        null
                    )
            }
        }

        return if (resultStack.empty())
            EvaluationResult(Result.ERROR, R.string.incomplete_expression, null)
        else
            EvaluationResult(Result.VALID, null, resultStack[0].trimZerosAndComa())
    }

}