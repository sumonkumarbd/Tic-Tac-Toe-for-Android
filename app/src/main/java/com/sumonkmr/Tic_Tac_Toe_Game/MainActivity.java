package com.sumonkmr.Tic_Tac_Toe_Game;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.sumonkmr.tic_tac_toe_game.R;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    AppCompatButton oneVsOne_btn, computer_btn, restart;
    AppCompatButton[][] buttons;
    TextView statusTxt, statusTxt2, player1_score, player2_score, player1tv, player2tv;
    boolean player1Turn = true;
    boolean player2Turn = true;
    Random random = new Random();
    Handler handler = new Handler();
    Runnable runnable,winSoundThread;
    int first_score, second_score;
    int count = 0;
    MediaPlayer player1Sound, player2Sound, winSound, drawSound, newGameSound, amma_behen, noni, aain, humayora, clockSound, clock_effects;
    AlphaAnimation fadeIn;
    Animation slideInLeft, slideInRight, slideInTop, slideInBottom;
    LottieAnimationView robot_lottie, happy_lottie, player2_pro_lottie, human_clock_anim;
    VideoView bottom_VideoView;
    CardView player1Img_card_1, player2Img_card_2, human_clock_card;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Init();
        oneVsOne_btn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, OneVsOne.class)));
        computer_btn.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, VsComputer.class)));


    }//onCreate


    protected void Init() {
        buttons = new AppCompatButton[3][3];
        buttons[0][0] = findViewById(R.id.btn1);
        buttons[0][1] = findViewById(R.id.btn2);
        buttons[0][2] = findViewById(R.id.btn3);
        buttons[1][0] = findViewById(R.id.btn4);
        buttons[1][1] = findViewById(R.id.btn5);
        buttons[1][2] = findViewById(R.id.btn6);
        buttons[2][0] = findViewById(R.id.btn7);
        buttons[2][1] = findViewById(R.id.btn8);
        buttons[2][2] = findViewById(R.id.btn9);

        player1tv = findViewById(R.id.player1tv);
        player2tv = findViewById(R.id.player2tv);
        restart = findViewById(R.id.restart); // Identifying xml view id (restart) in java as restart.
        statusTxt = findViewById(R.id.statusTxt);
        statusTxt2 = findViewById(R.id.statusTxt2);
        oneVsOne_btn = findViewById(R.id.oneVsOne_btn);
        computer_btn = findViewById(R.id.computer_btn);
        player1_score = findViewById(R.id.player1_score);
        player2_score = findViewById(R.id.player2_score);
        player1Img_card_1 = findViewById(R.id.player1Img_card_1); // Identifying xml view id (player1Img_card_1) in java as player1Img_card_1.
        player2Img_card_2 = findViewById(R.id.player2Img_card_2); // Identifying xml view id (player2Img_card_2) in java as player2Img_card_2.
        human_clock_card = findViewById(R.id.human_clock_card); // Identifying xml view id (human_clock_card) in java as human_clock_card.


//        bottom_VideoView = findViewById(R.id.bottom_VideoView); // Identifying xml view id (bottom_VideoView) in java as bottom_VideoView.
        robot_lottie = findViewById(R.id.robot_lottie);
        happy_lottie = findViewById(R.id.happy_lottie);
        player2_pro_lottie = findViewById(R.id.player2_pro_lottie); // Identifying xml view id (player2_pro_lottie) in java as player2_pro_lottie.
        human_clock_anim = findViewById(R.id.human_clock_anim);

        player1Sound = MediaPlayer.create(this, R.raw.player1);
        player2Sound = MediaPlayer.create(this, R.raw.player2);
        winSound = MediaPlayer.create(this, R.raw.win);
        drawSound = MediaPlayer.create(this, R.raw.draw);
        newGameSound = MediaPlayer.create(this, R.raw.new_game);
        amma_behen = MediaPlayer.create(this, R.raw.amma_bahen);
        noni = MediaPlayer.create(this, R.raw.nani);
        aain = MediaPlayer.create(this, R.raw.aain);
        humayora = MediaPlayer.create(this, R.raw.humayora_sounds);
        clockSound = MediaPlayer.create(this, R.raw.clock_sound);
        clock_effects = MediaPlayer.create(this, R.raw.clock_effects);

        slideInBottom = AnimationUtils.loadAnimation(this, R.anim.slide_in_bottom);
        slideInTop = AnimationUtils.loadAnimation(this, R.anim.slide_in_top);
        slideInLeft = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        slideInRight = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);

    }//Init


    //========protected Method=========//
    protected boolean checkWin(String player) {
        //===Loop start===
        for (int i = 0; i < 3; i++) {
            //===Condition start====
            if (buttons[i][0].getText().toString().equals(player) &&
                    buttons[i][1].getText().toString().equals(player) &&
                    buttons[i][2].getText().toString().equals(player)) {
                winningCells(new int[][]{{i, 0}, {i, 1}, {i, 2}});
                return true; //===Condition end===
            }

            if (buttons[0][i].getText().toString().equals(player) &&
                    buttons[1][i].getText().toString().equals(player) &&
                    buttons[2][i].getText().toString().equals(player)) {
                winningCells(new int[][]{{0, i}, {1, i}, {2, i}});
                return true; //===Condition end===
            }

            //===Condition start====
            if (buttons[0][0].getText().toString().equals(player) &&
                    buttons[1][1].getText().toString().equals(player) &&
                    buttons[2][2].getText().toString().equals(player)) {
                winningCells(new int[][]{{0, 0}, {1, 1}, {2, 2}});
                return true;

            }//===Condition end===

            //===Condition start====
            if (buttons[0][2].getText().toString().equals(player) &&
                    buttons[1][1].getText().toString().equals(player) &&
                    buttons[2][0].getText().toString().equals(player)) {
                winningCells(new int[][]{{0, 2}, {1, 1}, {2, 0}});
                return true;
            }//===Condition end===

        }//===Loop end!===

        return false;


    }//checkWin method end!

    //========protected Method=========//
    protected boolean isBordFull() {
        //=========Each loop start==========
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().toString().isEmpty()) {
                    return false;
                }
            }//=========Inner loop stop==========
        }//=========Outer & each loop stop==========

        return true;

    }//boolean isBordFull method end!


    //========protected Method=========//
    protected void newGame() {
        //===Condition start====
        newGameSound.start();
        player1Turn = true;
        robot_lottie.setVisibility(View.GONE);
        happy_lottie.setVisibility(View.GONE);
        buttons[0][0].setText("");
        buttons[0][0].setEnabled(true);

        buttons[0][1].setText("");
        buttons[0][1].setEnabled(true);

        buttons[0][2].setText("");
        buttons[0][2].setEnabled(true);

        buttons[1][0].setText("");
        buttons[1][0].setEnabled(true);

        buttons[1][1].setText("");
        buttons[1][1].setEnabled(true);

        buttons[1][2].setText("");
        buttons[1][2].setEnabled(true);

        buttons[2][0].setText("");
        buttons[2][0].setEnabled(true);

        buttons[2][1].setText("");
        buttons[2][1].setEnabled(true);

        buttons[2][2].setText("");
        buttons[2][2].setEnabled(true);
        count = 0;
        statusTxt.setText("Let'");
        statusTxt2.setText("s Go!");
        statusTxt.startAnimation(slideInLeft);
        statusTxt2.startAnimation(slideInRight);
        runnable = () -> {
            statusTxt.setText("");
            statusTxt2.setText("");
        };
        handler.postDelayed(runnable, 2500);

    }//newGame method end!


    //========Private Method=========//
    private void winningCells(int[][] winningCells) {
        for (int[] cell : winningCells) {
            int row = cell[0];
            int col = cell[1];
            animateBtn(buttons[row][col]);
        }

    }//winningCells method end!


    //========Private Method=========//
    private void animateBtn(AppCompatButton button) {
        fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(500);
        fadeIn.setRepeatMode(Animation.REVERSE);
        fadeIn.setRepeatCount(3);
        button.startAnimation(fadeIn);
    }//animateBtn method end!

    //========public Method=========//
    public void isWinSet(int score, String whoWin) {
        if (score > 2) {
            runnable = () -> {
                Toast.makeText(this, whoWin + " win the set!", Toast.LENGTH_SHORT).show();
                first_score = 0;
                second_score = 0;
                player1_score.setText("0");
                player2_score.setText("0");
            };
            handler.postDelayed(runnable, 1000);
        }//===Condition end===

    }//isWinSet method end!


    //========public Method=========//
    public int setRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }//setRandomColor method end!


    //========Public Method=========//
    public void animStart() {
        player1Img_card_1.startAnimation(slideInLeft);
        player2Img_card_2.startAnimation(slideInRight);
        player1tv.startAnimation(slideInLeft);
        player1_score.startAnimation(slideInLeft);
        player2tv.startAnimation(slideInRight);
        player2_score.startAnimation(slideInRight);
        buttons[0][0].startAnimation(slideInLeft);
        buttons[0][1].startAnimation(slideInTop);
        buttons[0][2].startAnimation(slideInRight);
        buttons[1][0].startAnimation(slideInLeft);
//        buttons[1][1].startAnimation();
        buttons[1][2].startAnimation(slideInRight);
        buttons[2][0].startAnimation(slideInLeft);
        buttons[2][1].startAnimation(slideInBottom);
        buttons[2][2].startAnimation(slideInRight);
        restart.startAnimation(slideInLeft);

    }//animStart method end!


    //========Public Method=========//
    public void clockSystem(boolean isPlayer1turn, MediaPlayer soundName) {
        if (isPlayer1turn) {
            human_clock_card.setVisibility(View.VISIBLE);
            human_clock_anim.setVisibility(View.VISIBLE);
            human_clock_anim.resumeAnimation();
            clockSound.start();
            clockSound.setLooping(true);
        } else {
            human_clock_anim.pauseAnimation();
            //===Condition start====
            if(clockSound.isPlaying()){
                clockSound.pause();
            }//===Condition end===
        }
    }//soundEffects method end!

    //========Public Method=========//
    public void clockVisibilityGone() {
        human_clock_card.setVisibility(View.GONE);
        human_clock_anim.setVisibility(View.GONE);
        clockSound.pause();
        clock_effects.pause();
    }//visible method end!


}//Main