package network.iut.org.flappydragon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Background {
    private GameView view;
    private Bitmap background1;
    private Bitmap background2;
    private Bitmap background3;
    private Bitmap background4;
    private Bitmap background5;

    private Bitmap background6;
    private Bitmap background7;
    private Bitmap background8;
    private Bitmap background9;
    private Bitmap background10;

    private float speedX45;
    private float speedX3;
    private float speedX2;
    private short speedX1;

    private int X45 = 0;
    private int X3 = 0;
    private int X2 = 0;
    private int X1 = 0;

    private int X6 = 0;
    private int X910 = 0;
    private int X8 = 0;
    private int X7 = 0;


    public Background(Context context, GameView view) {
        background1 = Util.getScaledBitmapAlpha8(context, R.drawable.layer1, 8);
        background2 = Util.getScaledBitmapAlpha8(context, R.drawable.layer2, 8);
        background3 = Util.getScaledBitmapAlpha8(context, R.drawable.layer3, 8);
        background4 = Util.getScaledBitmapAlpha8(context, R.drawable.layer4, 8);
        background5 = Util.getScaledBitmapAlpha8(context, R.drawable.layer5, 8);

        background6 = Util.getScaledBitmapAlpha8(context, R.drawable.layer1, 8);
        background7 = Util.getScaledBitmapAlpha8(context, R.drawable.layer2, 8);
        background8 = Util.getScaledBitmapAlpha8(context, R.drawable.layer3, 8);
        background9 = Util.getScaledBitmapAlpha8(context, R.drawable.layer4, 8);
        background10 = Util.getScaledBitmapAlpha8(context, R.drawable.layer5, 8);

        this.view = view;
        this.speedX45 = 10;
        this.speedX3 = 6;
        this.speedX2 = 3;
        this.speedX1 = 1;

        this.X910 = this.background1.getWidth() * -1;
        this.X8 = this.background1.getWidth() * -1;
        this.X7 = this.background1.getWidth() * -1;
        this.X6 = this.background1.getWidth() * -1;

    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(background1, new Rect(this.X1, 0, background1.getWidth()+this.X1, background1.getHeight()), new Rect(0, 0, view.getWidth(), view.getHeight()), null);
        canvas.drawBitmap(background6, new Rect(this.X6, 0, background6.getWidth()+this.X6, background6.getHeight()), new Rect(0, 0, view.getWidth(), view.getHeight()), null);

        canvas.drawBitmap(background2, new Rect(this.X2, 0, background2.getWidth()+this.X2, background2.getHeight()), new Rect(0, 0, view.getWidth(), view.getHeight()), null);
        canvas.drawBitmap(background7, new Rect(this.X7, 0, background7.getWidth()+this.X7, background7.getHeight()), new Rect(0, 0, view.getWidth(), view.getHeight()), null);

        canvas.drawBitmap(background3, new Rect(this.X3, 0, background3.getWidth()+this.X3, background3.getHeight()), new Rect(0, 0, view.getWidth(), view.getHeight()), null);
        canvas.drawBitmap(background8, new Rect(this.X8, 0, background8.getWidth()+this.X8, background8.getHeight()), new Rect(0, 0, view.getWidth(), view.getHeight()), null);

        canvas.drawBitmap(background4, new Rect(this.X45, 0, background4.getWidth()+this.X45, background4.getHeight()), new Rect(0, 0, view.getWidth(), view.getHeight()), null);
        canvas.drawBitmap(background9, new Rect(this.X910, 0, background9.getWidth()+this.X910, background9.getHeight()), new Rect(0, 0, view.getWidth(), view.getHeight()), null);

        canvas.drawBitmap(background5, new Rect(this.X45, 0, background5.getWidth()+this.X45, background5.getHeight()), new Rect(0, 0, view.getWidth(), view.getHeight()), null);
        canvas.drawBitmap(background10, new Rect(this.X910, 0, background10.getWidth()+this.X910, background10.getHeight()), new Rect(0, 0, view.getWidth(), view.getHeight()), null);

        this.X1 += speedX1;
        this.X6 += speedX1;

        this.X2 += speedX2;
        this.X3 += speedX3;
        this.X45 += speedX45;

        this.X910 += speedX45;
        this.X8 += speedX3;
        this.X7 += speedX2;

        if(X1 >= background1.getWidth())
            X1 = this.background1.getWidth() * -1 + 1;

        if(X6 >= background1.getWidth())
            X6 = this.background1.getWidth() * -1 + 1;

        if(X2 >= background2.getWidth())
            X2 = this.background2.getWidth() * -1 + 3;

        if(X3 >= background3.getWidth())
            X3 = this.background3.getWidth() * -1 + 6;

        if(X45 >= background5.getWidth())
            X45 = this.background5.getWidth() * -1 + 10;

        if(X7 >= background2.getWidth())
            X7 = this.background2.getWidth() * -1 + 3;

        if(X8 >= background3.getWidth())
            X8 = this.background3.getWidth() * -1 + 6;

        if(X910 >= background10.getWidth())
            X910 = this.background10.getWidth() * -1 + 10;
    }
}
