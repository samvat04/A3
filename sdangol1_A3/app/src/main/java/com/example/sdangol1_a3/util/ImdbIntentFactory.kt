package com.example.sdangol1_a3.util

import android.content.Intent
import android.net.Uri

object ImdbIntentFactory {
    fun buildPersonIntent(imdbPersonId: String): Intent {
        val url = "https://www.imdb.com/name/$imdbPersonId/"
        return Intent(Intent.ACTION_VIEW, Uri.parse(url))
    }
}
