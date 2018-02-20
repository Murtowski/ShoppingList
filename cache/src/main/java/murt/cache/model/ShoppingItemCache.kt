package murt.cache.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Piotr Murtowski on 20.02.2018.
 */
@Entity
class ShoppingItemCache(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val shoppingListId: Long,
    val number: Long,
    val title: String)