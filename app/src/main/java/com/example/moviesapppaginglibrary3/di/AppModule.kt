package com.example.moviesapppaginglibrary3.di

import android.content.Context
import android.provider.SyncStateContract
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moviesapppaginglibrary3.Constants
import com.example.moviesapppaginglibrary3.auth.AuthResponse
import com.example.moviesapppaginglibrary3.db.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.processNextEventInCurrentThread
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.LoggingEventListener
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module()
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofitInstance() : AuthResponse {
        val loggingInterceptor = HttpLoggingInterceptor()
        val interceptor = loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)

        return Retrofit.Builder()
            .baseUrl(Constants.MOVIES_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient.build())
            .build().create(AuthResponse::class.java)
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context)  =
        Room.databaseBuilder(context,MovieDatabase::class.java,"movie.db")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideDB(movieDatabase: MovieDatabase) = movieDatabase.MovieDao()


}