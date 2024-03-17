package nowowiejski.michal.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import nowowiejski.michal.database.dao.TagDao
import nowowiejski.michal.database.model.TagEntity
import nowowiejski.michal.database.model.asExternalModel
import nowowiejski.michal.domain.TagRepository
import nowowiejski.michal.model.Tag

class TagRepositoryImpl(private val tagDao: TagDao) : TagRepository {
    override fun getAllTags(): Flow<List<Tag>> = tagDao.getAllTags().map { tags ->
        tags.map { entity ->
            entity.asExternalModel()
        }
    }

    override suspend fun saveTag(tagName: String) {
        tagDao.insertTag(
            TagEntity(
                name = tagName
            )
        )
    }
}