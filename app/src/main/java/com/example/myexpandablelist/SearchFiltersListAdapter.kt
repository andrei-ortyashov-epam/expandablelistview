package com.example.myexpandablelist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView

class SearchFiltersListAdapter(
		private val context: Context,
		private val searchFilter: SelectedSearchFilter
) : BaseExpandableListAdapter() {

	override fun getGroup(listPosition: Int): Any = context.resources.getString(FilterGroup.getByIndex(listPosition).nameId)

	override fun getGroupCount(): Int = FilterGroup.size

	override fun getGroupId(listPosition: Int): Long = listPosition.toLong()

	override fun getGroupView(listPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup): View {
		val layoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
		val view = layoutInflater.inflate(R.layout.filter_list_group, null) as SearchFilterItemLayout

		//set group title
		(view.findViewById(R.id.groupTitle) as TextView).apply {
			text = getGroup(listPosition) as String
		}

		//show current filter from group
		val currentFilter = view.findViewById(R.id.selected) as TextView
		if (isExpanded) {
			currentFilter.visibility = View.GONE
		} else {
			currentFilter.apply {
				visibility = View.VISIBLE
				text = getChild(listPosition, getCurrentFilterIndex(listPosition)) as String
			}
			view.currentState = if (searchFilter.type == SelectedSearchType.FOLDERS) {
				when (FilterGroup.getByIndex(listPosition)) {
					FilterGroup.SIZE, FilterGroup.DATE -> false
					else -> true
				}
			} else {
				true
			}
		}
		return view
	}

	override fun getChild(listPosition: Int, expandedListPosition: Int): Any =
			context.resources.getString(FilterGroup.filters[FilterGroup.getNameByIndex(listPosition)]!![expandedListPosition])

	override fun getChildId(listPosition: Int, expandedListPosition: Int): Long = expandedListPosition.toLong()

	override fun getChildView(listPosition: Int, expandedListPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup): View {
		val layoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
		val view = layoutInflater.inflate(R.layout.filter_list_item, null) as SearchFilterItemLayout
		view.currentState = getCurrentFilterIndex(listPosition) == expandedListPosition
		(view.findViewById(R.id.item) as TextView).apply {
			text = getChild(listPosition, expandedListPosition) as String
		}
		//show divider for the last item
		(view.findViewById(R.id.bottom_divider) as View).apply {
			visibility = if (isLastFilter(listPosition, expandedListPosition)) View.VISIBLE else View.INVISIBLE
		}
		return view
	}

	private fun getCurrentFilterIndex(listPosition: Int): Int =
			when (FilterGroup.getByIndex(listPosition)) {
				FilterGroup.TYPE -> SelectedSearchType.values().asList().indexOf(searchFilter.type)
				FilterGroup.SIZE -> SelectedSearchSize.values().asList().indexOf(searchFilter.size)
				FilterGroup.DATE -> SelectedSearchDateModified.values().asList().indexOf(searchFilter.dateModified)
				FilterGroup.OWNER -> SelectedSearchOwner.values().asList().indexOf(searchFilter.owner)
			}

	private fun isLastFilter(listPosition: Int, expandedListPosition: Int): Boolean {
		val size = when (FilterGroup.getByIndex(listPosition)) {
			FilterGroup.TYPE -> SelectedSearchType.values().size
			FilterGroup.SIZE -> SelectedSearchSize.values().size
			FilterGroup.DATE -> SelectedSearchDateModified.values().size
			FilterGroup.OWNER -> SelectedSearchOwner.values().size
		}
		return size - 1 == expandedListPosition
	}

	override fun getChildrenCount(listPosition: Int): Int = FilterGroup.filters[FilterGroup.getNameByIndex(listPosition)]!!.size

	override fun hasStableIds(): Boolean = false

	override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean = true
}
