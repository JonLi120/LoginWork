package com.messon.noodoe.work.di.module

import com.messon.noodoe.work.ui.LoginFragment
import com.messon.noodoe.work.ui.MainActivity
import com.messon.noodoe.work.ui.UserConfigFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UIModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun contributeUserConfigFragment(): UserConfigFragment
}