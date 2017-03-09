# Inappbillingandroid

1. Don't use the default company domain name when creating the project especially the word example. In my case, it's com.image.ram.inappbillingandroid 
2. Download Google Play Billing Library from Android SDK Manager. In IntelliJ, it can be opened from Tools -> Android
3. Once downloaded, you can find the path /android-sdk-macosx/extras/google. Create a new folder named aidl under src/main. Create a package com.android.vending.billing under the aidl folder. Copy the IInAppBillingService.aidl from the above mentioned path and paste it in the com.android.vending.billing package.
4. Build your program
5. create a package util under com.image.ram.inappbillingandroid. You can find util files under /android-sdk-macosx/extras/google/play_billing/samples/TrivialDrive/src/com/example/android/trivialdrivesample/util. Copy files from that location and paste in the newly created package util.
