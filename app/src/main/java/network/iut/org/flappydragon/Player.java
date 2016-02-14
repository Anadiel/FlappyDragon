package network.iut.org.flappydragon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.Log;

public class Player {
    /** Static bitmap to reduce memory usage. */
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
    private boolean isAlive;
    private short playerFrame;
    private MediaPlayer jumpSound = null;

    public Player(Context context, GameView view) {
        if(globalBitmap == null) {
            globalBitmap = Util.getScaledBitmapAlpha8(context, R.drawable.frame1, 1);
        }
        this.bitmap = globalBitmap;
        this.width = this.bitmap.getWidth();
        this.height = this.bitmap.getHeight();
        this.frameTime = 3;		// the frame will change every 3 runs
        this.y = context.getResources().getDisplayMetrics().heightPixels / 2;	// Startposition in the middle of the screen

        this.view = view;
        this.x = 0; //this.view.getWidth() / 6;
        this.speedX = 0;
        this.isAlive = true;
        this.playerFrame = 1;
    }

    public void onTap() {
        this.playSound(R.raw.jump);
        this.speedY = getTabSpeed();
        this.y += getPosTabIncrease();
    }

    private float getPosTabIncrease() {
        return - view.getHeight() / 100;
    }

    private float getTabSpeed() {
        return -view.getHeight() / 16f;
    }

    public void move() {
        changeToNextFrame();

        if(speedY < 0){
            // The character is moving up
            Log.i("Move", "Moving up");
            speedY = speedY * 2 / 3 + getSpeedTimeDecrease() / 2;
        }else{
            // the character is moving down
            Log.i("Move", "Moving down");
            this.speedY += getSpeedTimeDecrease();
        }
        if(this.speedY > getMaxSpeed()){
            // speed limit
            this.speedY = getMaxSpeed();
        }

        if(this.playerFrame == 1)
            bitmap = Util.getScaledBitmapAlpha8(this.view.getContext(), R.drawable.frame1, 1);
        if(this.playerFrame == 2)
            bitmap = Util.getScaledBitmapAlpha8(this.view.getContext(), R.drawable.frame2, 1);
        if(this.playerFrame == 3)
            bitmap = Util.getScaledBitmapAlpha8(this.view.getContext(), R.drawable.frame3, 1);
        if(this.playerFrame == 4)
            bitmap = Util.getScaledBitmapAlpha8(this.view.getContext(), R.drawable.frame4, 1);


        // manage frames
/*        if(row != 3){
            // not dead
            if(speedY > getTabSpeed() / 3 && speedY < getMaxSpeed() * 1/3){
                row = 0;
            }else if(speedY > 0){
                row = 1;
            }else{
                row = 2;
            }
        }
*/
        this.x += speedX;
        this.y += speedY;
        if(this.y >= this.view.getHeight() - this.bitmap.getHeight()){
            this.isAlive = false;
            Log.i("Dead", "This little shit is dead");
        }
        else if(this.y <= 0){
            this.isAlive = false;
            Log.i("Dead", "This little shit is dead");
        }
    }

    protected void changeToNextFrame(){
        this.frameTimeCounter++;
        if(this.frameTimeCounter >= this.frameTime){
            //TODO Change frame
            this.frameTimeCounter = 0;
        }

        if(this.playerFrame < 4)
            this.playerFrame++;
        else
            this.playerFrame = 1;
    }

    private float getSpeedTimeDecrease() {
        return view.getHeight() / 320;
    }

    private float getMaxSpeed() {
        return view.getHeight() / 51.2f;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y , null);
    }

    public boolean getIsAlive(){
        return isAlive;
    }

    public Rect getRectCollision() {
        return new Rect(x,y,x+width,y+height);
    }

    private void playSound(int resId) {
        if(jumpSound != null) {
            jumpSound.stop();
            jumpSound.release();
        }
        jumpSound = MediaPlayer.create(this.view.getContext(), resId);
        jumpSound.start();
    }

    public int getX(){
        return x;
    }

    public int getWidth(){
        return width;
    }
}
