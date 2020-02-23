package com.adityamhatre.push.kafka;

import com.adityamhatre.push.firebase.FirebaseClient;
import dto.SongDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.adityamhatre.push.kafka.Topics.TopicConstants.ON_FOUND_SONG_LINK;
import static com.adityamhatre.push.kafka.Topics.TopicConstants.ON_RECEIVE_NEW_SONG;
import static com.adityamhatre.push.kafka.Topics.TopicConstants.ON_USER_FETCHED_ALL_SONGS;
import static com.adityamhatre.push.kafka.Topics.TopicConstants.ON_USER_FETCHED_NEW_SONGS;

@Component
@Slf4j
public class KafkaConsumer {
	private final FirebaseClient firebaseClient;

	public KafkaConsumer(FirebaseClient firebaseClient) {
		this.firebaseClient = firebaseClient;
	}

	@KafkaListener(topics = ON_USER_FETCHED_ALL_SONGS, groupId = "shazam.db", containerFactory = "kafkaListenerContainerFactory")
	void fetchedAllSongs(ConsumerRecord<String, SongDTO> record) {
		log.info("Got new value on \"{}\" channel", ON_USER_FETCHED_ALL_SONGS);
		String fcmToken = record.value().getShazamedBy().getDeviceFcmToken();
		firebaseClient.sendMessageTo(fcmToken, "Got links of all the songs previously shazamed by you");

	}

	@KafkaListener(topics = ON_FOUND_SONG_LINK, groupId = "shazam.db", containerFactory = "kafkaListenerContainerFactory")
	void fetchedNewSongs(ConsumerRecord<String, SongDTO> record) {
		log.info("Got new value on \"{}\" channel", ON_FOUND_SONG_LINK);
		String fcmToken = record.value().getShazamedBy().getDeviceFcmToken();
		firebaseClient.sendMessageTo(fcmToken, String.format("Got link of the song \"%s\" for you", record.value().getSongName()));
	}

}
