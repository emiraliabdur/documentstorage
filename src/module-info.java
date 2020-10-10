module com.documentstorage {
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires spring.context;
    requires reactor.netty;
    requires spring.webflux;
    requires spring.core;
    requires spring.data.commons;
    requires org.reactivestreams;
    requires spring.web;
    requires spring.data.mongodb;
    requires spring.beans;
    requires mongodb.driver.reactivestreams;
    requires lombok;
    requires org.apache.commons.text;
    requires slf4j.api;
    requires io.netty.handler;
    requires io.netty.transport;
    requires io.netty.transport.epoll;
    requires reactor.core;
}