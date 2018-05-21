package com.example.myexpandablelist

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout

class SearchFilterItemLayout : LinearLayout {

	var selectedState = false
		set(value) {
			field = value
			refreshDrawableState()
		}

	constructor(context: Context) : super(context)

	constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

	override fun onCreateDrawableState(extraSpace: Int): IntArray {
		val state = super.onCreateDrawableState(extraSpace + 1)
		if (selectedState) {
			View.mergeDrawableStates(state, STATE_SELECTED)
		}
		return state
	}

	companion object {

		private val STATE_SELECTED = intArrayOf(R.attr.selected_item)
	}
}
