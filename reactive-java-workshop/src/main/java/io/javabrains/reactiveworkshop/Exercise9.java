package io.javabrains.reactiveworkshop;

import java.io.IOException;

public class Exercise9 {


    public static void main(String[] args) throws IOException {

        // Use ReactiveSources.intNumbersFlux()

        // Print size of intNumbersFlux after the last item returns
        // ReactiveSources.intNumbersFlux().count().subscribe(System.out::println);

        // Collect all items of intNumbersFlux into a single list (Mono<List<>> from flux) and print it
        // ReactiveSources.intNumbersFlux().collectList().subscribe(System.out::println);

        // Transform to a sequence of sums of adjacent two numbers (1+2,2+3,3+4,4+5..)
        ReactiveSources.intNumbersFlux().buffer(2).map(n -> n.get(0)+n.get(1)).subscribe(System.out::println);

        System.out.println("Press a key to end");
        System.in.read();
    }

}
