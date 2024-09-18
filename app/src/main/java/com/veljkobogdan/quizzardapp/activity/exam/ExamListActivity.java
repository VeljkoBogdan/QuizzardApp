package com.veljkobogdan.quizzardapp.activity.exam;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.adapter.ExamAdapter;
import com.veljkobogdan.quizzardapp.database.RoomDB;
import com.veljkobogdan.quizzardapp.entity.Exam;
import com.veljkobogdan.quizzardapp.helper.RedirectHelper;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ExamListActivity extends Activity {
    private ListView examListView;
    private ExamAdapter examAdapter;
    ImageButton image_add, image_back;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_list);

        examListView = findViewById(R.id.exam_list_view);
        image_add = findViewById(R.id.image_add);
        image_back = findViewById(R.id.image_back);
        title = findViewById(R.id.title);
        title.setText("Your Exams");

        image_add.setOnClickListener(v -> {
            RedirectHelper.toNewExamActivity(this, 0);
        });
        image_back.setOnClickListener(v -> {
            finish();
        });

        // Get the list of exams from the database or repository
        List<Exam> exams = RoomDB.getInstance(this).examDAO().getAll();

        examAdapter = new ExamAdapter(this, exams);
        examListView.setAdapter(examAdapter);
    }
}