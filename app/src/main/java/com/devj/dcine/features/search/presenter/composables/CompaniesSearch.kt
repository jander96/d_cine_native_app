package com.devj.dcine.features.search.presenter.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import com.devj.dcine.features.detail.domain.ProductionCompany

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanySearch(
    query: String,
    onQueryChange: (String) -> Unit,
    companiesResult: List<ProductionCompany>,
    onResultClick: (ProductionCompany) -> Unit,
    modifier: Modifier = Modifier,

    // Customization options
    placeholder: @Composable () -> Unit = { Text("Search") },
    leadingIcon: @Composable (() -> Unit)? = {
        Icon(
            Icons.Default.Search,
            contentDescription = "Search"
        )
    },
    trailingIcon: @Composable (() -> Unit)? = null,
    supportingContent: (@Composable (String) -> Unit)? = null,
    leadingContent: (@Composable () -> Unit)? = null,
) {
    // Track expanded state of search bar
    var expanded by rememberSaveable { mutableStateOf(false) }
    var searchQuery by rememberSaveable { mutableStateOf(query) }

    Box(
        modifier
            .height(300.dp)
            .semantics { isTraversalGroup = true }
    ) {
        Column {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    onQueryChange(it)
                    searchQuery = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .semantics { traversalIndex = 0f },
            )
            LazyColumn {
                items(companiesResult) {
                    ListItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onResultClick(it) }
                            .semantics { traversalIndex = 1f },
                        headlineContent = { Text(it.name) },
                        leadingContent = leadingContent,
                        colors = ListItemDefaults.colors(
                            containerColor = Color.Transparent,
                            headlineColor = Color.Black,
                            supportingColor = Color.Gray
                        )
                    )
                }
            }
        }
    }
}