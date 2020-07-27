package com.mobileforce.hometeach.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobileforce.hometeach.data.repository.UserRepository
import com.mobileforce.hometeach.data.sources.remote.wrappers.UserClassRequestResponse
import com.mobileforce.hometeach.data.sources.local.entities.WalletEntity
import com.mobileforce.hometeach.data.sources.remote.wrappers.UserClassesResponse
import com.mobileforce.hometeach.models.TutorModel
import com.mobileforce.hometeach.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomePageViewModel(
    private val userRepository: UserRepository,
    private val preferenceHelper: PreferenceHelper
) : ViewModel() {

    val user = userRepository.getUser()
    val profile = userRepository.profileLiveData()

    //List of all tutors
    private val _tutorList = MutableLiveData<Result<List<TutorModel>>>()
    val tutorList = _tutorList.asLiveData()

    //Student class response for upcoming and ongoing class
    private val _studentClass = MutableLiveData<Result<UserClassesResponse>>()
    val studentClass = _studentClass.asLiveData()

    val wallet: LiveData<WalletEntity> = userRepository.observeWalletData()

    init {
        fetchUserWallet()
    }

    /**
     * This initially attempts to get data from the cache. If this is empty, it would
     * fetch from the remote source.
     */
    fun getTutorList() {
        viewModelScope.launch {
            val listTutors = userRepository.getTutorListDb()
            if (listTutors.isNullOrEmpty()) {
                refreshTutorList()
            } else {
                _tutorList.postValue(Result.Success(listTutors.map { it.toDomainModel() }))
            }
        }
    }


    /**
     * This function fetches a fresh list of [TutorModel] from the remote source.
     * This is called also when the user swipes down on the screen.
     */
    private fun refreshTutorList() {
        _tutorList.postValue(Result.Loading)
        viewModelScope.launch {
            try {
                val response = userRepository.getTutorList()
                if (response.isSuccessful) {
                    val tutorListResponse = response.body()
                    val listOfTutors = tutorListResponse?.map {
                        it.toDomainModel()
                    }
                    _tutorList.postValue(Result.Success(listOfTutors))
                    userRepository.clearTutorListDb()
                    userRepository.saveTutorList(listOfTutors!!.map { it.toDbEntity() })

                } else {
                    _tutorList.postValue(Result.Success(null))
                }
            } catch (exception: Exception) {
                _tutorList.postValue(Result.Error(exception))
            }
        }
    }

    /**
     * This function fetches an instance of [UserClassesResponse] from the remote source.
     * From which we can get the details about upcoming and ongoing classes
     */
    fun getStudentClasses() {
        _studentClass.postValue(Result.Loading)
        viewModelScope.launch {
            try {
                val response = userRepository.getStudentClasses()
                _studentClass.postValue(Result.Success(response))
            } catch (error: Throwable) {
                _studentClass.postValue(Result.Error(error))
            }
        }
    }

    fun logOut() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.logOut()
            preferenceHelper.isLoggedIn = false
        }
    }

    fun modify() {
        viewModelScope.launch {
            userRepository.modify()
            preferenceHelper.isLoggedIn = false
        }
    }


    private fun fetchUserWallet() {

        viewModelScope.launch {

            try {
                val response = userRepository.getUserWallet()

                if (response.status == "valid") {
                    //save to db
                    userRepository.saveWallet(response.data)
                }

            } catch (e: Exception) {

            }

            //save wallet to db
        }
    }

}