package com.example.moviematchv2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN = 3000;

    Animation topAnim, bottomAnim;
    ImageView image;
    TextView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Hides action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();

            //animations
            topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
            bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

            //Hooks
            image = findViewById(R.id.imageView);
            logo = findViewById(R.id.textView);

            image.setAnimation(topAnim);
            logo.setAnimation(bottomAnim);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, LandingPage.class);
                    //Pair[] pairs = new Pair[2];
                   // pairs[0] = new Pair<View, String>(image, "logo_image");
                   // pairs[1] = new Pair<View, String>(logo, "logo_text");
                   // ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                  //  startActivity(intent, options.toBundle());
                    startActivity(intent);
                }
            }, SPLASH_SCREEN);

        }
    }
}