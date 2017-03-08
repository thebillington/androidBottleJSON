# androidBottleJSON
A simple Android app that uses the loopj ASYNC http library for Android to connect to a simple bottle server that serves RESTful JSON over localhost.

##Running the bottle app

*Tested with Python version 2.7 and bottle version 0.12.13**

To run the bottle app simply open Terminal and navigate to the directory where the bottle app is stored. Then run:

    python json_server.py

To test that your server is running simply open your browser and navigate to:

    127.0.0.1:8080/json

##Running the Android app

If you are connecting the Android app to the JSON server then you either need to run the app in an emulator, or alternatively connect the computer that is running the server to a WiFi hotspot on your Android device.

To import the app into Android Studio simply navigate to the folder where the app is held using the Android Studio *open...* panel and select the build.gradle file.

The connection may take a while so just give it opportunity to complete once the app is running.

##Setting up loopj async http library

If you are setting up loopj libraries from scratch then there are a few things you should note.

**Install**

To install loopj simply include the following line of code in your *module:app* build.gradle file inside the dependencies.

    compile 'com.loopj.android:android-async-http:1.4.9'

**Configuring internet access**

To allow http request you need to enable them in the Android manifest. Add the following to your manifest.xml file:

    <uses-permission android:name="android.permission.INTERNET" />

**Creating a static http client**

Finally in a new Java file, you should create a static implementation of the http client:

    public class HTTPClient {

        private static final String BASE_URL = "http://10.0.2.2:8080";

        private static AsyncHttpClient client = new AsyncHttpClient(false, 80, 443);

        public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
            client.addHeader("Accept", "application/json");
            client.addHeader("Content-Type", "application/json");
            client.get(getAbsoluteUrl(url), params, responseHandler);
        }

        private static String getAbsoluteUrl(String relativeUrl) {
            return BASE_URL + relativeUrl;
        }
    }

To make a http get request:

    HTTPClient.get("/json", null, new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            try {
                text = response.get('body');
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable error, JSONObject response) {
            error.printStackTrace();
        }
    });

