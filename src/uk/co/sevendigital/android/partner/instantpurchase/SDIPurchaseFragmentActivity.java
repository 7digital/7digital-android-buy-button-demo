package uk.co.sevendigital.android.partner.instantpurchase;

import uk.co.sevendigital.android.partner.instantpurchase.R;
import uk.co.sevendigital.android.partner.instantpurchase.core.SDIConstants;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.KeyEvent;

/**
 * A purchase activity contains a SDIPurchaseFragment.
 * 
 * Can be used as is, or extended to add custom functionality.
 * 
 * Override {@link #onDownloadRelease(Uri)} and {@link #onDownloadTrack(Uri)} to handle the downloading of tracks/releases
 * By default downloads will be handled by the Seven Digital application (if installed), or the Android system.
 * 
 * 
 */
public class SDIPurchaseFragmentActivity extends FragmentActivity implements SDIPurchaseListener {

	private SDIPurchaseFragment mPurchaseFragment;

	/**
	 * Creates an intent and starts the Purchase Activity
	 * 
	 * @param context
	 * @param releaseId
	 * @param trackId
	 * @param partnerId
	 * @param countryCode
	 * @return
	 */
	public static Intent startActivity(Context context, Long releaseId, Long trackId, Long partnerId, String countryCode) {
		Intent activityIntent = createIntent(context, releaseId, trackId, partnerId, countryCode);
		context.startActivity(activityIntent);
		return activityIntent;
	}

	/**
	 * Creates and returns an intent to start the purchase activity
	 * 
	 * @param context
	 * @param releaseId
	 * @param trackId
	 * @param partnerId
	 * @param countryCode
	 * @return
	 */
	public static Intent createIntent(Context context, Long releaseId, Long trackId, Long partnerId, String countryCode) {
		if (releaseId == null && trackId == null) throw new IllegalArgumentException();
		Intent intent = new Intent(context, SDIPurchaseFragmentActivity.class);
		if (releaseId != null) intent.putExtra(SDIConstants.EXTRA_RELEASE_ID, releaseId);
		if (trackId != null) intent.putExtra(SDIConstants.EXTRA_TRACK_ID, trackId);
		if (partnerId != null) intent.putExtra(SDIConstants.EXTRA_PARTNER_ID, partnerId);
		if (!TextUtils.isEmpty(countryCode)) intent.putExtra(SDIConstants.EXTRA_COUNTRY_CODE, countryCode);
		return intent;
	}

	/** Called when the activity is first created. */
	@SuppressLint("SetJavaScriptEnabled") @Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sdi_purchase_activity);

		mPurchaseFragment = (SDIPurchaseFragment) getSupportFragmentManager().findFragmentById(R.id.sdi_purchase_fragment);

	}

	protected void onStart() {
		super.onStart();
		SDIPurchaseFragment purchaseFragment = (SDIPurchaseFragment) getSupportFragmentManager().findFragmentById(
				R.id.sdi_purchase_fragment);
		purchaseFragment.startPurchase(getIntent().getExtras());

	};

	@Override public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mPurchaseFragment.canGoBack()) {
			mPurchaseFragment.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override public boolean onDownloadRelease(Uri uri) {
		return false;
	}

	@Override public boolean onDownloadTrack(Uri uri) {
		return false;
	}

	@Override public void onProgressChanged(int newProgress) {}

	@Override public void onPageStartedLoading() {}

	@Override public void onPageFinishedLoading() {}

}
