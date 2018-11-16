package pl.balazinski.jakub.simplecalc

import org.junit.Assert.assertEquals
import org.junit.Test
import pl.balazinski.jakub.simplecalc.main.MainPresenter

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {


    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun isValuationCorrect(){
        val presenter = MainPresenter()
        val expression = presenter.shuntingYard("5*10-(80/2)+((4+3)-(5-2))")
        assertEquals(presenter.evaluateValue(expression), "14")

    }
}
