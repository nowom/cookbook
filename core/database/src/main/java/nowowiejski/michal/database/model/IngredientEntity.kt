package nowowiejski.michal.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import nowowiejski.michal.model.Ingredient

@Entity(
    tableName = "ingredients",
    foreignKeys = [ForeignKey(
        entity = RecipeEntity::class,
        parentColumns = ["id"],
        childColumns = ["recipeId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class IngredientEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val recipeId: Long,
    val name: String,
    val portion: String
)

fun IngredientEntity.asExternalModel(): Ingredient = Ingredient(name, portion)

fun Ingredient.asEntity(recipeId: Long) : IngredientEntity = IngredientEntity(
    recipeId = recipeId,
    name =  name,
    portion = portion
)
