package com.hustaty.android.alergia;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;
import com.hustaty.android.alergia.beans.DistrictStatus;
import com.hustaty.android.alergia.enums.Alergene;
import com.hustaty.android.alergia.enums.Concentration;
import com.hustaty.android.alergia.enums.County;
import com.hustaty.android.alergia.enums.Direction;
import com.hustaty.android.alergia.enums.District;
import com.hustaty.android.alergia.enums.Level;
import com.hustaty.android.alergia.enums.Prognosis;
import com.hustaty.android.alergia.service.location.AlergyLocationService;
import com.hustaty.android.alergia.util.HttpUtil;
import com.hustaty.android.alergia.util.LogUtil;
import com.hustaty.android.alergia.util.XmlUtil;


public class AlergiaskActivity extends Activity {

	public static final String LOG_TAG = "AlergiaSK";

	private Level depthLevel = Level.COUNTY;

	private static County currentCounty = County.BB;
	private static District currentDistrict = null; //District.BB;
	private static Alergene currentAlergene = null; //Alergene.OVERALL;

	private static final List<String> counties = County.getCounties();

	private int startXcoord;

	private int startYcoord;

	private TextView countyNameTextView;
	private TextView districtNameTextView;
	private TextView alergeneNameTextView;

	private TextView alergeneDetailsTextView;
	
	private ImageView upImageButton;
	private ImageView downImageButton;
	
	private boolean gotGPSfix = false;
	
	//A ProgressDialog object  
    private ProgressDialog progressDialog;  

    GoogleAnalyticsTracker tracker;

