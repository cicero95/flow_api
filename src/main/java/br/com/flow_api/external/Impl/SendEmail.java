package br.com.flow_api.external.Impl;

public interface SendEmail<T> {

    String sendEmail(T object);
    String checkStatus(T object);
}
