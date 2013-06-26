package uk.co.sevendigital.android.partner.instantpurchase.sample;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockDialogFragment;

public class SDICustomTrackDialog extends SherlockDialogFragment {
	public static SDICustomTrackDialog newInstance() {
		SDICustomTrackDialog f = new SDICustomTrackDialog();
		return f;
	}

	public SDICustomTrackDialog() {}

	public interface TrackDialogFragmentListener {
		public void onBuyAlbum(SDICustomTrackDialog customDialogFragment);

		public void onBuyTrack(SDICustomTrackDialog customDialogFragment);
	}

	static TrackDialogFragmentListener mListener;
	private EditText mReleaseEditText;
	private EditText mTrackEditText;
	private EditText mPartnerEditText;
	private EditText mCountryEditText;

	@Override public Dialog onCreateDialog(Bundle savedInstanceState) {
		mListener = (TrackDialogFragmentListener) getActivity();
		LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = inflater.inflate(R.layout.custom_track_dialog, null);

		mReleaseEditText = (EditText) view.findViewById(R.id.track_release_id_edittext);
		mTrackEditText = (EditText) view.findViewById(R.id.track_id_edittext);
		mPartnerEditText = (EditText) view.findViewById(R.id.affiliate_id_edittext);
		mCountryEditText = (EditText) view.findViewById(R.id.country_code_edittext);

		AlertDialog.Builder builder = new AlertDialog.Builder(getSherlockActivity());
		builder.setCancelable(false);
		builder.setView(view);
		builder.setInverseBackgroundForced(true);
		builder.setTitle("Enter purchase details");
		builder.setPositiveButton("Buy", null);

		final AlertDialog adialog = builder.create();
		adialog.setOnDismissListener(this);
		adialog.setOnCancelListener(this);

		return builder.create();
	}

	public void onTrackButtonClick() {
		final String release = mReleaseEditText.getText().toString();
		final String track = mTrackEditText.getText().toString();

		if (TextUtils.isEmpty(release) && TextUtils.isEmpty(track)) {
			mReleaseEditText.setError("Enter something");
			return;
		} else {
			mListener.onBuyTrack(SDICustomTrackDialog.this);
		}
	}

	public String getRelease() {
		return mReleaseEditText.getText().toString();
	}

	public String getTrack() {
		return mTrackEditText.getText().toString();
	}

	public String getPartner() {
		return mPartnerEditText.getText().toString();
	}

	public String getCountryCode() {
		return mCountryEditText.getText().toString();
	}

	@Override public void onResume() {
		super.onResume();
		AlertDialog alertDialog = (AlertDialog) getDialog();
		Button okButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
		okButton.setOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				onTrackButtonClick();

			}
		});
	}
}
