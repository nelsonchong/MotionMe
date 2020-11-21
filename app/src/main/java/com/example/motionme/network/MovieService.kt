package com.example.motionme.network

interface MovieService {

}

class MovieServiceImpl(
    private val apiService: ApiService
): MovieService {

}