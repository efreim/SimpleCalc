package pl.balazinski.jakub.simplecalc

import java.math.BigDecimal
import java.util.*
import java.util.regex.Pattern

class Algorithm {

    //Implementation of Shunting-yard_algorithm https://en.wikipedia.org/wiki/Shunting-yard_algorithm (conversion to rpn)
    suspend fun shuntingYard(expression: String): String {
        val allTokens = ArrayList<String>()
        val m = Pattern.compile("[-+/*()]|-?\\d+(\\.\\d+)?|-?\\.\\d+")
            .matcher(expression)

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
    private suspend fun evaluateValue(outputStack: List<String>): String {

        val resultStack = Stack<String>()
        for (item in outputStack) {
            if (item.isNumber())
                resultStack.push(item)
            else {
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
                        resultStack.push(second.divide(first).toString())
                    }
                    "*" -> {
                        resultStack.push(second.multiply(first).toString())
                    }
                }
            }
        }

        return resultStack[0]
    }
}