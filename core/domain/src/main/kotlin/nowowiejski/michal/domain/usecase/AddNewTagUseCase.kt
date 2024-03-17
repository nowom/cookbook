package nowowiejski.michal.domain.usecase

import nowowiejski.michal.domain.TagRepository

class AddNewTagUseCase(private val tagRepository: TagRepository) {
    suspend operator fun invoke(tagName: String) {
        tagRepository.saveTag(tagName)
    }
}