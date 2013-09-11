##7digital Buy button Android example

This is an example of how the 7digital Buy button can be added to an Android app.
The 7digital Buy button enables users to purchase a track/release without leaving your app.

###Usage
 
To make a purchase, the SDIPurchaseFragmentActivity needs to be started, with the parameters of the purchase:

 - releaseId
 - trackId
 - partnerId
 - countryCode
 
 The purchase activity will handle everything else, requesting the user to login, and purchase the song/release.
