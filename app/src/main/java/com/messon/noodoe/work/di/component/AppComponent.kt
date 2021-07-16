package com.messon.noodoe.work.di.component

import android.app.Application
import com.messon.noodoe.work.application.BaseApplication
import com.messon.noodoe.work.di.module.AppModule
import com.messon.noodoe.work.di.module.UIModule
import com.messon.noodoe.work.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    UIModule::class,
    ViewModelModule::class,
])
interface AppComponent: AndroidInjector<BaseApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(instance: BaseApplication)
}