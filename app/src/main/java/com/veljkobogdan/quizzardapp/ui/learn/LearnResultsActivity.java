package com.veljkobogdan.quizzardapp.ui.learn;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.models.LearnResult;
import com.veljkobogdan.quizzardapp.databinding.ActivityLearnResultsBinding;
import com.veljkobogdan.quizzardapp.ui.sets.FlashcardSetsActivity;
import com.veljkobogdan.quizzardapp.util.IntentGroup;

public class LearnResultsActivity extends AppCompatActivity {
    private ActivityLearnResultsBinding binding;
    private TextView correctStatus, percentageStatus;
    private Button backButton;
    private LearnResult result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityLearnResultsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        correctStatus = binding.correctStatus;
        percentageStatus = binding.percentageStatus;
        backButton = binding.backButton;

        getIntentContent();

        String correct = result.correctAmount + "/" + result.totalAmount + " correct";
        String percentage = result.percentage + "%";
        correctStatus.setText(correct);
        percentageStatus.setText(percentage);

        setTextColor();

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(LearnResultsActivity.this, FlashcardSetsActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void getIntentContent() {
        result = (LearnResult) getIntent().getSerializableExtra(IntentGroup.LEARN_RESULT);
    }

    private void setTextColor() {
        float value = result.percentage;
        value = Math.max(0, Math.min(value, 100));

        int green = Color.GREEN;
        int red = Color.RED;

        int color = (int) new ArgbEvaluator().evaluate(value / 100f, red, green);

        percentageStatus.setTextColor(color);
    }
}