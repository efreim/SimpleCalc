package pl.balazinski.jakub.simplecalc

import org.junit.Assert.assertEquals
import org.junit.Test
import pl.balazinski.jakub.simplecalc.calculations.Algorithm
import pl.balazinski.jakub.simplecalc.calculations.EvaluationResult
import pl.balazinski.jakub.simplecalc.calculations.Result

class AlgorithmUnitTest {

    private val algorithm = Algorithm()
    private val notCompleted = EvaluationResult(Result.ERROR, R.string.expression_not_complete, null)
    private val invalid = EvaluationResult(Result.ERROR, R.string.expression_invalid, null)
    private val dividingByZero = EvaluationResult(Result.ERROR, R.string.dividing_by_zero, null)

    @Test
    fun test1() {
        val expected = EvaluationResult(Result.VALID, null, "3")
        assertEquals(algorithm.shuntingYard("1+2"), expected)
    }

    @Test
    fun test2() {
        val expected = EvaluationResult(Result.VALID, null, "3035908.314")
        assertEquals(algorithm.shuntingYard("1236*2456.2365"), expected)
    }

    @Test
    fun test3() {
        val expected = EvaluationResult(Result.VALID, null, "1580")
        assertEquals(algorithm.shuntingYard("(100-21)*(40/2)"), expected)
    }

    @Test
    fun test4() {
        val expected = EvaluationResult(Result.VALID, null, "5")
        assertEquals(algorithm.shuntingYard("-5/(-1)"), expected)
    }

    @Test
    fun test5() {
        val expected = EvaluationResult(Result.VALID, null, "-79")
        assertEquals(algorithm.shuntingYard("21-100"), expected)
    }

    @Test
    fun test6() {
        val expected = EvaluationResult(Result.VALID, null, "3")
        assertEquals(algorithm.shuntingYard("1+2"), expected)
    }

    @Test
    fun test7() {
        val expected = EvaluationResult(Result.VALID, null, "100")
        assertEquals(algorithm.shuntingYard("10/0.1"), expected)
    }

    @Test
    fun testError1() {
        assertEquals(algorithm.shuntingYard("123+"), notCompleted)
    }

    @Test
    fun testError2() {
        assertEquals(algorithm.shuntingYard("123+((()()()()((((((((("), notCompleted)
    }

    @Test
    fun testError3() {
        assertEquals(algorithm.shuntingYard("123/0"), dividingByZero)
    }

    @Test
    fun testError4() {
        assertEquals(algorithm.shuntingYard("123/0.12312())))))))(())))))"), invalid)
    }
}