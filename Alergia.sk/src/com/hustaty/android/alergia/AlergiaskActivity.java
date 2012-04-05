package com.hustaty.android.alergia;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
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
import com.hustaty.android.alergia.util.HttpUtil;
import com.hustaty.android.alergia.util.XmlUtil;

public class AlergiaskActivity extends Activity {

	public static final String LOG_TAG = "AlergiaSK";
	
	private RelativeLayout container;
	
	private Level depthLevel = Level.COUNTY;
	
	private static County currentCounty = County.BB;
	private static District currentDistrict = District.BB;
	private static Alergene currentAlergene = Alergene.OVERALL;
	
	private static final List<String> counties = County.getCounties();
    
	private int startXcoord;
	
	private int startYcoord;

	private TextView countyNameTextView;
	private TextView districtNameTextView;
	private TextView alergeneNameTextView;

	//Temporary and will be removed
	private TextView alergeneDetailsTextView;
	
	private AdView adView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		this.container = (RelativeLayout) findViewById(R.id.container);

		this.countyNameTextView = new TextView(container.getContext());
		LayoutParams layoutParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, R.id.container);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, R.id.container);
		layoutParams.topMargin = 5;
		this.countyNameTextView.setText(counties.get(0));
		this.countyNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
		this.countyNameTextView.setId(0x7f060000);
		this.container.addView(this.countyNameTextView, layoutParams);
		
		this.districtNameTextView = new TextView(container.getContext());
		layoutParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, R.id.container);
		layoutParams.addRule(RelativeLayout.BELOW, 0x7f060000);
		layoutParams.topMargin = 5;
		this.districtNameTextView.setText("");
		this.districtNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);
		this.districtNameTextView.setId(0x7f070000);
		this.container.addView(this.districtNameTextView, layoutParams);
		
		this.alergeneNameTextView = new TextView(container.getContext());
		layoutParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, R.id.container);
		layoutParams.addRule(RelativeLayout.BELOW, 0x7f070000);
		layoutParams.topMargin = 5;
		this.alergeneNameTextView.setText("");
		this.alergeneNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
		this.alergeneNameTextView.setId(0x7f080000);
		this.container.addView(this.alergeneNameTextView, layoutParams);
		
		this.alergeneDetailsTextView = new TextView(container.getContext());
		layoutParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, R.id.container);
		layoutParams.addRule(RelativeLayout.CENTER_VERTICAL, R.id.container);
		layoutParams.topMargin = 5;
		this.alergeneDetailsTextView.setText("");
		this.alergeneDetailsTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
		this.alergeneDetailsTextView.setId(0x7f090000);
		this.container.addView(this.alergeneDetailsTextView, layoutParams);

		//Google AdMob advertisment
		this.adView = new AdView(this, AdSize.BANNER, "Test");
		layoutParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, R.id.container);
		layoutParams.addRule(RelativeLayout.BELOW, this.alergeneDetailsTextView.getId());
		layoutParams.topMargin = 5;
		this.container.addView(adView, layoutParams);

		this.adView.loadAd(new AdRequest());
		//Google AdMob advertisment

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//TextView textView = (TextView) findViewById(R.id.textView1);

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: {
				startXcoord = (int) event.getRawX();
	            startYcoord = (int) event.getRawY();
				break;
			}
	
