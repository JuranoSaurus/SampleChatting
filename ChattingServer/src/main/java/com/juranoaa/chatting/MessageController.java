package com.juranoaa.chatting;

import com.juranoaa.chatting.rest.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

	/**
	 * New Message url
	 * @return
	 */
	@RequestMapping(value = "/receive",  method = RequestMethod.GET, headers="Accept=application/json")
	public Message receiveMessage() {
		Message message = new Message();
		message.setName("kim");
		message.setData("Hello my name is kim");
		logger.debug("/receive - " + message);
		return message;
	}

	/**
	 * Echo url
	 * @param message
	 * @return
	 */
	@RequestMapping(value = "/send",  method = RequestMethod.POST, headers="Accept=application/json")
	public Message sendMessage(@RequestBody Message message) {
		logger.debug("/send - " + message);
		return message;
	}

}