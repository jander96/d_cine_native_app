package com.devj.dcine.core.data

import com.devj.dcine.BuildConfig
import com.devj.dcine.core.data.dtos.PaginatedResponse
import com.devj.dcine.core.data.dtos.movie.MovieDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.http.path

interface MovieApi {
    suspend fun getPopularMovies(): PaginatedResponse<MovieDto>
}

class MovieApiImpl(private val client: HttpClient) : MovieApi {
    override suspend fun getPopularMovies(): PaginatedResponse<MovieDto> {
        val response =  client.get() {
            headers {
                append(HttpHeaders.ContentType, "application/json")
            }
            url {
                protocol = URLProtocol.HTTPS
                host = "api.themoviedb.org/3"
                path("movie/popular")
                parameters.append("api_key", BuildConfig.MOVIE_API_KEY)
            }

        }


        return response.body()

    }

}