package com.dizdaroglu.titrek_oyun;


import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Vibrator;

import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



    public class MainActivity extends Activity implements SensorEventListener {
        private SensorManager sensorManager;
        private Vibrator vibrator;
        private float last_x, last_y, last_z = 0;
        CountDownTimer countDownTimer;
        TextView textView,skorr;
        Button bttn_name;
        EditText editName;
        int skorSonuc;
        String yedek;
        private static final int NOTIF_ID = 1;

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float[] values = event.values;
                float x = values[0];
                float y = values[1];
                float z = values[2];

                if (z*last_z < 0) {
                    vibrator.vibrate(500);
                    skorSonuc++;
                }

                Log.d("MainActivity", String.format("x : %f y : %f z : %f", x, y, z));
                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);



            sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
            vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

            textView=(TextView)findViewById(R.id.textView);
            bttn_name = (Button)findViewById(R.id.btn_name);
            editName =(EditText)findViewById(R.id.editText);
            bttn_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) { // Buton her tıklandığında bu fonksiyon çalışır

                    countDownTimer=new CountDownTimer(10000,1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            textView.setText(String.valueOf(millisUntilFinished/1000));
                        }

                        @Override
                        public void onFinish() {
                            skorr=(TextView)findViewById(R.id.skor);
                            skorr.setText(
                                    "Skor:"+skorSonuc+"-"+
                                            "Adınız:"+editName.getText().toString()
                            );

                        }
                    }.start();
                }
            });
        }



        @Override
        protected void onDestroy() {
            super.onDestroy();
            sensorManager.unregisterListener(this);
        }

    }

