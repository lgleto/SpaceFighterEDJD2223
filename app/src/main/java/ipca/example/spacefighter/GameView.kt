package ipca.example.spacefighter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameView : SurfaceView, Runnable {

    var playing = false
    lateinit var gameThread : Thread

    lateinit var player : Player

    var canvas : Canvas? = null
    lateinit var paint: Paint

    constructor(context: Context?, screenWidth : Int, screenHeight:Int) : super(context){
        init( context, screenWidth, screenHeight )
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    fun init (context : Context?, screenWidth : Int, screenHeight:Int ) {
        player  = Player(context!!, screenWidth, screenHeight)
        paint = Paint()
    }


    override fun run() {
        while (playing){
            update()
            draw()
            control()
        }
    }

    fun resume() {
        playing = true
        gameThread = Thread(this)
        gameThread.start()
    }

    fun pause() {
        playing = false
        gameThread.join()
    }

    fun update() {
        player.update()
    }
    fun draw() {
        if(holder.surface.isValid){
            canvas = holder.lockCanvas()
            canvas?.drawColor(Color.BLACK)
            canvas?.drawBitmap(player.bitmap, player.x, player.y, paint)
            holder.unlockCanvasAndPost(canvas)
        }
    }
    fun control() {
        Thread.sleep(17)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            when(it.action.and(MotionEvent.ACTION_MASK)){
                MotionEvent.ACTION_UP ->{
                    player.boosting = false
                }
                MotionEvent.ACTION_DOWN ->{
                    player.boosting = true
                }
            }
        }


        return true


    }
}