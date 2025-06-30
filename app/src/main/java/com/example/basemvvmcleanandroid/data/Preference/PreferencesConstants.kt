package com.trend.thecontent.data.local.preference

import androidx.compose.runtime.mutableStateOf

object PreferencesConstants {
    const val KEY_TOKEN = "token"
    const val USER_MODEL = "user_model"
    const val KEY_USERID = "userId"
    const val SIGNED_IN_STATE = "signedInState"
    const val INTRO = "intro"
    const val FINGER_PRINT = "finger_print"
    const val LANGUAGE = "language"
    const val DEFAULT_LANGUAGE = "ar"
    const val LANGUAGE_AR = "ar"
    const val LANGUAGE_EN = "en"
    const val CHOOSE_LANGUAGE = "choose_language"
    const val FCMTOKEN = "FCMToken"
    var notificationCount = mutableStateOf(0)
    var orderIDNotification = mutableStateOf(0)
    const val LOGINAUTH = "Basic Y29ycC5vdXRsZXQuaWRlbnRpdHkudWkuY2xpZW50OmM3MGFlMjU2LTUzY2MtNDk3My1hYTU0LTA0MWM2ZGUwMmE1Ng=="
    const val GRANT_TYPE = "localad"
    const val SCOPE = "corp.spotter.api corp.outlet.api"
    const val KEY_TOKEN_EXPIRATION_TIME = "tokenExpirationTime"
    const val KEY_TOKEN_TYPE = "tokenType"
    const val FIREBASE_TOKEN = "firebaseToken"
    const val LANGUAGECHOSEN = "languageChosen"
    const val USER_LOCATION_ID = "userLocationId"
    const val USER_LOCATION_NAME = "userLocationName"

}