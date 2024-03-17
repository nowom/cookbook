package nowowiejski.michal.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import nowowiejski.michal.model.Step

@Entity(
    tableName = "steps",
    foreignKeys = [ForeignKey(
        entity = RecipeEntity::class,
        parentColumns = ["id"],
        childColumns = ["recipeId"],
        onDelete = ForeignKey.CASCADE
    )],
)
data class StepEntity(
    @PrimaryKey(autoGenerate = true)
    val stepId: Long = 0,
    val recipeId: Long,
    val description: String,
)

fun Step.asEntity(recipeId: Long) : StepEntity = StepEntity(
    recipeId = recipeId,
    description =  description,
)

fun StepEntity.asExternalModel(): Step = Step(description)