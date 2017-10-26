package com.example.chamada;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.ProgressBar;

public class SplashActivity extends Activity {

    private ProgressBar mProgress;
    private int mProgressStatus = 0;
    private Handler mHandler = new Handler();
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		 mProgress = (ProgressBar) findViewById(R.id.pgbCarregando);
		 mProgress.setProgress(0);
		 mProgress.setMax(100000);
         // Start lengthy operation in a background thread
         new Thread(new Runnable() {
             public void run() {
                 while (mProgressStatus < 100000) {
                     mProgressStatus += 5;
                     // Atualiza progress bar
                     mHandler.post(new Runnable() {
                         public void run() {
                        	 //incrementa
                             mProgress.setProgress(mProgressStatus);
                         }
                     });
                 }                 
 					startActivity(new Intent(SplashActivity.this,
 					MainActivity.class));
 					//finish(); 				
             }
         }).start();
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

}
