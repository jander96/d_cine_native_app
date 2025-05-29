package com.devj.dcine.features.movies.presenter.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.devj.dcine.R
import com.devj.dcine.core.composables.AsyncImageShimmer
import com.devj.dcine.core.composables.InfiniteListRow
import com.devj.dcine.core.composables.shimmerBrush
import com.devj.dcine.core.presenter.MovieStateI
import com.devj.dcine.core.utils.extensions.toStringWithDecimal
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesList(
    modifier: Modifier = Modifier,
    listTitle: String = "",
    state: MovieStateI,
    listState: LazyListState = rememberLazyListState(),
    onLoad: suspend (Boolean) -> Unit = {},
    onItemClick: (Int) -> Unit = {}
) {
    val scope = rememberCoroutineScope()


    Column(modifier = modifier) {
        if (listTitle.isNotEmpty()) {
            Text(
                text = listTitle, modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        InfiniteListRow(
            elements = state.movies,
            paginationState = state.paginationState,
            modifier = Modifier,
            listState = listState,
            initialLoadingBuilder = {
                val screenWidth = LocalConfiguration.current.screenWidthDp.dp
                LazyRow(modifier = Modifier.width(screenWidth)) {
                    items(10) {
                        Box(
                            modifier = Modifier
                                .padding(6.dp)
                                .height(180.dp)
                                .aspectRatio(1f / 1.5f)
                                .clip(RoundedCornerShape(15.dp))
                                .background(shimmerBrush())
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center), strokeWidth = 1.dp
                            )
                        }
                    }
                }
            },
            errorBuilder = {
                Column(modifier = Modifier.fillMaxSize()) {
                    Text(text = state.error?.message ?: "", modifier = Modifier)
                    ElevatedButton(onClick = {
                        scope.launch {
                            onLoad(false)
                        }
                    }) {}
                }
            },
            itemBuilder = { movie ->

                Column(
                    modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImageShimmer(modifier = Modifier.clickable {
                        onItemClick(movie.id)
                    }, model = movie.posterPath, contentDescription = movie.title)

                    Text(
                        text = movie.title.run {
                            val maxChars = 16
                            val result = if (length > maxChars) {
                                take(maxChars) + "..."
                            } else {
                                this
                            }
                            result
                        },
                        style = MaterialTheme.typography.labelSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Row {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = movie.voteAverage.toStringWithDecimal(1),
                            style = MaterialTheme.typography.labelSmall,
                        )
                        VerticalDivider(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .size(12.dp)
                        )
                        Text(
                            text = movie.releaseDate.format(
                                DateTimeFormatter.ofPattern(
                                    "yyyy"
                                )
                            ),
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }
                }
            },
            onLoadMore = { onLoad(false) })
    }

}