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
		val view = layoutInflater.inflate(R.layout.filter_list_group, null)

		//set group title
		(view.findViewById(R.id.groupTitle) as TextView).apply {
			text = getGroup(listPosition) as String
		}

		//show current filter from group
		val currentFilter = view.findViewById(R.id.selected) as TextView
		val currentFilterInactive = view.findViewById(R.id.selectedInactive) as TextView
		if (isExpanded) {
			currentFilter.visibility = View.GONE
			currentFilterInactive.visibility = View.GONE
		} else {
			val nameFilter = getChild(listPosition, getCurrentFilterIndex(listPosition)) as String
			if (searchFilter.type == SelectedSearchType.FOLDERS) {
				when (FilterGroup.getByIndex(listPosition)) {
					FilterGroup.SIZE, FilterGroup.DATE -> {
						currentFilter.visibility = View.GONE
						currentFilterInactive.text = nameFilter
						currentFilterInactive.visibility = View.VISIBLE
						return view
					}
				}
			}
			currentFilter.text = nameFilter
			currentFilter.visibility = View.VISIBLE
			currentFilterInactive.visibility = View.GONE
		}
		return view
	}

	override fun getChild(listPosition: Int, expandedListPosition: Int): Any =
			context.resources.getString(FilterGroup.filters[FilterGroup.getNameByIndex(listPosition)]!![expandedListPosition])

	override fun getChildId(listPosition: Int, expandedListPosition: Int): Long = expandedListPosition.toLong()

	override fun getChildView(listPosition: Int, expandedListPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup): View {
		val layoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
		val view = layoutInflater.inflate(R.layout.filter_list_item, null)
		val expandedListText = getChild(listPosition, expandedListPosition) as String
		val item = view.findViewById(R.id.item) as TextView
		val selected = view.findViewById(R.id.selected) as TextView
		val currentFilterIndex = getCurrentFilterIndex(listPosition)
		if (currentFilterIndex == expandedListPosition) {
			item.visibility = View.GONE
			selected.text = expandedListText
			selected.visibility = View.VISIBLE
		} else {
			item.text = expandedListText
			item.visibility = View.VISIBLE
			selected.visibility = View.GONE
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

	override fun getChildrenCount(listPosition: Int): Int = FilterGroup.filters[FilterGroup.getNameByIndex(listPosition)]!!.size

	override fun hasStableIds(): Boolean = false

	override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean = true
}
