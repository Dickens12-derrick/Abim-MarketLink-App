package com.example.abimmarketlinkapp.di

import android.content.Context
import com.example.abimmarketlinkapp.data.local.DataStoreManager
import com.example.abimmarketlinkapp.data.repository.FakeProductRepository
import com.example.abimmarketlinkapp.data.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindProductRepository(fakeProductRepository: FakeProductRepository): ProductRepository

    companion object {
        @Provides
        @Singleton
        fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager {
            return DataStoreManager(context)
        }
    }
}
