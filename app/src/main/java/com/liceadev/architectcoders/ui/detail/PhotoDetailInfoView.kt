package com.liceadev.architectcoders.ui.detail

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.liceadev.architectcoders.model.User

class PhotoDetailInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    fun setPhoto(user: User) = with(user) {
        text = buildSpannedString {
            bold { append("Name: ") }
            appendln(name?:"")

            bold { append("UserName: ") }
            appendln(username?:"")

            bold { append("portfolio Url: ") }
            appendln(portfolioUrl?:"")

            bold { append("location: ") }
            appendln(location)
        }
    }
}