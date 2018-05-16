/*
 *  Copyright (C) 2018 - 2019 FINTECHVIET
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */
package com.facetec.zoom.sdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class FaceIDIdentification {

    //
    // Your ZoOm app token is used to enable additional features, usage tracking, and security purposes.
    // It will be set during initialization.
    //
    private String zoomAppToken;

    //
    // The ID of the user we will be enrolling and authenticating (passed in to enrollment and authentication APIs).
    //
    // It is recommended in a real application to use an identifier derived from the actual user ID of the user.
    // The userID is used internally to ZoOm to create and look up biometric data in the secure app sandbox.
    //
    // Multiple users can be created by simply passing different userID parameters to enrollment and authentication.
    //
    private static final String enrollmentUserId = "TnLdB72kKrXv$VyaXkw4k4Dn7KzEBcL@c=9?dESFwCKhDR+dn85pP5etW7c%NB%$HrTv$Z8tJdWYqX-ce&_*gsqw9z^=M$22EsbhHV9qpMc3&D4ZM#AD*tT@9H^DdTLbW23#vF@_Ktf3J%pG3*5%$*8Tb6HzAPRC@rgQV=?vR3&NpUt+uWJ6uLbd$ExT&*y=77zS?FCGnkHvGpG7a#Lku&rJhh*ZynQpLk^P97ZfFhcTj^^m7Zk^UgCF_DZgF8bDCEwYLK*GxL?J%u_KZbPvnC%a8RA7+CrN-f8hd3PnJ&3x@rHGhh7WznTNt5fCEFTNdXKYb%H#=jTed8^NTY7vKJFLEAbw=Lxy6tp%E*vf^n-P&PM#?%Yd^-yrDYaeqTu!Lrypp*pTLvmUTvC&rF?2-GWCz#2n65Y%V*FLyzUkK*v_L2=Yz*Pe#4FKGvvZ3z9MZkM#wWC#$YnGqpzEvgNwNPDf%kus@WGYh@ac72GeG4RHZ*rC!dSNStatcM$a_cZ2B+xJe6";

    //
    // The secret data we will store with the user.
    // This data is encrypted and secured by ZoOm.
    // The secret is only returned upon successful authentication by the user.
    //
    private static final String MY_SECRET_DATA_TO_STORE = "123456789";

    //
    // Used as a key part to encrypt the user enrollment and decrypt it during authentication.
    //
    // It is recommended that the application developer store this key in a manner fit for the overall security of the application.
    //
    // Level 1 - Change to a different hard-coded value.  This will encrypt the biometric and user data with a key that can only be found by reverse engineering.
    // Level 2 - Change to a different hard-coded value and obfuscate.  This will encrypt the biometric and user data with a key that can only be found by reverse engineering, and make reverse engineering harder.
    // Level 3 - Generate a key per-device, per-install and store that key using a custom scheme (i.e. storing in the iOS KeyChain would be a simple example).  This will encrypt the biometric and user data with a key that is
    //   unique to the device.  This prevents discovering a key as in #1 and #2 above and being able to utilize that knowledge for a larger-scale attack.  Storing in the iOS KeyChain also ensures that a deeper attack would be necessary (i.e. one that compromises OS-level security)
    // Level 4 - Generate a key per-device, per install and store key on your backend secure server.  Only pass this key down to the device after other basic or more involved security checks and device fingerprinting occurs.
    //   This ensures that a more simple data-at-rest attack cannot be performed, since nothing can be encrypted without data from the server.  This also allows for per-user keys to be temporarily disabled or destroyed completely in more sophisticated authentication schemes.
    //
    // Please see the Application Per-User Encryption Secret section of https://dev.zoomlogin.com/zoomsdk/#/android-guide for more details.
    //
    private String encryptionSecretForUserID = "BZKL@QP^FgXhW9=TnLdB72kKrXv$VyaXkw4k4Dn7KzEBcL@c=9?dESFwCKhDR+dn85pP5etW7c%NB%$HrTv$Z8tJdWYqX-ce&_*gsqw9z^=M$22EsbhHV9qpMc3&D4ZM#AD*tT@9H^DdTLbW23#vF@_Ktf3J%pG3*5%$*8Tb6HzAPRC@rgQV=?vR3&NpUt+uWJ6uLbd$ExT&*y=77zS?FCGnkHvGpG7a#Lku&rJhh*ZynQpLk^P97ZfFhcTj^^m7Zk^UgCF_DZgF8bDCEwYLK*GxL?J%u_KZbPvnC%a8RA7+CrN-f8hd3PnJ&3x@rHGhh7WznTNt5fCEFTNdXKYb%H#=jTed8^NTY7vKJFLEAbw=Lxy6tp%E*vf^n-P&PM#?%Yd^-yrDYaeqTu!Lrypp*pTLvmUTvC&rF?2-GWCz#2n65Y%V*FLyzUkK*v_L2=Yz*Pe#4FKGvvZ3z9MZkM#wWC#$YnGqpzEvgNwNPDf%kus@WGYh@ac72GeG4RHZ*rC!dSNStatcM$a_cZ2B+xJe6HPbBMy$93h&=RHndDxaudj%Nr9NSEsjmfZ*5B-UvMe#R%Ut&%CSA5hgnu@-y9***-?jmS%fRz8BMjG^yWx42B$DbTGsA%5qSa!pLGvk&KcYS7S@4z&JF%*rPzq6GNh5tv6JthuegV6nvD-Ap5H*#eYY3ar_=v=6&&=G*t7b*TPDRvU!bKJp9SSHpUUC$wBjRw@_nbD-E&u#c9H6*#x_Mx7jX9NW3VpJ2sj3nupDEmt5*Yd2!DBtBxsy7%*!5C@d_?=q2tKS_8SzPHh3f2aCemyPLYngP!nMr&Z4pS2%s$Rqz!sxL2S4JT_?G7uG7RM+ys+6hSkSqP-KZQ3gsGu=Y@-WB@@rpc$w49Nc=MnkFme=9SJwf7ke-Ey+a^YvXmW$$R^RH$-YBMB#_Sy@u&R%FTgP$3vS6q*!3PZNSzC$Au?wjTKqqJNaE78YAXr5A+&&fKwXng*S^rFg^tKsYaA^uqXCDA8JFQuNW%$ePAN=n$EGtn%xZfS#rh2^nCPbRF2H@$DSL!Tg&4E#ZSu+jX9WZ3u5L2fMszV@55?Y=k9N$LK$Ns76k$NPx!LxYyZPncxMU692EV$Cd@G-5t#@eprXqjnJ_26qA7jtau3VN@^Kk&wKhQ++4Jg$4U5xmM-2@!r_*94Kmx+x-vM_vEUJN*sDRHrr%Cw&y*yQzJtr-$?!59*3=7qD^*8#-2M2SZ?K2#uYsEP-=XmErX7^u*VzHaF+$hY*w_c$8hMeNWshyK_r#c=h%bDuMKQSr4kPxPay&T23+m9c3GZes43=c?E4!6Xqq2Xcu!BHWC92fwb2r&CBTK8WAa5VJZDAAM!x9rbvD+M8mjB3wdRcz%7$S6Q9ZrT@wSCjH+&PNkR?zr2ak4swES6#QPKDP-^sRRcMm=gQR#^dH_WA9E6T2bdNvV6v4y8^Km&PM&kG$*P5yY2gwdaKDby*c!ZG4ru9*M2C+Jjq+=DSjnbE9dZ+x_xaErU$_p&PCSPTs3^?#HCDBX?xCcB&dGQ+Zz2WwF&N?x%rtqBJn$!ZhC?Jr*86yCSLg2WgXzjHFykh*j%?c_N6X@ta6sqh4AJLH6f+2C$uUA*G+5E3pdg6uh?L@cPWG6F?yqDjCuB?Q!tQ%Ub8mEE2&#V_7Mkm%*44_=5GH6Fjams_&aqCT4*3U?sS!FEfwa_jx=a52R#434VmqxE+u$-ARaL5X$tb?mP@p?TF=zSF+R%688aHHAnY+xF8uAubceX3TG4^CNLhhWN%MxcVZG=eyYnt*Ej=nxa98@^^z9x+=YmQw8^6v2_DrprCrd=Rb_=fDT3e!#Q_hY6q!Y!XDMejY?6DsEy%b9zHRy_KWe*J7GbF^TWa6J7ydx*6!k@CM3kQPBPzRS7-UMP=c8ra=k?r=9^UMfRPX5%?cFD*WnZ8RbFa-h$7Qb@L?heg*scfyg$&pePDUeDw4aawFXBzfUh+n&PAgT94H@JGXHujdxuHEwV8j75F8k%j-pNw3CfDg#nA*UaJkV@R$m8?u55@vGMnjx?jmkQbRS55";

    //
    // Code for activity launched to select image for enrollment/auth verification
    //
    int VERIFICATION_PHOTO_SELECTION_REQUEST_CODE = 20000;

    //
    // Holds images used to verify against the ZoOm session that will be performed.
    // This can be set in the settings screen of the sample app.
    //
    Bitmap verificationImage;

    //
    // Holds images used for Audit Trail purposes
    //
    ArrayList<Bitmap> auditTrailResult;

    //
    // Used to store the result of the last enrollment or authentication attempt.
    //
    private String lastResult = "";

    //
    // Use the default customization for ZoOm.
    //
    private ZoomCustomization customization;
    private ZoomSDK.InitializeCallback mInitializeCallback;

    //
    // used to get set the selected enrollment strategy in the SampleAppActivity
    //
    private ZoomStrategy getEnrollmentStrategy() {

//        switch (zoomStrategies.getCheckedRadioButtonId()) {
//            case R.id.zoomStrategy:
//                return ZoomStrategy.ZOOM_ONLY;
//            case R.id.twoFactorStrategy:
//                return ZoomStrategy.TWO_FACTOR;
//            case R.id.threeFactorStrategy:
//                return ZoomStrategy.THREE_FACTOR;
//            default:
//                throw new RuntimeException("cannot find enroll strategy");
//        }

        return ZoomStrategy.ZOOM_ONLY;
    }

    /**
     *
     * @param context
     * @param mInitializeCallback
     */
    public void initializeZoom(Context context, ZoomSDK.InitializeCallback mInitializeCallback) {

        zoomAppToken = BuildConfig.ZOOM_APP_TOKEN;

        ZoomStrategy zoomStrategy = getEnrollmentStrategy();

        // Use default theme
        customization = new ZoomCustomization();
        customization.showZoomIntro = false;
        customization.showPreEnrollmentScreen = false;
        ZoomSDK.setCustomization(customization);

        //
        // Initialize the SDK
        //
        ZoomSDK.initialize(context, zoomAppToken, zoomStrategy, mInitializeCallback);

        //
        // signal to the ZoOm SDK that audit trail should be captured
        // note: this is enabled on a per-application basis
        // please contact support@zoomlogin.com to request access
        //
        ZoomSDK.setAuditTrailType(AuditTrailType.HEIGHT_640);
    }

    //
    // Enrolls a user, specifying user ID, application per-user encryption secret, and secret data to store with the enrollment.
    //
    public void onEnrollClick(Activity activity, String enrollmentUserId, String enrollmentSecretForUserId) {

        //
        // If we have selected an image to verify the enrollment against,
        // call setVerificationImages to signal to ZoOm that this matching should be performed.
        //
        ArrayList<Bitmap> verificationImages = new ArrayList<Bitmap>();

        if (verificationImage != null) {
            //
            // Create an ArrayList<Bitmap> and call the setVerificationImages API
            // to signal ZoOm to perform matching on the session
            //
            // NOTES:
            // - the bitmap image submitted to ZoOm must be correct orientation (i.e. the face must be upright in the frame)
            // - the width of the face in the image MUST be at least 50% of the overall image width
            // - currently, only the first image of the verificationImages array is used for matching
            //
            verificationImages.add(verificationImage);
        }

        ZoomSDK.setVerificationImages(verificationImages);

        //
        // Create the enrollment intent
        //
        Intent enrollmentIntent = new Intent(activity, ZoomEnrollmentActivity.class);

        //
        // Set the user ID so we can recall it later
        //
        enrollmentIntent.putExtra(ZoomSDK.EXTRA_ENROLLMENT_USER_ID, enrollmentUserId);

        //
        // Set the application/user encryption secret
        //
        enrollmentIntent.putExtra(ZoomSDK.EXTRA_USER_ENCRYPTION_SECRET, encryptionSecretForUserID);

        //
        // Set the secret data
        //
        enrollmentIntent.putExtra(ZoomSDK.EXTRA_ENROLLMENT_SECRET, enrollmentSecretForUserId);

        //
        // Flag to signal ZoOm to retrieve the raw biometric data and return this to the application
        // in onActivityResult on the result object.  The ZoOm Biometric can be used to perform server-side
        // authentication.  For more details and to request access to the server-side library,
        // please contact support@zoomlogin.com with a description of your application and use case.
        //
        enrollmentIntent.putExtra(ZoomSDK.EXTRA_RETRIEVE_ZOOM_BIOMETRIC, false);

        //
        // Launch enrollment
        //
        activity.startActivityForResult(enrollmentIntent, ZoomSDK.REQUEST_CODE_ENROLLMENT);
    }

    //
    // Authenticate current user.
    // Note that authentication is not possible without passing the encryptionSecretForUserID used when enrolling the user.
    //
    public void onAuthClick(Activity activity, String enrollmentUserId) {
        //
        // Make sure the user exists before launching
        //
        if(ZoomSDK.isUserEnrolled(activity, enrollmentUserId)) {

            //
            // If we have selected an image to verify the enrollment against,
            // call setVerificationImages to signal to ZoOm that this matching should be performed.
            //
            ArrayList<Bitmap> verificationImages = new ArrayList<Bitmap>();

            if(verificationImage != null) {
                verificationImages.add(verificationImage);
            }

            ZoomSDK.setVerificationImages(verificationImages);

            //
            // Create the authentication intent
            //
            Intent authenticationIntent = new Intent(activity, ZoomAuthenticationActivity.class);

            //
            // Set the user ID you want to authenticate
            //
            authenticationIntent.putExtra(ZoomSDK.EXTRA_AUTHENTICATION_USER_ID, enrollmentUserId);

            //
            // Set the required encryptionSecretForUserID used when enrolling the user.
            //
            authenticationIntent.putExtra(ZoomSDK.EXTRA_USER_ENCRYPTION_SECRET, encryptionSecretForUserID);

            //
            // Flag to signal ZoOm to retrieve the raw biometric data and return this to the application
            // in onActivityResult on the result object.  The ZoOm Biometric can be used to perform server-side
            // authentication.  For more details and to request access to the server-side library,
            // please contact support@zoomlogin.com with a description of your application and use case.
            //
            authenticationIntent.putExtra(ZoomSDK.EXTRA_RETRIEVE_ZOOM_BIOMETRIC, false);

            //
            // Launch authentication
            //
            activity.startActivityForResult(authenticationIntent, ZoomSDK.REQUEST_CODE_AUTHENTICATION);
        }
        else {
            Toast.makeText(activity, "User not enrolled", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * delete all user enrollments
     *
     * @param var0
     */
    public void deleteAllEnrollments(Context var0) {
        //
        // delete all user enrollments
        //
        ZoomSDK.deleteAllEnrollments(var0);
    }
}
