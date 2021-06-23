package com.ashtiv.myshows;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShowDetails extends AppCompatActivity {

    String title, language,showtype, premierDate, summary, thumbnail, previewLink, infoLink, buyLink;
    int runtime;
    private ArrayList<String> genres;

    TextView titleTV, showlanguage, descTV, pageTV, premDateTV,moviegenres,showtypeTV;
    Button previewBtn, buyBtn;
    private ImageView bookIV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        // initializing our views..
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.black));
        }
        titleTV = findViewById(R.id.idTVTitle);
        showlanguage = findViewById(R.id.idlanguage);
        showtypeTV = findViewById(R.id.idTVpublisher);
        descTV = findViewById(R.id.idTVDescription);
        pageTV = findViewById(R.id.idTVNoOfPages);
        premDateTV = findViewById(R.id.idTVPublishDate);
//        previewBtn = findViewById(R.id.idBtnPreview);
        buyBtn = findViewById(R.id.idBtnBuy);
        bookIV = findViewById(R.id.idIVbook);
        moviegenres=findViewById(R.id.idgenres);

        // getting the data which we have passed from our adapter class.
        title = getIntent().getStringExtra("title");
        language = getIntent().getStringExtra("language");
        showtype = getIntent().getStringExtra("showtype");
        premierDate = getIntent().getStringExtra("Date");
        summary = getIntent().getStringExtra("description");
        runtime = getIntent().getIntExtra("runtime", 0);
        thumbnail = getIntent().getStringExtra("thumbnail");
        previewLink = getIntent().getStringExtra("previewLink");
        infoLink = getIntent().getStringExtra("infoLink");
        buyLink = getIntent().getStringExtra("buyLink");
        genres = getIntent().getStringArrayListExtra("genres");
        StringBuilder au=new StringBuilder("");
        int aus= genres.size();
        for(int i=0;i<aus;i++){
            if(i<aus-1){
                au.append(genres.get(i)+", ");
            }
            else{
                au.append(genres.get(i));
            }
        }
//        System.out.println(au+"????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
        // after getting the data we are setting
        // that data to our text views and image view.
        titleTV.setText(title);
        showlanguage.setText(language);
        showtypeTV.setText(showtype);
        premDateTV.setText("Premiered On : " + premierDate);
        descTV.setText(summary);
        moviegenres.setText(au);
        pageTV.setText("Runtime : " + runtime + " minutes");
        Picasso.get().load(thumbnail).into(bookIV);
        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (buyLink.isEmpty()) {
//                    // below toast message is displaying when buy link is empty.
//                    Toast.makeText(ShowDetails.this, "No buy page present for this show", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                Log.d("pppppp",buyLink+"ppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppp");
                if(buyLink.equals("null")){
//                    Log.d("pppppp",buyLink+"ppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppppptttttt");
                    Toast.makeText(ShowDetails.this, "No buy page present for this show", Toast.LENGTH_SHORT).show();
                }
                // if the link is present we are opening
                // the link via an intent.
                else{
                    Uri uri = Uri.parse(buyLink);
                    Intent i = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(i);
                }

            }
        });
    }
}