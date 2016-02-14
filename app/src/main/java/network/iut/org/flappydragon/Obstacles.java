package network.iut.org.flappydragon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Valentin on 30/01/2016.
 */
public class Obstacles {

    public static Bitmap obsBitmap;
    private Bitmap bitmap;
    private final byte frameTime;
    private int frameTimeCounter;
    private final int width;
    private final int height;
    private int x;
    private int y;
    private int speedX;
    private GameView view;

    public Obstacles(Context context, GameView view) {
        if(obsBitmap == null) {
            obsBitmap = Util.getScaledBitmapAlpha8(context, R.drawable.pipe, 1);
        }
        this.bitmap = this.obsBitmap;
        this.width = this.bitmap.getWidth();
        this.height = this.bitmap.getHeight();
        this.frameTime = 3;		// the frame will change every 3 runs
        this.y = 0 - this.height;	// Startposition in the middle of the screen

        this.view = view;
        this.x = this.view.getWidth();
        this.speedX = context.getResources().getDisplayMetrics().widthPixels / 30;
        Log.i("Obstacle", "New fucking Pipe");
    }

    public void move() {
        changeToNextFrame();
        this.x -= this.speedX;
        if(this.x < (0 - this.bitmap.getWidth()*2))
            x = this.view.getWidth();
    }

    protected void changeToNextFrame() {
        this.frameTimeCounter++;
        if (this.frameTimeCounter >= this.frameTime) {
            //TODO Change frame
            this.frameTimeCounter = 0;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(this.bitmap, x, y, null);
    }

    public int getWidth(){
        return this.width;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getX(){
        return this.x;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setSpeedX(int speedX){
        this.speedX = speedX;
    }

    public boolean isTouchingPlayer(Player p) {
        Rect hitboxTuyau=new Rect(x,y,x+width,y+height);

        if(hitboxTuyau.intersect(p.getRectCollision()))
            return true;
        else
            return false;
    }
}
