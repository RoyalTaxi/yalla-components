package uz.yalla.components.example

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.SupportAgent
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.runtime.Composable
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

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun BrandServiceItemPreviewCard() {
    YallaTheme {
        Column(
            modifier = Modifier
                .background(System.color.backgroundBase)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            BrandServiceItem(
                state = BrandServiceItemState(
                    title = "Все",
                    selected = true,
                ),
                onClick = {},
                colors = BrandServiceItemDefaults.colors(

                ),
                iconModel = BrandServiceItemIconModel.Local(
                    painter = rememberVectorPainter(Icons.Outlined.Timer),
                ),
            )

            BrandServiceItem(
                state = BrandServiceItemState(
                    title = "Такси",
                    selected = false,
                ),
                onClick = {},
                iconModel = BrandServiceItemIconModel.Local(
                    painter = rememberVectorPainter(Icons.Outlined.DirectionsCar),
                ),
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun SupportButtonPreviewCard() {
    YallaTheme {
        Column(
            modifier = Modifier
                .background(System.color.backgroundBase)
                .padding(16.dp),
        ) {
            SupportButton(
                state = SupportButtonState(
                    text = "Помощь",
                    icon = rememberVectorPainter(Icons.Outlined.SupportAgent),
                ),
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
