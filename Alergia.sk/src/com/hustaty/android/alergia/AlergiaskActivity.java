package com.hustaty.android.alergia;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
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
import com.hustaty.android.alergia.util.XmlUtil;

public class AlergiaskActivity extends Activity {

	public static final String LOG_TAG = "AlergiaSK";

	private RelativeLayout container;

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

	// Temporary and will be removed
	private TextView alergeneDetailsTextView;
	
	private AdView adView;

	private boolean gotGPSfix = false;
	
	
	//A ProgressDialog object  
    private ProgressDialog progressDialog;  
    
	/** 
	 * Called when the activity is first created. 
	 * 
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.main);
	
		//Initialize a LoadViewTask object and call the execute() method  
        new LoadViewTask().execute();         
  
		

//		this.container = (RelativeLayout) findViewById(R.id.container);
//		initUI(null);
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
            /* This is just a code that delays the thread execution 4 times, 
             * during 850 milliseconds and updates the current progress. This 
             * is where the code that is going to be executed on a background 
             * thread must be placed. 
             */  
            try {  
                //Get the current thread's token  
                synchronized (this) {  
                    //Initialize an integer (that will act as a counter) to zero  
                    int counter = 0;  
                    //While the counter is smaller than four  
                    while(counter <= 4) {  
                        //Wait 850 milliseconds  
                        this.wait(850);  
                        //Increment the counter  
                        counter++;  
                        //Set the current progress.  
                        //This value is going to be passed to the onProgressUpdate() method.  
                        publishProgress(counter*25);  
                    }  
                }  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
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
            AlergiaskActivity.this.container = (RelativeLayout) findViewById(R.id.container);
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
		this.container = (RelativeLayout) findViewById(R.id.container);

		initUI(newConfig);
	}

