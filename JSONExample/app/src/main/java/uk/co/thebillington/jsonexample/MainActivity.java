package uk.co.thebillington.jsonexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.*;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Create a new JSON object to store the params
                //JSONObject params = new JSONObject();
                JSONObject jsonUser = new JSONObject();

                String user = ((EditText) findViewById(R.id.username)).getText().toString();
                String pass = ((EditText) findViewById(R.id.username)).getText().toString();

                try {
                    jsonUser.put("username", user);
                    jsonUser.put("password", pass);
                    //params.put(PrivateFields.TAG_USER, jsonUser);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                HTTPClient.post(getBaseContext(), "/mlogin", jsonUser, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        String msg = "No response";
                        try {
                            msg = response.getString("message");
                        } catch (JSONException e) {
                            msg = e.toString();
                        }

                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable error, JSONObject response) {
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


    }
}
