package com.juranoaa.chatting.rest;

import com.juranoaa.chatting.common.Constants;

import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * Created by SungGeun on 2015-08-31.
 */
@Rest(rootUrl = Constants.Host.CONTEXT_PATH, converters = { MappingJackson2HttpMessageConverter.class })
public interface AARestProtocol {
    @Post(Constants.Host.SEND_URL)
    Message echoProtocol(Message message);
}
