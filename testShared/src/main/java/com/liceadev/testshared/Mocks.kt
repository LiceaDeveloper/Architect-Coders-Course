package com.liceadev.testshared

import com.liceadev.domain.Photo
import com.liceadev.domain.User

val mockedPhoto by lazy {
    Photo(
        id = 0,
        description = "description",
        likes = 0,
        urlFull = "urlFull",
        urlThumb = "urlThumb",
        user = mockedUser,
        favorite = false
    )
}

 val mockedUser = User(
    userId = "userId",
    totalPhotos = 0,
    totalLikes = 0,
    portfolioUrl = "portfolioUrl",
    profileImage = "profileImage",
    name = "name",
    location = "Mexico",
    username = "userName"
)