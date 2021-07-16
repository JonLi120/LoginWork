package com.messon.noodoe.work.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Provider

class AppViewModelFactory @Inject constructor(private val creators: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val creators = creators[modelClass] ?: creators.entries.firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("unknown model class $modelClass")

        try {
            @Suppress("UNCHECKED_CAST")
            return creators.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}