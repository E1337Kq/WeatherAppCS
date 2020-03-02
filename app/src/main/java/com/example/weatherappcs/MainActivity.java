package com.example.weatherappcs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView editText;
    TextView result;
    TextView nameText;
    GridLayout mainGrid1;
    Button dial;

    class Weather extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... address) {
            //Kada imamo string u formatu onda mozemo slat vise adresa jer djeluju kao array.

            try {
                URL url = new URL(address[0]);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                //uspostavljanje veze sa konekcijom
                connection.connect();

                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                int data = isr.read();
                String content = "";
                char ch;
                while (data != -1){
                    ch = (char)data;
                    content = content + ch;
                    data = isr.read();
                }
                return  content;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

    }


    public  void search(View view){
        mainGrid1 = findViewById(R.id.mainGrid);
        editText = findViewById(R.id.editText);
        result = findViewById(R.id.result);

        String content;
        Weather weather = new Weather();
        try {
            content=weather.execute("http://213.149.113.86:8000/api/measurement_by_limit/?format=json&limit=3&node_id=3").get();
            Log.i("contentData", content);

            JSONObject jsonObject = new JSONObject(content);
            String weatherData = jsonObject.getString("data");
            Log.i("weatherData", weatherData);
            //Podaci o vremenu su u array formatu
            JSONArray array = new JSONArray(weatherData);

            String sensor_1_val_3 = "";
            String sensor_2_val_3 = "";
            String sensor_3_val_3 = "";
            String sensor_4_val_3 ="";

            String sensor_1_val_4 = "";
            String sensor_2_val_4 = "";
            String sensor_3_val_4 = "";


            String sensor_1_val_5 = "";
            String sensor_2_val_5 = "";



            for (int i=0; i <array.length(); i++){

                JSONObject weatherPart = array.getJSONObject(2);
                sensor_1_val_3 = weatherPart.getString("sensor_1_val");
                sensor_2_val_3 = weatherPart.getString("sensor_2_val");
                sensor_3_val_3 = weatherPart.getString("sensor_3_val");
                sensor_4_val_3 = weatherPart.getString("timestamp");
            }

            for (int i=0; i <array.length(); i++){

                JSONObject weatherPart = array.getJSONObject(1);
                sensor_1_val_4 = weatherPart.getString("sensor_1_val");
                sensor_2_val_4 = weatherPart.getString("sensor_2_val");
                sensor_3_val_4 = weatherPart.getString("sensor_3_val");

            }

            for (int i=0; i <array.length(); i++){

                JSONObject weatherPart = array.getJSONObject(0);
                sensor_1_val_5 = weatherPart.getString("sensor_1_val");
                sensor_2_val_5 = weatherPart.getString("sensor_2_val");

            }

            String resultText ="Free spots at the beach: 45/100" +
                    "\nAir Temperature: " + sensor_1_val_3.substring(0,5)+ " °C"+
                    "\nAir Humidity: " + sensor_2_val_3.substring(0,5)+ " %" +
                    "\nAir Pressure " + sensor_3_val_3.substring(0,6) + " mbar" +
                    "\nAverage Wind Speed:  " +sensor_1_val_4.substring(0,3) + " (1min) [m/s]"+
                    "\nMaximum Wind Speed: " +sensor_2_val_4.substring(0,3) + " (5min) [m/s]"+
                    "\nWind Direction: " +sensor_3_val_4.substring(0,4) + " deg°"+
                    "\nRainfall: "+ sensor_1_val_5.substring(0,4) + " (1h) [mm]°"+
                    "\nRainfall: " +sensor_2_val_5.substring(0,4) + " (24h) [mm]°"+
                    "\nDate: " +sensor_4_val_3.substring(0,10) +
                    "\nWarning flag status: Yellow ";

            result.setText(resultText);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameText = findViewById(R.id.nameText);
        String tHolder = getIntent().getStringExtra("tHolder");
        nameText.setText(tHolder);
        mainGrid1 = (GridLayout) findViewById(R.id.mainGrid1);
        dial = (Button)findViewById(R.id.dial);
    }

    public void onDialButton(View call){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:069736547"));
        startActivity(intent);
    }

}
