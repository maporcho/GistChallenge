package br.com.marcoporcho.netshoesgistchallenge.use_cases.list_gists.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Gist (

    @SerializedName("id")
    var id: String,

    @SerializedName("html_url")
    var htmlUrl: String? = null,

    @SerializedName("created_at")
    var createdAt: String? = null,

    @SerializedName("updated_at")
    var updatedAt: String? = null,

    @SerializedName("description")
    var description: String? = null,

    @SerializedName("owner")
    var owner: Owner? = null,

    @SerializedName("files")
    var files: @RawValue Map<String,Map<String, Any>>? = null,

    var favorite: Boolean = false

): Parcelable