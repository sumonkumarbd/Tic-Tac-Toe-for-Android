package com.sumonkmr.Tic_Tac_Toe_Game;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.sumonkmr.tic_tac_toe_game.R;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    AppCompatButton oneVsOne_btn, computer_btn, restart;
    AppCompatButton[][] buttons;
    TextView statusTxt,statusTxt2, player1_score, player2_score, player1tv, player2tv;
    boolean player1Turn = true;
    boolean player2Turn = true;
    Random random = new Random();
    Handler handler = new Handler();
    Runnable runnable;
    int first_score, second_score;
    int count = 0;
    MediaPlayer player1Sound, player2Sound, winSound, drawSound, newGameSound;
    AlphaAnimation fadeIn;
    Animation slideInLeft,slideInRight,slideInTop,slideInBottom;
    LottieAnimationView robot_lottie,happy_lottie;


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
        robot_lottie = findViewById(R.id.robot_lottie);
        happy_lottie = findViewById(R.id.happy_lottie);

        player1Sound = MediaPlayer.create(this, R.raw.player1);
        player2Sound = MediaPlayer.create(this, R.raw.player2);
        winSound = MediaPlayer.create(this, R.raw.win);
        drawSound = MediaPlayer.create(this, R.raw.draw);
        newGameSound = MediaPlayer.create(this, R.raw.new_game);

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
        robot_lottie.setVisibility(View.GONE);
        happy_lottie.setVisibility(View.GONE);
        buttons[0][0].setText("");
        buttons[0][1].setText("");
        buttons[0][2].setText("");
        buttons[1][0].setText("");
        buttons[1][1].setText("");
        buttons[1][2].setText("");
        buttons[2][0].setText("");
        buttons[2][1].setText("");
        buttons[2][2].setText("");
        count = 0;
        statusTxt.setText("New Game ");
        statusTxt2.setText("Start!");
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

}//Main