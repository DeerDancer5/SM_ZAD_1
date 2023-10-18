package pl.edu.pb.wi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
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

    private void checkAnswerCorrectness(boolean userAnswer) {
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId = 0;
        if(userAnswer == correctAnswer) {
            resultMessageId = R.string.correct_answer;
            correctAnswers++;
        }
        else {
            resultMessageId = R.string.incorrect_answer;
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
    }

    public void startAgain() {
        trueButton.setVisibility(View.VISIBLE);
        falseButton.setVisibility(View.VISIBLE);
        nextButton.setText(R.string.button_next);
        currentIndex = 0;
        correctAnswers = 0;
        setNextQuestion();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        questionTextView = findViewById(R.id.question_text_view);

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
       setNextQuestion();
    }
}