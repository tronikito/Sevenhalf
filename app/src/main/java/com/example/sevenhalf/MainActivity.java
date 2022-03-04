package com.example.sevenhalf;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Objects;

import static com.example.sevenhalf.Card.CARDS_DONE;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String PREF_FILE_NAME = "MySharedFile";

    int[] score = new int[]{0, 0};

    ObjectAnimator giveCardAnimation0;
    ObjectAnimator giveCardAnimation1;
    ObjectAnimator giveCardAnimation2;
    ObjectAnimator giveCardAnimation3;
    ObjectAnimator giveCardAnimation4;
    ObjectAnimator giveCardAnimation5;
    ObjectAnimator giveCardAnimation6;
    ObjectAnimator giveCardAnimation7;
    ObjectAnimator giveCardAnimation8;
    ObjectAnimator giveCardAnimation9;
    ObjectAnimator giveCardAnimation10;
    ObjectAnimator giveCardAnimation11;
    ObjectAnimator giveCardAnimation12;
    ObjectAnimator giveCardAnimation13;
    ObjectAnimator giveCardAnimation14;
    ObjectAnimator giveCardAnimation15;
    ObjectAnimator giveCardAnimation16;

    ImageView cardMiddle1;
    ImageView cardMiddle2;
    Button btGet;
    Button btStart;
    Button btPass;
    TextView playerScoreText;
    TextView bankScoreText;
    TextView resultText;
    LinearLayout bankScoreTextAlpha;
    LinearLayout playerScoreTextAlpha;
    TextView bankScoreGlobal;
    TextView playerScoreGlobal;

    private boolean playingCard;
    private float playerScore;
    private float bankScore;
    private boolean playerSurpas;
    private boolean bankSurpas;

    private Deque<View> cardPlayerList = new ArrayDeque<>();
    private Deque<View> cardBankList = new ArrayDeque<>();
    private Deque<Card> thisCardDeque = new ArrayDeque<>();

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        /*
        else {
            super.onBackPressed();
        }*/
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rate:
                Toast.makeText(this, "Rate", Toast.LENGTH_SHORT).show();
                break;
            case R.id.contact:
                Toast.makeText(this, "Contact us", Toast.LENGTH_SHORT).show();
                break;
            case R.id.resetgame:
                Toast.makeText(this, "Reset Game Global Score", Toast.LENGTH_SHORT).show();

                score[0] = 0;
                score[1] = 0;
                SharedPreferences sharPrefs = getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharPrefs.edit();
                editor.putInt("bankScore",score[0]);
                editor.putInt("playerScore",score[1]);
                editor.commit();
                bankScoreGlobal.setText(score[0] + "");
                playerScoreGlobal.setText(score[1] + "");

                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //hook

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.mytoolbar);
        bankScoreGlobal = findViewById(R.id.bankScoreResult);
        playerScoreGlobal = findViewById(R.id.playerScoreResult);
        bankScoreTextAlpha = findViewById(R.id.bankScoreTextAlpha);
        playerScoreTextAlpha = findViewById(R.id.playerScoreTextAlpha);
        cardMiddle1 = findViewById(R.id.cardMiddle1);
        cardMiddle2 = findViewById(R.id.cardMiddle2);
        btGet = findViewById(R.id.btGet);
        btStart = findViewById(R.id.btStart);
        btPass = findViewById(R.id.btPass);
        playerScoreText = findViewById(R.id.playerScoreText);
        bankScoreText = findViewById(R.id.bankScoreText);
        resultText = findViewById(R.id.resultText);

        playingCard = true;
        playerSurpas = false;
        bankSurpas = false;

        for (int x = 14; x >= 0; x--) {
            int resID = getResources().getIdentifier("card" + x, "id", getPackageName());
            View newCard = findViewById(resID);
            cardPlayerList.push(newCard);
        }

        for (int x = 14; x >= 0; x--) {
            int resID = getResources().getIdentifier("cardBank" + x, "id", getPackageName());
            View newCard = findViewById(resID);
            cardBankList.push(newCard);
        }

        SharedPreferences sharPrefs = getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        score[0] = sharPrefs.getInt("bankScore", 0);
        score[1] = sharPrefs.getInt("playerScore", 0);

        bankScoreGlobal.setText(score[0] + "");
        playerScoreGlobal.setText(score[1] + "");

        //nav bar

        //navigationView.setCheckedItem(R.id.share);
        navigationView.setNavigationItemSelectedListener(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_home, R.string.nav_logout);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //clickEvents

        btStart.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                AnimatorSet animationSet = new AnimatorSet();

                /** they have to be *-1 because playerCards are constraint attachment to bottom not top **/

                giveCardAnimation0 = ObjectAnimator.ofFloat(btStart, "alpha", 0);
                giveCardAnimation0.setDuration(500);
                giveCardAnimation1 = ObjectAnimator.ofFloat(btGet, "alpha", 1);
                giveCardAnimation1.setDuration(500);
                giveCardAnimation2 = ObjectAnimator.ofFloat(btPass, "alpha", 1);
                giveCardAnimation2.setDuration(500);
                giveCardAnimation3 = ObjectAnimator.ofFloat(playerScoreText, "alpha", 1);
                giveCardAnimation3.setDuration(500);
                giveCardAnimation4 = ObjectAnimator.ofFloat(bankScoreText, "alpha", 1);
                giveCardAnimation4.setDuration(500);
                giveCardAnimation5 = ObjectAnimator.ofFloat(playerScoreTextAlpha, "alpha", 1);
                giveCardAnimation5.setDuration(500);
                giveCardAnimation6 = ObjectAnimator.ofFloat(bankScoreTextAlpha, "alpha", 1);
                giveCardAnimation6.setDuration(500);

                animationSet.playTogether(giveCardAnimation0, giveCardAnimation1, giveCardAnimation2,
                                          giveCardAnimation3, giveCardAnimation4,giveCardAnimation5,
                                          giveCardAnimation6);
                animationSet.start();

                animationSet.addListener(new AnimatorListenerAdapter() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        playingCard = false;
                        playerScore = 0;
                        bankScore = 0;
                    }
                });
            }
        });

        btPass.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                checkGameState();
                playingCard = true;
            }
        });

        btGet.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                if (!playingCard) {
                    playingCard = true;
                    View actualCard = null;
                    if (!cardPlayerList.isEmpty()) {
                        actualCard = cardPlayerList.pop();
                    }

                    if (actualCard != null) {

                        Card newCard = new Card(MainActivity.this);
                        cardMiddle2.setBackgroundResource(newCard.backgroundResource);
                        actualCard.setBackgroundResource(newCard.backgroundResource);

                        float lineStartX = cardMiddle1.getX() - actualCard.getX();
                        float lineStartY = cardMiddle1.getY() - actualCard.getY();

                        AnimatorSet animationSet = moveCard(lineStartX,lineStartY,actualCard);

                        thisCardDeque.push(newCard);

                        animationSet.addListener(new AnimatorListenerAdapter() {
                            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);

                                playerScore += thisCardDeque.pop().realValue;
                                String result = Float.toString(playerScore);

                                if (result.contains(".")) {
                                    if (Objects.equals(result.charAt(result.length() - 1) + "", "0")) {
                                        result = String.format("%.0f", playerScore);
                                    }
                                }

                                playerScoreText.setText(result);

                                //permite seguir jugando si no has alcanzado 7.5
                                if (playerScore < 7.5) playingCard = false;
                                else if (playerScore == 7.5) checkGameState();
                                else if (playerScore > 7.5) {
                                    playerSurpas = true;
                                    checkGameState();
                                    //esta función puede ser llamada por el boton plantarse.
                                    //por eso es checkGameState, puede ser llamada desde diferentes puntos de la partida.
                                }
                            }
                        });
                    }
                }
            }
        });
    }
    //end ofCreate

    private void bankPlay() {
        //there is the ia and game features

        View actualCard = null;
        if (!cardBankList.isEmpty()) {
            actualCard = cardBankList.pop();
        }

        if (actualCard != null) {

            Card newCard = new Card(MainActivity.this);
            cardMiddle2.setBackgroundResource(newCard.backgroundResource);
            actualCard.setBackgroundResource(newCard.backgroundResource);

            float lineStartX = cardMiddle1.getX() - actualCard.getX();
            float lineStartY = cardMiddle1.getY() - actualCard.getY();

            AnimatorSet animationSet = moveCard(lineStartX,lineStartY,actualCard);

            thisCardDeque.push(newCard);

            animationSet.addListener(new AnimatorListenerAdapter() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);

                    bankScore += thisCardDeque.pop().realValue;
                    String result = Float.toString(bankScore);

                    if (result.contains(".")) {
                        if (Objects.equals(result.charAt(result.length() - 1) + "", "0")) {
                            result = String.format("%.0f", bankScore);
                        }
                    }
                    bankScoreText.setText(result);

                    if (bankScore <= 7.5) checkBankDecision();
                    else {
                        bankSurpas = true;
                        finishGame();
                    }
                }
            });
        }
    }

    private AnimatorSet moveCard(float lineStartX, float lineStartY, View actualCard) {
        AnimatorSet animationSet = new AnimatorSet();

        /** they have to be *-1 because playerCards are constraint attachment to bottom not top **/

        //pre Effect card1

        //card1
        giveCardAnimation0 = ObjectAnimator.ofFloat(cardMiddle1, "translationX", lineStartX*-1);
        giveCardAnimation0.setDuration(1000);
        giveCardAnimation1 = ObjectAnimator.ofFloat(cardMiddle1, "translationY", lineStartY*-1);
        giveCardAnimation1.setDuration(1000);
        giveCardAnimation2 = ObjectAnimator.ofFloat(cardMiddle1, "RotationY", 0);
        giveCardAnimation2.setDuration(1000);
        giveCardAnimation3 = ObjectAnimator.ofFloat(cardMiddle1, "Alpha", 0);
        giveCardAnimation3.setStartDelay(500);
        giveCardAnimation3.setDuration(0);

        //post Effect
        giveCardAnimation4 = ObjectAnimator.ofFloat(cardMiddle1, "translationX", 0);
        giveCardAnimation4.setStartDelay(1000);
        giveCardAnimation4.setDuration(0);
        giveCardAnimation5 = ObjectAnimator.ofFloat(cardMiddle1, "translationY", 0);
        giveCardAnimation5.setStartDelay(1000);
        giveCardAnimation5.setDuration(0);
        giveCardAnimation6 = ObjectAnimator.ofFloat(cardMiddle1, "RotationY", -180); //pre Effect
        giveCardAnimation6.setStartDelay(0);
        giveCardAnimation6.setDuration(0);
        giveCardAnimation7 = ObjectAnimator.ofFloat(cardMiddle1, "Alpha", 1);
        giveCardAnimation7.setStartDelay(1000);
        giveCardAnimation7.setDuration(0);

        //card2
        giveCardAnimation8 = ObjectAnimator.ofFloat(cardMiddle2, "translationX", lineStartX*-1);
        giveCardAnimation8.setDuration(1000);
        giveCardAnimation9 = ObjectAnimator.ofFloat(cardMiddle2, "translationY", lineStartY*-1);
        giveCardAnimation9.setDuration(1000);
        giveCardAnimation10 = ObjectAnimator.ofFloat(cardMiddle2, "RotationY", 0);
        giveCardAnimation10.setDuration(1000);
        giveCardAnimation11 = ObjectAnimator.ofFloat(cardMiddle2, "Alpha", 1);
        giveCardAnimation11.setStartDelay(500);
        giveCardAnimation11.setDuration(0);

        //post Effect
        giveCardAnimation12 = ObjectAnimator.ofFloat(cardMiddle2, "translationX", 0);
        giveCardAnimation12.setStartDelay(1000);
        giveCardAnimation12.setDuration(0);
        giveCardAnimation13 = ObjectAnimator.ofFloat(cardMiddle2, "translationY", 0);
        giveCardAnimation13.setStartDelay(1000);
        giveCardAnimation13.setDuration(0);
        giveCardAnimation14 = ObjectAnimator.ofFloat(cardMiddle2, "RotationY", -180); //pre Effect
        giveCardAnimation14.setStartDelay(0);
        giveCardAnimation14.setDuration(0);
        giveCardAnimation15 = ObjectAnimator.ofFloat(cardMiddle2, "Alpha", 0);
        giveCardAnimation15.setStartDelay(1000);
        giveCardAnimation15.setDuration(0);

        //cardTableUp
        giveCardAnimation16 = ObjectAnimator.ofFloat(actualCard, "Alpha", 1);
        giveCardAnimation16.setStartDelay(1000);
        giveCardAnimation16.setDuration(0);

        animationSet.playTogether(giveCardAnimation0, giveCardAnimation1, giveCardAnimation2,
                giveCardAnimation3, giveCardAnimation4, giveCardAnimation5, giveCardAnimation6,
                giveCardAnimation7, giveCardAnimation8, giveCardAnimation9, giveCardAnimation10,
                giveCardAnimation11, giveCardAnimation12, giveCardAnimation13, giveCardAnimation14,
                giveCardAnimation15, giveCardAnimation16);
        animationSet.start();

        return animationSet;
    }

    private void checkGameState() {

        giveCardAnimation0 = ObjectAnimator.ofFloat(btGet, "alpha", 0);
        giveCardAnimation0.setDuration(500);
        giveCardAnimation0.start();
        giveCardAnimation1 = ObjectAnimator.ofFloat(btPass, "alpha", 0);
        giveCardAnimation1.setDuration(500);
        giveCardAnimation1.start();

        if (playerSurpas) finishGame();
        if (playerScore <= 7.5) checkBankDecision();
    }

    private void checkBankDecision() {
        if (bankScore >= playerScore) finishGame();
        else bankPlay();
    }

    private void finishGame() {
        //if (playerSurpas && bankSurpas) playerDraw();
        if (playerSurpas && !bankSurpas) playerLose();
        if (!playerSurpas && bankSurpas) playerWinner();
        if (!playerSurpas && !bankSurpas) {
            if (playerScore > bankScore) playerWinner();
            if (playerScore <= bankScore) playerLose();
        }
    }

    private void playerWinner() {
        score[1]++;
        callForEndGameText("Winner");
    }

    private void playerLose() {
        score[0]++;
        callForEndGameText("Lose");
    }

    //private void playerDraw() {
        //callForEndGameText("Draw");
    //}

    private void callForEndGameText(String result) {

        AnimatorSet animationSet = new AnimatorSet();

        resultText.setText(result);
        giveCardAnimation0 = ObjectAnimator.ofFloat(resultText, "alpha", 1);
        giveCardAnimation0.setDuration(2000);
        giveCardAnimation1 = ObjectAnimator.ofFloat(resultText, "scaleX", 10);
        giveCardAnimation1.setDuration(2000);
        giveCardAnimation2 = ObjectAnimator.ofFloat(resultText, "scaleY", 10);
        giveCardAnimation2.setDuration(2000);
        giveCardAnimation3 = ObjectAnimator.ofFloat(resultText, "alpha", 0);
        giveCardAnimation3.setStartDelay(2000);
        giveCardAnimation3.setDuration(2000);
        giveCardAnimation4 = ObjectAnimator.ofFloat(resultText, "scaleX", 0);
        giveCardAnimation4.setStartDelay(2000);
        giveCardAnimation4.setDuration(2000);
        giveCardAnimation5 = ObjectAnimator.ofFloat(resultText, "scaleY", 0);
        giveCardAnimation5.setStartDelay(2000);
        giveCardAnimation5.setDuration(2000);

        animationSet.playTogether(giveCardAnimation0, giveCardAnimation1,giveCardAnimation2,
                                  giveCardAnimation3,giveCardAnimation4,giveCardAnimation5);
        animationSet.start();

        animationSet.addListener(new AnimatorListenerAdapter() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                btStart.setText("Re-play?");

                AnimatorSet animationSet = new AnimatorSet();

                giveCardAnimation0 = ObjectAnimator.ofFloat(btStart, "alpha", 1);
                giveCardAnimation0.setDuration(500);
                giveCardAnimation1 = ObjectAnimator.ofFloat(playerScoreTextAlpha, "alpha", 0);
                giveCardAnimation1.setDuration(0);
                giveCardAnimation2 = ObjectAnimator.ofFloat(bankScoreTextAlpha, "alpha", 0);
                giveCardAnimation2.setDuration(0);

                animationSet.playTogether(giveCardAnimation0,giveCardAnimation1,giveCardAnimation2);
                animationSet.start();

                resetGame();
            }
        });

    }

    private void resetGame() {

        SharedPreferences sharPrefs = getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharPrefs.edit();
        editor.putInt("bankScore",score[0]);
        editor.putInt("playerScore",score[1]);
        editor.commit();

        bankScoreGlobal.setText(score[0] + "");
        playerScoreGlobal.setText(score[1] + "");
        cardPlayerList = new ArrayDeque<>();
        cardBankList = new ArrayDeque<>();
        playerScore = 0;
        bankScore = 0;
        bankSurpas = false;
        playerSurpas = false;
        playerScoreText.setText("0");
        bankScoreText.setText("0");

        for (int x = 14; x >= 0; x--) {
            int resID = getResources().getIdentifier("card" + x, "id", getPackageName());
            View newCard = findViewById(resID);
            newCard.setBackgroundResource(0);
            newCard.setAlpha(0);
            cardPlayerList.push(newCard);
        }

        for (int x = 14; x >= 0; x--) {
            int resID = getResources().getIdentifier("cardBank" + x, "id", getPackageName());
            View newCard = findViewById(resID);
            newCard.setBackgroundResource(0);
            newCard.setAlpha(0);
            cardBankList.push(newCard);
        }
    }
}
