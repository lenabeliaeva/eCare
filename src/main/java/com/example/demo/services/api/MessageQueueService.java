package com.example.demo.services.api;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface MessageQueueService {
    void sendMessage(String message) throws IOException, TimeoutException;
}
