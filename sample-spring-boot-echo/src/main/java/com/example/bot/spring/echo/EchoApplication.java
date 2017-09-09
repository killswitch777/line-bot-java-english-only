/*
 * Copyright 2016 LINE Corporation
 *
 * LINE Corporation licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.example.bot.spring.echo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import com.linecorp.bot.model.ReplyMessage;
import java.util.Collections;
import com.linecorp.bot.client.LineMessagingClient;
import lombok.NonNull;






import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;




@SpringBootApplication
@LineMessageHandler
public class EchoApplication {
	@Autowired
	private LineMessagingClient lineMessagingClient;
	
	public static void main(String[] args) {
		SpringApplication.run(EchoApplication.class, args);
	}

	//@EventMapping
	//public void TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
		//System.out.println("event: " + event);
		//String text = event.getMessage().getText();
		//boolean detect = (text.getBytes().length == s1.length()) ? false : true;
		//if (detect) return new TextMessage("ENGLISH ONLY!!!\nENGLISH ONLY!!!\nENGLISH ONLY!!!");
		//else return new TextMessage();
		
	//}
	
	@EventMapping
	public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {
		TextMessageContent message = event.getMessage();
		handleTextContent(event.getReplyToken(), event, message);
	}
	
	private void handleTextContent(String replyToken, Event event, TextMessageContent content)
		throws Exception {
		String text = content.getText();
		log.info("Got text message from {}: {}", replyToken, text);
		this.replyText(replyToken, text);
	}
	
	@EventMapping
	public void handleDefaultMessageEvent(Event event) {
		System.out.println("event: " + event);
	}
	
	private void replyText(@NonNull String replyToken, @NonNull String message) {
		if (replyToken.isEmpty()) {
			throw new IllegalArgumentException("replyToken must not be empty");
		}
		if (message.length() > 1000) {
			message = message.substring(0, 1000 - 2) + "......";
		}
		this.reply(replyToken, new TextMessage(message));
	}
	
	private void reply(@NonNull String replyToken, @NonNull Message message) {
		reply(replyToken, Collections.singletonList(message));
	}
	
	private void reply(@NonNull String replyToken, @NonNull List<Message> messages) {
		try {
			BotApiResponse apiResponse = lineMessagingClient
				.replyMessage(new ReplyMessage(replyToken, messages))
				.get();
			log.info("Sent messages: {}", apiResponse);
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}
}
