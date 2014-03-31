##7digital Buy Button Android Example

This is an example of how the 7digital Buy button can be added to an Android app.
The 7digital Buy button enables users to purchase a track/release without leaving your app.

###Usage
 
To make a purchase, the SDIPurchaseFragmentActivity needs to be started, with the parameters of the purchase:

 - releaseId
 - trackId
 - partnerId 
 - countryCode

The purchase activity will handle everything else, requesting the user to login, and purchase the song/release.

#####Starting a SDIPurchaseFragmentActivity

The purchase activity can be started by calling the static method startActivity.

e.g.

	SDIPurchaseFragmentActivity.startActivity(context, releaseId, trackId, partnerId, "US");
	
You can also start the activity using the startActivity method of your activity/context.
For example you might want to start the activity for a result. 

The SDIPurchaseFragmentActivity includes a method for creating the intent for you.
	
	Intent intent = SDIPurchaseFragmentActivity.createIntent(context, releaseId,  trackId,  partnerId,  countryCode)
	this.startActivityForResult(intent, requestCode);
	
This activity will take the user through the purchase process, and simply finish with an ACTIVITY_OK result when complete. The activity will inherit any styling from the application.
	
#####Using the SDIPurchaseFragment

The SDIPurchaseFragmentActivity is basically just a wrapper around a SDIPurchaseFragment.

This fragment can be used directly for more control, or if it is required to be part of a dual pane layout etc.

Simply include the fragment in your own activity, and call

	startPurchase(Long releaseId, Long trackId, Long partnerId, String countryCode)
	
to start the purchase process. 

Your activity must implement the SDIPurchaseListener interface.


