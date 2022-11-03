package ipca.example.spacefighter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.media.MediaPlayer
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameView : SurfaceView, Runnable {

    var playing = false
    lateinit var gameThread : Thread

    lateinit var player : Player
    lateinit var boom : Boom
    var stars = arrayListOf<Star>()
    var enemies = arrayListOf<Enemy>()


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
        boom = Boom(context!!, screenWidth, screenHeight)
        paint = Paint()

        for( index in 0..99){
            stars.add(Star(context!!, screenWidth, screenHeight))
        }

        for( index in 0..2){
            enemies.add(Enemy(context!!, screenWidth, screenHeight))
        }
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
        boom.x = -250F
        boom.y = -250F



        player.update()
        for (s in stars){
            s.update(player.speed)
        }

        for (e in enemies){
            e.update(player.speed)

            if (e.detectColision.intersect(player.detectColision)){

                boom.x = e.x
                boom.y = e.y

                var boomSound = MediaPlayer.create(context, R.raw.boom)
                boomSound.start()
                e.x = -300F
            }

        }
    }
    fun draw() {
        if(holder.surface.isValid){
            canvas = holder.lockCanvas()
            canvas?.drawColor(Color.BLACK)

            paint.color = Color.YELLOW
            for ( s in stars) {
                paint.strokeWidth = s.starWidth()
                canvas?.drawPoint(s.x, s.y, paint)
            }

            paint.strokeWidth = 1.0F
            paint.style = Paint.Style.STROKE

            for (e in enemies){
                canvas?.drawBitmap(e.bitmap, e.x, e.y, paint)
                paint.color = Color.GREEN
                canvas?.drawRect(e.detectColision, paint)
            }

            canvas?.drawBitmap(player.bitmap, player.x, player.y, paint)
            canvas?.drawRect(player.detectColision, paint)

            canvas?.drawBitmap(boom.bitmap, boom.x, boom.y, paint)

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