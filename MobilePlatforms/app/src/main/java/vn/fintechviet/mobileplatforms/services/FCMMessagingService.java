/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package vn.fintechviet.mobileplatforms.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import vn.fintechviet.mobileplatforms.R;
import vn.fintechviet.mobileplatforms.ui.main.MainActivity;
import vn.fintechviet.mobileplatforms.utils.AndroidAgentException;
import vn.fintechviet.mobileplatforms.utils.Constants;
import vn.fintechviet.mobileplatforms.utils.Preference;

/**
 * IntentService responsible for handling FCM messages.
 */
public class FCMMessagingService extends FirebaseMessagingService {

    private static final String TAG = FCMMessagingService.class.getName();
    private static volatile boolean hasPending = false;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.i(TAG, "New FCM notification. Message id: " + remoteMessage.getMessageId());
        syncMessages();
        //message will contain the Push Message
        String id = remoteMessage.getData().get("id");
        String type = remoteMessage.getData().get("type");
        String title = remoteMessage.getData().get("title");
        String message = remoteMessage.getData().get("message");
        //To get a Bitmap image from the URL received
        Bitmap bitmap = getBitmapFromUrl("icon");
        sendNotification(title, message, bitmap, new FirebaseMessagingResponse());
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     */

    private void sendNotification(String title, String messageBody, Bitmap image, FirebaseMessagingResponse firebaseMessaging) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Bundle bundle = new Bundle();
        bundle.putInt("KEY_NAME_RESULT", 0);
        bundle.putParcelable(FirebaseMessagingResponse.class.getName(), firebaseMessaging);
        intent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(messageBody);
        bigText.setBigContentTitle(title);
        //bigText.setSummaryText(messageBody);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setLargeIcon(image)/*Notification icon image*/
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        notificationBuilder.setStyle(bigText);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setLights(Color.BLUE, 500, 500);
        long[] pattern = {500, 500, 500, 500, 500, 500, 500, 500, 500};
        notificationBuilder.setVibrate(pattern);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationBuilder.setSound(alarmSound);
        //notificationBuilder.setStyle(new NotificationCompat.InboxStyle());
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(new Random().nextInt(), notificationBuilder.build());
    }

    /*
     *To get a Bitmap image from the URL received
     * */
    public Bitmap getBitmapFromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;
        } catch (Exception e) {
            return BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        }
    }

    /**
     *
     */
    private void syncMessages() {
        try {
            if (Preference.getBoolean(this, Constants.PreferenceFlag.REGISTERED)) {
                long currentTimeStamp = Calendar.getInstance().getTimeInMillis();
                if (currentTimeStamp - MessageProcessor.getInvokedTimeStamp() > Constants.DEFAULT_START_INTERVAL) {
                    MessageProcessor messageProcessor = new MessageProcessor(this);
                    messageProcessor.getMessages();
                } else if (!hasPending) {
                    hasPending = true;
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (Constants.DEBUG_MODE_ENABLED) {
                                Log.d(TAG, "Triggering delayed operation polling.");
                            }
                            hasPending = false;
                            syncMessages();
                        }
                    }, Constants.DEFAULT_START_INTERVAL);
                    if (Constants.DEBUG_MODE_ENABLED) {
                        Log.d(TAG, "Scheduled for delayed operation syncing.");
                    }
                } else {
                    Log.i(TAG, "Ignoring message since there are ongoing and pending polling.");
                }
            }
        } catch (AndroidAgentException e) {
            Log.e(TAG, "Failed to perform operation", e);
        }
    }

}
