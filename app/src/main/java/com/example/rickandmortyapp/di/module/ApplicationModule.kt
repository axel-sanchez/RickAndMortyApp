package com.example.rickandmortyapp.di.module

import android.content.Context
import androidx.room.Room
import com.example.rickandmortyapp.data.room.Database
import com.example.rickandmortyapp.data.repository.CharacterRepositoryImpl
import com.example.rickandmortyapp.data.repository.FakeRepository
import com.example.rickandmortyapp.data.service.ApiClient
import com.example.rickandmortyapp.data.service.ApiServiceCharacter
import com.example.rickandmortyapp.data.source.CharacterLocalSource
import com.example.rickandmortyapp.data.source.CharacterLocalSourceImpl
import com.example.rickandmortyapp.data.source.CharacterRemoteSource
import com.example.rickandmortyapp.data.source.CharacterRemoteSourceImpl
import com.example.rickandmortyapp.domain.usecase.GetAllCharactersUseCase
import com.example.rickandmortyapp.domain.usecase.GetAllCharactersUseCaseImpl
import com.example.rickandmortyapp.domain.repository.CharacterRepository
import com.example.rickandmortyapp.domain.usecase.GetCharacterUseCase
import com.example.rickandmortyapp.domain.usecase.GetCharacterUseCaseImpl
import com.example.rickandmortyapp.helpers.Constants.BASE_URL
import com.example.rickandmortyapp.helpers.Constants.isRunningTest
import com.example.rickandmortyapp.helpers.NetworkHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @author Axel Sanchez
 */
@Module
class ApplicationModule(private val context: Context){
    @Provides
    @Singleton
    fun provideCharacterRepository(characterLocalSource: CharacterLocalSource, characterRemoteSource: CharacterRemoteSource): CharacterRepository {
        return if (isRunningTest) FakeRepository()
        else CharacterRepositoryImpl(characterRemoteSource, characterLocalSource)
    }

    @Provides
    @Singleton
    fun provideCharacterRemoteSource(characterRemoteSource: CharacterRemoteSourceImpl): CharacterRemoteSource = characterRemoteSource

    @Provides
    @Singleton
    fun provideGetAllCharactersUseCase(getAllCharactersUseCase: GetAllCharactersUseCaseImpl): GetAllCharactersUseCase = getAllCharactersUseCase

    @Provides
    @Singleton
    fun provideGetCharacterUseCase(getCharacterUseCase: GetCharacterUseCaseImpl): GetCharacterUseCase = getCharacterUseCase

    @Provides
    @Singleton
    fun provideApiServiceCharacter(): ApiServiceCharacter {
        return ApiClient.Builder<ApiServiceCharacter>()
            .setBaseUrl(BASE_URL)
            .setApiService(ApiServiceCharacter::class.java)
            .build()
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Context): Database {
        return Room
            .databaseBuilder(context, Database::class.java, "RickAndMortyDB.db1")
            .build()
    }

    @Provides
    @Singleton
    fun provideCharacterLocalSource(database: Database): CharacterLocalSource {
        return CharacterLocalSourceImpl(database.characterDao())
    }

    @Provides
    @Singleton
    fun provideNetworkHelper(context: Context) = NetworkHelper(context)

    @Provides
    @Singleton
    fun provideContext(): Context = context
}