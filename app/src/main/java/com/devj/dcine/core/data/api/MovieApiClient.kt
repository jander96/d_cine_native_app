package com.devj.dcine.core.data.api

import com.devj.dcine.BuildConfig
import com.devj.dcine.core.data.api.dtos.PaginatedResponse
import com.devj.dcine.core.data.api.dtos.movie.MovieDetailDto
import com.devj.dcine.core.data.api.dtos.movie.MovieDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.http.appendPathSegments
import io.ktor.http.path

class MovieApiImpl(private val client: HttpClient) : MovieApi {
    override suspend fun getPopularMovies(
        page: Int?,
        startDate: String?,
        endDate: String?
    ): PaginatedResponse<MovieDto> {
        val response = client.get {
            headers {
                append(HttpHeaders.ContentType, "application/json")
            }
            url {
                path("movie/popular")
                parameters.append("page", page.toString())
                startDate?.let {
                    parameters.append("start_date", it)
                }
                endDate?.let {
                    parameters.append("end_date", it)
                }
            }

        }


        return response.body()

    }

    override suspend fun getMovie(id: Int): MovieDetailDto {
        return  client.get{
            url {
                path("movie")
                appendPathSegments(id.toString())
            }
        }.body()
    }


}