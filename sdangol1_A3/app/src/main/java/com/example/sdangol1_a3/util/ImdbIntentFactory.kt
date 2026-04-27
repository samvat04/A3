package com.example.sdangol1_a3.util

import android.content.Intent
import android.net.Uri
import com.example.sdangol1_a3.data.CastMember

object ImdbIntentFactory {

    fun buildPersonPageIntent(castMember: CastMember): Intent {
        // Uses the API provided IMDb URL, otherwise builds one from the cast member's ID
        val url = if (castMember.imdbUrl.isNotBlank()) {
            castMember.imdbUrl
        } else {
            "https://www.imdb.com/name/${castMember.imdbPersonId}/"
        }

        return Intent(Intent.ACTION_VIEW, Uri.parse(url))
    }
}
