package ipca.example.spacefighter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import java.util.*

class Enemy {

    var bitmap : Bitmap

    var x : Float
    var y : Float

    var speed : Int = 0

    var maxY : Float
    var minY : Float
    var maxX : Float
    var minX : Float

    var detectColision = Rect()

    constructor(context: Context, screenWidth : Int, screenHeight : Int) {
        bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.enemy)

        maxX = screenWidth.toFloat()
        minX = 0F
        maxY = screenHeight.toFloat()
        minY = 0F

        var generator = Random()
        speed = generator.nextInt(6) + 10

        x = maxX
        y = generator.nextFloat()*maxY - bitmap.height

        detectColision = Rect(x.toInt(), y.toInt(), bitmap.width, bitmap.height)

    }

    fun update (playerSpeed : Int ) {
        x -= playerSpeed + speed

        detectColision.left = x.toInt()
        detectColision.top = y.toInt()
        detectColision.right = x.toInt() + bitmap.width
        detectColision.bottom = y.toInt() + bitmap.height

        if(x < 0 - bitmap.width ) {
            x = maxX
            var generator = Random()
            speed = generator.nextInt(6) + 10
            y = generator.nextFloat()*maxY - bitmap.height
        }

    }

}