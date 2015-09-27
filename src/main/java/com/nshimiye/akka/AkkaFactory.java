package com.nshimiye.akka;

import akka.actor.ActorSystem;

/**
 *
 */
public class AkkaFactory {

    private static ActorSystem system = null;

    public static ActorSystem getActorSystem() {

        if(system == null) {
            system = ActorSystem.create("AKKASystem");
        }

        return system;
	}
}