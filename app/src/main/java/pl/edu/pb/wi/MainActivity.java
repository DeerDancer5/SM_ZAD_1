package pl.edu.pb.wi;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button promptButton;
    private TextView questionTextView;
    private Question[] questions = new Question[] {
            new Question(R.string.q_city,true),
            new Question(R.string.q_guitarist,false),
            new Question(R.string.q_noel,false),
            new Question(R.string.q_single,true),
            new Question(R.string.q_studio,false)
    };

    private int currentIndex = 0;
    private int correctAnswers = 0;

    private static final String KEY_CURRENT_INDEX = "currentIndex";
    public static final String KEY_EXTRA_ANSWER= "pl.edu.wi.quiz.correctAnswer";
    public static final int REQUEST_CODE_PROMPT = 0;
    boolean answerWasShown;

    private void checkAnswerCorrectness(boolean userAnswer) {
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId = 0;
        if(answerWasShown) {
            resultMessageId = R.string.answer_was_shown;
        }
        else {
            if (userAnswer == correctAnswer) {
                resultMessageId = R.string.correct_answer;
                correctAnswers++;
            } else {
                resultMessageId = R.string.incorrect_answer;
            }
        }
        Toast.makeText(this,resultMessageId,Toast.LENGTH_SHORT).show();
    }

    private void setNextQuestion() {
        trueButton.setEnabled(true);
        falseButton.setEnabled(true);
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }

    public void showScore() {
        currentIndex++;
        questionTextView.setText("Twój wynik to: " + correctAnswers +"/"+questions.length);
        nextButton.setText("Spróbuj ponownie");
        trueButton.setVisibility(View.INVISIBLE);
        falseButton.setVisibility(View.INVISIBLE);
        promptButton.setVisibility(View.INVISIBLE);
    }

    public void startAgain() {
        trueButton.setVisibility(View.VISIBLE);
        falseButton.setVisibility(View.VISIBLE);
        promptButton.setVisibility(View.VISIBLE);
        nextButton.setText(R.string.button_next);
        currentIndex = 0;
        correctAnswers = 0;
        setNextQuestion();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("[state change]","onCreate");
        setContentView(R.layout.activity_main);

        if(savedInstanceState !=null) {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        questionTextView = findViewById(R.id.question_text_view);
        promptButton = findViewById(R.id.hint_button);

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswerCorrectness(true);
                trueButton.setEnabled(false);
                falseButton.setEnabled(false);
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswerCorrectness(false);
                trueButton.setEnabled(false);
                falseButton.setEnabled(false);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    answerWasShown = false;
                    if(currentIndex < questions.length-1) {
                        currentIndex = (currentIndex + 1) % questions.length;
                        setNextQuestion();
                    }

                    else if(currentIndex == questions.length) {
                        startAgain();
                    }

                    else {
                        showScore();
                    }
            }
        });

        promptButton.setOnClickListener((view) -> {
            Intent intent = new Intent(MainActivity.this, PromptActivity.class);
            boolean correctAnswer = questions[currentIndex].isTrueAnswer();
            intent.putExtra(KEY_EXTRA_ANSWER,correctAnswer);
            //startActivity(intent);
            startActivityForResult(intent,REQUEST_CODE_PROMPT);
        });


       setNextQuestion();
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("[state change]","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("[state change]","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("[state change]","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("[state change]","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("[state change]","onDestroy");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("[state change]","onSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX,currentIndex);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_OK) {
            return;
        }
        if(requestCode == REQUEST_CODE_PROMPT) {
           if(data == null) {
               return;
           }
           answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN,false);
        }
    }
}