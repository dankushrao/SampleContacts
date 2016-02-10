package com.utilities.dhananjayan.samplecontacts;

/**
 * Created by dhananjayan on 1/29/2016.
 */
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TimerDemo extends Activity
{
    View viewLoad;
    Button start,stop;
    Dialog dialog;
    TextView tv;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        viewLoad = LayoutInflater.from(TimerDemo.this).inflate(R.layout.timer_main, null); //inflate the view
        setContentView(viewLoad);
        Button b = (Button) findViewById(R.id.button1);
        final MyCounter timer = new MyCounter(600000, 1000);

        b.setOnClickListener(new OnClickListener()
        {
            public void onClick(View v)
            {
                dialog = new Dialog(viewLoad.getContext());
                View vLoad = LayoutInflater.from(TimerDemo.this).inflate(R.layout.timer_dialog, null);
                dialog.setContentView(vLoad);

                tv=(TextView)dialog.findViewById(R.id.tv);
                start=(Button)dialog.findViewById(R.id.start);
                stop=(Button)dialog.findViewById(R.id.stop);

                dialog.setTitle("Dialog with CountDownTimer");
                dialog.setCancelable(false);

                tv.setText("Countdown begins from : 10 minutes");
                tv.setTextColor(Color.RED);
                start.setOnClickListener(new OnClickListener()
                {
                    public void onClick(View v)
                    {
                        timer.start();  //will start the timer_dialog
                    }
                });

                stop.setOnClickListener(new OnClickListener()
                {
                    public void onClick(View v)
                    {
                        timer.cancel();
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

    } //end oncreate

    public class MyCounter extends CountDownTimer
    {

        public MyCounter(long millisInFuture, long countDownInterval)
        {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish()
        {
            Log.i("Main","Timer Completed");
            tv.setText("Timer Completed.");
        }

        @Override
        public void onTick(long millisUntilFinished)
        {
            tv.setText("Timer : " + (millisUntilFinished) + " " + "minutes remaining.");
        }

    }

} //end class