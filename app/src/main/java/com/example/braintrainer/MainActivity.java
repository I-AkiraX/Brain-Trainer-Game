package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView timer,score,question,gameOver;
    Button playAgainButton,restartButton,playButton;
    GridLayout gridLayout;
    ConstraintLayout gameLayout;
    RelativeLayout basicLayout;
    CountDownTimer countDownTimer,extraTime;
    Random random;
    final long defaultTime = 5;
    long time=defaultTime*1000,currentTime;
    int q1,q2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = (TextView) findViewById(R.id.timer);
        score = (TextView) findViewById(R.id.score);
        question = (TextView) findViewById(R.id.question);
        gameOver = (TextView) findViewById(R.id.gameOver);
        gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        playAgainButton = (Button) findViewById(R.id.playAgainButton);
        playButton = (Button) findViewById(R.id.playButton);
        restartButton = (Button) findViewById(R.id.restartButton);
        gameLayout = (ConstraintLayout) findViewById(R.id.gameLayout);
        basicLayout = (RelativeLayout) findViewById(R.id.basicLayout);
        random = new Random();
        initialiseUI();
        extraTime = new CountDownTimer(500,500) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                buttonEnable(true);
                gameOver.setAlpha(0);
                question.setText(questionCreate());
                startGameTimer();
            }
        };

    }

    public void onRestart(View v){
        initialiseUI();
    }

    public void onPlayAgain(View v){
        gameOver.setAlpha(0);
        time=defaultTime*1000;
        playAgainButton.setVisibility(View.GONE);
        question.setText(questionCreate());
        buttonEnable(true);
        timer.setText("00:05");
        startGameTimer();
        score.setText("0/0");
    }

    public void onPlay(View view){
        gameLayout.setVisibility(View.VISIBLE);
        basicLayout.setVisibility(View.GONE);
        question.setText(questionCreate());
        startGameTimer();
    }

    private void initialiseUI(){

        timer.setText("00:05");
        gameOver.setAlpha(0);
        time=defaultTime*1000;
        playAgainButton.setVisibility(View.GONE);
        buttonEnable(true);
        gameLayout.setVisibility(View.GONE);
        basicLayout.setVisibility(View.VISIBLE);
        score.setText("0/0");

    }

    public void onOption(View view){
        if(currentTime>=1) {
            countDownTimer.cancel();
            time = currentTime-1200;
            Log.i("Time",time+"");
            Button btn = (Button) view;
            gameOver.setAlpha(1);
            buttonEnable(false);
            if ((q1 + q2 + "").equals(btn.getText().toString())) {
                gameOver.setText("CORRECT!");
                scoreBoard(true);
            } else {
                gameOver.setText("WRONG!");
                scoreBoard(false);
            }
            extraTime.start();
        }
    }

    private void scoreBoard(Boolean bool){
        String getScore = score.getText().toString();
        String[] arrayOfScore = getScore.split("/");
        if(bool){
            arrayOfScore[0] = Integer.valueOf(Integer.parseInt(arrayOfScore[0])+1).toString();
            arrayOfScore[1] = Integer.valueOf(Integer.parseInt(arrayOfScore[1])+1).toString();
        }
        else {
            arrayOfScore[1] = Integer.valueOf(Integer.parseInt(arrayOfScore[1])+1).toString();
        }
        score.setText(arrayOfScore[0]+"/"+arrayOfScore[1]);
    }

    private String timeCorrection(long l){
        String x = l+"";
        if(x.length()==1)
            x="0"+x;
        return x;
    }

    private String questionCreate(){
        q1 = random.nextInt(20);
        q2 = random.nextInt(100);
        optionsAssign();
        return q1 + " + " + q2 + " = ?";
    }

    private void optionsAssign() {
        int ans;
        ans = q1 + q2;
        int pos = random.nextInt(4);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            Button btn = (Button) gridLayout.getChildAt(i);
            if (btn.getTag().toString().equals(String.valueOf(pos))) {
                btn.setText(ans + "");
            } else {
                int temp = random.nextInt(50);
                while (temp == ans) {
                    temp = random.nextInt(50);
                }
                btn.setText(temp + "");
            }
        }
    }

    private void buttonEnable(Boolean bool){
        if(bool){
            for(int i=0;i<gridLayout.getChildCount();i++){
                Button btn = (Button) gridLayout.getChildAt(i);
                btn.setEnabled(true);
            }
        }
        else {
            for(int i=0;i<gridLayout.getChildCount();i++){
                Button btn = (Button) gridLayout.getChildAt(i);
                btn.setEnabled(false);
            }
        }
    }
    
    private void startGameTimer(){

        countDownTimer = new CountDownTimer(time+1000,1000) {
            @Override
            public void onTick(long l) {
                currentTime = l;
                Log.i("Current time ",currentTime+"");
                l/=1000;
                timer.setText(timeCorrection(l/60) + ":" + timeCorrection(l%60));
            }

            @Override
            public void onFinish() {

                gameOver.setAlpha(1);
                gameOver.setText("GAME OVER");
                playAgainButton.setVisibility(View.VISIBLE);
                buttonEnable(false);

            }
        }.start();

    }

}



/*
package com.android.randomtester;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button btn;
    TextView txt;
    Random rand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button)findViewById(R.id.button);
        txt = (TextView)findViewById(R.id.textView);
        rand = new Random();
    }
    public void random(View v){
        String x = rand.nextInt(5) + "";
        txt.setText(x);
    }
}

 */