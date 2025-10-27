package com.sabya.truecallerandroidassignment

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * The entry point of the application.
 * Hilt will use this annotated class to start dependency injection (DI) processes.
 */
@HiltAndroidApp
class TruecallerApp : Application()
