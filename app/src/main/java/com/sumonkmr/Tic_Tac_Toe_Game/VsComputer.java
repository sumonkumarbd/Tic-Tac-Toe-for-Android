package com.sumonkmr.Tic_Tac_Toe_Game;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.sumonkmr.tic_tac_toe_game.R;


public class VsComputer extends OneVsOne {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vs_computer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Init();
        setButtonListeners();
        player1tv.setText("   You");
        player2tv.setText("Robot");
        restart.setOnClickListener(v -> newGame());
        animStart();

    }//onCreate

    //========Private Method=========//
    private void setButtonListeners() {
        first_score = 0;
        second_score = 0;
        //=========Each loop start==========
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final int row = i;
                final int col = j;
                buttons[i][j].setOnClickListener(v -> {
                    if (player1Turn && buttons[row][col].getText().toString().isEmpty()) {
                        player1Sound.start();
                        buttons[row][col].setText("X");
                        player1Turn = false;
                        clockSystem(false);
                        computerMove();
                    }
                });
            }//=========Inner loop stop==========
        }//=========Outer & each loop stop==========

    }//setButtonListeners method end!


    //========protected Method=========//
    protected void computerMove() {
        //===Condition start====
        if (!player1Turn && !checkWin("X") && !isBordFull()) {
            int row, col;
            int randomValue = random.nextInt(6000 - 1000 + 1) + 1000;
            do {
                row = random.nextInt(3);
                col = random.nextInt(3);
            } while (!buttons[row][col].getText().toString().isEmpty() && count < 4);
            count++;
            runnable = () -> {
                robot_lottie.setVisibility(View.VISIBLE);
                Log.d("computerMove", "computerMove: "+count);
                //===Condition start====
                    if (count > 2 && !player1Turn && randomValue > 1001 && randomValue < 2000) {
                        aain.start();
                    } else if (count > 2 && !player1Turn && randomValue > 4001 && randomValue < 6000) {
                        humayora.start();
                    }//===Condition end===
            };
            handler.postDelayed(runnable, 500);


            handleComputerMove(buttons, row, col, randomValue);
        }//===Condition end===


        //===Condition start====
        if (checkWin("X")) {
            statusTxt.setText("Congratu");
            statusTxt2.setText("lations!");
            statusTxt.startAnimation(slideInLeft);
            statusTxt2.startAnimation(slideInRight);
            player1_score.setText(String.valueOf(++first_score));
            winSound.start();
            player1Turn = true;
           clockVisibilityGone();
            runnable = () -> happy_lottie.setVisibility(View.VISIBLE);
            handler.postDelayed(runnable, 500);
            isWinSet(first_score, "You are");
            runnable = () -> newGame();
            handler.postDelayed(runnable, 3000);
        } else if (isBordFull()) {
            statusTxt.setText("Game i");
            statusTxt2.setText("s Tie!");
            drawSound.start();
            clockVisibilityGone();
            statusTxt.startAnimation(slideInLeft);
            statusTxt2.startAnimation(slideInRight);
            happy_lottie.setVisibility(View.GONE);
            runnable = () -> newGame();
            handler.postDelayed(runnable, 3000);
        }

    }//computerMove method end!


    //========Private Method=========//
    private void handleComputerMove(AppCompatButton[][] button, int row, int col, int randomValue) {
        Log.d("randomValue", "handleComputerMove: " + randomValue);
        runnable = () -> {
            //===Condition start====
            if (!checkWin("X") || !isBordFull() || !player1Turn) {
                button[row][col].setText("O");
                robot_lottie.setVisibility(View.GONE);
                player2Sound.start();
                player1Turn = true;
                clockSystem(true);
            }//===Condition end===
            if (checkWin("O")) {
                statusTxt.setText("Robot i");
                statusTxt2.setText("s Win!");
                robot_clock_card.setVisibility(View.GONE);
                robot_clock_anim.setVisibility(View.GONE);
                human_clock_card.setVisibility(View.GONE);
                human_clock_anim.setVisibility(View.GONE);
                statusTxt.startAnimation(slideInLeft);
                statusTxt2.startAnimation(slideInRight);
                player2_score.setText(String.valueOf(++second_score));
                isWinSet(second_score, "Robot is");
                winSound.start();
                runnable = () -> newGame();
                handler.postDelayed(runnable, 3000);
            }
        };
        handler.postDelayed(runnable, randomValue);

    }//handleComputerMove method end!
}