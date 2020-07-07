package com.advancedandroid.assignment_advanced_android;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.advancedandroid.assignment_advanced_android.LoginActivity.LoginActivity;
import com.advancedandroid.assignment_advanced_android.NewsFeed.NewsFeedActivity;
import com.advancedandroid.assignment_advanced_android.StudentsManagerActivity.ClassAndStudentsManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    LinearLayout studentsManager;
    LinearLayout newsManager;
    LinearLayout maps;
    Intent i;
    ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewFlipper = findViewById(R.id.sliderImage);

        int slideImage[] = {R.drawable.slide_1, R.drawable.slide_2, R.drawable.slide_3};

        //for loop
//        for (int i = 0; i < slideImage.length; i++){
//            slideImage(slideImage[i]);
//        }

        //foreach
        for (int slide: slideImage){
            slideImage(slide);
        }

        studentsManager = findViewById(R.id.studentsManager);
        newsManager = findViewById(R.id.newsManager);
        maps = findViewById(R.id.mapsLayout);

        studentsManager.setOnClickListener(this);
        newsManager.setOnClickListener(this);
        maps.setOnClickListener(this);

        findViewById(R.id.btnLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getSharedPreferences("ACCOUNT_DATA", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("USERNAME");
                editor.remove("PASSWORD");
                editor.remove("CHKREMEMBER");
                editor.commit();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void slideImage(int image){
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(1000);
        viewFlipper.setAutoStart(true);

        //animation
        viewFlipper.setInAnimation(this, android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this, android.R.anim.slide_out_right);
    }


    @Override
    public void onClick(View v) {
        if (v == studentsManager){
            i = new Intent(this, ClassAndStudentsManager.class);
            startActivity(i);
        }
        if (v == newsManager){
            i = new Intent(this, NewsFeedActivity.class);
            startActivity(i);
        }
        if (v == maps){
            i = new Intent(this, MapsActivity.class);
            startActivity(i);
        }
    }

}
