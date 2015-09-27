package com.nshimiye.akka;

import com.nshimiye.messaging.Envelope;

import akka.actor.UntypedActor;

/**
 * Listens to events published by the writing actors and then read the newly
 * saved value from Data store (writing side) to Data View (reading side)
 * 
 * @author mars
 *
 */
public class Denomalizer extends UntypedActor {

	@Override
	public void onReceive(Object message) {
		if (message instanceof Envelope) {
			//
			System.out.println("[ Denomalizer ] received an envelop");
			
			System.out.println(((Envelope) message).toString());
			com.nshimiye.cqrs.writer.domain.Spending payload =
			(com.nshimiye.cqrs.writer.domain.Spending) ((Envelope) message).payload;
			
			//TODO read data from data store
			com.nshimiye.cqrs.writer.domain.Spending spending = 
					com.nshimiye.cqrs.writer.akka.Database.read(payload.getId());
			
			
			//TODO write this data into the data view
			com.nshimiye.cprs.reader.domain.Spending newSpending = 
					new com.nshimiye.cprs.reader.domain.Spending(
							spending.getId(), 
							spending.getName(), 
							spending.getAmount());
			
			com.nshimiye.cqrs.reader.akka.Database.write(newSpending);
			
			System.out.println("[ Denomalizer ] done writing to read db");

		} else {
			System.err.println("[ Denomalizer ] received unknown message"+message.toString());
			unhandled(message);
		}

	}
}
