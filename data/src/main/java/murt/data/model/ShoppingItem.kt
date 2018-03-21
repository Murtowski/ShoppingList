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
        fun new() = ShoppingItem(1, "3 Oranges, 10 Eggs, Ham, Bratwurst," +
                " Kanapki, 2 Pizza, Mehl, Nudeln, Parka w rochliku",
            LocalDateTime.now())

        fun empty() = ShoppingItem(-1L, "", LocalDateTime.now())

    }
}