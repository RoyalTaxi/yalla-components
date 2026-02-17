package uz.yalla.components.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.SupportAgent
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.yalla.components.composite.item.BrandServiceItem
import uz.yalla.components.composite.item.BrandServiceItemDefaults
import uz.yalla.components.composite.item.BrandServiceItemIconModel
import uz.yalla.components.composite.item.BrandServiceItemState
import uz.yalla.components.primitive.button.SupportButton
import uz.yalla.components.primitive.button.SupportButtonState
import uz.yalla.design.theme.System
import uz.yalla.design.theme.YallaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YallaTheme {
                ExampleScreen()
            }
        }
    }
}

@Composable
private fun ExampleScreen() {
    var selectedServiceIndex by remember { mutableIntStateOf(0) }
    var supportClicks by remember { mutableIntStateOf(0) }

    val titles = listOf("Все", "Такси", "Грузовой", "Межгород")

    val localIcons = listOf(
        rememberVectorPainter(Icons.Outlined.Timer),
        rememberVectorPainter(Icons.Outlined.DirectionsCar),
        rememberVectorPainter(Icons.Outlined.LocalShipping),
        rememberVectorPainter(Icons.Outlined.Place),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(System.color.backgroundBase)
            .padding(16.dp),
    ) {
        Text(
            text = "BrandServiceItem",
            color = System.color.textBase,
            style = System.font.body.base.bold,
        )

        Spacer(Modifier.height(12.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(BrandServiceItemDefaults.dimens().itemSpacing),
        ) {
            itemsIndexed(titles) { index, title ->
                BrandServiceItem(
                    state = BrandServiceItemState(
                        title = title,
                        selected = selectedServiceIndex == index,
                    ),
                    onClick = { selectedServiceIndex = index },
                    iconModel = if (index == 1) {
                        BrandServiceItemIconModel.Async("https://example.invalid/taxi.png")
                    } else {
                        BrandServiceItemIconModel.Local(localIcons[index])
                    },
                    asyncImage = BrandServiceItemDefaults.asyncImage(
                        placeholder = rememberVectorPainter(Icons.AutoMirrored.Outlined.HelpOutline),
                        error = rememberVectorPainter(Icons.Outlined.ErrorOutline),
                        fallback = rememberVectorPainter(Icons.Outlined.DirectionsCar),
                    ),
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        Text(
            text = "SupportButton",
            color = System.color.textBase,
            style = System.font.body.base.bold,
        )

        Spacer(Modifier.height(12.dp))

        SupportButton(
            state = SupportButtonState(
                text = "Помощь ($supportClicks)",
                icon = rememberVectorPainter(Icons.Outlined.SupportAgent),
            ),
            onClick = { supportClicks += 1 },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun ExampleScreenPreview() {
    YallaTheme {
        ExampleScreen()
    }
}
