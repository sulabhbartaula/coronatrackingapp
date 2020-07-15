package np.com.sulabh.coronatrackingaustralia;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView total_cases, new_cases, deaths, new_deaths, recovered, isolated, lastUpdated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        total_cases = (TextView) findViewById(R.id.total_cases);
        new_cases = (TextView) findViewById(R.id.new_cases);

        deaths = (TextView) findViewById(R.id.deaths);
        new_deaths = (TextView) findViewById(R.id.new_deaths);

        recovered = (TextView) findViewById(R.id.recovered);
        isolated = (TextView) findViewById(R.id.isolated);

        lastUpdated = (TextView) findViewById(R.id.last_update);

        // call Volley
        getData();

    }

    private void getData() {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://corona.lmao.ninja/countries/australia";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.toString());

                    total_cases.setText(jsonObject.getString("cases"));
                    new_cases.setText(jsonObject.getString("todayCases"));

                    deaths.setText(jsonObject.getString("deaths"));
                    new_deaths.setText(jsonObject.getString("todayDeaths"));

                    recovered.setText(jsonObject.getString("recovered"));
                    isolated.setText(jsonObject.getString("active"));

                    lastUpdated.setText("Last Updated" + " : " + getDate(jsonObject.getLong("updated")));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error Response", error.toString());
            }
        });
        queue.add(stringRequest);
    }


    private String getDate(long milliSecond) {
        // Mon, 23 Mar 2020 02:01:04 PM
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss aaa");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSecond);
        return formatter.format(calendar.getTime());
    }
}

