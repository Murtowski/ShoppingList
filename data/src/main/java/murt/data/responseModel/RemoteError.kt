package murt.data.responseModel

/**
 * Piotr Murtowski on 20.02.2018.
 */
class RemoteError(code: Int, message: String = ""): Throwable(message)