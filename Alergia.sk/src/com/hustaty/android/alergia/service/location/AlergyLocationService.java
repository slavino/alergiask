package com.hustaty.android.alergia.service.location;

import static com.hustaty.android.alergia.AlergiaskActivity.LOG_TAG;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.hustaty.android.alergia.AlergiaskActivity;
import com.hustaty.android.alergia.enums.District;
import com.hustaty.android.alergia.enums.ZIPCode;
import com.hustaty.android.alergia.util.LogUtil;

public class AlergyLocationService {

	private List<Address> addressList = new ArrayList<Address>();
	
	private Location myLocation;
	
	private AlergiaskActivity activity;
	
	public AlergyLocationService(final AlergiaskActivity activity) {
		super();

		// Acquire a reference to the system Location Manager
		LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

		this.activity = activity;
		
		this.myLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		Geocoder geocoder = new Geocoder(activity.getApplicationContext());
		if(this.myLocation != null) {
			try {
				this.addressList = geocoder.getFromLocation(this.myLocation.getLatitude(), this.myLocation.getLongitude(), 1);
				logAddressList(addressList);
			} catch (IOException e) {
				Log.e(LOG_TAG, "#getLastKnownLocation(): " + e.getMessage());
			}
		}
		
		// Define a listener that responds to location updates
		LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				if(!activity.isGotGPSfix()) {
					// Called when a new location is found by the network location
					// provider.
					LogUtil.appendLog("#AlergyLocationService.LocationListener.onLocationChanged(): Will try to resolve GPS to Address: LAT:" 
										+ location.getLatitude() 
										+ ", LON:" + location.getLongitude()
										+ ", PROVIDER: " + location.getProvider());
					
					Geocoder geocoder = new Geocoder(activity.getApplicationContext());
					
					try {
						addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
						logAddressList(addressList);
					} catch (IOException e) {
						LogUtil.appendLog("#AlergyLocationService.LocationListener.onLocationChanged(): " + e.getMessage());
						Log.e(LOG_TAG, e.getMessage());
					}
				}
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				Log.i(LOG_TAG, "#onStatusChanged(): Provider enabled: " + provider);
				LogUtil.appendLog("#AlergyLocationService.LocationListener.onStatusChanged(): " + provider + ", status: " + status);
			}

			public void onProviderEnabled(String provider) {
				Log.i(LOG_TAG, "#onProviderEnabled(): Provider enabled: " + provider);
				LogUtil.appendLog("#AlergyLocationService.LocationListener.onProviderEnabled(): " + provider);
			}

