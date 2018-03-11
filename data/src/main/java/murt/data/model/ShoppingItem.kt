package murt.data.model

import org.threeten.bp.LocalDateTime

/**
 * Piotr Murtowski on 20.02.2018.
 */
open class ShoppingItem(
    val id: Long,
    var title: String,
    var updatedAt: LocalDateTime){

    companion object {
        fun new() = ShoppingItem(1, "jakis tytul",
            LocalDateTime.of(2019,1,1,1,1,1))

        fun empty() = ShoppingItem(-1L, "", LocalDateTime.now())

    }
}