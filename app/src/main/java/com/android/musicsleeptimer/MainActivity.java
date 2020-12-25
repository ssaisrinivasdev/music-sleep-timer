package com.android.musicsleeptimer;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.os.CountDownTimer;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Timer;

import soup.neumorphism.NeumorphButton;
import soup.neumorphism.NeumorphCardView;
import soup.neumorphism.NeumorphImageButton;

public class MainActivity extends AppCompatActivity {

    enum TimerState{
        Pause,
        Running,
        Stopped
    }

    private int  i;
    CountDownTimer cTimer;
    private NeumorphButton setTime;
    private ProgressBar progress_bar;
    private long milseconds ;
    CoordinatorLayout constraintLayout;
    AudioManager am;
    private int prog =0;
    private TextView timertext,textmain;

    private TimerState timerState = TimerState.Pause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        progress_bar = findViewById(R.id.progress_bar);
        progress_bar.setMax(1000);
        timertext = findViewById(R.id.timertext);

        am = (AudioManager)MainActivity.this.getSystemService(Context.AUDIO_SERVICE);



        progress_bar.setProgress(prog);

        setTime = findViewById(R.id.settime);
        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialogue();
            }
        });




        FloatingActionButton play = findViewById(R.id.play);
        FloatingActionButton stop = findViewById(R.id.stop);

        //Declare timer
        cTimer = null;





        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timerState!=TimerState.Running&& milseconds!=0) {
                    startTimer();
                }
                else{
                    Toast.makeText(MainActivity.this, "Set timer to start", Toast.LENGTH_SHORT).show();
                }
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(timerState == TimerState.Running) {
                    timerState = TimerState.Stopped;
                    cTimer.cancel();
                    milseconds =0;
                    progress_bar.setProgress(0);
                    timertext.setText("00:00");
                }
                else {
                    Toast.makeText(MainActivity.this, "No Active Timer Running", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void startTimer() {
        timerState = TimerState.Running;
            cTimer = new CountDownTimer(milseconds, 1000) {
                public void onTick(long millisUntilFinished) {
                    float progress = ((millisUntilFinished*1000/milseconds));
                    progress_bar.setProgress( (int)progress);

                    long Mmin = (millisUntilFinished / 1000) / 60;
                    long Ssec = (millisUntilFinished / 1000) % 60;
                    if (Ssec < 10) {
                        timertext.setText(Mmin + ":0" + Ssec);
                    } else timertext.setText( Mmin + ":" + Ssec);
                }

                public void onFinish() {
                    am.requestAudioFocus(null,AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                    timerState = TimerState.Stopped;
                    progress_bar.setProgress(0);
                    timertext.setText("00:00");
                }
            };
            cTimer.start();
    }

    private void showCustomDialogue() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.select_time);
        final NeumorphButton ten =dialog.findViewById(R.id.ten);
        final NeumorphButton twenty =dialog.findViewById(R.id.twenty);
        final NeumorphButton thirty =dialog.findViewById(R.id.thirty);
        final NeumorphButton forty =dialog.findViewById(R.id.forty);
        final NeumorphImageButton done = dialog.findViewById(R.id.done);
        final NeumorphImageButton cancel = dialog.findViewById(R.id.cancel);

        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ten.getShapeType()==0) {
                    ten.setShapeType(1);
                    twenty.setShapeType(0);
                    thirty.setShapeType(0);
                    forty.setShapeType(0);
                    milseconds = 600000;
                }
                else{
                    ten.setShapeType(0);
                    milseconds = 0;
                }

            }
        });
        twenty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(twenty.getShapeType()==0) {
                    ten.setShapeType(0);
                    twenty.setShapeType(1);
                    thirty.setShapeType(0);
                    forty.setShapeType(0);
                    milseconds =600000*2;
                }else{
                    twenty.setShapeType(0);
                    milseconds = 0;
                }
            }
        });
        thirty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thirty.getShapeType()==0) {
                    ten.setShapeType(0);
                    twenty.setShapeType(0);
                    thirty.setShapeType(1);
                    forty.setShapeType(0);
                    milseconds = 600000*3;
                }else {
                    thirty.setShapeType(0);
                    milseconds =0;
                }

            }
        });

        forty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(forty.getShapeType()==0) {
                    ten.setShapeType(0);
                    twenty.setShapeType(0);
                    thirty.setShapeType(0);
                    forty.setShapeType(1);
                    milseconds = 600000*4;
                }else{
                    forty.setShapeType(0);
                    milseconds=0;
                }
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(timerState == TimerState.Running){
                    Snackbar.make(v, "Stop active to start new timer", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
                else {
                    timertext.setText((milseconds / 1000) / 60 + ":00");
                    progress_bar.setProgress(1000);
                    dialog.dismiss();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });


        dialog.show();
    }


}