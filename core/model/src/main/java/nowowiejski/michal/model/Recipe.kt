package nowowiejski.michal.model


data class Recipe(
    val recipeName: String = "",
    val shortDescription: String = "",
    val portions: Int = 0,
    val ingredients: List<String> = emptyList(),
    val source: String = "",
    val cookTime: String = ""
)