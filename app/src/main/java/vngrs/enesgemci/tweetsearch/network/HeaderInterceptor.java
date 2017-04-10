package vngrs.enesgemci.tweetsearch.network;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;

public class HeaderInterceptor implements Interceptor {

    private static final String TAG = HeaderInterceptor.class.getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        try {
            Buffer buffer = new Buffer();
            String body;

            if (request.body() != null) {
                request.body().writeTo(buffer);
                body = buffer.readUtf8();
            } else {
                body = "encoded path -> " + request.url().encodedPath();
            }

            Log.wtf(TAG, "---> [REQUEST] : " + body);
        } catch (Exception e) {
        }

        Request newReq = request.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                .method(request.method(), request.body())
                .build();

        Log.e(TAG, "[METHOD] : " + newReq.method());
        Log.e(TAG, String.format("[REQUEST HEADER] Sending request %s on %s%n%s",
                newReq.url(), chain.connection(), newReq.headers()));

        long t1 = System.nanoTime();
        Response response = chain.proceed(newReq);
        long t2 = System.nanoTime();
        double elapsedTime = (t2 - t1) / 1e6d;

        Log.e(TAG, String.format("[RESPONSE] Received response for %s in %.1fms%n%s",
                response.request().url(), elapsedTime, response.headers()));

        return response;
    }
}