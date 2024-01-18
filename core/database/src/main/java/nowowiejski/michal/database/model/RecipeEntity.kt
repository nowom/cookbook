package nowowiejski.michal.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import nowowiejski.michal.model.Recipe

@Entity("recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val recipeName: String,
    val shortDescription: String,
    val portions: Int,
    val ingredients: List<String>,
    val source: String,
    val cookTime: String,
)

fun RecipeEntity.asExternalModel() = Recipe(
    recipeName, shortDescription, portions, ingredients, source, cookTime
)