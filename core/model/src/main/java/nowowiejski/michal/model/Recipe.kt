package nowowiejski.michal.model


data class Recipe(
    val recipeName: String = "",
    val shortDescription: String = "",
    val portions: Int = 0,
    val ingredients: List<Ingredient>,
    val steps: List<Step>,
    val source: String = "",
    val cookTime: String? = null,
)