package ipca.example.spacefighter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect

class Player {

    var bitmap : Bitmap
    var x : Float
    var y : Float
    var speed : Int = 0

    var boosting = false

    var maxY : Float
    var minY : Float

    companion object {
        const val GRAVITY = 10
        const val MAX_SPEED = 20
        const val MIN_SPEED = 1
    }

    var detectColision = Rect()

    constructor(context: Context, screenWidth : Int, screenHeight : Int) {
        x = 75f
        y = 50f
        speed = 1
        bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.player)

        minY = 0f
        maxY = (screenHeight - bitmap.height).toFloat()

        detectColision = Rect(x.toInt(), y.toInt(), bitmap.width, bitmap.height)
    }

    fun update(){
        if (boosting){
            speed += 2
        }else{
            speed -= 5
        }

        if (speed > MAX_SPEED) speed = MAX_SPEED
        if (speed < MIN_SPEED) speed = MIN_SPEED

        y -= speed - GRAVITY

        if (y < minY) y = minY
        if (y > maxY) y = maxY

        detectColision.left = x.toInt()
        detectColision.top = y.toInt()
        detectColision.right = x.toInt() + bitmap.width
        detectColision.bottom = y.toInt() + bitmap.height
    }


}