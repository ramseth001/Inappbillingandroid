package com.image.ram.inappbillingandroid;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setVisibility(View.INVISIBLE);
        textView =(TextView) findViewById(R.id.textView);
    }

    public void doWork(View view) {
          new CountDownTimer(5000, 1000) {
              public void onTick(long millisUntilFinished) {
                imageView.setVisibility(View.VISIBLE);
            }
              public void onFinish() {
               imageView.setVisibility(View.INVISIBLE);
            }
          }.start();
    }
}
