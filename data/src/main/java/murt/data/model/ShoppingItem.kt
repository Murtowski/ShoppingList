package murt.data.model

import org.threeten.bp.LocalDateTime

/**
 * Piotr Murtowski on 20.02.2018.
 */
data class ShoppingItem(
    val id: Long,
    val title: String,
    val createdAt: LocalDateTime){

    companion object {
        fun new() = ShoppingItem(1, "jakis tytul",
            LocalDateTime.of(2019,1,1,1,1,1))

    }
}