			public void onProviderDisabled(String provider) {
				Log.i(LOG_TAG, "#onProviderDisabled(): Provider disabled " + provider);
				LogUtil.appendLog("#AlergyLocationService.LocationListener.onProviderDisabled(): " + provider);
			}
		};

		// Register the listener with the Location Manager to receive location
		// updates
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 1000, locationListener);
		
	}

	private void logAddressList(List<Address> addressList) {

		StringBuilder logEntry = new StringBuilder(); 
		
		for (Address address : addressList) {
			logEntry.append("### Iterating over address ###\n");
			logEntry.append("countryName: " + address.getCountryName() + "\n");
			logEntry.append("countryCode: " + address.getCountryCode() + "\n");
			logEntry.append("AdminArea: " + address.getAdminArea() + "\n");
			logEntry.append("FeatureName: " + address.getFeatureName() + "\n");
			logEntry.append("PostalCode: " + address.getPostalCode() + "\n");
			logEntry.append("SubAdminArea: " + address.getSubAdminArea() + "\n");
			logEntry.append("Thoroughfare: " + address.getThoroughfare() + "\n");
			logEntry.append("Extras: " + address.getExtras() + "\n");
			for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
				logEntry.append("AddressLine[" + i + "]: " + address.getAddressLine(i) + "\n");
			}
			Log.d(LOG_TAG, logEntry.toString());
			LogUtil.appendLog(logEntry.toString());
			
			//user notification that application has discovered his location
			if(!activity.isGotGPSfix()) {
				Toast toast = Toast.makeText(this.activity.getApplicationContext(), 
						"Got your location:\n" + address.getSubAdminArea() + ", " + address.getAdminArea() , 
						Toast.LENGTH_LONG);
				toast.show();
			}
			
		}
	}

	public District getDistrictFromLastAddress() {
		
		if(this.addressList == null || this.addressList.size() == 0) {
			if(this.myLocation != null) {
				return getNearestDistrict(this.myLocation);
			}
			LogUtil.appendLog("AlergyLocationService.getDistrictFromLastAddress(): got empty addressList - return NULL");
			return null;
		}
		
		Address address = this.addressList.get(0);

		//try to fetch District from ZIPcode
		if(address.getPostalCode() != null) {
			ZIPCode zipCode = ZIPCode.getByZIPcode(address.getPostalCode());
			if(zipCode != null) {
				LogUtil.appendLog("AlergyLocationService.getDistrictFromLastAddress(): got from ZIP code - " + zipCode.getDistrict().getDistrictName());
				return zipCode.getDistrict();
			}
		} else if((address.getMaxAddressLineIndex() > 0)
				&& (address.getAddressLine(address.getMaxAddressLineIndex()-1) != null)) {
			Pattern p = Pattern.compile("[0-9]{5}");
			Matcher m = p.matcher(address.getAddressLine(address.getMaxAddressLineIndex()-1));
			while(m.find()) {
				String identifiedRegexZIP = m.group();
				try {
					ZIPCode zipCode = ZIPCode.getByZIPcode(identifiedRegexZIP);
					if(zipCode != null) {
						LogUtil.appendLog("AlergyLocationService.getDistrictFromLastAddress(): got from last AddressLine " + identifiedRegexZIP + "- " + zipCode.getDistrict().getDistrictName());
						return zipCode.getDistrict();
					}
				} catch (Exception e) {
					LogUtil.appendLog("AlergyLocationService.getDistrictFromLastAddress(): Exception - " + identifiedRegexZIP + e);
				}
			}
		}
		
		//try to fetch District from subAdminArea
		String subAdminArea = address.getSubAdminArea();
		
		if(subAdminArea != null) {
			
			if(subAdminArea.contains("-")) {
				subAdminArea = subAdminArea.substring(0, subAdminArea.indexOf("-")).trim();
			}
			
			if(subAdminArea != "") {
				District district = District.getDistrictByDistrictName(subAdminArea); 
				LogUtil.appendLog("AlergyLocationService.getDistrictFromLastAddress(): got from subAdminArea '" + subAdminArea + "' - " + district);
				if(district == null) {
					district = getNearestDistrict(this.myLocation);
				}
				return district;
			}
			
		}
		
		LogUtil.appendLog("AlergyLocationService.getDistrictFromLastAddress(): got NOT empty addressList BUT couldn't fetch District from " + subAdminArea + " - return NULL");
		return null;
	}

	
	private District getNearestDistrict(Location myLocation) {
		double minValue = Double.POSITIVE_INFINITY;
		District nearestDistrict = null;
		for(District district : District.getAllDistricts()) {
			double value = distance(myLocation.getLatitude(), myLocation.getLongitude(), district.getLatitude(), district.getLongitude()); 
			if(value < minValue) {
				minValue = value;
				nearestDistrict = district;
			}
		}
		
		LogUtil.appendLog("AlergyLocationService.getNearestDistrict(): got Location LAT:" + myLocation.getLatitude() + ", LON:" + myLocation.getLongitude() + ", " + myLocation.toString() +  " - returning District " + nearestDistrict.getDistrictName());

		return nearestDistrict;
	}
	
	private double distance(double x0, double y0, double x1, double y1) {
		return Math.sqrt(Math.pow(Math.abs(y0-y1),2) + Math.pow(Math.abs(x0-x1), 2));
	}
	
}
