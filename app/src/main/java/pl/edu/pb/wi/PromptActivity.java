package pl.edu.pb.wi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PromptActivity extends AppCompatActivity {

    private boolean correctAnswer;
    Button showCorrectAnswerButton;
    TextView answerTextView;
    public static final String KEY_EXTRA_ANSWER_SHOWN = "pb.edu.pl.wi.quiz.answerShown";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt);

        answerTextView = findViewById(R.id.answer_text_view);
        correctAnswer = getIntent().getBooleanExtra(MainActivity.KEY_EXTRA_ANSWER,true);
        showCorrectAnswerButton = findViewById(R.id.show_correct_answer_button);
        showCorrectAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int answer = correctAnswer ? R.string.button_true : R.string.button_false;
                answerTextView.setText(answer);
                setAnswerWasShown(true);
            }
        });

    }
    public void setAnswerWasShown(boolean answerWasShown) {
       Intent resultIntent = new Intent();
       resultIntent.putExtra(KEY_EXTRA_ANSWER_SHOWN,answerWasShown);
       setResult(RESULT_OK,resultIntent);
    }
}