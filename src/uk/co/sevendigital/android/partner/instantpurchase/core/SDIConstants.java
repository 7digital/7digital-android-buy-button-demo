package uk.co.sevendigital.android.partner.instantpurchase.core;

import android.net.Uri;

public class SDIConstants {

	public static final Uri DOWNLOAD_RELEASE_URI = Uri.parse("http://media.geo.7digital.com/media/user/download/release");
	public static final Uri DOWNLOAD_TRACK_URI = Uri.parse("http://media.geo.7digital.com/media/user/downloadtrack");

	public static final String INSTANT_BASE_URL = "https://instant.7digital.com/m/";
	public static final String INSTANT_PURCHASE_URL = INSTANT_BASE_URL + "purchase/";
	public static final String INSTANT_PURCHASE_TRACK_URL = INSTANT_PURCHASE_URL + "track/%d";
	public static final String INSTANT_PURCHASE_RELEASE_URL = INSTANT_PURCHASE_URL + "release/%d";
	
	public static final String TAG = "SDI";
	
	public static final String EXTRA_RELEASE_ID = "releaseId";
	public static final String EXTRA_TRACK_ID = "trackId";
	public static final String EXTRA_PARTNER_ID = "partnerId";
	public static final String EXTRA_COUNTRY_CODE = "countrycode";
	

}
