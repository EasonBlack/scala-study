package eason.entities


case class SearchResult[E](items: Seq[E], page: Int, size: Int, count: Int, totalPages: Int) {
}
