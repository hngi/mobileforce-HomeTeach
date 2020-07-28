package com.mobileforce.hometeach.data.sources

import androidx.lifecycle.LiveData
import com.mobileforce.hometeach.data.model.User
import com.mobileforce.hometeach.data.sources.local.entities.ProfileEntity
import com.mobileforce.hometeach.data.sources.local.entities.TutorDetailsEntity
import com.mobileforce.hometeach.data.sources.local.entities.TutorEntity
import com.mobileforce.hometeach.data.sources.local.entities.UserEntity
import com.mobileforce.hometeach.data.sources.remote.Api
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.data.sources.remote.wrappers.*
import com.mobileforce.hometeach.models.TutorRequestDataModel
import com.mobileforce.hometeach.models.TutorUpcomingDataModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class RemoteDataSource(private val api: Api) : DataSource {

    override suspend fun logIn(params: Params.SignIn): LoginResponse {
        val map = hashMapOf(
            "email" to params.email,
            "password" to params.password,
            "is_tutor" to false
        )
        return api.login(map)
    }

    override suspend fun signUp(params: Params.SignUp): RegisterUserResponse {

        val map = hashMapOf(
            "full_name" to params.full_name,
            "email" to params.email,
            "organization_email" to params.organization_email,
            "phone_number" to params.phone_number,
            "is_tutor" to params.is_tutor,
            "password" to params.password
        )
        return api.register(map)
    }

    override suspend fun saveUser(user: User) {
        TODO("Not yet implemented")
    }


    override fun getUser(): LiveData<UserEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getSingleUser(): UserEntity {
        TODO("Not yet implemented")
    }

    override suspend fun editTutorProfile(
        id: Int,
        params: Params.EditTutorProfile
    ): EditTutorProfileResponse {
        return api.editTutorProfile(id, params)
    }

    override suspend fun getProfileList(): List<ProfileResponse> {
        return api.getProfileList()
    }

    override suspend fun getTutorDetails(
        id: Int
    ): UserProfileResponse {
        return api.getTutorDetails(id)
    }

    override suspend fun clearDb() {
        TODO("Not yet implemented")
    }

    override suspend fun getTutorList(): Response<List<TutorNetworkResponse>> {
        return api.getTutorList()
    }

    override suspend fun saveUserCardDetails(params: Params.CardDetails) {
        val map = hashMapOf(
            "user" to params.user,
            "card_holder_name" to params.card_holder_name,
            "card_number" to params.card_number,
            "cvv" to params.cvv,
            "expiry_month" to params.expiry_month,
            "expiry_year" to params.expiry_year
        )
        api.saveUserCardDetails(map)
    }

    override suspend fun getUserCardDetails(params: Params.UserID): List<UserCardDetailResponse> {
        val map = hashMapOf(
            "user" to params.user
        )
        return api.getUserCardDetails(map)
    }

    override suspend fun getTutorDetailsForUser(id: Int): Response<TutorDetailsResponse> {
        return api.getTutorDetailsForUser(id)
    }


    override suspend fun uploadTutorMedia(
        id: RequestBody,
        profile_pic: MultipartBody.Part,
        credentials: MultipartBody.Part,
        video: MultipartBody.Part
    ): Response<UploadResponse> {
        return api.uploadTutorMedia(id, profile_pic, credentials, video)
    }

    override suspend fun uploadProfilePic(
        id: Int,
        profile_pic: MultipartBody.Part
    ): UploadResponse {
        return api.uploadProfilePic(id,profile_pic)
    }

    override suspend fun uploadVideo(
        id: Int,
        video: MultipartBody.Part
    ): UploadResponse{
       return api.uploadVideo(id,video)
    }

    override suspend fun uploadCredential(
        id: Int,
        credentials: MultipartBody.Part
    ):UploadResponse{
      return api.uploadCredential(id,credentials)
    }


    override suspend fun resetPassword(params: Params.PasswordReset): Response<EmailResponse> {
        val map = hashMapOf(
            "email" to params.email
        )
        return api.resetPassword(map)
    }


    override suspend fun requestTutorService(params: Params.RequestTutorService): Response<TutorServiceRequestResponse> {
        return api.requestTutorService(params)
    }

    override suspend fun saveUserProfile(profile: Profile) {
        TODO("Not yet implemented")
    }

    override fun profileLiveData(): LiveData<ProfileEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun saveTutorList(tutorList: List<TutorEntity>) {
        TODO("Not yet implemented")
    }

    override fun searchTutors(query: String): LiveData<List<TutorEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun clearTutorListDb() {
        TODO("Not yet implemented")
    }

    override suspend fun getTutorListDb(): List<TutorEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getTutorDetailsFromDb(id: Int): TutorDetailsEntity {
        TODO("Not yet implemented")
    }

    override suspend fun saveTutorDetailsToDb(tutorDetailsEntity: TutorDetailsEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun getUserProfile(
        id: Int
    ): UserProfileResponse {
        return api.getUserProfile(id)
    }

    override suspend fun getSingleUserProfile(): ProfileEntity {
        TODO("Not yet implemented")
    }

    override suspend fun getProfileId(): Int {
        TODO("Not yet implemeaddressaddressnted")
    }

    override suspend fun updateTutorProfile(
        id: Int,
        params: Params.UpdateTutorProfile
    ): LoginResponse {

        val map = mapOf(
            "field" to params.field,
            "major_course" to params.major_course,
            "other_courses" to params.other_courses,
            "state" to params.state,
            "address" to params.address,
            "hourly_rate" to params.hourly_rate,
            "desc" to params.desc
        )
        return api.updateTutorProfile(id, map)
    }

    override suspend fun getUserWallet(param: Params.UserWallet): UserWalletResponse {

        return api.getUserWallet(param)
    }

    override suspend fun getStudentClassRequest(param: Params.StudentID): UserClassRequestResponse {
        val map = mapOf(
            "student_id" to param.student_id
        )
        return api.getStudentClassRequest(map)
    }

    override suspend fun getStudentClasses(param: Params.StudentID): UserClassesResponse {
        val map = mapOf(
            "student_id" to param.student_id
        )
        return api.getStudentClasses(map)
    }

    override suspend fun getTutorClassesRequest(param: Params.TutorClassesRequest):TutorRequestDataModel{
        val map = mapOf(
            "tutor_id" to param.tutor_id
        )
        return api.getTutorClassesRequest(map)
    }

    override suspend fun getTutorClasses(param: Params.TutorClassesRequest): TutorUpcomingDataModel {

        val map = mapOf(
            "tutor_id" to param.tutor_id
        )
        return api.getTutorClasses(map)

    }

    override suspend fun grantStudentRequest(params: Params.StudentRequest): StudentRequestResponse {
        val map = mapOf(
            "id" to params.id,
            "request_id" to params.request_id,
            "action" to params.action
        )
        return api.grantStudentRequest(map)
    }

}
