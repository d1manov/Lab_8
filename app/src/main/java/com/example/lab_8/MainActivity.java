package com.example.lab_8;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.weather);
        button = (Button) findViewById(R.id.get_weather);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpHandler handler=new OkHttpHandler();
                handler.execute();
            }
        });
    }
    public class OkHttpHandler extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void ... voids) {
            Request.Builder builder=new Request.Builder();

            Request request=builder.url("https://api.openweathermap.org/data/2.5/weather?lat=57.919&lon=59.965&appid=549ce26b30877e1afd76edb960f706dc&units=metric")
                    .get()
                    .build();

            OkHttpClient client=new OkHttpClient().newBuilder().build();

            try {
                Response response=client.newCall(request).execute();
                JSONObject object=new JSONObject(response.body().string());
                String temperature = object.getJSONObject("main").getString("temp");
                String feels_like = object.getJSONObject("main").getString("feels_like");
                return "Температура в Нижнем Тагиле: " + temperature + "\n" + "Ощущается как: " + feels_like;
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textView.setText(s);
        }
    }
}