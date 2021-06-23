package com.ashtiv.myshows;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
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
import org.jsoup.Jsoup;

import java.util.ArrayList;

import static java.sql.DriverManager.println;

public class MainActivity extends AppCompatActivity {
    // creating variables for our request queue,
    // array list, progressbar, edittext,
    // image button and our recycler view.
    private RequestQueue mRequestQueue;
    private ArrayList<showInfo> showInfoArrayList;
    private ProgressBar progressBar;
    private EditText searchEdt;
    private TextView searchBtn;
//    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.black));
        }
        // initializing our views.
        progressBar = findViewById(R.id.idLoadingPB);
        searchEdt = findViewById(R.id.idEdtSearchBooks);
        searchBtn = findViewById(R.id.idBtnSearch);
//        logo=findViewById(R.id.logo_mainact);
//        logo.setImageAlpha(30);
        // initializing on click listener for our button.
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                // checking if our edittext field is empty or not.
                if (searchEdt.getText().toString().isEmpty()) {
                    searchEdt.setError("Please enter search query");
                    return;
                }
                // if the search query is not empty then we are
                // calling get book info method to load all
                // the books from the API.
                getshowInfo(searchEdt.getText().toString());
            }
        });
    }
    private void getshowInfo(String query) {

        // creating a new array list.
        showInfoArrayList = new ArrayList<>();

        // below line is use to initialize
        // the variable for our request queue.
        mRequestQueue = Volley.newRequestQueue(MainActivity.this);

        // below line is use to clear cache this
        // will be use when our data is being updated.
        mRequestQueue.getCache().clear();

        // below is the url for getting data from API in json format.
        String url = "http://api.tvmaze.com/search/shows?q=" + query;

        // below line we are  creating a new request queue.
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        JsonArrayRequest booksObjrequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);
                // inside on response method we are extracting all our json data.
                try {
//                    JSONArray itemsArray = response.getJSONArray("items");
//                    JSONArray itemsArray =new JSONArray(response);
                    if(response.length()==0){
                        Toast.makeText(MainActivity.this, "No shows found...", Toast.LENGTH_SHORT).show();
                    }
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject itemsObj = response.optJSONObject(i);
                        JSONObject volumeObj = itemsObj.optJSONObject("show");
                        String title = volumeObj.optString("name");
                        String lang = volumeObj.optString("language");;
                        JSONArray genreArray = volumeObj.optJSONArray("genres");
                        String showtype = volumeObj.optString("type");
                        String premDate = volumeObj.optString("premiered");
                        if(premDate=="null"){
                            premDate="Not yet";
                        }
                        String description = volumeObj.optString("summary");
                        description=html2text(description);
                        int mins = volumeObj.optInt("runtime");
                        if(mins==0){
                            mins = volumeObj.optInt("averageRuntime");
                        }
                        JSONObject imageLinks = volumeObj.optJSONObject("image");
                        String thumbnail="https://ashtiv.github.io/justsomeuploads/alternate_thumbnail.png";
                        String previewLink="https://ashtiv.github.io/justsomeuploads/alternate_thumbnail.png";
                        if(imageLinks!=null){
                            thumbnail = imageLinks.optString("original");
//                            previewLink = volumeObj.optString("officialSite");
                        }
                        String infoLink = "aaaaainfolink";
                        JSONObject saleInfoObj = volumeObj.optJSONObject("_links");
                        saleInfoObj = saleInfoObj.optJSONObject("self");
                        String buyLink = volumeObj.optString("officialSite");
                        ArrayList<String> genresArrayList = new ArrayList<>();
                        if (genreArray.length() != 0) {
                            for (int j = 0; j < genreArray.length(); j++) {
                                genresArrayList.add(genreArray.getString(j));
                            }
                        }
                        // after extracting all the data we are
                        // saving this data in our modal class.
                        showInfo showInfo = new showInfo(title, lang, genresArrayList, showtype, premDate, description, mins, thumbnail, previewLink, infoLink, buyLink);

                        // below line is use to pass our modal
                        // class in our array list.
                        showInfoArrayList.add(showInfo);

                        // below line is use to pass our
                        // array list in adapter class.
                        ShowAdapter adapter = new ShowAdapter(showInfoArrayList, MainActivity.this);

                        // below line is use to add linear layout
                        // manager for our recycler view.
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
                        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.idRVBooks);

                        // in below line we are setting layout manager and
                        // adapter to our recycler view.
                        mRecyclerView.setLayoutManager(linearLayoutManager);
                        mRecyclerView.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    // displaying a toast message when we get any error from API
                    Toast.makeText(MainActivity.this, "No Data Found" + e, Toast.LENGTH_SHORT).show();

                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // also displaying error message in toast.
                println(error.toString());
                Toast.makeText(MainActivity.this, "Error found is " + error, Toast.LENGTH_SHORT).show();
//                Log.d("pppppp",error.toString());
            }
        });
        // at last we are adding our json object
        // request in our request queue.
        queue.add(booksObjrequest);
    }
    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }

}