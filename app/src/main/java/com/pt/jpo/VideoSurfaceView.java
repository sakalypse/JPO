package com.pt.jpo;

/**
 * Created by sakalypse on 28/01/17.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class VideoSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private MediaPlayer mediaPlayer;
    private boolean has_started = false;


    public VideoSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public VideoSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VideoSurfaceView(Context context) {
        super(context);
        init();
    }

    private void init() {
        //prepare la vidéo
        mediaPlayer = new MediaPlayer();
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        AssetFileDescriptor afd = getResources().openRawResourceFd(R.raw.video);
        try {
            if (!has_started) {
                has_started = true;
                mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
            }

            mediaPlayer.prepare();
            android.view.ViewGroup.LayoutParams lp = getLayoutParams();
            lp.height = getHeight();
            lp.width = getWidth();

            setLayoutParams(lp);
            mediaPlayer.setDisplay(holder);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override public void surfaceDestroyed(SurfaceHolder holder) {
        mediaPlayer.stop();
    }
}