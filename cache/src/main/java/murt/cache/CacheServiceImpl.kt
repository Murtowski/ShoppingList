package murt.cache

import murt.data.repository.CacheService
import javax.inject.Inject

/**
 * Piotr Murtowski on 25.02.2018.
 */
class CacheServiceImpl @Inject constructor(private val database: RoomDatabaseCache) : CacheService{
}