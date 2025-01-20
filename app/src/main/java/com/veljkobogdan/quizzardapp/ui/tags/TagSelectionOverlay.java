package com.veljkobogdan.quizzardapp.ui.tags;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.database.entities.Tag;
import com.veljkobogdan.quizzardapp.data.repository.TagRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TagSelectionOverlay extends View {
    static int resource = R.layout.common_tag_overlay;
    private Context context;
    private ViewGroup root;
    private View view;
    private List<Tag> tags = new ArrayList<>();
    private View saveButton;
    private LayoutInflater inflater;
    private TagRepository tagRepository;
    private HashSet<Tag> selectedTags = new HashSet<>();

    private final Observer<List<Tag>> tagObserver = tagList -> {
        if (tagList != null) {
            tags.clear();
            tags.addAll(tagList);
            showTags();
        }
    };

    public TagSelectionOverlay(Context context) {
        super(context);
        this.context = context;
        this.tagRepository = new TagRepository(context);
    }

    public void create(OnClickListener onClickListener) {
        this.root = (ViewGroup) ((Activity) context).findViewById(android.R.id.content);

        inflater = LayoutInflater.from(this.context);
        view = inflater.inflate(resource, null);

        EditText tagText = view.findViewById(R.id.tagEditText);
        tagText.setOnEditorActionListener((v, actionId, event) -> {
            String title = tagText.getText().toString().trim();
            if (!title.isEmpty()) {
                Tag tag = new Tag(title, "amog");
                tagRepository.insertTag(tag);
            }
            return true;
        });

        saveButton = this.view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> {
            // general on click listener
            if (onClickListener != null) {
                onClickListener.onClick(v);
            }

            // return tags
            if (onSaveListener != null) {
                onSaveListener.onSave(new ArrayList<>(selectedTags));
            }

            remove();
        });

        LiveData<List<Tag>> liveTags = tagRepository.getAllTags();
        liveTags.observeForever(tagObserver);

        root.addView(this.view);
    }

    private void showTags() {
        LinearLayout tagLayout = view.findViewById(R.id.tagLayout);
        tagLayout.removeAllViews();

        for (Tag tag : tags) {
            CheckBox checkBox = new CheckBox(context);
            checkBox.setText(tag.getName());
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    selectedTags.add(tag);
                } else {
                    selectedTags.remove(tag);
                }
            });

            tagLayout.addView(checkBox);
        }
    }

    public void remove() {
        root.removeView(this.view);
    }

    public interface OnSaveListener {
        void onSave(List<Tag> selectedTags);
    }

    private OnSaveListener onSaveListener;
    public void setOnSaveListener(OnSaveListener listener) {
        this.onSaveListener = listener;
    }
}
