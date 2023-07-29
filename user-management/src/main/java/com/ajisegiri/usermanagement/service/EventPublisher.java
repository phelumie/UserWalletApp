package com.ajisegiri.usermanagement.service;

public interface EventPublisher<T> {
    void publish(T event,String routingKey);
}
