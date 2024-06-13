package dev.kuromiichi.omnitimer.platform

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.caverock.androidsvg.SVG

actual fun String.toImageBitmap(): ImageBitmap {
    val svg = SVG.getFromString(this)

    val bitmap = Bitmap.createBitmap(
        svg.documentWidth.toInt(),
        svg.documentHeight.toInt(),
        Bitmap.Config.ARGB_8888
    )

    val canvas = android.graphics.Canvas(bitmap)

    svg.renderToCanvas(canvas)

    return bitmap.asImageBitmap()
}
