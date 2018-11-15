package pl.balazinski.jakub.simplecalc.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import pl.balazinski.jakub.simplecalc.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val presenter = Presenter()

        presenter.evaluateExpression("5*10-(80/2)+((4+3)-(5-2))")
    }
}
