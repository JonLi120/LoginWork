package com.messon.noodoe.work.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.messon.noodoe.work.di.AppViewModelFactory
import com.messon.noodoe.work.di.ViewModelKey
import com.messon.noodoe.work.viewmodel.UserViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    abstract fun bindMainViewModel(viewModel: UserViewModel): ViewModel
}