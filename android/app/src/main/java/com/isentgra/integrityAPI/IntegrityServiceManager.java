package com.isentgra.integrityAPI;

import android.content.Intent;
import android.util.Log;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;
import android.provider.Settings;
import android.content.Context;
import android.text.style.WrapTogetherSpan;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.bridge.Callback;
import com.google.gson.JsonElement;
import com.isentgra.BuildConfig;

// Google API
import com.google.android.play.core.integrity.IntegrityTokenRequest;
import com.google.android.play.core.integrity.IntegrityManager;
import com.google.android.play.core.integrity.IntegrityManagerFactory;
import com.google.android.play.core.integrity.IntegrityTokenResponse;

import com.google.android.gms.tasks.Task;
import  com.google.android.gms.tasks.OnSuccessListener;
import  com.google.android.gms.tasks.OnFailureListener;



public class IntegrityServiceManager extends ReactContextBaseJavaModule {
    Context applicationContext;

    IntegrityServiceManager(ReactApplicationContext reactContext) {
        super(reactContext);
        this.applicationContext = reactContext.getApplicationContext(); 
    }

    @Override
    public String getName() {
        return "IntegrityServiceManager";
    }

    /**
     * This method is invoked from Java Script accepts Promise and return a Promise resolved
     * with the integrity token
     * @param nonce - nonce to be used in the integrity token
     * @param promise - resolved with the integrity token
     */
    @ReactMethod
    public void getIntegrityToken(String nonce, Promise promise) {
        try {
            Long projectNumber = Long.valueOf(BuildConfig.GOOGLE_PROJECT_NUMBER);
            // Create an instance of a manager.
            IntegrityManager integrityManager = IntegrityManagerFactory.create(applicationContext);

            // Request the integrity token by providing a nonce.
            Task<IntegrityTokenResponse> integrityTokenResponse = integrityManager
                .requestIntegrityToken(IntegrityTokenRequest.builder()
                .setCloudProjectNumber(projectNumber)
                .setNonce(nonce).build());
            
            integrityTokenResponse.addOnSuccessListener(
                new OnSuccessListener<IntegrityTokenResponse>() {
                    @Override
                    public void onSuccess(IntegrityTokenResponse integrityTokenResponse) {
                        // The integrity token is available.
                        String integrityToken = integrityTokenResponse.token();
                        promise.resolve(integrityToken);
                    }
                }
            );

            integrityTokenResponse.addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        // An error occurred.
                        promise.reject("Error", e.getMessage());
                    }
                }
            );

            } catch (Exception e) {
                promise.reject("Error",e.getMessage());
            }
        }
}


