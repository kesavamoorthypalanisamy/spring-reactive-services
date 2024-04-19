package io.javabrains.reactiveworkshop;

public class Exercise1 {

    public static void main(String[] args) {

        // Use StreamSources.intNumbersStream() and StreamSources.userStream()

        // Print all numbers in the intNumbersStream stream
        System.out.println("1. Print all numbers in the intNumbersStream stream");
        StreamSources.intNumbersStream().forEach(e -> System.out.println(e));
        System.out.println("--------------------------------------------------------- \n");

        // Print numbers from intNumbersStream that are less than 5
        System.out.println("2. Print numbers from intNumbersStream that are less than 5");
        StreamSources.intNumbersStream().filter(n -> n<5).forEach(System.out::println);
        System.out.println("--------------------------------------------------------- \n");

        // Print the second and third numbers in intNumbersStream that's greater than 5
        System.out.println(
                "3. Print the second and third numbers in intNumbersStream that's greater than 5");
        StreamSources.intNumbersStream().filter(n -> n > 5)
                    .skip(1).limit(2).forEach(System.out::println);
        System.out.println("--------------------------------------------------------- \n");

        //  Print the first number in intNumbersStream that's greater than 5.
        //  If nothing is found, print -1
        System.out.println(
                "4. Print the first number in intNumbersStream that's greater than 5.,If nothing is found, print -1");
        System.out.println(StreamSources.intNumbersStream().filter(n -> n > 5).findFirst().orElse(-1));
        System.out.println("--------------------------------------------------------- \n");

        // Print first names of all users in userStream
        System.out.println("5. Print first names of all users in userStream");
        // StreamSources.userStream().forEach(u -> System.out.println(u.getFirstName()));
        StreamSources.userStream().map(u -> u.getFirstName()).forEach(System.out::println);
        System.out.println("--------------------------------------------------------- \n");

        // Print first names in userStream for users that have IDs from number stream
        System.out.println(
                "6. Print first names in userStream for users that have IDs from number stream");
        /*List<Integer> ids = StreamSources.intNumbersStream().toList();
        StreamSources.userStream().filter(u -> ids.contains(u.getId()))
        .forEach(u -> System.out.println(u.getFirstName())); */

        StreamSources.intNumbersStream().flatMap(id -> StreamSources.userStream()
                .filter(user -> user.getId() == id)).map(u -> u.getFirstName()).forEach(System.out::println);
        
        /* StreamSources.userStream()
                        .filter(user -> StreamSources.intNumbersStream().anyMatch(i -> i == user.getId()))
                        .map( user -> user.getFirstName()).forEach(System.out::println); */    
        System.out.println("--------------------------------------------------------- \n");
    }

}
