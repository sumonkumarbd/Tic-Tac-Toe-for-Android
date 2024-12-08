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
        player1tv.setText("Your score : ");
        player2tv.setText(" : Computer score");
        restart.setOnClickListener(v -> {
            newGame();
        });


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
                        computerMove();
                    }
                });
            }//=========Inner loop stop==========
        }//=========Outer & each loop stop==========

    }//setButtonListeners method end!


    //========protected Method=========//
    protected void computerMove() {
        int row, col;
        do {
            row = random.nextInt(3);
            col = random.nextInt(3);
        } while (!buttons[row][col].getText().toString().isEmpty() && count < 4);
        count++;
        runnable = () -> robot_lottie.setVisibility(View.VISIBLE);
        handler.postDelayed(runnable, 500);
        handleComputerMove(buttons, row, col);


        //===Condition start====
       if (checkWin("X")) {
            statusTxt.setText("You are ");
            statusTxt2.setText("Win!");
            statusTxt.startAnimation(slideInLeft);
            statusTxt2.startAnimation(slideInRight);
            player1_score.setText(String.valueOf(++first_score));
            winSound.start();
            runnable = () -> newGame();
            player1Turn = true;
            handler.postDelayed(runnable, 4000);
        } else if (isBordFull()) {
           statusTxt.setText("Game is ");
           statusTxt2.setText("Draw!");
           drawSound.start();
           statusTxt.startAnimation(slideInLeft);
           statusTxt2.startAnimation(slideInRight);
            happy_lottie.setVisibility(View.GONE);
            runnable = () -> newGame();
            handler.postDelayed(runnable, 4000);
        }

    }//computerMove method end!


    //========Private Method=========//
    private void handleComputerMove(AppCompatButton[][] button, int row, int col){
        runnable = () -> {
            //===Condition start====
            if(!checkWin("X")){
                button[row][col].setText("O");
                robot_lottie.setVisibility(View.GONE);
                player2Sound.start();
                player1Turn = true;
            }//===Condition end===
            if (checkWin("O")) {
                statusTxt.setText("Computer is ");
                statusTxt2.setText("Win!");
                statusTxt.startAnimation(slideInLeft);
                statusTxt2.startAnimation(slideInRight);
                player2_score.setText(String.valueOf(++second_score));
                winSound.start();
                runnable = () -> happy_lottie.setVisibility(View.VISIBLE);
                handler.postDelayed(runnable, 500);
                runnable = () -> newGame();
                handler.postDelayed(runnable, 4000);
            }
        };
        int randomValue = random.nextInt(3000 - 1000 + 1) + 1000;
        handler.postDelayed(runnable,randomValue);
        Log.d("computerMoveTime", "handleComputerMove: "+randomValue);

    }//handleComputerMove method end!
}