package com.nimius.airportdirect.service.repository;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.nimius.airportdirect.service.retrofit.SmartBusRestClient;
import com.nimius.airportdirect.utility.Constants;
import com.nimius.airportdirect.utility.Utils;
import com.nimius.airportdirect.service.model.bookingModel.BookingModel;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingIntentService extends IntentService {
    private static final String TAG = "BookingIntentService";

    public static String EXTRA_CODE = "responseCode";
    public static final String EXTRA_BOOKING_API = "bookingApi";

    public static int CODE_SUCCESS = 1;
    public static int CODE_ERROR = 2;
    public static int CODE_FAILURE = 3;
    public static int CODE_INTERNET = 4;


    public BookingIntentService() {
        super("Cashback IntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String authenticateApi = intent.getStringExtra(EXTRA_BOOKING_API);
        authenticateRequest(authenticateApi, this);

    }


    private void sendBookingDetailToClient(BookingModel model, int code) {
        Intent intent = new Intent();
        intent.setAction(Constants.BOOKING_DETAILS_BROADCAST_FILTER);
        intent.putExtra(EXTRA_CODE, code);
        intent.putExtra(EXTRA_BOOKING_API, model);
        sendBroadcast(intent);
    }



    public void authenticateRequest(String url,Context mContext) {
        if (Utils.isNetworkAvailable(mContext)) {
            Call<BookingModel> call = SmartBusRestClient.getInstance().getApiInterface()
                    .authenticateApi(url);
            call.enqueue(new Callback<BookingModel>() {
                @Override
                public void onResponse(@NonNull Call<BookingModel> call, @NonNull Response<BookingModel> response) {
                    Log.e(TAG, "onResponse: ");

                    if (response.isSuccessful()) {
                        BookingModel result = response.body();
                        if (result != null)
                            sendBookingDetailToClient(result, CODE_SUCCESS);
                    } else {
                        try {
                            if (!TextUtils.isEmpty(response.errorBody().string())) {
                                Log.e(TAG, "onResponse: error=" + response.message());
//                                showSnackBar(getActivity(), response.message());
                                BookingModel bookingModel = new BookingModel();
                                bookingModel.errorMessage = response.message();
                                sendBookingDetailToClient(bookingModel, CODE_ERROR);

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                }

                @Override
                public void onFailure(@NonNull Call<BookingModel> call, @NonNull Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getMessage());
                    sendBookingDetailToClient(null, CODE_FAILURE);

                }
            });
        } else {
            Log.e(TAG, "no_internet");
            sendBookingDetailToClient(null, CODE_INTERNET);

        }
    }




}