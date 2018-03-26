package murt.data.model

import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZonedDateTime
import java.util.*

/**
 * Piotr Murtowski on 20.02.2018.
 */
data class ShoppingList(
    var id: Long?,
    var updatedAt: ZonedDateTime,
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

            return ShoppingList(null, ZonedDateTime.now(), "Some title", shoppingItems,
                Random().nextBoolean())
        }

        fun empty(): ShoppingList{
            return ShoppingList(null, ZonedDateTime.now(), "", emptyList(), false)
        }


    }
}