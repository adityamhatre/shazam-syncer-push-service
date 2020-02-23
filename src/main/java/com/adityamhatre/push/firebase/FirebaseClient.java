package com.adityamhatre.push.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Component
public class FirebaseClient {

	private FirebaseApp firebaseApp;

	@PostConstruct
	private void startFirebase() {
		try {
			InputStream serviceAccount = getClass().getResourceAsStream("/static/firebase-service-account.json");
			if (serviceAccount == null) {
				log.error("Cannot find firebase service account json file");
				return;
			}

			FirebaseOptions options = new FirebaseOptions.Builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
					.setDatabaseUrl("https://my-shazam-songs.firebaseio.com")
					.build();

			firebaseApp = FirebaseApp.initializeApp(options);


		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void sendMessageTo(String fcmToken, String messageString) {
		Message message = Message.builder()
				.putData("message", messageString)
				.setToken(fcmToken)
				.build();
		try {
			String response = FirebaseMessaging.getInstance(firebaseApp).send(message);
			if (response != null) {
				log.debug("Response from firebase: {}", response);
			}
		} catch (FirebaseMessagingException e) {
			e.printStackTrace();
		}
		log.info("Successfully sent message {} to {}", messageString, fcmToken);
	}

}
