package com.trend.thecontent.data.local.preference

import android.content.Context
import android.content.SharedPreferences
import com.aramex.mypos.Data.Preference.Preferences
import com.google.gson.Gson
import com.trend.thecontent.data.local.preference.PreferencesConstants.BASE_URL
import com.trend.thecontent.data.local.preference.PreferencesConstants.DEFAULT_LANGUAGE
import com.trend.thecontent.data.local.preference.PreferencesConstants.FIREBASE_TOKEN
import com.trend.thecontent.data.local.preference.PreferencesConstants.GRANT_TYPE
import com.trend.thecontent.data.local.preference.PreferencesConstants.KEY_TOKEN
import com.trend.thecontent.data.local.preference.PreferencesConstants.KEY_TOKEN_EXPIRATION_TIME
import com.trend.thecontent.data.local.preference.PreferencesConstants.KEY_TOKEN_TYPE
import com.trend.thecontent.data.local.preference.PreferencesConstants.KEY_USERID
import com.trend.thecontent.data.local.preference.PreferencesConstants.LANGUAGE
import com.trend.thecontent.data.local.preference.PreferencesConstants.LANGUAGECHOSEN
import com.trend.thecontent.data.local.preference.PreferencesConstants.LOGINAUTH
import com.trend.thecontent.data.local.preference.PreferencesConstants.SCOPE
import com.trend.thecontent.data.local.preference.PreferencesConstants.SIGNED_IN_STATE
import com.trend.thecontent.data.local.preference.PreferencesConstants.USER_MODEL
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SavePreferences @Inject constructor(
    @ApplicationContext context: Context
): Preferences {

    companion object {
        const val PREFERENCES_NAME = "My_POS_Preferences"
        const val language = "ar"
        var signedInState = false
    }

    private val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    override fun putToken(token: String) {
        edit { putString(KEY_TOKEN, token) }
    }


    private inline fun edit(block: SharedPreferences.Editor.() -> Unit) {
        with(preferences.edit()) {
            block()
            commit()
        }
    }

    override fun putLanguage(language: String) {
        edit { putString(LANGUAGE, language) }
    }

    override fun getLanguage(): String {
        return preferences.getString(LANGUAGE, DEFAULT_LANGUAGE).orEmpty()
    }

    override fun getAuthLogin(): String {
         return preferences.getString(LOGINAUTH, "").orEmpty()
    }

    override fun putGrantType(granType: String) {
        edit { putString(GRANT_TYPE, granType) }
    }

    override fun getGrantType(): String {
        return preferences.getString(GRANT_TYPE, "").orEmpty()
    }

    override fun putScope(scope: String) {
        edit { putString(SCOPE, scope) }
    }

    override fun getScope(): String {
        return preferences.getString(SCOPE, "").orEmpty()
    }

    override fun putUserId(userId: Int) {
        edit { putInt(KEY_USERID, userId) }
    }

    override fun getUserId(): Int {
        return preferences.getInt(KEY_USERID, 0)
    }

    override fun putTokenExpirationTime(time: Long) {
        edit { putLong(KEY_TOKEN_EXPIRATION_TIME, time) }
    }

    override fun putTokenType(tokenType: String) {
        edit { putString(KEY_TOKEN_TYPE, tokenType) }
    }

    override fun getToken(): String {
        return preferences.getString(KEY_TOKEN, "").orEmpty()
    }

    override fun getTokenExpirationTime(): Long {
        return preferences.getLong(KEY_TOKEN_EXPIRATION_TIME, -1)
    }

    override fun getTokenType(): String {
        return preferences.getString(KEY_TOKEN_TYPE, "").orEmpty()
    }

    override fun deleteTokenInfo() {
        edit {
            remove(KEY_TOKEN)
            remove(KEY_TOKEN_EXPIRATION_TIME)
            remove(KEY_TOKEN_TYPE)
        }
    }



    override fun clearPreferences() {
        edit { clear() }
    }

    fun clearToken() {
        PreferencesConstants.orderIDNotification.value = 0
        putToken("")
    }

    override fun putBaseUrl(baseUrl: String) {
        edit { putString(BASE_URL, baseUrl) }
    }

    override fun getBaseUrl(): String {
        return preferences.getString(BASE_URL, "").orEmpty()
    }

    override fun putOrganizer(organizer: String) {
        edit { putString(PreferencesConstants.ORGANIZER, organizer) }
    }

    override fun getOrganizer(): String {
        return preferences.getString(PreferencesConstants.ORGANIZER, "").orEmpty()
    }

    override fun putIsSport(isSport: Boolean?) {
        edit {
            if (isSport != null) {
                putBoolean("isSport", isSport)
            } else {
                remove("isSport") // Remove the key entirely when null
            }
        }
    }

    override fun getIsSport(): Boolean? {
        return if (preferences.contains("isSport")) {
            preferences.getBoolean("isSport", false)
        } else {
            null // Return null if the key doesn't exist
        }
    }

    override fun removeIsSport() {
        edit { remove("isSport") }
    }

    // New methods for selected event and checkin list ID
    override fun putSelectedEventSlug(eventSlug: String) {
        edit { putString("selected_event_slug", eventSlug) }
    }

    override fun getSelectedEventSlug(): String {
        return preferences.getString("selected_event_slug", "").orEmpty()
    }

    override fun putCheckInListId(checkInListId: Int) {
        edit { putInt("checkin_list_id", checkInListId) }
    }

    override  fun getCheckInListId(): Int {
        return preferences.getInt("checkin_list_id", -1)
    }

    override  fun clearSelectedEventData() {
        edit {
            remove("selected_event_slug")
            remove("checkin_list_id")
        }
    }

}