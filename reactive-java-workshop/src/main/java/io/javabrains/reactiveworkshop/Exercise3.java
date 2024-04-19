package io.javabrains.reactiveworkshop;

import java.io.IOException;
import java.util.List;

public class Exercise3 {

    public static void main(String[] args) throws IOException {

        // Use ReactiveSources.intNumbersFlux()

        // Get all numbers in the ReactiveSources.intNumbersFlux stream
        // into a List and print the list and its size
        List<Integer> numbers = ReactiveSources.intNumbersFlux()
                .log() //Log operator is helpful for logging flux
                .toStream()
                .toList();
        
        System.out.println(numbers);
        System.out.println(numbers.size());
    }

}
