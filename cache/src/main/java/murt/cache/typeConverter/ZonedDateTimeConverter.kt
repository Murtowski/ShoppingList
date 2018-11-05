package murt.cache.typeConverter

import androidx.room.TypeConverter
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset
import org.threeten.bp.ZonedDateTime

/**
 * Piotr Murtowski on 26.03.2018.
 */
class ZonedDateTimeConverter {
    @TypeConverter
    fun getZonedDateFromLong(dateLong: Long): ZonedDateTime {
        return ZonedDateTime.ofInstant(
            Instant.ofEpochSecond(dateLong),
            ZoneId.systemDefault()
        )
    }

    @TypeConverter
    fun getLongFromDate(date: ZonedDateTime): Long{
        return date.toEpochSecond()
    }

/*    @TypeConverter
    fun getDateFromLong(dateLong: Long): LocalDateTime {
        return LocalDateTime.ofInstant(
            Instant.ofEpochSecond(dateLong),
            ZoneId.systemDefault()
        )
    }

    @TypeConverter
    fun getLongFromDate(date: LocalDateTime): Long{
        return date.toEpochSecond(ZoneOffset.UTC)
    }*/
}