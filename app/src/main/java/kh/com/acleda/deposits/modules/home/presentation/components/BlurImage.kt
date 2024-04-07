package kh.com.acleda.deposits.modules.home.presentation.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import kh.com.acleda.deposits.R
import kotlinx.coroutines.delay


@Composable
fun BlurImage(
    bitmap: Bitmap,
    modifier: Modifier = Modifier.fillMaxSize(),
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
    modifier: Modifier = Modifier.fillMaxSize(),
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
fun ViewGirl() {
    val bitmap = BitmapFactory
        .decodeResource(LocalContext.current.resources, R.drawable.img)

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
        LegacyBlurImage(bitmap = bitmap, blurRadio = 25f)
    } else {
        BlurImage(
            bitmap,
            Modifier
                .fillMaxSize()
                .blur(radiusX = 15.dp, radiusY = 15.dp)
        )
    }
}


@Preview
@Composable
private fun ViewGirlPreview() {
    ViewGirl()
}
