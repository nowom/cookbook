package nowowiejski.michal.database.model

import androidx.room.Embedded
import androidx.room.Relation
import nowowiejski.michal.model.Recipe

data class PopulatedRecipe(
    @Embedded val recipe: RecipeEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "recipeId"
    )
    val ingredients: List<IngredientEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "recipeId"
    )
    val steps: List<StepEntity>,
)

fun PopulatedRecipe.asExternalModel() = Recipe(
    recipeName = recipe.recipeName,
    shortDescription = recipe.shortDescription,
    portions = recipe.portions,
    ingredients = ingredients.map { it.asExternalModel() },
    steps = steps.map { it.asExternalModel() },
    source = recipe.source,
    cookTime = recipe.cookTime
)