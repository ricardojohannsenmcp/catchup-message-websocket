package br.com.pulse.maestro.notificacoes.service;

import java.util.HashSet;
import java.util.Set;

import javax.management.Notification;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import br.com.pulse.maestro.notificacoes.model.NotificationDTO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NotificationDispatcher {
	
    private final SimpMessagingTemplate template;
    
    private Set<String> listeners = new HashSet<>();
    
    
    public void add(String sessionId) {
        listeners.add(sessionId);
    }

    public void remove(String sessionId) {
        listeners.remove(sessionId);
    }
    
    
    public void dispatch() {
        for (String listener : listeners) {
        	
        	   SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
               headerAccessor.setSessionId(listener);
               headerAccessor.setLeaveMutable(true);
               
               
           
            template.convertAndSendToUser(listener,"/notification/item",new NotificationDTO("xxxx"),headerAccessor.getMessageHeaders());
        }
    }

    @EventListener
    public void sessionDisconnectionHandler(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        remove(sessionId);
    }



}
