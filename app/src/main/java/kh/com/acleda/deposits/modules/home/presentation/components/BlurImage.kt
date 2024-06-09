package kh.com.acleda.deposits.modules.home.presentation.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.renderscript.Allocation
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kh.com.acleda.deposits.R


@Composable
fun BlurImage(
    bitmap: Bitmap,
    modifier: Modifier = Modifier,
) {
    Image(
        bitmap = bitmap.asImageBitmap(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}

@Composable
fun LegacyBlurImage(
    modifier: Modifier = Modifier,
    bitmap: Bitmap,
    blurRadio: Float
) {
    val renderScript = RenderScript.create(LocalContext.current)
    val bitmapAlloc = Allocation.createFromBitmap(renderScript, bitmap)
    ScriptIntrinsicBlur.create(renderScript, bitmapAlloc.element).apply {
        setRadius(blurRadio)
        setInput(bitmapAlloc)
        forEach(bitmapAlloc)
    }
    bitmapAlloc.copyTo(bitmap)
    renderScript.destroy()

    BlurImage(bitmap, modifier)
}

@Composable
fun BlurryImage(
    modifier: Modifier = Modifier,
    @DrawableRes image: Int,
    blurRadio: Dp,
) {
    val bitmap = BitmapFactory.decodeResource(LocalContext.current.resources, image)

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
        LegacyBlurImage(
            modifier = modifier,
            bitmap = bitmap,
            blurRadio = blurRadio.value
        )
    } else {
        BlurImage(
            bitmap,
            modifier
                .blur(radiusX = blurRadio, radiusY = blurRadio)
        )
    }
}


@Preview
@Composable
private fun ViewGirlPreview() {
    BlurryImage(
        modifier = Modifier.fillMaxSize(),
        image = R.drawable.just_profile,
        blurRadio = 25.dp
    )
}
