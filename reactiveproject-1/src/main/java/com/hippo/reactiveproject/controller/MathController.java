package com.hippo.reactiveproject.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hippo.reactiveproject.models.CustomResponse;
import com.hippo.reactiveproject.service.MathService;
import lombok.RequiredArgsConstructor;




@RestController
@RequestMapping("math")
@RequiredArgsConstructor
public class MathController {
    private final MathService mathService;

    @GetMapping("square/{input}")
    public CustomResponse getSquareValue(@PathVariable Integer input) {
        return mathService.findSqure(input);
    }

    @GetMapping("table/{input}")
    public List<CustomResponse> getMultiplicationTable(@PathVariable Integer input) {
        return mathService.multiplicationTable(input);
    }
    
}
