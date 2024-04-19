package com.hippo.reactiveproject.service;

import java.util.List;
import java.util.stream.IntStream;
import org.springframework.stereotype.Service;
import com.hippo.reactiveproject.models.CustomResponse;
import com.hippo.reactiveproject.utils.SleepUtil;

@Service
public class MathService {
    public CustomResponse findSqure(Integer value) {
        return new CustomResponse(value * value);
    }

    public List<CustomResponse> multiplicationTable(Integer input) {
        return IntStream.range(0, 10).peek(i -> SleepUtil.sleepInSeconds(1))
                .peek(i -> System.out.println("MathService processing: " + i))
                .mapToObj(i -> new CustomResponse(i * input)).toList();
    }

}
