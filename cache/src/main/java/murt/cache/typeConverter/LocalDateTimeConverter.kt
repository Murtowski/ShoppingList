package murt.cache.typeConverter

import androidx.room.TypeConverter
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset

/**
 * Piotr Murtowski on 20.02.2018.
 */
class LocalDateTimeConverter {
    @TypeConverter
    fun getDateFromLong(dateLong: Long): LocalDateTime {
        return LocalDateTime.ofInstant(
            Instant.ofEpochSecond(dateLong),
            ZoneId.systemDefault()
        )
    }

    @TypeConverter
    fun getLongFromDate(date: LocalDateTime): Long{
        return date.toEpochSecond(ZoneOffset.UTC)
    }
}