package com.sumonkmr.Tic_Tac_Toe_Game;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.sumonkmr.tic_tac_toe_game.R;

public class OneVsOne extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_one_vs_one);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Init();
        setBntListener();
        restart.setOnClickListener(v -> {
            newGame();
        });
        player2_pro_lottie.setImageResource(R.drawable.player_2);
        animStart();

    }//onCreate

    //========Private Method=========//
    protected void setBntListener() {
        first_score = 0;
        second_score = 0;
        //===Loop start===
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final int row = i;
                final int col = j;
                buttons[i][j].setOnClickListener(v -> {
                    count++;
                    //===Condition start====
                    if (player1Turn && buttons[row][col].getText().toString().isEmpty()) {
                        player1Sound.start();
                        buttons[row][col].setText("X");
                        player1Turn = false;
//                        clockSystem(false,player1Sound);
                    } else if (player2Turn && buttons[row][col].getText().toString().isEmpty()) {
                        buttons[row][col].setText("O");
                        player2Sound.start();
                        player1Turn = true;
//                        clockSystem(true,player2Sound);
                    }//===Condition end===

                    if (checkWin("X")) {
                        statusTxt.setText("Player 1 is ");
                        statusTxt2.setText("Win!");
                        statusTxt.startAnimation(slideInLeft);
                        statusTxt2.startAnimation(slideInRight);
                        winSound.start();
                        clockVisibilityGone();
                        player1_score.setText(String.valueOf(++first_score));
                        isWinSet(first_score,"Player 1 is");
                        runnable = this::newGame;
                        handler.postDelayed(runnable, 3000);

                    } else if (checkWin("O")) {
                        statusTxt.setText("Player 2 is ");
                        statusTxt2.setText("Win!");
                        statusTxt.startAnimation(slideInLeft);
                        statusTxt2.startAnimation(slideInRight);
                        winSound.start();
                        clockVisibilityGone();
                        player2_score.setText(String.valueOf(++second_score));
                        isWinSet(second_score,"Player 2 is");
                        runnable = this::newGame;
                        handler.postDelayed(runnable, 3000);
                        //===Condition start====
                    } else if (isBordFull()) {
                        statusTxt.setText("Game is ");
                        statusTxt2.setText("Draw!");
                        statusTxt.startAnimation(slideInLeft);
                        statusTxt2.startAnimation(slideInRight);
                        drawSound.start();
                        //===Condition start====
                        if (count > 8) {
                            runnable = this::newGame;
                            handler.postDelayed(runnable, 3000);
                        }//===Condition end===

                    }
                });
            }
        }//===Loop end!===

    }//setBntListener method end!


}//OneVsOne