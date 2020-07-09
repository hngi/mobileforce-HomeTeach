package com.mobileforce.hometeach.data.sources

class DataSourceFactory constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    fun local(): DataSource {
        return localDataSource
    }

    fun remote(): DataSource {
        return remoteDataSource
    }
}