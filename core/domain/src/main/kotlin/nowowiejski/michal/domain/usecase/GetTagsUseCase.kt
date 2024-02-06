package nowowiejski.michal.domain.usecase

import kotlinx.coroutines.flow.Flow
import nowowiejski.michal.domain.TagRepository
import nowowiejski.michal.model.Tag

class GetTagsUseCase(private val tagRepository: TagRepository) {
    operator fun invoke(): Flow<List<Tag>> = tagRepository.getAllTags()
}