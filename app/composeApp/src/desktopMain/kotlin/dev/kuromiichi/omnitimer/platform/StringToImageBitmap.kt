package dev.kuromiichi.omnitimer.platform

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.apache.batik.transcoder.TranscoderInput
import org.apache.batik.transcoder.TranscoderOutput
import org.apache.batik.transcoder.image.PNGTranscoder
import org.jetbrains.skia.Image
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

actual fun String.toImageBitmap(): ImageBitmap {
    val transcoder = PNGTranscoder()
    val inputStream = ByteArrayInputStream(this.toByteArray(Charsets.UTF_8))
    val input = TranscoderInput(inputStream)
    val outputStream = ByteArrayOutputStream()
    val output = TranscoderOutput(outputStream)
    transcoder.transcode(input, output)
    val byteArray = outputStream.toByteArray()
    val bufferedImage = ImageIO.read(ByteArrayInputStream(byteArray))
    return bufferedImage.toImageBitmap()

}

private fun BufferedImage.toImageBitmap(): ImageBitmap {
    val outputStream = ByteArrayOutputStream()
    ImageIO.write(this, "png", outputStream)
    val byteArray = outputStream.toByteArray()
    val image = Image.makeFromEncoded(byteArray)
    return image.toComposeImageBitmap()
}
