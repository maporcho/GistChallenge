package br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.repository.converter.implementation

import android.graphics.BitmapFactory
import br.com.marcoporcho.netshoesgistchallenge.common.util.ImageUtil
import br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.model.FavoriteGist
import br.com.marcoporcho.netshoesgistchallenge.use_cases.favorite_list.repository.converter.FavoriteGistConverter
import br.com.marcoporcho.netshoesgistchallenge.use_cases.list_gists.model.Gist
import okhttp3.OkHttpClient
import okhttp3.Request


class FavoriteGistDownloaderConverter: FavoriteGistConverter {

    private val client = OkHttpClient()

    override fun toFavoriteGist(gist: Gist): FavoriteGist {
        return FavoriteGist(
            id = gist.id!!,
            owner = gist.owner?.login ?: "Anonymous",
            type = (gist.files?.values?.firstOrNull()?.getValue("type") ?: "Unknown") as String,
            avatar = downloadAvatarImageAsBase64(gist),
            htmlUrl = gist.htmlUrl,
            description = if(gist.description.isNullOrBlank()) "None" else gist.description!!
        )

    }

    private fun downloadAvatarImageAsBase64(gist: Gist): String? {
        gist.owner?.avatarUrl.let { avatarUrl->

            val request = Request.Builder().url(avatarUrl).build()

            val response = client.newCall(request).execute()

            if (response.isSuccessful && response.body() != null) {
                val inputStream = response.body()!!.byteStream()
                val bitmap = BitmapFactory.decodeStream(inputStream)
                return ImageUtil.convert(bitmap)
            }

        }

        return null
    }

}