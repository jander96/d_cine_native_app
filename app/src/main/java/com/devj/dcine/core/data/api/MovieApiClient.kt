package com.devj.dcine.core.data.api

import com.devj.dcine.BuildConfig
import com.devj.dcine.core.data.api.dtos.PaginatedResponse
import com.devj.dcine.core.data.api.dtos.cast.CastResponse
import com.devj.dcine.core.data.api.dtos.movie.GenreDto
import com.devj.dcine.core.data.api.dtos.movie.GenreListDto
import com.devj.dcine.core.data.api.dtos.movie.MovieDetailDto
import com.devj.dcine.core.data.api.dtos.movie.MovieDto
import com.devj.dcine.core.data.api.dtos.movie.ProductionCompanyDto
import com.devj.dcine.core.data.api.dtos.video.VideoResponseDto
import com.devj.dcine.features.filters.domain.models.MovieFilter
import com.devj.dcine.features.filters.domain.models.SortBy
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpSendPipeline
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

    override suspend fun getSimilarMovies(
        id: Int,
        page: Int?
    ): PaginatedResponse<MovieDto> {
//        https://api.themoviedb.org/3/movie/{movie_id}/similar
        return client.get {
            url {
                path("movie")
                appendPathSegments(id.toString())
                appendPathSegments("similar")
                parameters.append("page", page.toString())
            }
        }.body()
    }

    override suspend fun getTopRatedMovies(
        page: Int?,
        startDate: String?,
        endDate: String?
    ): PaginatedResponse<MovieDto> {
        val response = client.get {
            headers {
                append(HttpHeaders.ContentType, "application/json")
            }
            url {
                path("movie/top_rated")
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

    override suspend fun getNowPlayingMovies(
        page: Int?,
        startDate: String?,
        endDate: String?
    ): PaginatedResponse<MovieDto> {
        val response = client.get {
            headers {
                append(HttpHeaders.ContentType, "application/json")
            }
            url {
                path("movie/now_playing")
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

    override suspend fun getUpcomingMovies(
        page: Int?,
        startDate: String?,
        endDate: String?
    ): PaginatedResponse<MovieDto> {
        val response = client.get {
            headers {
                append(HttpHeaders.ContentType, "application/json")
            }
            url {
                path("movie/upcoming")
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
        return client.get {
            url {
                path("movie")
                appendPathSegments(id.toString())
            }
        }.body()
    }

    override suspend fun getCasting(id: Int): CastResponse {
        // https://api.themoviedb.org/3/movie/{movie_id}/credits
        return client.get {
            url {
                path("movie")
                appendPathSegments(id.toString())
                appendPathSegments("credits")
            }
        }.body()
    }

    override suspend fun getMovieVideos(id: Int): VideoResponseDto {
        // https://api.themoviedb.org/3/movie/950387/videos
        return client.get {
            url {
                path("movie")
                appendPathSegments(id.toString())
                appendPathSegments("videos")
            }
        }.body()
    }

    override suspend fun discoverMovies(
        filter: MovieFilter,
        page: Int,
        order: SortBy,
    ): PaginatedResponse<MovieDto> {

        return client.get {
            url {
                path("discover/movie")
                parameters.append("page", page.toString())
                parameters.append("include_adult", filter.includeAdult.toString())
                parameters.append("include_video", filter.includeVideo.toString())
                parameters.append("language", filter.language ?: "en-US")
                parameters.append("sort_by", order.value)
                filter.year?.let { parameters.append("primary_release_year", it.toString()) }
                filter.voteAverage?.let { parameters.append("vote_average.gte", it.toString()) }
                filter.country?.let { parameters.append("with_origin_country", it) }
                filter.genres.takeIf { it.isNotEmpty() }?.let { genres ->
                    parameters.append("with_genres", genres.joinToString(",") { it.id.toString() })
                }
                filter.companies.takeIf { it.isNotEmpty() }?.let { companies ->
                    parameters.append(
                        "with_companies",
                        companies.joinToString("|") { it.id.toString() })
                }

            }
        }.body()
    }

    override suspend fun getGenres(): List<GenreDto> {
        // https://api.themoviedb.org/3/genre/movie/list
        return client.get {
            url {
                path("genre/movie/list")
            }
        }.body<GenreListDto>().genres
    }

    override suspend fun searchCompany(
        query: String,
        page: Int
    ): PaginatedResponse<ProductionCompanyDto> {
        //https://api.themoviedb.org/3/search/company
        return client.get {
            url {
                path("search/company")
                parameters.append("query", query)
                parameters.append("page", page.toString())
            }
        }.body()
    }


}