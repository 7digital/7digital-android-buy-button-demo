package uk.co.sevendigital.android.partner.instantpurchase;

import android.net.Uri;

public interface SDIPurchaseListener {
	/**
	 * Called when the user has chosen to download a track.
	 * 
	 * @param uri The Uri (Url) of the track to download.
	 * @return true if the download has been handled by your application, or false for the download to be handled by
	 *         the Seven Digital application (if installed), otherwise handled by android.
	 */
	boolean onDownloadTrack(Uri uri);

	/**
	 * Called when the user has chosen to download a release (album).
	 * 
	 * @param uri The Uri (Url) of the release to download.
	 * @return true if the download has been handled by your application, or false for the download to be handled by
	 *         the Seven Digital application (if installed), otherwise handled by android.
	 */
	boolean onDownloadRelease(Uri uri);

	void onProgressChanged(int newProgress);
	void onPageStartedLoading();
	void onPageFinishedLoading();
	
	/** Called to request the purchase flow be terminated, i.e the activity finished.*/
	void onFinishAndClose();

}
