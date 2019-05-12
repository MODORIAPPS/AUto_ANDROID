package com.modori.kwonkiseokee.AUto.data.api;

import com.modori.kwonkiseokee.AUto.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface FileDownloadClient {

    @Headers(
            {"Authorization: Client-ID 790a2bb347b11e1167cad7a85d7c01c2fc3ccd781b4265a5f7fabae767ed0b38"}
    )

    @GET("photos/{id}/download")
    Call<ResponseBody> downloadFile(@Path("id") String photoID);
}
