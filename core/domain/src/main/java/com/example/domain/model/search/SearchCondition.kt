package com.example.domain.model.search

import com.example.domain.model.post.SearchType
import com.example.domain.model.post.TabType

data class SearchCondition(
    val keyword: String,
    val tabType: TabType,
    val searchType: SearchType,
    val isSearched: Boolean
)