package com.aramex.mypos.Data.remote

import androidx.compose.runtime.mutableStateOf

object APIConstants {
    const val BASE_URL_DEV = "https://seeq-testing.apps.fslabs.net/"
    const val STAGING_URL = ""
    const val LIVE_URL = ""
    const val AUTH_ENDPOINT = "Authorization/"
    const val ACCEPT = "Accept"
    const val ACCEPT_LANGUAGE = "Accept-Language"
    var versionNumber = mutableStateOf("")
}

object ApiParameters {
    const val TOKEN_TYPE = "Bearer "
    const val CONTENT_TYPE = "Content-Type"
    const val COOKIE = "Cookie"
    const val AUTH_HEADER = "Authorization"
    const val TOKEN_GENERATOR = "Basic Y29ycC5vdXRsZXQuaWRlbnRpdHkudWkuY2xpZW50OjI2N2JlMmQxLWQ1YjQtNDc0Zi05NjIwLWNmZTNlYTZlNDFjNw=="
    const val GRANT_TYPE_KEY = "grant_type"
    const val GRANT_TYPE_VALUE = "client_credentials"
    const val CLIENT_ID = "client_id"
    const val CLIENT_SECRET = "client_secret"
    const val HTML_HEADER = "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9"
    const val SCOPE = "corp.spotter.api corp.outlet.api"
    const val PAGE = "page"
    const val LIMIT = "limit"
    const val LOCATION = "location"
    const val DISTANCE = "distance"
    const val NAME = "name"
    const val AGE = "age"
    const val TYPE = "type"
}