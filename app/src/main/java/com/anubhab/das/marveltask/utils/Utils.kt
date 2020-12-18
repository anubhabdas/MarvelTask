package com.anubhab.das.marveltask.utils

import android.net.Uri
import com.anubhab.das.marveltask.R
import kotlin.random.Random

class Utils {

    fun getURLForResource(resourceId: Int): String {
        //use BuildConfig.APPLICATION_ID instead of R.class.getPackage().getName() if both are not same
        return Uri.parse(
            "android.resource://" + R::class.java.getPackage()?.getName() + "/" + resourceId
        ).toString()
    }

    fun generateRandomInteger(): Int {
        return Random.nextInt((1000 - 10) + 1) + 10;
    }
}