package com.nshimiye.akka;

import akka.actor.ActorRef;
import akka.actor.Props;

import com.nshimiye.messaging.Envelope;

/**
 * Akka systems have built-in Event Bus called "EventStream" 
 * @author mars
 * Design followed https://lostechies.com/jimmybogard/files/2012/08/image4.png
 */
public class AkkaInitializer {

	//only for subscription purposes
	private ActorRef denomalizer = AkkaFactory.getActorSystem()
	        .actorOf(Props.create(Denomalizer.class), "denomalizer");
	
	public void subscribeActors(){
    	// Subscribe the writing worker
		// AkkaFactory.getActorSystem().eventStream().subscribe(readWorker, ReadWorker.class);
    	
    	// Subscribe the reading worker
    	AkkaFactory.getActorSystem().eventStream().subscribe(denomalizer, Envelope.class);
    	
	}
}
