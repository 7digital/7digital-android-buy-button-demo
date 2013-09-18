package uk.co.sevendigital.android.partner.instantpurchase;

import uk.co.sevendigital.android.partner.instantpurchase.core.SDIConstants;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class SDIPurchaseFragment extends Fragment {

	private WebView mWebView;
	private boolean mViewsInitialised;
	private SDIPurchaseListener mDownloadListener;

	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
	 * on create view
	 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
	@SuppressLint("SetJavaScriptEnabled") @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.sdi_purchase_fragment, container, false);
		mWebView = (WebView) view.findViewById(R.id.sdi__purchase_webview);

		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

		mWebView.setWebViewClient(new WebViewClient() {

			@Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
				Uri uri = Uri.parse(url);
				boolean isDownloadReleaseUri = SDIUtil.isSimilarUri(SDIConstants.DOWNLOAD_RELEASE_URI, uri);
				boolean isDownloadTrackUri = SDIUtil.isSimilarUri(SDIConstants.DOWNLOAD_TRACK_URI, uri);
				if (isDownloadReleaseUri) {
					if (!mDownloadListener.onDownloadRelease(uri)) ;
					SDIUtil.onDownloadRelease(getActivity(), uri);
				} else if (isDownloadTrackUri) {
					if (!mDownloadListener.onDownloadRelease(uri)) SDIUtil.onDownloadTrack(getActivity(), uri);
				}
				return false;
			}

			@Override public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				mDownloadListener.onPageStartedLoading();
			}

			@Override public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				mDownloadListener.onPageFinishedLoading();
			}

		});

		mWebView.setWebChromeClient(new WebChromeClient() {
			@Override public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				mDownloadListener.onProgressChanged(newProgress);
			}

			@Override public void onCloseWindow(WebView window) {
				super.onCloseWindow(window);
				mDownloadListener.onFinishAndClose();
			}
		});

		return view;
	}

	@Override public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		super.setRetainInstance(true);

		if (getActivity() instanceof SDIPurchaseListener) mDownloadListener = (SDIPurchaseListener) getActivity();

		mViewsInitialised = true;
	}

	@Override public void onStart() {
		super.onStart();
		updateView();

		Bundle arguments = getArguments();
		/*
		 *  - Check whether there are arguments passed to this fragment
		 *  - If there are, check whether they contain a release id, or track id
		 *    because these are required for a purchase
		 */
		if (null != arguments
				&& (arguments.containsKey(SDIConstants.EXTRA_RELEASE_ID) || arguments.containsKey(SDIConstants.EXTRA_TRACK_ID))) {

			//build the purchase url from the arguments
			String url = SDIUtil.buildUrl(arguments);

			startPurchaseInternal(url);
		}
	}

	/**
	 * @return true if the web view can go back to a previous page
	 */
	public boolean canGoBack() {
		return mWebView.canGoBack();
	}

	/**
	 * @return true if the web view can go foward to a previous page
	 */
	public boolean canGoForward() {
		return mWebView.canGoForward();
	}

	/**
	 * Asks the webview to go back to the previous page
	 */
	public void goBack() {
		mWebView.goBack();
	}

	public void startPurchase(Bundle purchaseArguments) {
		String url = SDIUtil.buildUrl(purchaseArguments);
		startPurchaseInternal(url);
	}

	public void startPurchase(Long releaseId, Long trackId, Long partnerId, String countryCode) {
		String url = SDIUtil.buildUrl(releaseId, trackId, partnerId, countryCode);
		startPurchaseInternal(url);
	}

	public void setOnDownloadListener(SDIPurchaseListener listener) {
		this.mDownloadListener = listener;
	}

	private void startPurchaseInternal(String purchaseUrl) {
		if (!isAdded()) {
			Log.w(SDIConstants.TAG, "Purchase can't be started when fragment is not added!");
			return;
		}
		/**
		 * Wrap the url in some javascript that opens it in a new window, because the close button needs to call window.close(), to request
		 * the window be closed.
		 * For some reason this only sometimes works loading the url via loadUrl. The method below works consistently.
		 */
		mWebView.loadData("<html><head><script>window.open(\"" + purchaseUrl + "\")</script></head></html>", "text/html", "UTF-8");
	}

	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
	 * lifecycle
	 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

	@Override public void onDestroyView() {
		super.onDestroyView();
	}

	@Override public void onResume() {
		super.onResume();
	}

	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
	 * update view
	 * - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
	private void updateView() {
		if (!isAdded() || !mViewsInitialised) return;
	}

}
