package com.example.quizapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView questionTextView;
    private RadioGroup answerRadioGroup;
    private RadioButton option1RadioButton;
    private RadioButton option2RadioButton;
    private RadioButton option3RadioButton;
    private RadioButton option4RadioButton;
    protected Button submitButton;

    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionTextView = findViewById(R.id.questionTextView);
        answerRadioGroup = findViewById(R.id.answerRadioGroup);
        option1RadioButton = findViewById(R.id.option1RadioButton);
        option2RadioButton = findViewById(R.id.option2RadioButton);
        option3RadioButton = findViewById(R.id.option3RadioButton);
        option4RadioButton = findViewById(R.id.option4RadioButton);
        submitButton = findViewById(R.id.submitButton);

        initializeQuestions();
        displayQuestion();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuestionIndex < questions.size()) {
                    checkAnswer();
                    currentQuestionIndex++;
                    if (currentQuestionIndex < questions.size()) {
                        displayQuestion();
                    } else {
                        showResult();  // Display the result when no more questions are left
                    }
                } else {
                    showResult();
                }
            }
        });
    }

    private void initializeQuestions() {
        questions = new ArrayList<>();
        questions.add(new Question("What is the capital of France?",
                new String[]{"London", "Berlin", "Paris", "Madrid"}, 2));
        questions.add(new Question("Which planet is known as the Red Planet?",
                new String[]{"Venus", "Mars", "Jupiter", "Saturn"}, 1));
        questions.add(new Question("What is 2 + 2?",
                new String[]{"3", "4", "5", "6"}, 1));
    }

    private void displayQuestion() {
        Question currentQuestion = questions.get(currentQuestionIndex);
        questionTextView.setText(currentQuestion.getText());
        option1RadioButton.setText(currentQuestion.getOptions()[0]);
        option2RadioButton.setText(currentQuestion.getOptions()[1]);
        option3RadioButton.setText(currentQuestion.getOptions()[2]);
        option4RadioButton.setText(currentQuestion.getOptions()[3]);
        answerRadioGroup.clearCheck();
    }

    private void checkAnswer() {
        int selectedAnswerIndex = answerRadioGroup.indexOfChild(findViewById(answerRadioGroup.getCheckedRadioButtonId()));

        if (selectedAnswerIndex == -1) {
            Toast.makeText(this, "Please select an answer.", Toast.LENGTH_SHORT).show();
            return; // Stop further processing if no option is selected
        }

        if (selectedAnswerIndex == questions.get(currentQuestionIndex).getCorrectAnswerIndex()) {
            score++;
        }
    }



    private void showResult() {
        String resultMessage = "Quiz completed! Your score: " + score + " out of " + questions.size();
        Toast.makeText(this, resultMessage, Toast.LENGTH_LONG).show();
    }

    private static class Question {
        private final String text;
        private final String[] options;
        private final int correctAnswerIndex;

        public Question(String text, String[] options, int correctAnswerIndex) {
            this.text = text;
            this.options = options;
            this.correctAnswerIndex = correctAnswerIndex;
        }

        public String getText() {
            return text;
        }

        public String[] getOptions() {
            return options;
        }

        public int getCorrectAnswerIndex() {
            return correctAnswerIndex;
        }
    }
}