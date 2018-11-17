package pl.balazinski.jakub.simplecalc

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import pl.balazinski.jakub.simplecalc.main.MainContract
import pl.balazinski.jakub.simplecalc.main.MainPresenter

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MainPresenterUnitTest {

    private lateinit var presenter: MainContract.Presenter
    @Mock
    lateinit var view: MainContract.View<MainContract.Presenter>

    private val add = "+"
    private val divide = "/"
    private val multiply = "*"
    private val subtract = "-"
    private val dot = "."
    private val openedBracket = "("
    private val closedBracket = ")"


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = MainPresenter(view)
    }


    @Test
    fun addClickValidTextTest() {
        val expression2 = "12+3-3/2"
        presenter.addClick(expression2)
        verify(view).updateCalculationView("+")
    }

    @Test
    fun addClickInvalidTextTest() {
        val expression = ""
        presenter.addClick(expression)
        verify(view, never()).updateCalculationView("+")
    }

    @Test
    fun numberClickTest() {
        val number = "3"
        presenter.numberClick(number)
        verify(view).updateCalculationView(number)
    }

    @Test
    fun dotClickValidTest() {
        val currentText = "312/32"
        presenter.dotClick(currentText)
        verify(view).updateCalculationView(dot)
    }

    @Test
    fun dotClickInvalidTest1() {
        val currentText = "312/3.2"
        presenter.dotClick(currentText)
        verify(view, never()).updateCalculationView(dot)
    }

    @Test
    fun dotClickInvalidTest2() {
        val currentText = ""
        presenter.dotClick(currentText)
        verify(view, never()).updateCalculationView(dot)
    }

    @Test
    fun dotClickInvalidTest3() {
        val currentText = "("
        presenter.dotClick(currentText)
        verify(view, never()).updateCalculationView(dot)
    }

    @Test
    fun dotClickInvalidTest4() {
        val currentText = ")"
        presenter.dotClick(currentText)
        verify(view, never()).updateCalculationView(dot)
    }

    @Test
    fun dotClickInvalidTest5() {
        val currentText = "21+"
        presenter.dotClick(currentText)
        verify(view, never()).updateCalculationView(dot)
    }

    @Test
    fun dotClickInvalidTest6() {
        val currentText = "12312."
        presenter.dotClick(currentText)
        verify(view, never()).updateCalculationView(dot)
    }

    @Test
    fun multiplyClickValidTest1() {
        val currentText = "3"
        presenter.multiplyClick(currentText)
        verify(view).updateCalculationView(multiply)
    }

    @Test
    fun multiplyClickValidTest2() {
        val currentText = "(1231+12)"
        presenter.multiplyClick(currentText)
        verify(view).updateCalculationView(multiply)
    }

    @Test
    fun multiplyClickInvalidTest1() {
        val currentText = ""
        presenter.multiplyClick(currentText)
        verify(view, never()).updateCalculationView(multiply)
    }

    @Test
    fun multiplyClickInvalidTest2() {
        val multiply = "*"
        val currentText = ""
        presenter.multiplyClick(currentText)
        verify(view, never()).updateCalculationView(multiply)
    }

    @Test
    fun multiplyClickInvalidTest3() {
        val currentText = "1212+"
        presenter.multiplyClick(currentText)
        verify(view, never()).updateCalculationView(multiply)
    }

    @Test
    fun multiplyClickInvalidTest4() {
        val currentText = "12+("
        presenter.multiplyClick(currentText)
        verify(view, never()).updateCalculationView(multiply)
    }

    @Test
    fun divideClickValidTest1() {
        val currentText = "3"
        presenter.divideClick(currentText)
        verify(view).updateCalculationView(divide)
    }

    @Test
    fun divideClickValidTest2() {
        val currentText = "(1231+12)"
        presenter.divideClick(currentText)
        verify(view).updateCalculationView(divide)
    }

    @Test
    fun divideClickInvalidTest1() {
        val currentText = ""
        presenter.divideClick(currentText)
        verify(view, never()).updateCalculationView(divide)
    }

    @Test
    fun divideClickInvalidTest2() {
        val currentText = ""
        presenter.divideClick(currentText)
        verify(view, never()).updateCalculationView(divide)
    }

    @Test
    fun divideClickInvalidTest3() {
        val currentText = "1212+"
        presenter.divideClick(currentText)
        verify(view, never()).updateCalculationView(divide)
    }

    @Test
    fun divideClickInvalidTest4() {
        val currentText = "12+("
        presenter.divideClick(currentText)
        verify(view, never()).updateCalculationView(divide)
    }


    @Test
    fun addClickValidTest1() {
        val currentText = "3"
        presenter.addClick(currentText)
        verify(view).updateCalculationView(add)
    }

    @Test
    fun addClickValidTest2() {
        val currentText = "(1231+12)"
        presenter.addClick(currentText)
        verify(view).updateCalculationView(add)
    }

    @Test
    fun addClickInvalidTest1() {
        val currentText = ""
        presenter.addClick(currentText)
        verify(view, never()).updateCalculationView(add)
    }

    @Test
    fun addClickInvalidTest2() {
        val currentText = ""
        presenter.addClick(currentText)
        verify(view, never()).updateCalculationView(add)
    }

    @Test
    fun addClickInvalidTest3() {
        val currentText = "1212+"
        presenter.addClick(currentText)
        verify(view, never()).updateCalculationView(add)
    }

    @Test
    fun addClickInvalidTest4() {
        val currentText = "12+("
        presenter.addClick(currentText)
        verify(view, never()).updateCalculationView(add)
    }

    @Test
    fun subtractClickValidTest1() {
        val currentText = ""
        presenter.subtractClick(currentText)
        verify(view).updateCalculationView(subtract)
    }

    @Test
    fun subtractClickValidTest2() {
        val currentText = "12+("
        presenter.subtractClick(currentText)
        verify(view).updateCalculationView(subtract)
    }

    @Test
    fun subtractClickValidTest3() {
        val currentText = "12"
        presenter.subtractClick(currentText)
        verify(view).updateCalculationView(subtract)
    }

    @Test
    fun subtractClickValidTest4() {
        val currentText = "(12+3)"
        presenter.subtractClick(currentText)
        verify(view).updateCalculationView(subtract)
    }

    @Test
    fun subtractClickInvalidTest1() {
        val currentText = "12-"
        presenter.subtractClick(currentText)
        verify(view, never()).updateCalculationView(subtract)
    }

    @Test
    fun subtractClickInvalidTest2() {
        val currentText = "12."
        presenter.subtractClick(currentText)
        verify(view, never()).updateCalculationView(subtract)
    }

    @Test
    fun clearClickTest() {
        presenter.clearClick()
        verify(view).clearViews()
    }

    @Test
    fun removeLastCharacterClickTest1() {
        val currentText = "123123"
        presenter.removeLastCharacterClick(currentText)
        verify(view).removeLastFromView()
    }

    @Test
    fun removeLastCharacterClickTest2() {
        val currentText = "1"
        presenter.removeLastCharacterClick(currentText)
        verify(view).clearViews()
    }

    @Test
    fun removeLastCharacterClickTest3() {
        val currentText = ""
        presenter.removeLastCharacterClick(currentText)
        verify(view, never()).removeLastFromView()
        verify(view, never()).clearViews()
    }

    @Test
    fun removeLastCharacterClickTest4() {
        val currentText = "(1+2)"
        presenter.setOpenBracketsCount(1)
        presenter.setClosedBracketsCount(1)
        presenter.removeLastCharacterClick(currentText)
        verify(view).updateBracketsView(1, 0)
        verify(view).removeLastFromView()
    }

    @Test
    fun removeLastCharacterClickTest5() {
        val currentText = "(1+2)("
        presenter.setOpenBracketsCount(2)
        presenter.setClosedBracketsCount(1)
        presenter.removeLastCharacterClick(currentText)
        verify(view).updateBracketsView(1, 1)
        verify(view).removeLastFromView()
    }

    @Test
    fun openBracketsClickTest() {
        presenter.openBracketsClick()
        verify(view).updateBracketsView(1, 0)
        verify(view).updateCalculationView(openedBracket)
    }

    @Test
    fun closedBracketsClickTest() {
        presenter.closeBracketsClick()
        verify(view).updateBracketsView(0, 1)
        verify(view).updateCalculationView(closedBracket)
    }


}
