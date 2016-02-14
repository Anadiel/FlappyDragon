package network.iut.org.flappydragon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Valentin on 12/02/2016.
 */
public class BouledeFeu {

    public static Bitmap globalBitmap;
    private Bitmap bitmap;
    private final byte frameTime;
    private int frameTimeCounter;
    private final int width;
    private final int height;
    private int x;
    private int y;
    private float speedX;
    private float speedY;
    private GameView view;

    public BouledeFeu(Context context, GameView view) {
        if (globalBitmap == null) {
            globalBitmap = Util.getScaledBitmapAlpha8(context, R.drawable.bouledefeu, 1);
        }
        this.bitmap = globalBitmap;
        this.width = this.bitmap.getWidth();
        this.height = this.bitmap.getHeight();
        this.frameTime = 3;        // the frame will change every 3 runs
        this.y = 0;    // Startposition in the middle of the screen
        this.speedY = 0;
        this.view = view;
        this.x = context.getResources().getDisplayMetrics().widthPixels - width;
        this.speedX = context.getResources().getDisplayMetrics().widthPixels / 3;
        Log.i("Boule", "New Bob attack");
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(this.bitmap, x, y, null);
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

    public void setY(int y){
        this.y = y;
    }

    public int getX(){
        return this.x;
    }

    public void setX(int x){
        this.x = x;
    }

    public boolean isTouchingPlayer(Player p) {
        Rect hitboxTuyau=new Rect(x,y,x+width,y+height);

        if(hitboxTuyau.intersect(p.getRectCollision()))
            return true;
        else
            return false;
    }

    public int getWidth(){
        return this.width;
    }
}
