package network.iut.org.flappydragon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaPlayer;

import java.util.Random;

public class Boss {
    /**
     * Static bitmap to reduce memory usage.
     */
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
    private MediaPlayer bossSound = null;
    private boolean enter = true;

    public Boss(Context context, GameView view) {
        if (globalBitmap == null) {
            globalBitmap = Util.getScaledBitmapAlpha8(context, R.drawable.bosslenon, 1);
        }
        this.bitmap = globalBitmap;
        this.width = this.bitmap.getWidth();
        this.height = this.bitmap.getHeight();
        this.frameTime = 3;        // the frame will change every 3 runs
        this.y = context.getResources().getDisplayMetrics().heightPixels / 2 - height;    // Startposition in the middle of the screen
        this.speedY = 10;
        this.view = view;
        this.x = context.getResources().getDisplayMetrics().widthPixels - width;
        this.speedX = 0;
    }

    public void draw(Canvas canvas) {
        if(enter == true){
            this.playSound(R.raw.bossenter);
            enter = false;
        }
        canvas.drawBitmap(this.bitmap, x, y, null);
    }

    public void move() {
        changeToNextFrame();
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(this.view.getHeight() - height);
        this.y = randomInt;
    }

    public void attack(){
        this.playSound(R.raw.fusrodah);
        try { Thread.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
    }

    protected void changeToNextFrame() {
        this.frameTimeCounter++;
        if (this.frameTimeCounter >= this.frameTime) {
            //TODO Change frame
            this.frameTimeCounter = 0;
        }
    }

    private void playSound(int resId) {
        if(bossSound != null) {
            bossSound.stop();
            bossSound.release();
        }
        bossSound = MediaPlayer.create(this.view.getContext(), resId);
        bossSound.start();
    }

    public int getY(){
        return this.y;
    }


}