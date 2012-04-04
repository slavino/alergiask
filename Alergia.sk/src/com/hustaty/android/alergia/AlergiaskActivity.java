package com.hustaty.android.alergia;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.hustaty.android.alergia.beans.DistrictStatus;
import com.hustaty.android.alergia.enums.Alergene;
import com.hustaty.android.alergia.enums.County;
import com.hustaty.android.alergia.enums.Direction;
import com.hustaty.android.alergia.enums.District;
import com.hustaty.android.alergia.enums.Level;
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

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		container = (RelativeLayout) findViewById(R.id.container);

		TextView countyNameTextView = new TextView(container.getContext());
		LayoutParams layoutParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, R.id.container);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, R.id.container);
		layoutParams.topMargin = 5;
		countyNameTextView.setText(counties.get(0));
		countyNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
		countyNameTextView.setId(0x7f060000);
		container.addView(countyNameTextView, layoutParams);
		
		TextView districtNameTextView = new TextView(container.getContext());
		layoutParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, R.id.container);
		layoutParams.addRule(RelativeLayout.BELOW, 0x7f060000);
		layoutParams.topMargin = 5;
		districtNameTextView.setText("");
		districtNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);
		districtNameTextView.setId(0x7f070000);
		container.addView(districtNameTextView, layoutParams);
		
		TextView alergeneNameTextView = new TextView(container.getContext());
		layoutParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, R.id.container);
		layoutParams.addRule(RelativeLayout.BELOW, 0x7f070000);
		layoutParams.topMargin = 5;
		alergeneNameTextView.setText("");
		alergeneNameTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
		alergeneNameTextView.setId(0x7f080000);
		container.addView(alergeneNameTextView, layoutParams);
	
		ImageView barImageView = new ImageView(container.getContext());
		layoutParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.FILL_PARENT);
		layoutParams.addRule(RelativeLayout.BELOW, 0x7f080000);
		layoutParams.addRule(RelativeLayout.ALIGN_RIGHT, R.id.container);
		layoutParams.topMargin = 5;
		barImageView.setId(0x7f080001);
		barImageView.setImageResource(R.drawable.barlevel0);
		container.addView(barImageView, layoutParams);

		
		//Temporary output will be removed
		TextView alergeneDetailsTextView = new TextView(container.getContext());
		layoutParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.ALIGN_LEFT, R.id.container);
		layoutParams.addRule(RelativeLayout.BELOW, 0x7f080000);
		layoutParams.topMargin = 5;
		alergeneDetailsTextView.setText("");
		alergeneDetailsTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
		alergeneDetailsTextView.setId(0x7f090000);
		container.addView(alergeneDetailsTextView, layoutParams);
		//END - Temporary output will be removed
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		TextView textView = (TextView) findViewById(R.id.textView1);

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
						textView.setText("to left " + depthLevel + " | " + currentCounty + currentDistrict + currentAlergene);
						modify(Direction.LEFT);
					} else {
						textView.setText("to right "  + depthLevel + " | " + currentCounty + currentDistrict + currentAlergene);
						modify(Direction.RIGHT);
					}
				} else {
					if(deltaY >= 0) {
						textView.setText("up " + depthLevel + " | " + currentCounty + currentDistrict + currentAlergene);
						modify(Direction.UP);
					} else {
						textView.setText("down " + depthLevel + " | " + currentCounty + currentDistrict + currentAlergene);
						modify(Direction.DOWN);
					}
				}
				break;
			}
		}

		return true;
	}

	private void modify(Direction direction) {
		TextView countyNameTextView = (TextView)findViewById(0x7f060000);
		TextView districtNameTextView = (TextView)findViewById(0x7f070000);
		TextView alergeneNameTextView = (TextView)findViewById(0x7f080000);
		TextView alergeneDetailsTextView = (TextView)findViewById(0x7f090000);
		ImageView barImageView = (ImageView)findViewById(0x7f080001);

		
		List<String> districts = District.getDistrictNamesByCounty(County.getCountyByCountyName(countyNameTextView.getText().toString()));
		List<String> alergenes = Alergene.getAlergenes(); 
		
		switch(depthLevel) {
			case COUNTY :
				for(int c = 0 ; c < counties.size(); c++) {
					if(counties.get(c).equals(countyNameTextView.getText())) {
						if(direction == Direction.RIGHT) {
							int index = (c-1 >= 0 ? c-1 : counties.size()-1 );
							countyNameTextView.setText(counties.get(index));
							currentCounty = County.getCountyByCountyName(counties.get(index));
							break;
						} else if(direction == Direction.LEFT) {
							int index = (c+1 >= counties.size() ? 0 : c+1);
							countyNameTextView.setText(counties.get(index));
							currentCounty = County.getCountyByCountyName(counties.get(index));
							break;
						} else if(direction == Direction.UP) {
							depthLevel = Level.DISTRICT;
							if(districts.size() > 0 && districts.get(0) != null) {
								districtNameTextView.setText(districts.get(0));
								currentDistrict = District.getDistrictByDistrictName(districts.get(0));
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
							districtNameTextView.setText(districts.get(index));
							currentDistrict = District.getDistrictByDistrictName(districts.get(index));
							break;
						}
					}
				}
				if(direction == Direction.UP) {
					//TODO
					depthLevel = Level.ALERGENE;
					if(alergenes.size() > 0 && alergenes.get(0) != null) {
						alergeneNameTextView.setText(alergenes.get(0));
						currentAlergene = Alergene.getAlergeneByName(alergenes.get(0));
					}
				} else if(direction == Direction.DOWN) {
					//TODO
					depthLevel = Level.COUNTY;
					districtNameTextView.setText("");
					alergeneDetailsTextView.setText("");
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
							alergeneNameTextView.setText(alergenes.get(index));
							currentAlergene = Alergene.getAlergeneByName(alergenes.get(index));
							alergeneDetailsTextView.setText("Loading...");
							alergeneDetailsTextView.setText(loadData());
							break;
						}
					}
				}
				if(direction == Direction.UP) {
					//TODO we are at bottom nothing more to display...maybe reload data?
					depthLevel = Level.ALERGENE;
					alergeneDetailsTextView.setText("Loading...");
					alergeneDetailsTextView.setText(loadData());
				} else if(direction == Direction.DOWN) {
					//TODO
					depthLevel = Level.DISTRICT;
					alergeneNameTextView.setText("");
					alergeneDetailsTextView.setText("");
				}
				break;
		}
	}
	
	private String loadData() {
		String url = "http://alergia.sk/pelove-spravodajstvo/verejnost/xml?xml=" + currentCounty.getCountyNumber() + "-" + currentAlergene.getAlergeneNumber();
		String content = HttpUtil.getContent(url);
		
		XmlUtil xmlUtil = new XmlUtil(content, currentAlergene.getAlergeneNumber(), currentCounty.getCountyNumber());
		List<DistrictStatus> districtStatusList = xmlUtil.getDistrictStatusList();
		for(DistrictStatus districtStatus : districtStatusList) {
			if(currentAlergene.equals(districtStatus.getAlergene()) && currentDistrict.equals(districtStatus.getDistrict())) {
				return districtStatus.toString();
			}
		}
		return content;
	}
	
}