	/**
	 * Initiate UI.
	 */
	private void initUI(Configuration newConfig) {
		
		/**
		 * county textView
		 */
		this.countyNameTextView = new TextView(container.getContext());
		LayoutParams layoutParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, R.id.container);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, R.id.container);
		layoutParams.topMargin = 5;

		if(AlergiaskActivity.currentCounty != null) {
			this.countyNameTextView.setText(AlergiaskActivity.currentCounty.getCountyName());
			this.depthLevel = Level.COUNTY;
		} else {
			this.countyNameTextView.setText(counties.get(0));
			this.depthLevel = Level.COUNTY;
		}
		this.countyNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
		this.countyNameTextView.setId(0x7f060000);
		this.container.addView(this.countyNameTextView, layoutParams);

		
		/**
		 * district textView
		 */
		this.districtNameTextView = new TextView(container.getContext());
		layoutParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, R.id.container);
		layoutParams.addRule(RelativeLayout.BELOW,
				this.countyNameTextView.getId());
		layoutParams.topMargin = 5;

		if(AlergiaskActivity.currentDistrict != null) {
			this.districtNameTextView.setText(AlergiaskActivity.currentDistrict.getDistrictName());
			this.depthLevel = Level.DISTRICT;
		} else {
			this.districtNameTextView.setText("");
			this.depthLevel = Level.COUNTY;
		}
		this.districtNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);
		this.districtNameTextView.setId(0x7f070000);
		this.container.addView(this.districtNameTextView, layoutParams);

		
		/**
		 * alergene textView 
		 */
		this.alergeneNameTextView = new TextView(container.getContext());
		layoutParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, R.id.container);
		layoutParams.addRule(RelativeLayout.BELOW,
				this.districtNameTextView.getId());
		layoutParams.topMargin = 5;

		if(AlergiaskActivity.currentAlergene != null) {
			this.alergeneNameTextView.setText(AlergiaskActivity.currentAlergene.getAlergeneName());
			this.depthLevel = Level.ALERGENE;
		} else {
			this.alergeneNameTextView.setText("");
			this.depthLevel = (AlergiaskActivity.currentDistrict != null ? Level.DISTRICT : Level.COUNTY);
		}
		
		this.alergeneNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
		this.alergeneNameTextView.setId(0x7f080000);
		this.container.addView(this.alergeneNameTextView, layoutParams);

		/**
		 * alergene details textView
		 */
		this.alergeneDetailsTextView = new TextView(container.getContext());
		layoutParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, R.id.container);
		layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, R.id.container);
		layoutParams.topMargin = 5;
		
		if(AlergiaskActivity.currentAlergene != null) {
			this.alergeneDetailsTextView.setText("Loading...");
			DistrictStatus data = loadData();
			this.alergeneDetailsTextView.setText(data.toHumanReadableString());
		} else {
			this.alergeneDetailsTextView.setText("Vertikálnymi a horizontálnymi\nťahmi po displeji ovládate\nzobrazené položky.");
		}
		this.alergeneDetailsTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
		this.alergeneDetailsTextView.setId(0x7f090000);
		this.container.addView(this.alergeneDetailsTextView, layoutParams);

		// Google AdMob advertisement
		AdSize adSize = AdSize.BANNER;

		if(newConfig != null) {
			if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
				adSize = AdSize.IAB_BANNER;
			}
		} else {
			//keep defaults
		}
		
		this.adView = new AdView(this, adSize, "a14f7d7523406d6");
		layoutParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, R.id.container);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,
				this.alergeneDetailsTextView.getId());
		layoutParams.topMargin = 5;
		this.container.addView(this.adView, layoutParams);
		AdRequest adRequest = new AdRequest();
		
		adRequest.addTestDevice("5B68BB4FA54B94EA5FE1EA69B5824A66");
		adRequest.addTestDevice("30335FA7CB8500EC");
		
		this.adView.loadAd(adRequest);
		// Google AdMob advertisement

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
						modify(Direction.LEFT);
					} else {
						modify(Direction.RIGHT);
					}
				} else {
					if (y <= 0) {
						modify(Direction.UP);
					} else {
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
					modify(Direction.LEFT);
				} else {
					modify(Direction.RIGHT);
				}
			} else {
				if (deltaY > 0) {
					modify(Direction.DOWN);
				} else {
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
		if (this.adView != null) {
			this.adView.destroy();
		}
		super.onDestroy();
	}

	/**
	 * OnBackPressed.
	 * handles back button press
	 */
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
		System.runFinalizersOnExit(true);
		System.exit(0);
	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
//	    return true;
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()) {
			case R.id.close:
				finish();
				System.runFinalizersOnExit(true);
				System.exit(0);
				break;
			case R.id.share:
				share();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * UI modifications.
	 * @param direction LEFT, RIGHT, UP, DOWN
	 */
	private void modify(Direction direction) {

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
						AlergiaskActivity.currentCounty = County
								.getCountyByCountyName(counties.get(index));
						break;
					} else if (direction == Direction.LEFT) {
						int index = (c + 1 >= counties.size() ? 0 : c + 1);
						this.countyNameTextView.setText(counties.get(index));
						AlergiaskActivity.currentCounty = County
								.getCountyByCountyName(counties.get(index));
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
				depthLevel = Level.ALERGENE;
				this.alergeneDetailsTextView.setText("Loading...");
				DistrictStatus data = loadData();
				this.alergeneDetailsTextView.setText(data.toHumanReadableString());
//				share();
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
		try {
			String content = HttpUtil.getContent(url);

			XmlUtil xmlUtil = new XmlUtil(content,
					AlergiaskActivity.currentAlergene.getAlergeneNumber(),
					AlergiaskActivity.currentCounty.getCountyNumber());
			
			List<DistrictStatus> districtStatusList = xmlUtil.getDistrictStatusList();
			
			for (DistrictStatus districtStatus : districtStatusList) {
				if (AlergiaskActivity.currentAlergene.equals(districtStatus.getAlergene())
						&& AlergiaskActivity.currentDistrict.equals(districtStatus.getDistrict())) {
					return districtStatus;
				}
			}
		} catch (Exception e) {
			Log.e(LOG_TAG, "Very weird exception");
		}
		return new DistrictStatus(
				AlergiaskActivity.currentDistrict,
				AlergiaskActivity.currentAlergene, 
				Prognosis.UNKNOWN,
				Concentration.UNKNOWN);
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
		message.append("\n--\n" + (new Date()) + "\n\nSent from my Android phone...");
		
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, message.toString());
		sendIntent.setType("text/plain");
		startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share)));
	}
	
}