package com.example.mimedicokotlin.hilt

import com.example.mimedicokotlin.services.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun providesUserService(): UserService = UserService()

    @Provides
    fun providesAuthService(): AuthService = AuthService(providesUserService())

    @Provides
    fun providesPetitionService(): PetitionService = PetitionService(providesAuthService())

    @Provides
    fun providesConsultService(): ConsultService = ConsultService()

    @Provides
    fun providesProposalService(): ProposalService =
        ProposalService(providesPetitionService(), providesConsultService())
}