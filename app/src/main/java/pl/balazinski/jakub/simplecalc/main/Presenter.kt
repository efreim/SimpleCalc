package pl.balazinski.jakub.simplecalc.main

import pl.balazinski.jakub.simplecalc.isNumber
import java.math.BigDecimal
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList

class Presenter {
    
    fun evaluateExpression(expression: String) {
        shuntingYard(expression)
    }

    fun shuntingYard(expression: String): List<String> {
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

        val stack = Stack<String>()
        val output = Stack<String>()

        for (s in allTokens) {
            if (s.isNumber()) {
                output.push(s)
            } else if (operationsMap.containsKey(s)) {


                while (stack.size > 0) {
                    val prev = stack.peek()
                    var i: Int? = operationsMap[prev]
                    if (i == null)
                        i = -2

                    if (operationsMap.containsKey(s)) {
                        if (prev == "(")
                            break
                        else if (operationsMap[s]!! <= i)
                            output.push(stack.pop())
                        else break
                    }
                }
                stack.push(s)

            } else if (s.equals("(")) {
                stack.push("(")
            } else if (s.equals(")")) {
                while (stack.peek() != "(") {
                    output.push(stack.pop())
                }
                stack.pop()
            }
        }
        while (!stack.isEmpty())
            output.push(stack.pop())

        return output.toList()

    }

    fun evaluateValue(outputStack: List<String>): String {

        val resultStack = Stack<String>()
        for (s in outputStack) {
            if (s.isNumber())
                resultStack.push(s)
            else {
                when (s) {
                    "-" -> {
                        val first = BigDecimal(resultStack.pop())
                        val second = BigDecimal(resultStack.pop())
                        resultStack.push(second.subtract(first).toString())
                    }
                    "+" -> {
                        val first = BigDecimal(resultStack.pop())
                        val second = BigDecimal(resultStack.pop())
                        resultStack.push(second.add(first).toString())
                    }
                    "/" -> {
                        val first = BigDecimal(resultStack.pop())
                        val second = BigDecimal(resultStack.pop())
                        resultStack.push(second.divide(first).toString())
                    }
                    "*" -> {
                        val first = BigDecimal(resultStack.pop())
                        val second = BigDecimal(resultStack.pop())
                        resultStack.push(second.multiply(first).toString())
                    }
                }
            }
        }

        return resultStack[0]
    }
}