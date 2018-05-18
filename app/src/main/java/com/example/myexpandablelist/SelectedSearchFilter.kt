package com.example.myexpandablelist

import java.io.Serializable

data class SelectedSearchFilter(
		var type: SelectedSearchType = SelectedSearchType.ANY,
		var size: SelectedSearchSize = SelectedSearchSize.ANY,
		var dateModified: SelectedSearchDateModified = SelectedSearchDateModified.ANY,
		var owner: SelectedSearchOwner = SelectedSearchOwner.ANY
) : Serializable

enum class FilterGroup(val nameId: Int) {
	TYPE(R.string.search_filter_type),
	SIZE(R.string.search_filter_size),
	DATE(R.string.search_filter_date),
	OWNER(R.string.search_filter_owner);

	companion object {

		fun getByIndex(index: Int): FilterGroup = FilterGroup.values()[index]

		fun getNameByIndex(index: Int): String = FilterGroup.values()[index].name

		val size by lazy { FilterGroup.values().size }

		val filters by lazy {
			mapOf(
					TYPE.name to SelectedSearchType.values().map { it.nameId },
					SIZE.name to SelectedSearchSize.values().map { it.nameId },
					DATE.name to SelectedSearchDateModified.values().map { it.nameId },
					OWNER.name to SelectedSearchOwner.values().map { it.nameId }
			)
		}
	}
}

enum class SelectedSearchType(val nameId: Int) {
	ANY(R.string.search_filter_type_any),
	ARCHIVES(R.string.search_filter_type_archives),
	AUDIO(R.string.search_filter_type_audio),
	DOCUMENTS(R.string.search_filter_type_documents),
	EXECUTABLES(R.string.search_filter_type_executables),
	FOLDERS(R.string.search_filter_type_folders),
	IMAGES(R.string.search_filter_type_images),
	PDF(R.string.search_filter_type_pdf),
	VIDEO(R.string.search_filter_type_video)
}

enum class SelectedSearchSize(val nameId: Int) {
	ANY(R.string.search_filter_size_any),
	TINY(R.string.search_filter_size_tiny),
	SMALL(R.string.search_filter_size_small),
	MEDIUM(R.string.search_filter_size_medium),
	BIG(R.string.search_filter_size_big),
	HUGE(R.string.search_filter_size_huge)
}

enum class SelectedSearchDateModified(val nameId: Int) {
	ANY(R.string.search_filter_date_modified_any),
	DAY(R.string.search_filter_date_modified_day),
	WEEK(R.string.search_filter_date_modified_week),
	MONTH(R.string.search_filter_date_modified_month),
	QUARTER(R.string.search_filter_date_modified_quarter),
	YEAR(R.string.search_filter_date_modified_year),
	MORE_YEAR(R.string.search_filter_date_modified_more_year)
}

enum class SelectedSearchOwner(val nameId: Int) {
	ANY(R.string.search_filter_owner_any),
	ME(R.string.search_filter_owner_me)
}
