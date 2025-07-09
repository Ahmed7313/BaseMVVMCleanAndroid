package com.aramex.mypos.Data.Preference


interface Preferences {

    fun putToken(token: String)

    fun getToken(): String

    fun clearPreferences()

    fun putLanguage(language: String)

    fun getLanguage(): String

    fun getAuthLogin(): String

    fun putGrantType(granType: String)

    fun getGrantType(): String

    fun putScope (scope : String)

    fun getScope (): String

    fun putUserId (userId: Int)

    fun getUserId() : Int


    fun putTokenExpirationTime(time: Long)

    fun putTokenType(tokenType: String)

    fun getTokenExpirationTime(): Long

    fun getTokenType(): String

    fun deleteTokenInfo()


    fun putBaseUrl(baseUrl: String)

    fun getBaseUrl(): String

    fun putOrganizer(organizer:String)

    fun getOrganizer(): String

    fun putIsSport(isSport: Boolean?)

    fun getIsSport(): Boolean?

    fun removeIsSport()

    fun putSelectedEventSlug(eventSlug: String)

    fun getSelectedEventSlug(): String

    fun putCheckInListId(checkInListId: Int)

    fun getCheckInListId(): Int

    fun clearSelectedEventData()


}