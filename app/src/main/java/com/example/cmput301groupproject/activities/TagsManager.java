package com.example.cmput301groupproject.activities;

import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TagsManager {

    private static final String TAG = "TagsManager";
    private static final String USERS_COLLECTION = "users";
    private static final String TAGS_FIELD = "tags";
    private static final String TAG_LIST_FIELD = "tagList";

    private FirebaseFirestore db;
    private String userId;

    public TagsManager(String userId) {
        this.userId = userId;
        db = FirebaseFirestore.getInstance();
    }

    public void getTags(final CallbackHandler callback) {
        DocumentReference userDocRef = getUserDocumentReference();

        userDocRef.get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        // Document exists, retrieve tags
                        Map<String, Object> userData = documentSnapshot.getData();
                        if (userData != null && userData.containsKey(TAGS_FIELD)) {
                            Map<String, Object> tagsData = (Map<String, Object>) userData.get(TAGS_FIELD);
                            if (tagsData != null && tagsData.containsKey(TAG_LIST_FIELD)) {
                                ArrayList<String> tags = (ArrayList<String>) tagsData.get(TAG_LIST_FIELD);
                                callback.onSuccess(tags);
                                return;
                            }
                        }
                    }
                    // If the document or tags field is missing, return an empty list
                    callback.onSuccess(new ArrayList<>());
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error fetching tags: " + e.getMessage());
                    callback.onFailure(e.getMessage());
                });
    }

    public void addTag(String tag, final CallbackHandler callback) {
        DocumentReference userDocRef = getUserDocumentReference();

        Map<String, Object> tagsData = new HashMap<>();
        tagsData.put(TAG_LIST_FIELD, new ArrayList<>()); // Initialize an empty tag list

        userDocRef.set(tagsData, SetOptions.merge()) // Set or update the tags field
                .addOnSuccessListener(aVoid -> {
                    // Add the new tag to the list
                    ArrayList<String> tagsList = (ArrayList<String>) tagsData.get(TAG_LIST_FIELD);
                    if (tagsList != null) {
                        tagsList.add(tag);
                        callback.onSuccess(tag);
                    } else {
                        callback.onFailure("Error adding tag");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error adding tag: " + e.getMessage());
                    callback.onFailure(e.getMessage());
                });
    }

    public void deleteTags(ArrayList<String> tagsToDelete, final CallbackHandler callback) {
        DocumentReference userDocRef = getUserDocumentReference();

        Map<String, Object> tagsData = new HashMap<>();
        tagsData.put(TAG_LIST_FIELD, tagsToDelete); // Use the list of tags to delete

        userDocRef.update(tagsData)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Tags deleted: " + tagsToDelete);
                    callback.onSuccess(tagsToDelete);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error deleting tags: " + e.getMessage());
                    callback.onFailure(e.getMessage());
                });
    }

    private DocumentReference getUserDocumentReference() {
        return db.collection(USERS_COLLECTION).document(userId);
    }

    public interface CallbackHandler<T> {
        void onSuccess(T result);

        void onFailure(String e);
    }
}

