package com.hustaty.android.alergia.service.location;

import static com.hustaty.android.alergia.AlergiaskActivity.LOG_TAG;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	
	private AlergiaskActivity activity;
	
	public AlergyLocationService(final AlergiaskActivity activity) {
		super();

		// Acquire a reference to the system Location Manager
		LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

		this.activity = activity;
		
		Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		Geocoder geocoder = new Geocoder(activity.getApplicationContext());
		if(location != null) {
			try {
				this.addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
				logAddressList(addressList);
			} catch (IOException e) {
				Log.e(LOG_TAG, "#getLastKnownLocation(): " + e.getMessage());
			}
		}
		
		// Define a listener that responds to location updates
		LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				// Called when a new location is found by the network location
				// provider.
				Geocoder geocoder = new Geocoder(activity.getApplicationContext());
				
				try {
					addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
					logAddressList(addressList);
				} catch (IOException e) {
					Log.e(LOG_TAG, e.getMessage());
				}

			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				Log.i(LOG_TAG, "#onStatusChanged(): Provider enabled: " + provider);
			}

			public void onProviderEnabled(String provider) {
				Log.i(LOG_TAG, "#onProviderEnabled(): Provider enabled: " + provider);
			}

			public void onProviderDisabled(String provider) {
				Log.i(LOG_TAG, "#onProviderDisabled(): Provider disabled " + provider);
			}
		};

		// Register the listener with the Location Manager to receive location
		// updates
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		
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
		
		if(this.addressList.size() == 0) {
			LogUtil.appendLog("AlergyLocationService.getDistrictFromLastAddress(): got empty addressList - return NULL");
			return null;
		}
		
		Address address = this.addressList.get(0);

		//try to fetch District from ZIPcode
		ZIPCode zipCode = ZIPCode.getByZIPcode(address.getPostalCode());
		if(zipCode != null) {
			LogUtil.appendLog("AlergyLocationService.getDistrictFromLastAddress(): got from ZIP code - " + zipCode.getDistrict().getDistrictName());
			return zipCode.getDistrict();
		}

		//try to fetch District from subAdminArea
		String subAdminArea = address.getSubAdminArea();
		
		if(subAdminArea != null) {
			
			if(subAdminArea.contains("-")) {
				subAdminArea = subAdminArea.substring(0, subAdminArea.indexOf("-")).trim();
			}
			
			if(subAdminArea != "") {
				District district = District.getDistrictByDistrictName(subAdminArea); 
				LogUtil.appendLog("AlergyLocationService.getDistrictFromLastAddress(): got from subAdminArea '" + subAdminArea + "' - " + district.getDistrictName());
				return district;
			}
			
		}
		
		LogUtil.appendLog("AlergyLocationService.getDistrictFromLastAddress(): got NOT empty addressList BUT couldn't fetch District from " + subAdminArea + " - return NULL");
		return null;
	}
	
}
