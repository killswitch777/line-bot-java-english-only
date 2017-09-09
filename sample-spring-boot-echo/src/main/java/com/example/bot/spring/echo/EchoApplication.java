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

	//@EventMapping
	//public void TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
		//System.out.println("event: " + event);
		//String text = event.getMessage().getText();
		//boolean detect = (text.getBytes().length == s1.length()) ? false : true;
		//if (detect) return new TextMessage("ENGLISH ONLY!!!\nENGLISH ONLY!!!\nENGLISH ONLY!!!");
		//else return new TextMessage();
		
	//}
	
	@EventMapping
	public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
		TextMessageContent message = event.getMessage();
		handleTextContent(event.getReplyToken(), event, message);
	}
	
	private void handleTextContent(String replyToken, Event event, TextMessageContent content) {
		String text = content.getText();
		this.replyText(replyToken, text);
	}
	
	@EventMapping
	public void handleDefaultMessageEvent(Event event) {
		System.out.println("event: " + event);
	}
}