//			case MotionEvent.ACTION_MOVE: {
//	            break;
//			}
	
			case MotionEvent.ACTION_UP: {
				int deltaX = startXcoord - (int)event.getRawX();
				int deltaY = startYcoord - (int)event.getRawY();
				if(Math.abs((float)deltaX/(float)deltaY) > 1) {
					if(deltaX >= 0) {
						//textView.setText("to left " + depthLevel + " | " + currentCounty + currentDistrict + currentAlergene);
						modify(Direction.LEFT);
					} else {
						//textView.setText("to right "  + depthLevel + " | " + currentCounty + currentDistrict + currentAlergene);
						modify(Direction.RIGHT);
					}
				} else {
					if(deltaY >= 0) {
						//textView.setText("up " + depthLevel + " | " + currentCounty + currentDistrict + currentAlergene);
						modify(Direction.UP);
					} else {
						//textView.setText("down " + depthLevel + " | " + currentCounty + currentDistrict + currentAlergene);
						modify(Direction.DOWN);
					}
				}
				break;
			}
		}

		return true;
	}
	
	@Override
	protected void onDestroy() {
		if (adView != null) {
			adView.destroy();
		}
		super.onDestroy();
	}

	private void modify(Direction direction) {
		
		List<String> districts = District.getDistrictNamesByCounty(County.getCountyByCountyName(countyNameTextView.getText().toString()));
		List<String> alergenes = Alergene.getAlergenes(); 
		
		switch(depthLevel) {
			case COUNTY :
				for(int c = 0 ; c < counties.size(); c++) {
					if(counties.get(c).equals(countyNameTextView.getText())) {
						if(direction == Direction.RIGHT) {
							int index = (c-1 >= 0 ? c-1 : counties.size()-1 );
							this.countyNameTextView.setText(counties.get(index));
							AlergiaskActivity.currentCounty = County.getCountyByCountyName(counties.get(index));
							break;
						} else if(direction == Direction.LEFT) {
							int index = (c+1 >= counties.size() ? 0 : c+1);
							this.countyNameTextView.setText(counties.get(index));
							AlergiaskActivity.currentCounty = County.getCountyByCountyName(counties.get(index));
							break;
						} else if(direction == Direction.UP) {
							this.depthLevel = Level.DISTRICT;
							if(districts.size() > 0 && districts.get(0) != null) {
								this.districtNameTextView.setText(districts.get(0));
								AlergiaskActivity.currentDistrict = District.getDistrictByDistrictName(districts.get(0));
							}
							break;
						} else if(direction == Direction.DOWN) {
							//TODO but what?
						}
						
					}
				}
				break;
			case DISTRICT :
				if(direction == Direction.LEFT || direction == Direction.RIGHT) {
					for(int c = 0 ; c < districts.size(); c++) {
						if(districts.get(c).equals(districtNameTextView.getText())) {
							int index = 0;
							if(direction == Direction.RIGHT) {
								index = (c-1 >= 0 ? c-1 : districts.size()-1 );
							} else if(direction == Direction.LEFT) {
								index = (c+1 >= districts.size() ? 0 : c+1);
							}
							this.districtNameTextView.setText(districts.get(index));
							AlergiaskActivity.currentDistrict = District.getDistrictByDistrictName(districts.get(index));
							break;
						}
					}
				}
				if(direction == Direction.UP) {
					//TODO
					this.depthLevel = Level.ALERGENE;
					if(alergenes.size() > 0 && alergenes.get(0) != null) {
						this.alergeneNameTextView.setText(alergenes.get(0));
						AlergiaskActivity.currentAlergene = Alergene.getAlergeneByName(alergenes.get(0));
					}
				} else if(direction == Direction.DOWN) {
					//TODO
					this.depthLevel = Level.COUNTY;
					this.districtNameTextView.setText("");
					this.alergeneDetailsTextView.setText("");
				}
				break;
			case ALERGENE :
				if(direction == Direction.LEFT || direction == Direction.RIGHT) {
					for(int c = 0 ; c < alergenes.size(); c++) {
						if(alergenes.get(c).equals(alergeneNameTextView.getText())) {
							int index = 0;
							if(direction == Direction.RIGHT) {
								index =  (c-1 >= 0 ? c-1 : alergenes.size()-1 );
							} else if(direction == Direction.LEFT) {
								index = (c+1 >= alergenes.size() ? 0 : c+1);
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
				if(direction == Direction.UP) {
					//TODO we are at bottom nothing more to display...maybe reload data?
					depthLevel = Level.ALERGENE;
					this.alergeneDetailsTextView.setText("Loading...");
					DistrictStatus data = loadData();
					this.alergeneDetailsTextView.setText(data.toHumanReadableString());
				} else if(direction == Direction.DOWN) {
					//TODO
					this.depthLevel = Level.DISTRICT;
					this.alergeneNameTextView.setText("");
					this.alergeneDetailsTextView.setText("");
				}
				break;
		}
	}
	
	private DistrictStatus loadData() {
		String url = "http://alergia.sk/pelove-spravodajstvo/verejnost/xml?xml=" + AlergiaskActivity.currentCounty.getCountyNumber() + "-" + AlergiaskActivity.currentAlergene.getAlergeneNumber();
		try {
			String content = HttpUtil.getContent(url);
			
			XmlUtil xmlUtil = new XmlUtil(content, AlergiaskActivity.currentAlergene.getAlergeneNumber(), AlergiaskActivity.currentCounty.getCountyNumber());
			List<DistrictStatus> districtStatusList = xmlUtil.getDistrictStatusList();
			for(DistrictStatus districtStatus : districtStatusList) {
				if(AlergiaskActivity.currentAlergene.equals(districtStatus.getAlergene()) && AlergiaskActivity.currentDistrict.equals(districtStatus.getDistrict())) {
					return districtStatus;
				}
			}
		} catch (Exception e) {
			Log.e(LOG_TAG, e.getMessage());
		}
		return new DistrictStatus(AlergiaskActivity.currentDistrict, AlergiaskActivity.currentAlergene, Prognosis.UNKNOWN, Concentration.UNKNOWN);
	}
	
}