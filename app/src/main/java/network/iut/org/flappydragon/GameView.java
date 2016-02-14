package network.iut.org.flappydragon;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameView extends SurfaceView implements Runnable {
    private SurfaceHolder holder;
    private boolean paused = true;
    private Player player;
    private MediaPlayer mPlayer = null;
    private Obstacles Pipe1;
    private Obstacles Pipe2;
    private Boss BossLenon;
    private List<BouledeFeu> boules;
    private int bossMove = 1;

    public static final long UPDATE_INTERVAL = 50; // = 20 FPS
    private Timer timer = new Timer();
    private TimerTask timerTask;
    private Background background;
    private Context context;
    private TextPaint affScore;
    private int score = 0;
    private boolean isScorable = false;
    private Paint pScore;
    private boolean bossActivate;
    private int bossAttacks = 0;
    private int boulesSize = 0;

    public GameView(Context context) {
        super(context);
        pScore = new Paint();
        pScore.setTextSize(this.getHeight()/5);
        player = new Player(context, this);
        Pipe1 = new Obstacles(context, this);
        Pipe2 = new Obstacles(context, this);
        BossLenon = new Boss(context, this);
        background = new Background(context, this);
        holder = getHolder();
        affScore = new TextPaint();
        this.context = context;
        this.playSound(R.raw.mainmusic);
        boules = new ArrayList<>();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(paused) {
                resume();
            } else {
                Log.i("PLAYER", "PLAYER TAPPED");
                this.player.onTap();
            }
        }
        return true;
    }

    private void resume() {
        paused = false;
        startTimer();
    }

    private void startTimer() {
        Log.i("TIMER", "START TIMER");
        setUpTimerTask();
        timer = new Timer();
        timer.schedule(timerTask, UPDATE_INTERVAL, UPDATE_INTERVAL);
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
        if (timerTask != null) {
            timerTask.cancel();
        }
    }

    private void setUpTimerTask() {
        stopTimer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                GameView.this.run();
            }
        };
    }

    @Override
    public void run() {
        player.move();
        if(bossActivate == false){
            Pipe1.move();
            Pipe2.move();
        }
        if(bossActivate == true) {
            if(bossMove == 30) {
                BossLenon.move();
                BossLenon.attack();
                BouledeFeu tmp = new BouledeFeu(context, this);
                tmp.setY(BossLenon.getY());
                boules.add(tmp);
                boulesSize = boules.size();
                Log.i("Boules", "Size : " + Integer.toString(boulesSize));
                bossMove = 1;
                bossAttacks++;
            }
            if(bossAttacks == 15) {
                bossActivate = false;
                bossAttacks = 0;
                boules.clear();
                score += 10;
                //score  + 10 si boss passé parce qu'on est des putes
            }
            else
                bossMove++;

            if(boules.size() >= 0) {
                for (int i = 0; i < boules.size(); i++)
                    boules.get(i).move();

                for (int i = 0; i < boules.size(); i++) {
                    if (boules.get(i).isTouchingPlayer(player)) {
                        this.mPlayer.stop();
                        this.stopTimer();
                        this.deathPopup();
                    }
                    if (this.boules.get(i).getX() < (0 - this.boules.get(i).getWidth()))
                        boules.remove(i);
                }
            }
        }
        if(player.getIsAlive() == false) {
            this.mPlayer.stop();
            this.stopTimer();
            this.deathPopup();
        }
        if((this.Pipe2.getX() + this.Pipe2.getWidth()/2 <= player.getX() + this.player.getWidth()/2) && this.isScorable == true) {
            score++;
            this.isScorable = false;
        }
        else if((this.Pipe2.getX() + this.Pipe2.getWidth()/2) > (player.getX() + this.player.getWidth()/2) && this.isScorable == false) {
            this.isScorable = true;
        }
        if(this.Pipe2.getX() < (0 - this.Pipe2.getWidth())) {
            this.genObstacles();
        }

        if(Pipe1.isTouchingPlayer(player) || Pipe2.isTouchingPlayer(player)){
            this.mPlayer.stop();
            this.stopTimer();
            this.deathPopup();
        }

        if(score > 0 && score%5 == 0 && bossActivate == false)
        {
            score++;
            bossActivate = true;
            this.playSound(R.raw.bossmusic);
            Pipe1.setX(this.getWidth());
            Pipe2.setX(this.getWidth());
        }

        draw();
    }

    private void draw() {
        while(!holder.getSurface().isValid()){
			/*wait*/
            try { Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        Canvas canvas = holder.lockCanvas();
        drawCanvas(canvas);
        holder.unlockCanvasAndPost(canvas);
    }

    private void drawCanvas(Canvas canvas) {
        background.draw(canvas);
        player.draw(canvas);

        if(bossActivate == false){
            Pipe1.draw(canvas);
            Pipe2.draw(canvas);
        }
        if(bossActivate == true){
            BossLenon.draw(canvas);
            if(boules.size() >= 0){
                for(int i = 0; i < boules.size(); i++) {
                    Log.i("Boules", "Bob attack appeared");
                    boules.get(i).draw(canvas);
                }
            }
        }
        canvas.drawText(Integer.toString(score), this.getWidth() / 2 - 25, 0 + this.affScore.getTextSize()*5, pScore);
    }

    private void deathPopup() {
        ((Activity)getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                GameView.this.playSound(R.raw.dead);
                AlertDialog.Builder deathDialogBuilder = new AlertDialog.Builder(context);
                deathDialogBuilder.setTitle("Game Over");
                deathDialogBuilder.setMessage("Oh, you died. That was unexpected");

                deathDialogBuilder.setCancelable(false).setPositiveButton("Merde", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        System.exit(0);
                    }
                });

                deathDialogBuilder.setCancelable(false).setNeutralButton("Restart", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        /*GameView.this.score = 0;
                        GameView.this.Pipe1.setX(context.getResources().getDisplayMetrics().widthPixels + Pipe1.getWidth());
                        GameView.this.Pipe2.setX(context.getResources().getDisplayMetrics().widthPixels + Pipe1.getWidth());
                        GameView.this.isScorable = true;
                        GameView.this.startTimer();
                        GameView.this.player = new Player(context, GameView.this);
                        GameView.this.playSound(R.raw.mainmusic);
                        GameView.this.run();
                        GameView.this.genObstacles();*/
                        GameView.this.mPlayer.stop();
                        ((GameActivity)context).recreate();
                    }
                });

                AlertDialog alertDialog = deathDialogBuilder.create();
                // show it
                alertDialog.show();
            }
        });
    }


    private void playSound(int resId) {
        if(mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
        }
        mPlayer = MediaPlayer.create(this.getContext(), resId);
        mPlayer.setLooping(true);
        if(resId == R.raw.dead)
            mPlayer.setLooping(false);
        mPlayer.start();
    }

    private void genObstacles(){
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(9) +2;
        Pipe2.setY(0 - randomInt*50 - 25);
        Pipe1.setY(this.getHeight() - randomInt*50 + 25);
    }

}
