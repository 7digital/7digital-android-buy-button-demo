package uk.co.sevendigital.android.partner.instantpurchase.sample;

import uk.co.sevendigital.android.partner.instantpurchase.SDIPurchaseFragmentActivity;
import uk.co.sevendigital.android.partner.instantpurchase.sample.SDICustomTrackDialog.TrackDialogFragmentListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;

/**
 * The purpose of this activity is to test launching and interfacing with the 7digital application.
 * 
 */
public class SDIPurchaseExampleActivity extends FragmentActivity implements TrackDialogFragmentListener {

	private Button mBuyButton;
	private SeekBar mSeekBar;
	
	
	/** Called when the activity is first created. */
	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.example_activity);

		mBuyButton = (Button) findViewById(R.id.buy_button);
		mSeekBar = (SeekBar) findViewById(R.id.seekBar);

		mBuyButton.setOnClickListener(new OnClickListener() {

			@Override public void onClick(View v) {
				Long releaseId = (long) 2235853;
				Long trackId = (long) 24058764;
				SDIPurchaseFragmentActivity.startActivity(SDIPurchaseExampleActivity.this, releaseId, trackId, null, "US");
			}
		});

		mSeekBar.setProgress(70);
	}

	@Override public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.home_menu, menu);
		return true;
	}

	@Override public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.buy_album_item: {
			SDIPurchaseFragmentActivity.startActivity(this, (long) 1234, null, null, null);
			return true;
		}
		case R.id.buy_track_item: {
			SDIPurchaseFragmentActivity.startActivity(this, null, (long) 12345, null, null);
			return true;
		}
		case R.id.buy_specific_track_item: {
			SDIPurchaseFragmentActivity.startActivity(this, (long) 1302, (long) 12345, null, null);
			return true;
		}
		case R.id.buy_us_track_item: {
			SDIPurchaseFragmentActivity.startActivity(this, null, (long) 23243634, (long) 712, "US");
			return true;
		}
		case R.id.buy_track_partner_item: {
			SDIPurchaseFragmentActivity.startActivity(this, null, (long) 12345, (long) 712, null);
			return true;
		}
		case R.id.free_track_item: {
			SDIPurchaseFragmentActivity.startActivity(this, null, (long) 8515447, null, null);
			return true;
		}
		case R.id.custom_track_item: {
			SDICustomTrackDialog dialog = SDICustomTrackDialog.newInstance();
			dialog.show(getSupportFragmentManager(), "CustomTrackDialog");
			return true;
		}
		}
		return super.onOptionsItemSelected(item);
	}

	@Override public void onBuyAlbum(SDICustomTrackDialog customDialogFragment) {
		Long album = getLongFromString(customDialogFragment.getRelease());
		Long partnerId = getLongFromString(customDialogFragment.getPartner());
		String countryCode = customDialogFragment.getCountryCode();
		SDIPurchaseFragmentActivity.startActivity(this, album, null, partnerId, countryCode);
	}

	@Override public void onBuyTrack(SDICustomTrackDialog customDialogFragment) {
		Long album = getLongFromString(customDialogFragment.getRelease());
		Long track = getLongFromString(customDialogFragment.getTrack());
		Long partnerId = getLongFromString(customDialogFragment.getPartner());
		String countryCode = customDialogFragment.getCountryCode();
		SDIPurchaseFragmentActivity.startActivity(this, album, track, partnerId, countryCode);
	}

	private Long getLongFromString(String edittext) {
		if (TextUtils.isEmpty(edittext)) return null;
		try {
			return Long.parseLong(edittext);
		} catch (NumberFormatException e) {
			return null;
		}
	}

}
