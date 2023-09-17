package com.example.listviewdynamicdata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    ListView listView;
    ArrayList <HashMap<String,String>> arrayList = new ArrayList<>();
    HashMap<String,String> hashMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        listView = findViewById(R.id.listView);


        String url = "https://maruf5682.000webhostapp.com/apps/studentInfo.json";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressBar.setVisibility(View.GONE);

                        try {
                            for (int i = 0; i<response.length(); i++){
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    String studentName = jsonObject.getString("name");
                                    String roll = jsonObject.getString("roll");

                                    hashMap = new HashMap<>();
                                    hashMap.put("studentName",studentName);
                                    hashMap.put("roll",roll);
                                    arrayList.add(hashMap);

                            }


                            MyAdapter myAdapter = new MyAdapter();
                            listView.setAdapter(myAdapter);

                        }
                        catch (JSONException e) {
//                            throw new RuntimeException(e);
                            Log.d("error", String.valueOf(e));

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Log.d("error2", String.valueOf(error));
                Toast.makeText(MainActivity.this, " error", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(jsonArrayRequest);
    }


    private  class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View myView = layoutInflater.inflate(R.layout.student_info,null);
            TextView nameText, rollText;
            nameText = myView.findViewById(R.id.nameS);
            rollText = myView.findViewById(R.id.roll);

            HashMap<String,String> hashMap1 = arrayList.get(position);
            String nameS = hashMap1.get("studentName");
            String rollS = hashMap1.get("roll");

            nameText.setText(nameS);
            rollText.setText(rollS);

            return myView;
        }
    }
}