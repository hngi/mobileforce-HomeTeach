package com.mobileforce.hometeach.data.sources

class DataSourceFactory constructor(
    private val localDataSource: DataSource,
    private val remoteDataSource: DataSource
) {

    fun local(): DataSource {
        return localDataSource
    }

    fun remote(): DataSource {
        return remoteDataSource
    }
}