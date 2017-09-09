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

@SpringBootApplication
@LineMessageHandler
public class EchoApplication {
	public static void main(String[] args) {
		SpringApplication.run(EchoApplication.class, args);
	}

	@EventMapping
	public TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
		//System.out.println("event: " + event);
		//String text = event.getMessage().getText();
		//boolean detect = (text.getBytes().length == s1.length()) ? false : true;
		//if (detect) return new TextMessage("ENGLISH ONLY!!!\nENGLISH ONLY!!!\nENGLISH ONLY!!!");
		//else return new TextMessage();
		
		TextMessage textMessage = new TextMessage("hello");
		ReplyMessage replyMessage = new ReplyMessage(event.getReplyToken(),textMessage);
		Response<BotApiResponse> response = LineMessagingServiceBuilder
			.create("5/C98qjdhrqyoM0gW+rHCLlX9GdkZPHw4DdRncIgC7lA4ncP+ZXfN3/SyKg1HVnaSNq0e4yJFwCE5KKpwqdJDo9HhONmE8yGSM83XSQfKjv2CakjV9JxBg5Sc+j3lRC2usqOa69IKI8eWexH5c9JTAdB04t89/1O/w1cDnyilFU=")
			.build()
			.replyMessage(replyMessage)
			.execute();
		System.out.println(response.code() + " " + response.message());
	}

	@EventMapping
	public void handleDefaultMessageEvent(Event event) {
		System.out.println("event: " + event);
	}
}
