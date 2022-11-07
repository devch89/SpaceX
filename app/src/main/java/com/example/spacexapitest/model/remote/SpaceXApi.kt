package com.example.spacexapitest.model.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SpaceXApi {
    @POST(ROUTES_LAUNCHES)
    suspend fun getAllLaunches(): LaunchResponse
    @GET(ROUTES_COMPANAY)
    suspend fun getCompanyInfo(): Response<CompanyResponse>
    @GET(ROUTES_ROCKET)
    suspend fun getRocketInfo(
        @Path(ROCKET_ID) rocketId: String
    ): RocketResponse
    }