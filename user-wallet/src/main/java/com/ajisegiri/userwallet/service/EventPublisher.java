package com.ajisegiri.userwallet.service;

public interface EventPublisher<T> {
    void publish(T event,String routingKey);
}
