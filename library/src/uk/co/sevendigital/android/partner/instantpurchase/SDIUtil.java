package uk.co.sevendigital.android.partner.instantpurchase;

import java.util.List;

import uk.co.sevendigital.android.partner.instantpurchase.core.SDIConstants;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

public class SDIUtil {

	public static String buildUrl(Bundle purchaseArguments) {
		Long releaseId = purchaseArguments.getLong(SDIConstants.EXTRA_RELEASE_ID, -1);
		Long trackId = purchaseArguments.getLong(SDIConstants.EXTRA_TRACK_ID, -1);
		Long partnerId = purchaseArguments.getLong(SDIConstants.EXTRA_PARTNER_ID, -1);
		String countryCode = purchaseArguments.getString(SDIConstants.EXTRA_COUNTRY_CODE);
		return buildUrl(releaseId, trackId, partnerId, countryCode);
	}

	public static String buildUrl(long releaseId, long trackId, long partnerId, String countryCode) {
		Uri.Builder builder;
		if (trackId != -1) builder = Uri.parse(String.format(SDIConstants.INSTANT_PURCHASE_TRACK_URL, trackId)).buildUpon();
		else builder = Uri.parse(String.format(SDIConstants.INSTANT_PURCHASE_RELEASE_URL, releaseId)).buildUpon();

		// if both track id and release id are supplied, append release id as query param
		if (trackId != -1 && releaseId != -1) builder.appendQueryParameter(SDIConstants.EXTRA_RELEASE_ID, Long.toString(releaseId));
		if (partnerId != -1) builder.appendQueryParameter(SDIConstants.EXTRA_PARTNER_ID, Long.toString(partnerId));
		if (countryCode != null) builder.appendQueryParameter(SDIConstants.EXTRA_COUNTRY_CODE, countryCode);
		return builder.build().toString();
	}

	public static boolean isSimilarUri(Uri one, Uri two) {
		return one.getHost().equals(two.getHost()) && one.getPath().equals(two.getPath());
	}

	public static boolean onDownloadRelease(Context context, Uri uri) {
		Long releaseId = Long.parseLong(uri.getQueryParameter("releaseid"));
		boolean downloadHandled = downloadUri(context, uri,releaseId);
		return downloadHandled;
	}

	protected static boolean onDownloadTrack(Context context, Uri uri) {
		// http://media.geo.7digital.com/media/user/downloadtrack?country=GB&formatid=17&oauth_consumer_key=7dpygdhr4x5k&oauth_nonce=1411278395&oauth_signature_method=HMAC-SHA1&oauth_timestamp=1370399177&oauth_token=ZD31y98a_spR_3Vvwhomsw&oauth_version=1.0&releaseid=769006&trackid=8515447&oauth_signature=J%2BAedHYR5fwGpBl5G3prgKWajck%3D
		Long releaseId = Long.parseLong(uri.getQueryParameter("releaseid"));
		Long trackId = Long.parseLong(uri.getQueryParameter("trackid"));

		boolean downloadHandled = downloadUri(context, uri,releaseId,trackId);
		return downloadHandled;
	}

	private static boolean downloadUri(Context context, Uri uri,Long releaseId) {
		return downloadUri(context, uri, releaseId, null);
	}
	@SuppressLint("NewApi") private static boolean downloadUri(Context context, Uri uri,Long releaseId, Long trackId) {
		/*
		 * default behaviour is to start downloads with the 7Digital app
		 */
		
		Intent intent = new Intent(SDIConstants.INTENT_VIEW_AND_SYNC_DOWNLOADS);
		
		// Verify it resolves
		PackageManager packageManager = context.getPackageManager();
		List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
		boolean isIntentSafe = activities.size() > 0;

		// Start an activity if it's safe
		if (isIntentSafe) {
			if (null!=releaseId)intent.putExtra(SDIConstants.INTENT_EXTRA_RELEASEID, releaseId);
			if (null!=trackId)intent.putExtra(SDIConstants.INTENT_EXTRA_TRACKID, trackId);
			context.startActivity(intent);
		} else {
			/*
			 * If 7Digital is not installed, start download with the download manager.
			 */

			if (isGingerbread()) {
				DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
				Request request = new Request(uri);
				long enqueue = dm.enqueue(request);
			} else {
				/*
				 * If the download manager is not available (below SDK 9), then launch the store page for 7digital 
				 */
				Intent storeIntent = getPlayStoreListingIntent();
				context.startActivity(storeIntent);
				return false;
			}

		}
		return true;
	}

	/** Return true if the device is an Android 2.3.x 'gingerbread' device (or later). */
	public static boolean isGingerbread() {
		return Build.VERSION.SDK_INT >= 9;
	}

	public static Intent getPlayStoreListingIntent() {
		Uri uri = Uri.parse("http://play.google.com/store/apps/details?id=" + "uk.co.sevendigital.android");
		Intent goToPlayStore = new Intent(Intent.ACTION_VIEW, uri);
		return goToPlayStore;
	}

}
