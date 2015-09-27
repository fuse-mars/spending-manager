package com.nshimiye.messaging;

public class Envelope {
	public final String topic;
	public final Object payload;

	public Envelope(String topic, Object payload) {
		this.topic = topic;
		this.payload = payload;
	}
	
	@Override
	public String toString() {
		return String.format("Envelope[topic= %s, payload= %s]", topic, payload);
	}
}