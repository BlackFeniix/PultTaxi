package com.hito.nikolay.pulttaxi

data class SmsCodeClientResponse(
    val status: String
)

data class AuthenticateClientsToken(
    val token: String? = null,
    val error: String? = null
)

data class GetInfoClient(
    var active_order: Int? = null,
    var birth_day:String? = null,
    var city: String? = null,
    var email: String? = null,
    var id: Int? = null,
    var name: String? = null,
    var need_registration: Boolean,
    var organization_id: Int? = null,
    var phone_number: String? = null,
    var rating: String? = null,
    var sex: String? = null,

    var status: Any
)