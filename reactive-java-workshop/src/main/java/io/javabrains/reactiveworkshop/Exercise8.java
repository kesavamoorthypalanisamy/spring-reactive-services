package io.javabrains.reactiveworkshop;

import java.io.IOException;
import reactor.core.publisher.Flux;

public class Exercise8 {


    public static void main(String[] args) throws IOException {

        // Use ReactiveSources.intNumbersFluxWithException()

        // Print values from intNumbersFluxWithException and print a message when error happens
        // ReactiveSources.intNumbersFluxWithException().subscribe(
        //         number -> System.out.println(number), err -> System.out.println("error happened"));
        // ReactiveSources.intNumbersFluxWithException()
        //         .doOnError(e -> System.out.println(e.getMessage())) // I want to make some action if any error happen instead of swallow the error like above impl
        //         .subscribe(number -> System.out.println(number));

        // Print values from intNumbersFluxWithException and continue on errors
        // ReactiveSources.intNumbersFluxWithException()
        //         .onErrorContinue((e, item) -> System.out.println("Error! " + e.getMessage())) //This will not allow error to terminate the flux
        //         .subscribe(number -> System.out.println(number));

        // Print values from intNumbersFluxWithException and when errors
        // happen, replace with a fallback sequence of -1 and -2
        ReactiveSources.intNumbersFluxWithException()
                .onErrorResume(err -> Flux.just(-1,-2))
                .subscribe(number -> System.out.println(number));

        System.out.println("Press a key to end");
        System.in.read();
    }

}
