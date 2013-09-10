##7Digital Instant Purchase Library

The 7Digital Instant Purchase Library is a library designed to be integrated by 3rd party apps.

It enables tracks/releases to be purchased through the 7Digital store, and downloaded, or the download handled by the 3rd party app.

It's desdigned in a way that can be extended by the 3rd party app, or there is a purchase activity that can be used as is.



###Usage
 
To make a purchase, the SDIPurchaseFragmentActivity needs to be started, with the parameters of the purchase:

 - releaseId
 - trackId
 - partnerId
 - countryCode
 
 The purchase activity will handle everything else, requesting the user to login, and purchase the song/release.