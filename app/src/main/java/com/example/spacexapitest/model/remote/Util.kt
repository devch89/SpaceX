package com.example.spacexapitest.model.remote

// defines the constant values used for the remote package
// ENDPOINTS are also considered Routes

const val BASE_URL = "https://api.spacexdata.com"
const val ROUTES_LAUNCHES = "v4/launches/query"
const val ROUTES_COMPANAY = "v4/company"
const val ROCKET_ID = "rocket_id"
const val ROUTES_ROCKET = "v4/rockets/{$ROCKET_ID}"
