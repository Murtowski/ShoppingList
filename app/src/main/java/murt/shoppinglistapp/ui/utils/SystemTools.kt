package murt.shoppinglistapp.ui.utils

import android.os.Build
import murt.shoppinglistapp.BuildConfig

/**
 * Piotr Murtowski on 25.02.2018.
 */
object SystemTools {

    @JvmStatic val isAtLeastOreo: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

    @JvmStatic val isAtLeastNought: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N

    @JvmStatic val isAtLeastMarshmallow: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

    @JvmStatic val isAtLeastLolipop: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

    @JvmStatic val isAtLeastLolipop2: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1

    @JvmStatic val isAtLeastKitKat: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

    @JvmStatic val isAtLeastJellyBeanMR1: Boolean
        get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1

    @JvmStatic val isDebugMode: Boolean
        get() = !BuildConfig.RELEASE

    @JvmStatic val getApplicationTypeInfo: String
        get() = BuildConfig.FLAVOR + " " + BuildConfig.VERSION_NAME + " " + if(isDebugMode) "debug" else "release"
}