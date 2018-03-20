package murt.data.model

import org.threeten.bp.LocalDateTime
import java.util.*

/**
 * Piotr Murtowski on 20.02.2018.
 */
data class ShoppingList(
    var id: Long,
    val updatedAt: LocalDateTime,
    var title: String,
    var items: List<ShoppingItem>,
    var isArchived: Boolean
){

    companion object {
        fun new(): ShoppingList{
            val shoppingItems = listOf(
                ShoppingItem.new(),
                ShoppingItem.new(),
                ShoppingItem.new(),
                ShoppingItem.new()
            )

            return ShoppingList(-1, LocalDateTime.now(), "Some title", shoppingItems,
                Random().nextBoolean())
        }
    }
}