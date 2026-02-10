package nl.kampmeijer.adthemovies.model

data class Movie(
    val titleId: Int,
    val title: String,
    val description: String,
    val rating: Int,
    val category: String,
    val releaseDate: Date
) {
    override fun toString(): String {
        return title
    }
}