package com.juranoaa.chatting.rest;


import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * Created by SungGeun on 2015-08-31.
 */
@Rest(rootUrl = "http://www.nornenjs.com/chat", converters = { MappingJackson2HttpMessageConverter.class })
public interface AARestProtocol {
    @Post("/send")
    Message echoProtocol(Message message);
}
