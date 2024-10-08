package org.example.controller;

import org.example.service.FutureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RequestMapping("/future")
@RestController
public class FutureController {

    @Autowired
    private FutureService futureService;

    @PostMapping("/newFuture")
    public void newFuture() throws ExecutionException, InterruptedException {
        futureService.newFuture();
    }
}
