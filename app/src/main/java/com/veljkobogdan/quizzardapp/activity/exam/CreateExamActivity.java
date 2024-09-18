package com.veljkobogdan.quizzardapp.activity.exam;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.database.RoomDB;
import com.veljkobogdan.quizzardapp.entity.Exam;

public class CreateExamActivity extends AppCompatActivity {
    private EditText examNameEditText;
    ImageButton image_add, image_back;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exam);

        examNameEditText = findViewById(R.id.exam_name_edit_text);

        image_add = findViewById(R.id.image_add);
        image_back = findViewById(R.id.image_back);
        title = findViewById(R.id.title);
        title.setText("Create Exam");

        image_add.setOnClickListener(this::createExam);
        image_back.setOnClickListener(v -> finish());
    }

    public void createExam(View view) {
        String examName = examNameEditText.getText().toString();

        if (examName.isEmpty()) {
            Toast.makeText(this, "Exam name cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        Exam exam = new Exam();
        exam.setName(examName);

        RoomDB.getInstance(this).examDAO().insert(exam);

        Toast.makeText(this, "Exam created: " + examName, Toast.LENGTH_SHORT).show();
    }
}