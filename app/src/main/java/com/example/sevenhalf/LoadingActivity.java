package com.example.sevenhalf;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class LoadingActivity extends AppCompatActivity {

    ObjectAnimator obj1;
    ObjectAnimator obj2;
    ObjectAnimator obj3;
    ImageView logo;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);

        logo = findViewById(R.id.cardMiddle1);

        AnimatorSet animationSet = new AnimatorSet();

        obj1 = ObjectAnimator.ofFloat(logo, "Rotation", -360);
        obj1.setDuration(2000);
        obj1.start();

        animationSet.playTogether(obj1);
        animationSet.start();

        animationSet.addListener(new AnimatorListenerAdapter() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View,String>(logo,"logo");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                        LoadingActivity.this,
                        pairs[0]
                );
                Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                startActivity(intent, options.toBundle());
            }
        });
    }
}
