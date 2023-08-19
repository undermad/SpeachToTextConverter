package com.example.speachtotext;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView writtenSpeech;
    ImageButton mic;
    ActivityResultLauncher<Intent> activityResultLauncherForSpeechTranslation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerActivityForSpeechTranslation();

        writtenSpeech = findViewById(R.id.textView);
        mic = findViewById(R.id.imageButton);

        mic.setOnClickListener(v -> {
            convertSpeechToText();
        });

    }

    public void convertSpeechToText() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        // define model
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        // define language that recognizer will work in
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        activityResultLauncherForSpeechTranslation.launch(intent);


    }

    public void registerActivityForSpeechTranslation() {

        activityResultLauncherForSpeechTranslation = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    int resultCode = result.getResultCode();
                    Intent data = result.getData();
                    if (resultCode == RESULT_OK && data != null) {
                        ArrayList<String> speakResult = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        writtenSpeech.setText(speakResult.get(0));
                    }
                });

    }


}