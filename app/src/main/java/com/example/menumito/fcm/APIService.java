package com.example.menumito.fcm;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAu1uOH1c:APA91bF_Rx8bQpCUbGB0TMHgV_HN0jpX9hgWTPB-aKEUF5OvsL6wrY6H_OJKFOTsizEU1rEictCnhlkqF7pAJY-TH11A13h6Bd4pnMZ7H0mGv2aO3y1ETzvKoQ7prZv_oGpVfaw8IoD-" // Your server key refer to video for finding your server key
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotifcation(@Body NotificationSender body);
}
