package ipca.example.spacefighter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Boom {

    var bitmap : Bitmap

    var x : Float
    var y : Float

    constructor(context: Context, screenWidth: Int, screenHeight: Int) {
        y = -250F
        x = -250F

        bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.boom)
    }
}