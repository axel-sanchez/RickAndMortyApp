package com.example.rickandmortyapp.helpers

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

/**
 * @author Axel Sanchez
 */
fun inThePosition(position: Int, itemMatcher: Matcher<View?>): Matcher<View?> {
    return object: BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java){
        override fun describeTo(description: Description) {
            description.appendText("tiene un item en la posicion $position: ")
        }

        override fun matchesSafely(view: RecyclerView): Boolean {
            val viewHolder = view.findViewHolderForAdapterPosition(position)
                ?: return false
            return itemMatcher.matches(viewHolder.itemView)
        }
    }
}