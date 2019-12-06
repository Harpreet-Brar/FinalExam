package com.capstone.finalexam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TitlesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_titles);
        ListView listView = findViewById(R.id.titlelist);
        final OkHttpClient client = new OkHttpClient();
        final ArrayList<String> headings = new ArrayList<>();
        Intent intent = getIntent ();
        String title=intent.getStringExtra ("Title");
        Log.d ("test", "onCreate: "+title);
        String url = "  https://www.reddit.com/r/"+title+".json";
        final Request request = new Request.Builder().url(url).build();

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Response response = client.newCall(request).execute();
                    String data = response.body().string();
                    JSONObject target = (JSONObject) new JSONTokener(data).nextValue();
                    JSONArray jsonArray = target.getJSONObject("data").getJSONArray("children");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        String x =item.getJSONObject("data").getString("title");
                        headings.add(x);
                    }
                    runOnUiThread(() -> {
                        if(headings.isEmpty ()){
                            headings.add ("No postings found");
                        }
                        listView.setAdapter(new ArrayAdapter (TitlesActivity.this, android.R.layout.simple_list_item_1, headings));

                    });
                } catch (IOException | JSONException e) {
                    runOnUiThread(() -> {
                        headings.add("Networking Error");
                        Log.d ("Exception", "run: "+e.toString ());
                    });
                }
                ;
            }
        };

        thread.start();

    }
}
