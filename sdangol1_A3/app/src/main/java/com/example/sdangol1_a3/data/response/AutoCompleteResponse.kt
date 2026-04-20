package com.example.sdangol1_a3.data.response

data class AutocompleteResponse(
    val d: List<AutocompleteItem> = emptyList()
)

data class AutocompleteItem(
    val id: String = "",
    val l: String = "",
    val q: String = "",
    val y: Int? = null,
    val i: AutocompleteImage? = null
)

data class AutocompleteImage(
    val imageUrl: String? = null
)
