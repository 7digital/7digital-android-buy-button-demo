package uk.co.sevendigital.android.partner.instantpurchase.sample;

import uk.co.sevendigital.android.partner.instantpurchase.SDIPurchaseFragmentActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * The purpose of this activity is to test launching and interfacing with the 7digital application.
 * @author juliusspencer
 */
public class SDIPurchaseExampleActivity extends Activity implements View.OnClickListener {

	// dialog
	private static final int ERROR_DIALOG_ID = 1234;
	private static final String EXTRA_ERROR_MESSAGE = "extra_error_message";
	
	// Change this to your partner code
	public static final String PARTNER_CODE = "777";

	private Button mBuyReleaseButton;
	private EditText mReleaseIdEdittext;
	private EditText mTrackIdEdittext;
	private Button mBuyTrackButton;
	private EditText mAffiliateIdEdittext;
	private EditText mCountryCodeEditText;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		// Bind views
		mReleaseIdEdittext = (EditText) findViewById(R.id.track_release_id_edittext);
		mTrackIdEdittext = (EditText) findViewById(R.id.track_id_edittext);
		mAffiliateIdEdittext = (EditText) findViewById(R.id.affiliate_id_edittext);
		mCountryCodeEditText = (EditText) findViewById(R.id.country_code_edittext);
		mBuyTrackButton = (Button) findViewById(R.id.buy_track_button);
		mBuyReleaseButton = (Button) findViewById(R.id.buy_release_button);

		// attach listeners
		mBuyReleaseButton.setOnClickListener(this);
		mBuyTrackButton.setOnClickListener(this);
	}

	@Override protected Dialog onCreateDialog(int id, Bundle args) {
		if (id != ERROR_DIALOG_ID) return super.onCreateDialog(id, args);
		String message = args.getString(EXTRA_ERROR_MESSAGE);
		return new AlertDialog.Builder(this)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setPositiveButton(android.R.string.ok, null)
			.setTitle(R.string.error)
			.setMessage(message)
			.create();
	}
	
	@Override protected void onPrepareDialog(int id, Dialog dialog, Bundle args) {
		super.onPrepareDialog(id, dialog, args);
		if (!(dialog instanceof AlertDialog)) return; 
		String message = args.getString(EXTRA_ERROR_MESSAGE);
		
		((AlertDialog) dialog).setMessage(message);
	}
	
	@Override public void onClick(View v) {
		try {
			Long partnerId = getLongFromEditText(mAffiliateIdEdittext);
			String countryCode = mCountryCodeEditText.getText().toString().trim();
			switch (v.getId()) {
				case R.id.buy_track_button: {
					Long releaseId = getLongFromEditText(mReleaseIdEdittext);
					Long trackId = getLongFromEditText(mTrackIdEdittext);
					SDIPurchaseFragmentActivity.startActivity(this, releaseId, trackId, partnerId, countryCode);
					break;
				} case R.id.buy_release_button: {
					Long releaseId = getLongFromEditText(mReleaseIdEdittext);
					SDIPurchaseFragmentActivity.startActivity(this, releaseId, null, partnerId, countryCode);
					break;
				}
			}
		} catch (IllegalArgumentException e) {
			showDialog(ERROR_DIALOG_ID, buildErrorMessageBundle(e.getMessage()));
		}
	}

	private Long getLongFromEditText(EditText edittext) {
		if (TextUtils.isEmpty(edittext.getText())) return null;
		try { return Long.parseLong(edittext.getText().toString()); }
		catch (NumberFormatException e) { 
			showDialog(ERROR_DIALOG_ID, buildErrorMessageBundle(e.getMessage()));
			return null;
		}
	}
	
	private static Bundle buildErrorMessageBundle(String message) {
		Bundle bundle = new Bundle();
		bundle.putString(EXTRA_ERROR_MESSAGE, message);
		return bundle;
	}

}