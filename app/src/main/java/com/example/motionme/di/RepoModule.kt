package com.example.motionme.di

import com.example.motionme.network.ApiService
import com.example.motionme.repo.MovieRepo
import com.example.motionme.repo.MovieRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object RepoModule {

    @Provides
    fun provideMovieRepo(apiService: ApiService): MovieRepo {
        return MovieRepoImpl(apiService)
    }

}