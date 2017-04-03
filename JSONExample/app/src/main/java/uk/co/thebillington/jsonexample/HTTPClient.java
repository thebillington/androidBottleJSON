package uk.co.thebillington.jsonexample;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * Created by thebillington on 08/03/17.
 */
public class HTTPClient {

    private static final String BASE_URL = "http://10.0.2.2:8080";

    private static AsyncHttpClient client = new AsyncHttpClient(false, 80, 443);

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.addHeader("Accept", "application/json");
        client.addHeader("Content-Type", "application/json");
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(Context context, String url, JSONObject params, AsyncHttpResponseHandler responseHandler) {

        StringEntity entity = null;

        try {
            //Create an entity to send as parameters
            entity = new StringEntity(params.toString());
            //Set header to JSON
            client.addHeader("Accept", "application/json");
            client.addHeader("Content-Type", "application/json");
            //Post and receive response
            client.post(context, getAbsoluteUrl(url), entity, "application/json", responseHandler);
        } catch (UnsupportedEncodingException e) {
            Log.d("UEE", "Cannot process request: " + e);
        }
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

}
