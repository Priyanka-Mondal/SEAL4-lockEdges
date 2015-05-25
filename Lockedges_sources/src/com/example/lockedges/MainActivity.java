package com.example.lockedges;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	final Handler mhand = new  Handler(Looper.getMainLooper());
	int x;
	/* sdkversion 14, compile target 19, style: none */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 final Lock lock= new ReentrantLock();
		Runnable myRunnable1 = new Runnable()
		{
			public void run()
			{
				Log.v("ONE","--------Task ONE is executing---------");
				System.out.println("--------Task ONE is executing---------");
				
				Runnable myRunnable2 = new Runnable()
				{
					public void run()
					{
						Log.v("TWO","--------Task TWO is executing--------");
						System.out.println("--------Task TWO is executing---------");
						
						lock.tryLock();
							x=2;
						lock.unlock();
						Log.v("Write to x","value of x is "+x);
						Runnable myRunnable4 = new Runnable()
						{
							public void run()
							{
								Log.v("FOUR","----------Task FOUR is executing------");
								System.out.println("--------Task FOUR is executing---------");
								//lock.lock();
									int y =x;
								//lock.unlock();
								Log.v("Read to x","value of x is "+x);
							};
						};
						mhand.post(myRunnable4);
					};
				};mhand.post(myRunnable2);
				
				
				Runnable myRunnable3 = new Runnable()
				{
					public void run()
					{   
						lock.lock();
							x=200;
						lock.unlock();
						Log.v("THREE","------------Task THREE is executing-------------");
						System.out.println("--------Task THREE is executing---------");
						Log.v("Write to x","value of x is "+x);
					};
				};mhand.post(myRunnable3);
				
			};
		};
		mhand.post(myRunnable1);	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
