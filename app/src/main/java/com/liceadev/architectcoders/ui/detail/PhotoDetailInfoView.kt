package com.liceadev.architectcoders.ui.detail

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.liceadev.architectcoders.R
import com.liceadev.domain.User

class PhotoDetailInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    fun setPhoto(user: User) = with(user) {
        text = buildSpannedString {
            bold { append("Name: ") }
            appendLine(name ?: context.getString(R.string.detail_not_data))

            bold { append("UserName: ") }
            appendLine(username ?: context.getString(R.string.detail_not_data))

            val photosCount = totalPhotos?:0
            bold { append("Total photos: ") }
            appendLine("$photosCount")

            bold { append("Portfolio Url: ") }
            appendLine(portfolioUrl ?: context.getString(R.string.detail_not_data))

            bold { append("Location: ") }
            appendLine(location ?: context.getString(R.string.detail_not_data))
        }
    }
}