	/** 
	 * Called when the activity is first created. 
	 * 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LogUtil.appendLog("#AlergiaskActivity.onCreate(): ### STARTING APPLICATION ###");
		//Initialize a LoadViewTask object and call the execute() method  

		tracker = GoogleAnalyticsTracker.getInstance();
		// Start the tracker in manual dispatch mode...
	    tracker.startNewSession("UA-31112884-1", 20, this);

		new LoadViewTask().execute();         
	}

	
	//////////////////////////////////////////////////////////////////////////////////////////
	//To use the AsyncTask, it must be subclassed  
    private class LoadViewTask extends AsyncTask<Void, Integer, Void> {  
        //Before running code in separate thread  
        @Override  
        protected void onPreExecute() {  
            progressDialog = ProgressDialog.show(AlergiaskActivity.this,"Loading...",  
            	    "Loading application, please wait...", false, false); 
            
            AlergyLocationService locationService = new AlergyLocationService(AlergiaskActivity.this);

    		District district = locationService.getDistrictFromLastAddress();

    		if(district != null && !gotGPSfix) {
    			AlergiaskActivity.this.gotGPSfix = true;
    			currentCounty = district.getCounty();
    			currentDistrict = district;
    			currentAlergene = Alergene.OVERALL;
    			depthLevel = Level.ALERGENE;
    		}
        }  
  
        //The code to be executed in a background thread.  
        @Override  
        protected Void doInBackground(Void... params) {  
			/**
			 * This is just a code that delays the thread execution 4 times,
			 * during 500 milliseconds and updates the current progress. This is
			 * where the code that is going to be executed on a background
			 * thread must be placed.
			 */
            try {  
                //Get the current thread's token  
                synchronized (this) {  
                    //Initialize an integer (that will act as a counter) to zero  
                    int counter = 0;  
                    //While the counter is smaller than four  
                    while(counter <= 4) {  
                        //Wait 500 milliseconds  
                        this.wait(500);  
                        //Increment the counter  
                        counter++;  
                        //Set the current progress.  
                        //This value is going to be passed to the onProgressUpdate() method.  
                        publishProgress(counter*25);  
                    }  
                }  
            } catch (InterruptedException e) {
            	LogUtil.appendLog("#AlergiaskActivity.LoadViewTask.doInBackground(): " + e.getMessage());
                Log.e(LOG_TAG, e.getMessage());  
            }  
            return null;  
        }  
  
        //Update the progress  
        @Override  
        protected void onProgressUpdate(Integer... values) {  
            //set the current progress of the progress dialog  
            //progressDialog.setProgress(values[0]);  
        }  
  
        //after executing the code in the thread  
        @Override  
        protected void onPostExecute(Void result) {  
            //close the progress dialog  
            progressDialog.dismiss();  
            //initialize the View  
            setContentView(R.layout.main); 
            initUI(null);
        }  
    }  
	//////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * OnConfigurationChange.
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		setContentView(R.layout.main);
		initUI(newConfig);
	}

	/**
	 * Initiate UI.
	 */
	private void initUI(Configuration newConfig) {
		
//		ConnectivityManager connectivityManager =  (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
//		NetworkInfo[] networkInfoArray = connectivityManager.getAllNetworkInfo();
//		
//		boolean isDeviceAbleToConnect = false;
//		for(NetworkInfo networkInfo : networkInfoArray) {
//			if(networkInfo.getState().equals(NetworkInfo.State.DISCONNECTED)) {
//				//?
//			} else {
//				isDeviceAbleToConnect = true;
//			}
//		}
		
		/**
		 * county textView
		 */
		this.countyNameTextView = (TextView)findViewById(R.id.countyNameTextView);

		if(AlergiaskActivity.currentCounty != null) {
			this.countyNameTextView.setText(AlergiaskActivity.currentCounty.getCountyName());
			this.depthLevel = Level.COUNTY;
		} else {
			this.countyNameTextView.setText(counties.get(0));
			this.depthLevel = Level.COUNTY;
		}
		
		/**
		 * district textView
		 */
		this.districtNameTextView = (TextView)findViewById(R.id.districtNameTextView);

		if(AlergiaskActivity.currentDistrict != null) {
			this.districtNameTextView.setText(AlergiaskActivity.currentDistrict.getDistrictName());
			this.depthLevel = Level.DISTRICT;
		} else {
			this.districtNameTextView.setText("");
			this.depthLevel = Level.COUNTY;
		}
		
		/**
		 * alergene textView 
		 */
		this.alergeneNameTextView = (TextView)findViewById(R.id.alergeneNameTextView);

		if(AlergiaskActivity.currentAlergene != null) {
			this.alergeneNameTextView.setText(AlergiaskActivity.currentAlergene.getAlergeneName());
			this.depthLevel = Level.ALERGENE;
		} else {
			this.alergeneNameTextView.setText("");
			this.depthLevel = (AlergiaskActivity.currentDistrict != null ? Level.DISTRICT : Level.COUNTY);
		}
		

		/**
		 * alergene details textView
		 */
		this.alergeneDetailsTextView = (TextView)findViewById(R.id.alergeneDetailsTextView);
		
		if(AlergiaskActivity.currentAlergene != null) {
			this.alergeneDetailsTextView.setText("Loading...");
			DistrictStatus data = loadData();
			this.alergeneDetailsTextView.setText(data.toHumanReadableString());
		} else {
			this.alergeneDetailsTextView.setText("Vertikálnymi a horizontálnymi\nťahmi po displeji ovládate\nzobrazené položky.");
		}

		
		ImageView prevImageButton = (ImageView) findViewById(R.id.countyPrevButton);
		prevImageButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				tracker.trackEvent(
			            "Clicks",  // Category
			            "countyPrevButton",  // Action
			            "clicked", // Label
			            77);       // Value

				modify(Direction.RIGHT);
			}
		});

		ImageView nextImageButton = (ImageView) findViewById(R.id.countyNextButton);
		nextImageButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				tracker.trackEvent(
			            "Clicks",  // Category
			            "countyNextButton",  // Action
			            "clicked", // Label
			            77);       // Value

				modify(Direction.LEFT);
			}
		});

		this.upImageButton = (ImageView) findViewById(R.id.upButton);
		this.upImageButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				tracker.trackEvent(
			            "Clicks",  // Category
			            "upButton",  // Action
			            "clicked", // Label
			            77);       // Value

				modify(Direction.DOWN);
			}
		});

		this.downImageButton = (ImageView) findViewById(R.id.downButton);
		this.downImageButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				tracker.trackEvent(
			            "Clicks",  // Category
			            "downButton",  // Action
			            "clicked", // Label
			            77);       // Value

				modify(Direction.UP);
			}
		});

		ImageView alergeneCurrentWeekTextOverview = (ImageView) findViewById(R.id.alergeneCurrentWeekTextOverview);
		alergeneCurrentWeekTextOverview.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String url = "http://alergia.sk/pelove-spravodajstvo/verejnost/aktualne/komentar";
				
				tracker.trackPageView("alergeneCurrentWeekTextOverview");
				
				Intent browserIntent = new Intent(Intent.ACTION_VIEW);
				browserIntent.setData(Uri.parse(url));
				startActivity(browserIntent);
			}
		});

		ImageView alergeneAnnualOverview = (ImageView) findViewById(R.id.alergeneAnnualOverview);
		alergeneAnnualOverview.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String url = "http://alergia.sk/index.php?page=kalendar";
				
				tracker.trackPageView("alergeneAnnualOverview");

				Intent browserIntent = new Intent(Intent.ACTION_VIEW);
				browserIntent.setData(Uri.parse(url));
				startActivity(browserIntent);
			}
		});

		ImageView alergeneWeeklyOverview = (ImageView) findViewById(R.id.alergeneWeeklyOverview);
		alergeneWeeklyOverview.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String url = "http://alergia.sk/pelove-spravodajstvo/verejnost/aktualne/tabulka?k=" + AlergiaskActivity.currentCounty.getCountyNumber();
				
				tracker.trackPageView("alergeneWeeklyOverview");

				Intent browserIntent = new Intent(Intent.ACTION_VIEW);
				browserIntent.setData(Uri.parse(url));
				startActivity(browserIntent);
			}
		});

		
