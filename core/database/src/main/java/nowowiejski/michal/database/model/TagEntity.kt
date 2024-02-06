package nowowiejski.michal.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import nowowiejski.michal.model.Tag

@Entity(tableName = "tags")
data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String
)

fun TagEntity.asExternalModel(): Tag = Tag(name)