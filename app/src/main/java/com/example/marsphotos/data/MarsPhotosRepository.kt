package com.example.marsphotos.data

import com.example.marsphotos.MainActivity
import com.example.marsphotos.UserRepository //
import com.example.marsphotos.model.MarsPhoto
import com.example.marsphotos.network.MarsApiService
import com.google.firebase.firestore.core.ActivityScope
import dagger.Component
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.jetbrains.annotations.Contract

interface MarsPhotosRepository {
    suspend fun getMarsPhotos(): List<MarsPhoto>
}

interface LoginComponentProvider {
    fun provideLoginComponent(): LoginComponent
}

@Singleton
@Component
interface AppComponent {
    fun userRepository(): UserRepository
}

@Component(dependencies = [AppComponent::class])
interface LoginComponent {

    @Component.Factory
    interface Factory {
        // Takes an instance of AppComponent when creating
        // an instance of LoginComponent
        fun create(appComponent: AppComponent): LoginComponent
    }

    fun inject(activity: MainActivity)
}

/*@Contract(pure = true)
@ActivityScope*/
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) {  }

class NetworkMarsPhotosRepository(
    private val marsApiService: MarsApiService
) : MarsPhotosRepository {
    /** Fetches list of MarsPhoto from marsApi*/
    override suspend fun getMarsPhotos(): List<MarsPhoto> = marsApiService.getPhotos()
}