package com.example.sdangol1_a3.data.response

data class CastResponse(
    val cast: List<CastPerson> = emptyList()
)

data class CastPerson(
    val nconst: String = "",
    val name: String = "",
    val category: String = "",
    val characters: List<String> = emptyList()
)
