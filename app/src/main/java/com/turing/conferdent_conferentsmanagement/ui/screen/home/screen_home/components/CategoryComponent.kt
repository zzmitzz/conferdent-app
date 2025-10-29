package com.turing.conferdent_conferentsmanagement.ui.screen.home.screen_home.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.turing.conferdent_conferentsmanagement.R
import com.turing.conferdent_conferentsmanagement.ui.theme.JosefinSans
import com.turing.conferdent_conferentsmanagement.utils.Constants


@Composable
fun CategoryIcon(
    @DrawableRes icon: Int,
    @StringRes name: Int
){
    Column(
        modifier = Modifier
            .background(
                shape = RoundedCornerShape(12.dp),
                color = Color.White
            )
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(icon),
            contentDescription = ""
        )
        Spacer(
            Modifier.height(8.dp)
        )
        Text(
            text = stringResource(name),
            color = Color.Black,
            fontFamily = JosefinSans,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        )
    }
}

data class CategoryUIWrapper(
    val id: String,
    val icon: Int,
    val name: Int
)

@Composable
fun CategoryComponent(
    listCategory: List<CategoryUIWrapper> = categoryList
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier.padding(start = 16.dp)
        ){
            Text(
                text = "Danh mục",
                color = Color.Black,
                fontFamily = JosefinSans,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow {
            items(1){
                Spacer(modifier = Modifier.width(16.dp))
            }
            items(listCategory.size) {
                Box(
                    modifier = Modifier.padding(end = 16.dp)
                ){
                    CategoryIcon(
                        icon = listCategory[it].icon,
                        name = listCategory[it].name
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun CatePrev() {
    CategoryComponent()
}

val categoryList = Constants.EventCategory.entries.map {
    CategoryUIWrapper(
        id = it.ordinal.toString(),
        icon = it.icon,
        name = it.showName,
    )
}