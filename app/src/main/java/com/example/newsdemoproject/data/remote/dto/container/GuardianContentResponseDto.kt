package com.example.newsdemoproject.data.remote.dto.container

import com.example.newsdemoproject.data.remote.dto.single_item.GuardianSingleItemDto

data class GuardianContentResponseDto(
    val status: String?,
    val userTier: String?,
    val total: Int?,
    val startIndex: Int?,
    val pageSize: Int?,
    val currentPage: Int?,
    val pages: Int?,
    val orderBy: String?,
    val results: List<GuardianSingleItemDto>
)
