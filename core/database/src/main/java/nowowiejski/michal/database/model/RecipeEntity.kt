package nowowiejski.michal.database.model

import androidx.room.PrimaryKey

data class RecipeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
)