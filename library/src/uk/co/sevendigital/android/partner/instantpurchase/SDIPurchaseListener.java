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

	/**
	 * Called when the webview loading progress of the SDIPurchaseFragment changes
	 * @param newProgress
	 */
	void onProgressChanged(int newProgress);
	/**
	 * Called when the SDIPurchaseFragment webview starts loading a page.
	 * Can be used to display a progress spinner etc
	 */
	void onPageStartedLoading();
	/**
	 * Called when the SDIPurchaseFragment webview finishes loading a page
	 * Can be used to remove a progress spinner.
	 */
	void onPageFinishedLoading();
	
	/** Called to request the purchase flow be terminated, i.e the activity finished.*/
	void onFinishAndClose();

}
