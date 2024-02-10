package com.example.iworks.domain.notification.controller;

import com.example.iworks.domain.notification.service.UserNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/notification-push")
public class NotificationPushController {

//    private ExecutorService executorService = Executors.newFixedThreadPool(10);
    private final UserNotificationService userNotificationService;

    @GetMapping("/test")
    public DeferredResult<String> test(@RequestHeader("Authorization") String token) {
        int userId = 101;

        long timeOutInMilliSec = 100000L;
        String timeOutResp = "Time Out.";

        DeferredResult<String> deferredResult = new DeferredResult<>(timeOutInMilliSec, timeOutResp);
        CompletableFuture.runAsync(()->{
            try {
                //Long polling task
                while (userNotificationService.getCountIsNotSent(userId) == 0){
                    log.info("Waiting for message...");
                    TimeUnit.SECONDS.sleep(10);
                }

                String message = userNotificationService.getOneMessage(userId);
                log.info("Message received : {}  ", message);
                deferredResult.setResult(message);

            }catch (Exception ex){
                deferredResult.setErrorResult(ex);
            }
        });
        return deferredResult;
    }

//    @PostMapping("/ask-message")
//    public DeferredResult<String> pushMessage(@RequestBody String message) {
//        DeferredResult<String> deferredResult = new DeferredResult<>();
//        executorService.execute(() -> {
//            try {
//                Thread.sleep(1000);
//                deferredResult.setResult(message);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        });
//        return deferredResult;
//    }

//    @PostMapping("/ask-message/non-async")
//    public String pushMessageNonAsync(@RequestBody String message) {
//        DeferredResult<String> deferredResult = new DeferredResult<>();
//        try {
//            Thread.sleep(100000);
//            deferredResult.setResult(message);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        return message;
//    }


}


