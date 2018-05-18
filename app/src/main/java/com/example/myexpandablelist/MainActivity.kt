package com.example.myexpandablelist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ExpandableListView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

	lateinit var searchFiltersListAdapter: SearchFiltersListAdapter
	val searchFilter = SelectedSearchFilter()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		searchFiltersListAdapter = SearchFiltersListAdapter(this, searchFilter)
		expandableListView.setAdapter(searchFiltersListAdapter)

		expandableListView.setOnGroupExpandListener(groupExpandListener)

		expandableListView.setOnChildClickListener(onChildClickListener)

		buttonClear.setOnClickListener {
			searchFilter.apply {
				type = SelectedSearchType.ANY
				size = SelectedSearchSize.ANY
				dateModified = SelectedSearchDateModified.ANY
				owner = SelectedSearchOwner.ANY
			}
			FilterGroup.values().withIndex().forEach { expandableListView.collapseGroup(it.index) }
			searchFiltersListAdapter.notifyDataSetChanged()
		}

		buttonApply.setOnClickListener {
			Toast.makeText(applicationContext, searchFilter.toString(), Toast.LENGTH_LONG).show()
		}
	}

	private val groupExpandListener = ExpandableListView.OnGroupExpandListener { groupPosition ->
		if (searchFilter.type == SelectedSearchType.FOLDERS) {
			when (FilterGroup.getByIndex(groupPosition)) {
				FilterGroup.SIZE, FilterGroup.DATE -> expandableListView.collapseGroup(groupPosition)
				else -> FilterGroup.values().withIndex()
						.filter { it.index != groupPosition }
						.forEach { expandableListView.collapseGroup(it.index) }
			}
		} else {
			FilterGroup.values().withIndex()
					.filter { it.index != groupPosition }
					.forEach { expandableListView.collapseGroup(it.index) }
		}
	}

	private val onChildClickListener = ExpandableListView.OnChildClickListener { parent, v, groupPosition, childPosition, id ->
		when (FilterGroup.getByIndex(groupPosition)) {
			FilterGroup.TYPE -> searchFilter.type = SelectedSearchType.values().asList()[childPosition]
			FilterGroup.SIZE -> searchFilter.size = SelectedSearchSize.values().asList()[childPosition]
			FilterGroup.DATE -> searchFilter.dateModified = SelectedSearchDateModified.values().asList()[childPosition]
			FilterGroup.OWNER -> searchFilter.owner = SelectedSearchOwner.values().asList()[childPosition]
		}
		if (searchFilter.type == SelectedSearchType.FOLDERS) {
			searchFilter.size = SelectedSearchSize.ANY
			searchFilter.dateModified = SelectedSearchDateModified.ANY
		}
		expandableListView.collapseGroup(groupPosition)
		false
	}
}
