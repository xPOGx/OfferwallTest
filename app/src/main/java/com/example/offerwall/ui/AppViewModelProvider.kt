package com.example.offerwall.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.offerwall.OfferwallApplication
import com.example.offerwall.ui.home.HomeViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire Offerwall app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                entityRepository = flightSearchApplication().container.entityRepository
            )
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [OfferwallApplication].
 */
fun CreationExtras.flightSearchApplication(): OfferwallApplication =
    (this[APPLICATION_KEY] as OfferwallApplication)