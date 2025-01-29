package com.nexus.nexusplugin.models;

import java.util.UUID;

public class TpaRequest {
    private final UUID sender;
    private final long timestamp;

    public TpaRequest(UUID sender) {
        this.sender = sender;
        this.timestamp = System.currentTimeMillis();
    }

    public UUID getSender() {
        return sender;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - timestamp > 60000;
    }
} 