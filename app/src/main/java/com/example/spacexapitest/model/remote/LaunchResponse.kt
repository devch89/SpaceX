package com.example.spacexapitest.model.remote
//region Launch Info
//endregion
data class LaunchResponse(
    val docs: List<LaunchItem>
)
data class LaunchItem(
    val links: LaunchLinks,
    val rocket: String,
    val success: Boolean,
    val name: String,
    val dat_utc: String
)
data class LaunchLinks(
    val patch: LaunchLinksPatch,
    val webcast: String,
    val article: String

)
data class LaunchLinksPatch(
    val small: String,
    val large: String
)

//region Company Info
//endregion
data class CompanyResponse(
    val name: String,
    val founder: String,
    val founded: Int,
    val employees: Int,
    val launchSite: Int,
    val evaluation: Long
)

//region Rocket Info
//endregion
data class RocketResponse(
    val name: String,
    val type: String
)
