package com.adityamhatre.push.kafka;

import lombok.Getter;

enum Topics {
	ON_RECEIVE_NEW_USER(TopicConstants.ON_RECEIVE_NEW_USER, 5),
	ON_RECEIVE_NEW_SONG(TopicConstants.ON_RECEIVE_NEW_SONG, 5),
	ON_FOUND_SONG_LINK(TopicConstants.ON_FOUND_SONG_LINK, 1),
	ON_USER_FETCHED_ALL_SONGS(TopicConstants.ON_USER_FETCHED_ALL_SONGS, 1),
	ON_POLL_TIMER_TICK(TopicConstants.ON_POLL_TIMER_TICK, 5),
	ON_USER_FETCHED_NEW_SONGS(TopicConstants.ON_USER_FETCHED_NEW_SONGS, 1);

	@Getter
	private final String topicName;

	@Getter
	private final int numPartitions;

	Topics(String topicName, int numPartitions) {
		this.topicName = topicName;
		this.numPartitions = numPartitions;
	}

	static class TopicConstants {
		static final String ON_RECEIVE_NEW_USER = "on_receive_new_user";
		static final String ON_RECEIVE_NEW_SONG = "on_receive_new_song";
		static final String ON_FOUND_SONG_LINK = "on_found_song_link";
		static final String ON_USER_FETCHED_ALL_SONGS = "on_user_fetched_all_songs";
		static final String ON_POLL_TIMER_TICK = "on_poll_timer_tick";
		static final String ON_USER_FETCHED_NEW_SONGS = "on_user_fetched_new_songs";
	}

}
