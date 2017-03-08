package uk.co.thebillington.jsonexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.loopj.android.http.*;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv = (TextView) findViewById(R.id.textBox);

        HTTPClient.get("/json", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                tv.setText("SUCCESS");
                // If the response is JSONObject instead of expected JSONArray
                String text = "";
                try {
                    text += response.get("title");
                    text += "\n";
                    text += response.get("description");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tv.setText(text);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable error, JSONObject response) {

                tv.setText("FAILURE");

                error.printStackTrace();
            }
        });
    }
}
