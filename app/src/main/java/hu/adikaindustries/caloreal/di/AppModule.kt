package hu.adikaindustries.caloreal.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.adikaindustries.core.domain.data.preferences.DefaultPreferences
import hu.adikaindustries.core.domain.preferences.Preferences
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providePreferences(
        app:Application
    ):Preferences{
        return DefaultPreferences(provideSharedPreferences(app))
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(app:Application):SharedPreferences{
        return app.getSharedPreferences("shared_pref",MODE_PRIVATE)
    }

}