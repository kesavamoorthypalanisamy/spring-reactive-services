package io.javabrains.reactiveworkshop;

import java.time.Duration;
import reactor.core.publisher.Flux;

public class PlayWithFlux {
public static void main(String[] args) throws InterruptedException {
    Flux.just(1, 2, 3).delayElements(Duration.ofSeconds(2)).subscribe(System.out::println);
    Thread.sleep(7000);
}
}
