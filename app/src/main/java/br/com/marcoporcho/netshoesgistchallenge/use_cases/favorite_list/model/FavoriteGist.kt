package br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.lang.reflect.Constructor

@Parcelize
@Entity(tableName = "favorite_gists")
data class FavoriteGist (
    @PrimaryKey val id: String,
    @ColumnInfo val avatar: String?,
    @ColumnInfo val owner: String,
    @ColumnInfo val type: String,
    @ColumnInfo val htmlUrl: String?,
    @ColumnInfo val description: String) : Parcelable {
    constructor(id: String): this(id= id, avatar = null, owner = "", type = "", htmlUrl = "", description = "")
}