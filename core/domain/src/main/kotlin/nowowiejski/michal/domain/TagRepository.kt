package nowowiejski.michal.domain

import kotlinx.coroutines.flow.Flow
import nowowiejski.michal.model.Tag

interface TagRepository {

    fun getAllTags(): Flow<List<Tag>>
    suspend fun saveTag(tagName: String)
}