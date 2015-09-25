package com.nshimiye.cqrs.reader.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;

import com.nshimiye.cqrs.reader.akka.AkkaFactory;
import com.nshimiye.cqrs.reader.akka.ReadWorker;
import com.nshimiye.reader.domain.Spending;

@RestController
public class SpendingRController {


    private ActorRef readWorker = AkkaFactory.getActorSystem()
                .actorOf(ReadWorker.createWorker(), "readWorker");

    @RequestMapping("api/expenses/read")
    public Spending recordExpense(
        @RequestParam(value="id") long id
        ) {

        System.out.println("read route called");
        System.out.println("Actor reference: " + readWorker.toString());
        System.out.println("Actor path: " + readWorker.path().toString());
        System.out.println("Actor path address: " + readWorker.path().address().toString());
        System.out.println("Actor path name: " + readWorker.path().name());
        ActorSelection readWorker1 = AkkaFactory.getActorSystem()
        		.actorSelection("akka://AKKASystem/user/readWorker");
        
        System.out.println("Actor reference: [] - " + readWorker1.toString());
        System.out.println("Actor path: [] - " + readWorker1.path());
        System.out.println("Actor path anchor: [] - " + readWorker1.anchor());
        System.out.println("Actor path name: [] - " + readWorker1.pathString());
        System.out.println("Actor path serial: [] - " + readWorker1.toSerializationFormat());
        
        // send a command to read the requested value
        readWorker.tell(new Long(id), ActorRef.noSender());
        
        // testing actorSelection
        readWorker1.tell(new Long(id), ActorRef.noSender());

        //this would usually be a push request because
        // this function would have to wait for a notification from "readWorker"
        // saying that it has finished writing the value.
        return ReadWorker.getValue();
    }
}