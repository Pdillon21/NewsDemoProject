package com.example.newsdemoproject.data.remote.dto.single_item

data class GuardianSingleItemDto(
    val id: String?,
    val type: String?,
    val sectionId: String?,
    val sectionName: String?,
    val webPublicationDate: String?,
    val webTitle: String?,
    val webUrl: String?,
    val apiUrl: String?,
    val isHosted: Boolean?,
    val pillarId: String?,
    val pillarName: String?,
    val fields: GuardianArticleFieldsDto
)
