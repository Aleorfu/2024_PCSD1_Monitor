package com.example.plugins

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import java.io.FileInputStream

object FirebaseAdmin {
    private val serviceAccount: FileInputStream = FileInputStream("src/main/resources/monitor-6dc6d-firebase-adminsdk-kret7-da36f77bf2.json");

    private val options: FirebaseOptions = FirebaseOptions.builder()
    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
    .setDatabaseUrl("https://monitor-6dc6d-default-rtdb.firebaseio.com")
    .build();

    fun init(): FirebaseApp = FirebaseApp.initializeApp(options);
}