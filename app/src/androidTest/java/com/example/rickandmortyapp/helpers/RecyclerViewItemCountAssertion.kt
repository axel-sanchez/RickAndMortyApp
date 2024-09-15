package com.example.rickandmortyapp.helpers

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matchers

/**
 * @author Axel Sanchez
 */
class RecyclerViewItemCountAssertion(private val expectedCount: Int) :
    androidx.test.espresso.ViewAssertion {
    override fun check(view: View?, noViewFoundException: androidx.test.espresso.NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }

        val recyclerView = view as RecyclerView
        val adapter = recyclerView.adapter
        ViewMatchers.assertThat(adapter?.itemCount, Matchers.`is`(expectedCount))
    }
}