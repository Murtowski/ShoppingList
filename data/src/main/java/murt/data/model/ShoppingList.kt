package murt.data.model

import org.threeten.bp.LocalDateTime

/**
 * Piotr Murtowski on 20.02.2018.
 */
data class ShoppingList(
    var id: Long,
    val updatedAt: LocalDateTime,
    var title: String,
    var items: List<ShoppingItem>,
    var isArchived: Boolean
)