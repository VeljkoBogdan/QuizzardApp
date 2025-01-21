package com.veljkobogdan.quizzardapp.ui.tags;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.google.android.flexbox.FlexboxLayout;
import com.veljkobogdan.quizzardapp.R;
import com.veljkobogdan.quizzardapp.data.database.entities.Tag;
import com.veljkobogdan.quizzardapp.data.repository.TagRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@SuppressLint("ViewConstructor")
public class TagSelectionOverlay extends View {
    private static final int LAYOUT_RESOURCE = R.layout.common_tag_overlay;

    private final Context context;
    private final TagRepository tagRepository;
    private final List<Tag> tags = new ArrayList<>();
    private final HashSet<Tag> selectedTags = new HashSet<>();
    private final List<Tag> existingTags;

    private ViewGroup root;
    private View overlayView;
    private OnSaveListener onSaveListener;

    public TagSelectionOverlay(Context context, @Nullable List<Tag> existingTags) {
        super(context);
        this.context = context;
        this.tagRepository = new TagRepository(context);
        this.existingTags = existingTags != null ? existingTags : new ArrayList<>();
    }

    public void create(OnClickListener generalClickListener) {
        root = ((Activity) context).findViewById(android.R.id.content);
        overlayView = LayoutInflater.from(context).inflate(LAYOUT_RESOURCE, root, false);

        setupTagInput();
        setupSaveButton(generalClickListener);
        observeTags();

        root.addView(overlayView);
        overlayView.setAlpha(0f);
        overlayView.animate().alpha(1f).setDuration(300).start();
    }

    private void setupTagInput() {
        EditText tagInput = overlayView.findViewById(R.id.tagEditText);
        tagInput.setOnEditorActionListener((v, actionId, event) -> {
            String newTagName = tagInput.getText().toString().trim();
            if (!newTagName.isEmpty()) {
                tagRepository.insertTag(new Tag(newTagName, "amog"));
                tagInput.setText("");
            }
            return true;
        });
    }

    private void setupSaveButton(OnClickListener generalClickListener) {
        View saveButton = overlayView.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> {
            if (generalClickListener != null) {
                generalClickListener.onClick(v);
            }
            if (onSaveListener != null) {
                onSaveListener.onSave(new ArrayList<>(selectedTags));
            }
            remove();
        });
    }

    private void observeTags() {
        LiveData<List<Tag>> liveTags = tagRepository.getAllTags();
        liveTags.observeForever(new Observer<List<Tag>>() {
            @Override
            public void onChanged(List<Tag> tagList) {
                if (tagList != null) {
                    tags.clear();
                    tags.addAll(tagList);
                    populateTags();
                }
            }
        });
    }

    private void populateTags() {
        FlexboxLayout tagLayout = overlayView.findViewById(R.id.tagLayout);
        tagLayout.removeAllViews();

        for (Tag tag : tags) {
            CheckBox checkBox = createTagCheckBox(tag);
            tagLayout.addView(checkBox);
        }
    }

    private CheckBox createTagCheckBox(Tag tag) {
        CheckBox checkBox = new CheckBox(context);
        checkBox.setText(tag.getName());
        checkBox.setChecked(isTagExisting(tag));

        if (checkBox.isChecked()) {
            selectedTags.add(tag);
        }

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedTags.add(tag);
            } else {
                selectedTags.remove(tag);
            }
        });

        return checkBox;
    }

    private boolean isTagExisting(Tag tag) {
        for (Tag existingTag : existingTags) {
            if (Objects.equals(existingTag.getName(), tag.getName())) {
                return true;
            }
        }
        return false;
    }

    public void remove() {
        root.removeView(overlayView);
    }

    public void setOnSaveListener(OnSaveListener listener) {
        this.onSaveListener = listener;
    }

    public interface OnSaveListener {
        void onSave(List<Tag> selectedTags);
    }
}
