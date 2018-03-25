package murt.shoppinglistapp.ui

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * Piotr Murtowski on 25.03.2018.
 */
abstract class MyViewModel: ViewModel() {

    protected val disposable = CompositeDisposable()

    override fun onCleared() {
        if(disposable.isDisposed)
            disposable.dispose()
        super.onCleared()
    }
}