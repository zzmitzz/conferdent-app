package com.ptit_booth_chekin.project.ui.screen.home.screen_search.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.ptit_booth_chekin.project.R
import com.ptit_booth_chekin.project.ui.screen.home.screen_search.FilterOption
import com.ptit_booth_chekin.project.ui.theme.JosefinSans


@Composable
fun SearchComponents(
    isFilterVisible: Boolean = false,
    selectedFilter: FilterOption = FilterOption.NEWEST,
    onSearchKeyChange: (String) -> Unit = {},
    onFilterClick: () -> Unit = {},
    onFilterSelect: (FilterOption) -> Unit = {}
) {
    var searchKey by remember { mutableStateOf("") }
    Column {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(53.dp),
            value = searchKey,
            onValueChange = {
                searchKey = it
                onSearchKeyChange(it)
            },
            placeholder = {
                Text(
                    stringResource(R.string.search),
                    color = Color("#B5B4B4".toColorInt())
                )
            },
            prefix = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    tint = Color("#22272F".toColorInt())
                )
            },
            suffix = {
                Image(
                    painter = painterResource(id = R.drawable.ic_filter),
                    contentDescription = null,
                    modifier = Modifier.clickable { onFilterClick() }
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(100.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                errorContainerColor = Color.White,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent
            ),
        )
        
        if (isFilterVisible) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FilterChip(
                    text = "Mới nhất",
                    isSelected = selectedFilter == FilterOption.NEWEST,
                    onClick = { onFilterSelect(FilterOption.NEWEST) }
                )
                FilterChip(
                    text = "A-Z",
                    isSelected = selectedFilter == FilterOption.A_Z,
                    onClick = { onFilterSelect(FilterOption.A_Z) }
                )
                FilterChip(
                    text = "Z-A",
                    isSelected = selectedFilter == FilterOption.Z_A,
                    onClick = { onFilterSelect(FilterOption.Z_A) }
                )
                FilterChip(
                    text = "Sắp diễn ra",
                    isSelected = selectedFilter == FilterOption.UPCOMING,
                    onClick = { onFilterSelect(FilterOption.UPCOMING) }
                )
            }
        }
    }
}

@Composable
private fun FilterChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Text(
        text = text,
        modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .background(if (isSelected) Color.Black else Color.White)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        color = if (isSelected) Color.White else Color.Black,
        fontFamily = JosefinSans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    )
}