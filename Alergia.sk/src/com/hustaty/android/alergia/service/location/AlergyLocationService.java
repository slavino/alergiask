package com.hustaty.android.alergia.service.location;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import static com.hustaty.android.alergia.AlergiaskActivity.LOG_TAG;

public class AlergyLocationService {

	public AlergyLocationService(final Activity activity) {
		super();

		// Acquire a reference to the system Location Manager
		LocationManager locationManager = (LocationManager) activity
				.getSystemService(Context.LOCATION_SERVICE);

		// Define a listener that responds to location updates
		LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				// Called when a new location is found by the network location
				// provider.
				Geocoder geocoder = new Geocoder(
						activity.getApplicationContext());
				
				try {
					List<Address> addressList = geocoder.getFromLocation(
							location.getLatitude(), location.getLongitude(), 1);
					logAddressList(addressList);
				} catch (IOException e) {
					Log.e("Alergia.sk", e.getMessage());
				}
				// makeUseOfNewLocation(location);

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
		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		locationManager.requestLocationUpdates(
				LocationManager.GPS_PROVIDER, 0, 0, locationListener);
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

		}
	}

}
