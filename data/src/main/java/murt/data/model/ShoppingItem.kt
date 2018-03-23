package murt.data.model

import org.threeten.bp.LocalDateTime

/**
 * Piotr Murtowski on 20.02.2018.
 */
open class ShoppingItem(
    var id: Long?,
    var title: String,
    var updatedAt: LocalDateTime){

    companion object {
        fun new() = ShoppingItem(null, "3 Oranges, 10 Eggs, Ham, Bratwurst," +
                " Kanapki, 2 Pizza, Mehl, Nudeln, Parka w rochliku",
            LocalDateTime.now())

        fun empty() = ShoppingItem(null, "", LocalDateTime.now())

    }
}