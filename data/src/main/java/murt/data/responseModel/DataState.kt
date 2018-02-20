package murt.data.responseModel

/**
 * Piotr Murtowski on 20.02.2018.
 */
sealed class DataState

data class DataSuccess<T> (val data: T): DataState()

data class DataLoading(val loadingMessage: String = ""): DataState()

data class DataError(val throwable: Throwable,
                     val errorMessage: String,
                     val errorType: EnumErrorType = EnumErrorType.UNKNOWN) : DataState()