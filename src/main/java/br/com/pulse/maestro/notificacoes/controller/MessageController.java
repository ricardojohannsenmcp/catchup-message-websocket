package br.com.pulse.maestro.notificacoes.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {
	
	
	
	@MessageMapping("/news")
	@SendTo("/topic/news")
	public String  broadcastNews(@Payload String message) {
	  return message;
	}
	
	
	
	
	

}
