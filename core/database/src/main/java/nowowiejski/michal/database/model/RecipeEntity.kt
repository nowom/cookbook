package nowowiejski.michal.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import nowowiejski.michal.model.Recipe

@Entity("recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val recipeName: String,
    val shortDescription: String,
    val portions: Int,
    val source: String,
    val cookTime: String,
)

fun RecipeEntity.asExternalModel(ingredients: List<IngredientEntity>, steps: List<StepEntity>) = Recipe(
    recipeName = recipeName,
    shortDescription = shortDescription,
    portions = portions,
    ingredients = ingredients.map { it.asExternalModel() },
    steps = steps.map { it.asExternalModel() },
    source = source,
    cookTime = cookTime
)

fun Recipe.asEntity() = RecipeEntity(
    recipeName = recipeName,
    shortDescription = shortDescription,
    portions = portions,
    source = source,
    cookTime = cookTime
)