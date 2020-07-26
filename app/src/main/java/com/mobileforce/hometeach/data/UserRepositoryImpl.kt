package com.mobileforce.hometeach.data

import androidx.lifecycle.LiveData
import com.mobileforce.hometeach.data.model.User
import com.mobileforce.hometeach.data.repository.UserRepository
import com.mobileforce.hometeach.data.sources.DataSourceFactory
import com.mobileforce.hometeach.data.sources.local.entities.*
import com.mobileforce.hometeach.data.sources.remote.Params
import com.mobileforce.hometeach.data.sources.remote.wrappers.*
import retrofit2.Response


class UserRepositoryImpl(private val dataSource: DataSourceFactory) : UserRepository {

    override suspend fun login(params: Params.SignIn): LoginResponse {
        return dataSource.remote().logIn(params)
    }

    override suspend fun register(params: Params.SignUp): RegisterUserResponse {
        return dataSource.remote().signUp(params)
    }

    override suspend fun saveUser(user: User) {
        dataSource.local().saveUser(user)
    }

    override suspend fun logOut() {
        dataSource.local().clearDb()
    }
//
//    override suspend fun modify() {
//        dataSource.local().clearDb()
//    }

    override suspend fun editTutorProfile(
        id: Int,
        params: Params.EditTutorProfile
    ): EditTutorProfileResponse {
        return dataSource.remote().editTutorProfile(id, params)
    }

    override suspend fun getProfileList(): List<ProfileResponse> {
        return dataSource.remote().getProfileList()
    }

    override suspend fun getTutorDetails(): UserProfileResponse {
        val user = dataSource.local().getSingleUser()
        return dataSource.remote().getTutorDetails(user.id.toDouble().toInt())
    }

    override fun getUser(): LiveData<UserEntity> {
        return dataSource.local().getUser()
    }

    override suspend fun getTutorList(): Response<List<TutorNetworkResponse>> {
        return dataSource.remote().getTutorList()
    }

    override suspend fun requestTutorService(params: Params.RequestTutorService): Response<TutorServiceRequestResponse> {
        return dataSource.remote().requestTutorService(params)
    }

    override suspend fun saveTutorList(tutorList: List<TutorEntity>) {
        dataSource.local().saveTutorList(tutorList)
    }

    override fun searchTutor(query: String): LiveData<List<TutorEntity>> {
        return dataSource.local().searchTutors(query)
    }

    override suspend fun clearTutorListDb() {
        dataSource.local().clearTutorListDb()
    }

    override suspend fun getTutorListDb(): List<TutorEntity> {
        return dataSource.local().getTutorListDb()
    }

    override suspend fun saveUserCardDetails(params: Params.CardDetails) {
        dataSource.remote().saveUserCardDetails(params)

    }

    override suspend fun getUserCardDetails(): List<UserCardDetailResponse> {
        val user = dataSource.local().getSingleUser()
        val userId = Params.UserID(user = user.id)
        return dataSource.remote().getUserCardDetails(userId)
    }

    override suspend fun getSingleUser(): UserEntity {
        return dataSource.local().getSingleUser()
    }

    override suspend fun passwordReset(params: Params.PasswordReset): Response<EmailResponse> {
        return dataSource.remote().resetPassword(params)
    }

    override suspend fun saveUserProfile(profile: Profile) {
        dataSource.local().saveUserProfile(profile)
    }

    override fun profileLiveData(): LiveData<ProfileEntity> {
        return dataSource.local().profileLiveData()
    }


    override suspend fun modify() {
        return dataSource.local().clearDb()
    }

    override suspend fun save() {
        return dataSource.local().clearDb()
    }

    override suspend fun getSingleUserProfile(): ProfileEntity {
        return dataSource.local().getSingleUserProfile()
    }

    override suspend fun getUserProfile(): UserProfileResponse {
        val user = getSingleUser()
        val profile = dataSource.local().getSingleUserProfile()
        return if (user.is_tutor) {
            dataSource.remote().getTutorDetails(profile.id)
        } else {
            dataSource.remote().getUserProfile(profile.id)
        }

    }

    override suspend fun getTutorDetailsForUser(id: Int): Response<TutorDetailsResponse> {
        return dataSource.remote().getTutorDetailsForUser(id)
    }

    override suspend fun getTutorDetailsForUserDb(id: Int): TutorDetailsEntity? {
        return dataSource.local().getTutorDetailsFromDb(id)
    }

    override suspend fun saveTutorDetailsForUserDb(tutorDetailsEntity: TutorDetailsEntity) {
        dataSource.local().saveTutorDetailsToDb(tutorDetailsEntity)
    }

    override suspend fun getStudentClassRequest(): UserClassRequestResponse {
        val user = dataSource.local().getSingleUser()
        val studentId = Params.StudentID(student_id = user.id)
        return dataSource.remote().getStudentClassRequest(studentId)
    }

    override suspend fun getUserWallet(): UserWalletResponse {
        val user = getSingleUser()
        val param = Params.UserWallet(user = user.id)
        return dataSource.remote().getUserWallet(param)
    }

    override suspend fun saveWallet(walletData: WalletData) {

        val wallet = WalletEntity(
            total_balance = walletData.total_balance,
            availableBalance = walletData.available_balance
        )
        dataSource.local().saveUserWallet(wallet)

    }

    override fun observeWalletData(): LiveData<WalletEntity> {
        return dataSource.local().observeWalletData()
    }

    override suspend fun saveCardToDb(cardEntity: CardEntity) {
        dataSource.local().saveCardToDb(cardEntity)
    }

    override fun observeUSerCards(): LiveData<List<CardEntity>> {
        return dataSource.local().observeUserCards()
    }
}