//		if(!isDeviceAbleToConnect) {
//			this.alergeneDetailsTextView.setText("You might be offline.\nPlease, check your internet connection.");
//		}
	}

	
	/**
	 * OnTrackballEvent.
	 */
	@Override
	public boolean onTrackballEvent(MotionEvent event) {
		
		switch (event.getAction()) {
			case MotionEvent.ACTION_MOVE: {
				float x = event.getRawX();
				float y = event.getRawY();
				if (Math.abs((float) x / (float) y) > 1) {
					if (x <= 0) {
						tracker.trackEvent("Moves", "Trackball", "left", 77);
						modify(Direction.LEFT);
					} else {
						tracker.trackEvent("Moves", "Trackball", "right", 77);
						modify(Direction.RIGHT);
					}
				} else {
					if (y <= 0) {
						tracker.trackEvent("Moves", "Trackball", "up", 77);
						modify(Direction.UP);
					} else {
						tracker.trackEvent("Moves", "Trackball", "down", 77);
						modify(Direction.DOWN);
					}
				}
				break;
			}

		}

		return super.onTrackballEvent(event);
	}

	/**
	 * OnTouchEvent.
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			startXcoord = (int) event.getRawX();
			startYcoord = (int) event.getRawY();
			break;
		}

		case MotionEvent.ACTION_UP: {
			int deltaX = startXcoord - (int) event.getRawX();
			int deltaY = startYcoord - (int) event.getRawY();
			if (Math.abs((float) deltaX / (float) deltaY) > 1) {
				if (deltaX >= 0) {
					tracker.trackEvent("Moves", "Touch", "left", 77);
					modify(Direction.LEFT);
				} else {
					tracker.trackEvent("Moves", "Touch", "right", 77);
					modify(Direction.RIGHT);
				}
			} else {
				if (deltaY > 0) {
					tracker.trackEvent("Moves", "Touch", "down", 77);
					modify(Direction.DOWN);
				} else {
					tracker.trackEvent("Moves", "Touch", "up", 77);
					modify(Direction.UP);
				}
			}
			break;
		}
		}
		return true;
	}

	/**
	 * OnDestroy.
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		// Stop the tracker when it is no longer needed.
	    tracker.stopSession();
	}

	/**
	 * OnBackPressed.
	 * handles back button press
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		exit();
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.close:
				exit();
				break;
			case R.id.share:
				LogUtil.appendLog("#AlergiaskActivity.onOptionsItemSelected(): Sharing info.");
				share();
				break;
			case R.id.help:
				LogUtil.appendLog("#AlergiaskActivity.onOptionsItemSelected(): Showing help.");
				help();
				break;
			case R.id.senderror:
				LogUtil.appendLog("#AlergiaskActivity.onOptionsItemSelected(): Sending error log to developer.");
				sendErrorReport();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * UI modifications.
	 * @param direction LEFT, RIGHT, UP, DOWN
	 */
	private void modify(Direction direction) {

		Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		long[] vibratePattern = { 0, 200, 200 };
		
		List<String> districts = District
				.getDistrictNamesByCounty(County
						.getCountyByCountyName(countyNameTextView.getText()
								.toString()));
		List<String> alergenes = Alergene.getAlergenes();

		switch (depthLevel) {
		case COUNTY:

			AlergiaskActivity.currentDistrict = null;
			AlergiaskActivity.currentAlergene = null;
			
			for (int c = 0; c < counties.size(); c++) {
				if (counties.get(c).equals(countyNameTextView.getText())) {
					if (direction == Direction.RIGHT) {
						int index = (c - 1 >= 0 ? c - 1 : counties.size() - 1);
						this.countyNameTextView.setText(counties.get(index));
						AlergiaskActivity.currentCounty = County.getCountyByCountyName(counties.get(index));
						break;
					} else if (direction == Direction.LEFT) {
						int index = (c + 1 >= counties.size() ? 0 : c + 1);
						this.countyNameTextView.setText(counties.get(index));
						AlergiaskActivity.currentCounty = County.getCountyByCountyName(counties.get(index));
						break;
					} else if (direction == Direction.UP) {
						this.depthLevel = Level.DISTRICT;
						if (districts.size() > 0 && districts.get(0) != null) {
							this.districtNameTextView.setText(districts.get(0));
							AlergiaskActivity.currentDistrict = District.getDistrictByDistrictName(districts.get(0));
						}
						break;
					} else if (direction == Direction.DOWN) {
						// TODO but what?
						vibrator.vibrate(vibratePattern, -1);
					}

				}
			}
			break;
		case DISTRICT:

			AlergiaskActivity.currentAlergene = null;

			if (direction == Direction.LEFT || direction == Direction.RIGHT) {
				for (int c = 0; c < districts.size(); c++) {
					if (districts.get(c).equals(districtNameTextView.getText())) {
						int index = 0;
						if (direction == Direction.RIGHT) {
							index = (c - 1 >= 0 ? c - 1 : districts.size() - 1);
						} else if (direction == Direction.LEFT) {
							index = (c + 1 >= districts.size() ? 0 : c + 1);
						}
						this.districtNameTextView.setText(districts.get(index));
						AlergiaskActivity.currentDistrict = District.getDistrictByDistrictName(districts.get(index));
						break;
					}
				}
			}
			if (direction == Direction.UP) {
				// TODO
				this.depthLevel = Level.ALERGENE;
				if (alergenes.size() > 0 && alergenes.get(0) != null) {
					this.alergeneNameTextView.setText(alergenes.get(0));
					AlergiaskActivity.currentAlergene = Alergene
							.getAlergeneByName(alergenes.get(0));
					DistrictStatus data = loadData();
					this.alergeneDetailsTextView.setText(data.toHumanReadableString());
				}
			} else if (direction == Direction.DOWN) {
				// TODO
				this.depthLevel = Level.COUNTY;
				this.districtNameTextView.setText("");
				this.alergeneNameTextView.setText("");
				this.alergeneDetailsTextView.setText("");
			}
			break;

		case ALERGENE:
		
			if (direction == Direction.LEFT || direction == Direction.RIGHT) {
				for (int c = 0; c < alergenes.size(); c++) {
					if (alergenes.get(c).equals(alergeneNameTextView.getText())) {
						int index = 0;
						if (direction == Direction.RIGHT) {
							index = (c - 1 >= 0 ? c - 1 : alergenes.size() - 1);
						} else if (direction == Direction.LEFT) {
							index = (c + 1 >= alergenes.size() ? 0 : c + 1);
						}
						this.alergeneNameTextView.setText(alergenes.get(index));
						currentAlergene = Alergene.getAlergeneByName(alergenes.get(index));
						this.alergeneDetailsTextView.setText("Loading...");
						DistrictStatus data = loadData();
						this.alergeneDetailsTextView.setText(data.toHumanReadableString());
						break;
					}
				}
			}
			if (direction == Direction.UP) {
				// TODO we are at bottom nothing more to display...maybe reload
				// data?
				vibrator.vibrate(vibratePattern, -1);
				
				depthLevel = Level.ALERGENE;
				this.alergeneDetailsTextView.setText("Loading...");
				DistrictStatus data = loadData();
				this.alergeneDetailsTextView.setText(data.toHumanReadableString());
			} else if (direction == Direction.DOWN) {
				// TODO
				this.depthLevel = Level.DISTRICT;
				AlergiaskActivity.currentAlergene = null;
				this.alergeneNameTextView.setText("");
				this.alergeneDetailsTextView.setText("");
			}
			break;
		}
	}

	/**
	 * Loads data.
	 * @return districtStatus object of currently slected district and alergene combination
	 */
	private DistrictStatus loadData() {
		String url = "http://www.alergia.sk/pelove-spravodajstvo/verejnost/xml?xml="
				+ AlergiaskActivity.currentCounty.getCountyNumber()
				+ "-"
				+ AlergiaskActivity.currentAlergene.getAlergeneNumber();
		
		tracker.trackPageView(
				"/" 
				+ ( currentCounty == null ? "null" : currentCounty.getCountyName()) 
				+ "/" 
				+ (currentDistrict == null ? "null" : currentDistrict.getDistrictName()) 
				+ "/" 
				+ (currentAlergene == null ? "null" : currentAlergene.getAlergeneName()));
		
		try {
			String content = HttpUtil.getContent(url);

			XmlUtil xmlUtil = new XmlUtil(content,
					AlergiaskActivity.currentAlergene.getAlergeneNumber(),
					AlergiaskActivity.currentCounty.getCountyNumber());
			
			List<DistrictStatus> districtStatusList = xmlUtil.getDistrictStatusList();
			
			for (DistrictStatus districtStatus : districtStatusList) {
				if (AlergiaskActivity.currentAlergene.equals(districtStatus.getAlergene())
						&& AlergiaskActivity.currentDistrict.equals(districtStatus.getDistrict())) {
					LogUtil.appendLog("#AlergiaskActivity.loadData(): " + districtStatus.toString());
					return districtStatus;
				}
			}
		} catch (Exception e) {
			Log.e(LOG_TAG, "Very weird exception");
			LogUtil.appendLog("Very weird Exception with URL: '" + url + "'");
		}
		
		DistrictStatus districtStatus = new DistrictStatus(
				AlergiaskActivity.currentDistrict,
				AlergiaskActivity.currentAlergene, 
				Prognosis.UNKNOWN,
				Concentration.UNKNOWN);
		
		LogUtil.appendLog("#AlergiaskActivity.loadData(): " + districtStatus.toString());
		
		return districtStatus;
	}

	/**
	 * Single GPS fix enabled only.
	 * @return whether the app already got loaction fix
	 */
	public boolean isGotGPSfix() {
		return gotGPSfix;
	}

	/**
	 * Use share option provided by Android.
	 */
	private void share() {
		StringBuilder message = new StringBuilder(getResources().getText(R.string.app_name) + "\n\n");
		message.append(getResources().getText(R.string.county) + ": " + currentCounty.getCountyName() + "\n");
		message.append(getResources().getText(R.string.district) + ": " + currentDistrict.getDistrictName() + "\n");
		message.append(getResources().getText(R.string.allergene) + ": " + currentAlergene.getAlergeneName() + "\n");
		message.append(this.alergeneDetailsTextView.getText() + "\n");
		message.append("\n--\n" + (new Date()) + "\nhttps://groups.google.com/d/forum/alergiask-discuss\n\nSent from my Android phone...");
		
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, message.toString());
		sendIntent.setType("text/plain");

		tracker.trackPageView("/share");

		this.startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share)));
	}
	/**
	 * Send Error report and truncate the log file.
	 */
	private void sendErrorReport() {
//		String logContent = LogUtil.readLog();
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		emailIntent.setType("plain/text");
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ "slavomir.hustaty@gmail.com" });
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Alergia.sk - error report");
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Error report is attached.\n");
		emailIntent.putExtra(android.content.Intent.EXTRA_STREAM, LogUtil.getLogFileUri());

		tracker.trackPageView("/sendErrorReport");
		
		this.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
//		LogUtil.clearLog();
	}
	
	/**
	 * Exit application.
	 */
	private void exit() {
		LogUtil.appendLog("#AlergiaskActivity.exit(): ### EXITING APPLICATION ###");

		tracker.trackPageView("/exit");
		
		finish();
		System.runFinalizersOnExit(true);
		System.exit(0);
	}
	
	/**
	 * Show help dialog.
	 */
	private void help() {

		tracker.trackPageView("/help");

		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Alergia.sk");
		alertDialog.setMessage(getResources().getText(R.string.helpText).toString());
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
		   public void onClick(DialogInterface dialog, int which) {
		      // here you can add functions
		   }
		});
		alertDialog.setIcon(R.drawable.ic_launcher);
		alertDialog.show();
	}
		
}