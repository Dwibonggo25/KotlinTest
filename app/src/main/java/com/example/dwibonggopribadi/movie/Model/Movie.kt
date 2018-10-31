package com.example.dwibonggopribadi.movie.Model

import android.media.Rating
import com.google.gson.annotations.SerializedName

data class Movie(
                @SerializedName("poster_path") val posterPath : String?,
                @SerializedName("original_title") val originalTitle : String?,
                 @SerializedName("overview") val overview : String?,
                 @SerializedName("vote_average") val vote_avarage : String?,
                 @SerializedName("release_date") val release_date : String?,
                 @SerializedName("original_language") val genre : String?,
                 @SerializedName("popularity") val popularity : String?
                 )