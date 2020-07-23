package com.mobileforce.hometeach.data.sources

import androidx.lifecycle.LiveData
import com.mobileforce.hometeach.data.sources.local.entities.ProfileEntity
import com.mobileforce.hometeach.data.sources.local.entities.TutorEntity
import com.mobileforce.hometeach.data.model.User
import com.mobileforce.hometeach.data.sources.local.entities.UserEntity
import com.mobileforce.hometeach.data.sources.local.AppDataBase
import com.mobileforce.hometeach.data.sources.local.entities.TutorDetailsEntity
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.data.sources.remote.wrappers.*
import com.mobileforce.hometeach.remotesource.wrappers.UserCardDetailResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class LocalDataSource(private val db: AppDataBase) : DataSource {

    override suspend fun logIn(params: Params.SignIn): LoginResponse {
        TODO("Not yet implemented")
    }


    override suspend fun signUp(params: Params.SignUp): RegisterUserResponse {
        TODO("Not yet implemented")
    }

    override suspend fun saveUser(user: User) {
        db.userDao().saveUser(mapUserToEntity(user))
    }


    override fun getUser(): LiveData<UserEntity> {
        return db.userDao().getUser()
    }

    override suspend fun getSingleUser(): UserEntity {
        return db.userDao().getSingleUser()
    }

    override suspend fun clearDb() {
        db.clearAllTables()
    }

    override suspend fun resetPassword(params: Params.PasswordReset): Response<EmailResponse> {

        TODO("Not yet implemented")
    }


    override suspend fun editTutorProfile(
        id: Int,
        params: Params.EditTutorProfile
    ): EditTutorProfileResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getProfileList(): List<ProfileResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getTutorDetails(
        id: Int
    ): UserProfileResponse {
        TODO("Not yet implemented")
    }


    override suspend fun getTutorList(): Response<List<TutorNetworkResponse>> {
        TODO("Not yet implemented")
    }


    override suspend fun uploadTutorMedia(
        id: RequestBody,
        profile_pic: MultipartBody.Part,
        credentials: MultipartBody.Part,
        video: MultipartBody.Part
    ): Response<UploadResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun uploadProfilePic(
        id: Int,
        profile_pic: MultipartBody.Part
    ): UploadResponse {
        TODO("Not yet implemented")
    }

    override suspend fun uploadVideo(id: Int, video: MultipartBody.Part): UploadResponse {
        TODO("Not yet implemented")
    }

    override suspend fun uploadCredential(
        id: Int,
        credentials: MultipartBody.Part
    ): UploadResponse {
        TODO("Not yet implemented")
    }

    override suspend fun requestTutorService(params: Params.RequestTutorService): Response<TutorServiceRequestResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun saveTutorList(tutorList: List<TutorEntity>) {
        db.tutorListDao().saveTutors(tutorList)
    }

    override fun searchTutors(query: String): LiveData<List<TutorEntity>> {
        return db.tutorListDao().getSearchTutor(query)
    }

    override suspend fun clearTutorListDb() {
        db.tutorListDao().clearDatabase()
    }

    override suspend fun getTutorListDb(): List<TutorEntity> {
        return db.tutorListDao().getTutors()
    }

    override suspend fun saveUserCardDetails(params: Params.CardDetails) {
        TODO("Not yet implemented")
    }

    override suspend fun getUserCardDetails(params: Params.UserID): List<UserCardDetailResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getTutorDetailsForUser(id: Int): Response<TutorDetailsResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getTutorDetailsFromDb(id: Int): TutorDetailsEntity? {
        return db.tutorDetailsDao().getTutorDetails(id)
    }

    override suspend fun saveTutorDetailsToDb(tutorDetailsEntity: TutorDetailsEntity) {
        db.tutorDetailsDao().saveTutorDetails(tutorDetailsEntity)
    }

    override suspend fun saveUserProfile(profile: Profile) {
        db.userDao().saveUserProfile(mapProfileToEntity(profile))
    }

    override fun profileLiveData(): LiveData<ProfileEntity> {
        return db.userDao().observeableProfileData()
    }


    private fun mapUserToEntity(user: User): UserEntity {
        return UserEntity(
            id = user.id,
            is_tutor = user.isTutor,
            email = user.email,
            phone_number = user.phoneNumber,
            token = user.token,
            full_name = user.fullName
        )
    }

    override suspend fun getUserProfile(
        id: Int
    ): UserProfileResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getSingleUserProfile(): ProfileEntity {
        return db.userDao().getUserProfile()
    }

    override suspend fun getProfileId(): Int {
        return db.userDao().getUserProfile().id
    }

    override suspend fun updateTutorProfile(
        id: Int,
        params: Params.UpdateTutorProfile
    ): LoginResponse {
        TODO("Not yet implemented")
    }

    private fun mapProfileToEntity(profile: Profile): ProfileEntity {
        return ProfileEntity(
            profile.id,
            profile.profile_pic,
            profile.hourly_rate,
            profile.desc,
            profile.field,
            profile.major_course,
            profile.other_courses,
            profile.state,
            profile.address,
            profile.user_url,
            rating = profile.rating?.rating, rating_count = profile.rating?.count,
            credentials = profile.credentials,
            videoUrl = profile.videoUrl

        )
    }
}