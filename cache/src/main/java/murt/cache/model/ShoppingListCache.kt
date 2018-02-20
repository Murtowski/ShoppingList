package murt.cache.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Relation
import android.arch.persistence.room.TypeConverters
import murt.cache.typeConverter.LocalDateTimeConverter
import org.threeten.bp.LocalDateTime

/**
 * Piotr Murtowski on 20.02.2018.
 */
@Entity
class ShoppingListCache(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    @TypeConverters(LocalDateTimeConverter::class)
    val createdAt: LocalDateTime,
    @Relation(parentColumn = "", entityColumn = "")
    val shoppingItems: List<ShoppingItemCache>
)