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
					addressList = geocoder.getFromLocation(
							location.getLatitude(), location.getLongitude(), 1);
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
		for (Address address : addressList) {
			Log.d(LOG_TAG, "### Iterating over address ###");
			String countryName = address.getCountryName();
			String countryCode = address.getCountryCode();
			Log.d(LOG_TAG, "countryName: " + countryName);
			Log.d(LOG_TAG, "countryCode: " + countryCode);
			Log.d(LOG_TAG, "AdminArea: " + address.getAdminArea());
			Log.d(LOG_TAG, "FeatureName: " + address.getFeatureName());
			Log.d(LOG_TAG, "PostalCode: " + address.getPostalCode());
			Log.d(LOG_TAG, "SubAdminArea: " + address.getSubAdminArea());
			Log.d(LOG_TAG, "Thoroughfare: " + address.getThoroughfare());
			Log.d(LOG_TAG, "Extras: " + address.getExtras());
			for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
				Log.d(LOG_TAG,
						"AddressLine[" + i + "]: " + address.getAddressLine(i));
			}
			
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
			return null;
		}
		
		Address address = this.addressList.get(0);

		//try to fetch District from ZIPcode
		ZIPCode zipCode = ZIPCode.getByZIPcode(address.getPostalCode());
		if(zipCode != null) {
			return District.getDistrictByDistrictName(zipCode.getDistrict());
		}

		//try to fetch District from subAdminArea
		String subAdminArea = address.getSubAdminArea();
		if(subAdminArea != null) {
			if(subAdminArea.contains("-")) {
				subAdminArea = subAdminArea.substring(0, subAdminArea.indexOf("-")).trim();
			}
			
			if(subAdminArea != "") {
				return District.getDistrictByDistrictName(subAdminArea);
			}
		}
		return null;
	}


	
}
