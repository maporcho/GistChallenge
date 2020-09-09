package br.com.marcoporcho.netshoesgistchallenge.use_cases.list_gists.model

import android.os.Parcelable
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Owner (

    @SerializedName("login")
    @Expose
    var login: String? = null,

    @SerializedName("avatar_url")
    @Expose
    var avatarUrl: String? = null,

    @SerializedName("html_url")
    @Expose
    var htmlUrl: String? = null

): Parcelable