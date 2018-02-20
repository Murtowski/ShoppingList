package murt.data.model

/**
 * Piotr Murtowski on 20.02.2018.
 */
data class ShoppingItem(val number: Long, val title: String){

    companion object {
        fun new() = ShoppingItem(1, "jakis tytul")
    }
}