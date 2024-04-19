package io.javabrains.reactiveworkshop;

import java.io.IOException;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;

public class Exercise5 {

    public static void main(String[] args) throws IOException {

        // Use ReactiveSources.intNumberMono() and ReactiveSources.userMono()
        ReactiveSources.intNumberMono().subscribe(number -> System.out.println(number),
                err -> System.out.println(err.getMessage()), () -> System.out.println("completed"));
        

        // Subscribe to a flux using the error and completion hooks
        ReactiveSources.intNumbersFlux().subscribe(i -> System.out.println(i),
                err -> System.out.println(err.getMessage()), () -> System.out.println("completed"));
        
        ReactiveSources.userFlux().subscribe(number -> System.out.println(number),
                err -> System.out.println(err.getMessage()), () -> System.out.println("completed"));
        

        // Subscribe to a flux using an implementation of BaseSubscriber
        // ReactiveSources.intNumbersFlux().subscribe(i -> System.out.println(i),
        //         err -> System.out.println(err.getMessage()), () -> System.out.println("completed"), s -> {System.out.println("ïnside subscriber");});

        ReactiveSources.intNumbersFlux().subscribe(new MySubscriber<>());
        System.out.println("Press a key to end");
        System.in.read();
    }

}


class MySubscriber<T> extends BaseSubscriber<T> {
    public void hookOnSubscribe(Subscription subscription) {
        System.out.println("Subscribe happened");
        request(1); //Request for only "n" items when ready until I request for more.
    }

    public void hookOnNext(T value) {
        System.out.println(value.toString() + " received");
        request(1); //Reuest for "n" every time after process one when it is ready.
	}
    
}