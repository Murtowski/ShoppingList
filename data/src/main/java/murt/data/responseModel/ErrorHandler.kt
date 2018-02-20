package murt.data.responseModel

import java.io.IOException
import java.util.concurrent.TimeoutException

/**
 * Piotr Murtowski on 20.02.2018.
 */
object ErrorHandler {
    fun getResponseError(throwable: Throwable): DataError{
        return when(throwable){
            is RemoteError -> DataError(throwable, "remote error", EnumErrorType.API)
            is TimeoutException -> DataError(throwable, "timeout", EnumErrorType.TIMEOUT)
            is IOException -> DataError(throwable, "network error", EnumErrorType.CONNECTION)
            else -> DataError(throwable, "unrecognized" , EnumErrorType.UNKNOWN)
        }
    }
}