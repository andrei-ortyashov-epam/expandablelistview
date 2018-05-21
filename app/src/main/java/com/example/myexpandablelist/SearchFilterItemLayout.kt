package com.example.myexpandablelist

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout

class SearchFilterItemLayout : LinearLayout {

	var currentState = false
		set(value) {
			field = value
			refreshDrawableState()
		}

	constructor(context: Context) : super(context)

	constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

	override fun onCreateDrawableState(extraSpace: Int): IntArray {
		val state = super.onCreateDrawableState(extraSpace + 1)
		if (currentState) {
			View.mergeDrawableStates(state, STATE_CURRENT)
		}
		return state
	}

	companion object {

		private val STATE_CURRENT = intArrayOf(R.attr.state_current)
	}
}
