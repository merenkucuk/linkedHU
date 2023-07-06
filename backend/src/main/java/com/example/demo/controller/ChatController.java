package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.demo.model.Chat;
import com.example.demo.repository.ChatRepository;
import com.example.demo.viewmodel.CreateChat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/V1/")
public class ChatController {

    @Autowired
    private ChatRepository chatRepository;

    // get read all chaats
    @GetMapping("/chats")
    public List<Chat> getAllChat() {

        return chatRepository.findAll();
    }

    // create chat
    @PostMapping("/chats")
    public Chat createChat(@RequestBody CreateChat createChat) {
        Chat chat = new Chat(
                createChat.getSenderId(),
                createChat.getReceiverId(),
                createChat.getMessage());

        return chatRepository.save(chat);

    }

    //get chat by userıd (sender)
    @GetMapping("/chats/{userid}")
    public List<List<Chat>> getChatBySenderId(@PathVariable Long userid){
        List<Chat> allChats = chatRepository.findAll();
        List<Chat> userChats= new ArrayList<>();
        List<Long> users=new ArrayList<>();
        for (Chat chat : allChats) {
            if(chat.getSenderId()==userid || chat.getReceiverId()==userid){
                if(!users.contains(chat.getReceiverId())){
                    users.add(chat.getReceiverId());
                }
                if(!users.contains(chat.getSenderId())){
                    users.add(chat.getSenderId());
                }
                userChats.add(chat);
            }
        }        
        users.remove(userid);
        List<List<Chat>> groupedChat= new ArrayList<>();
        Collections.sort(users);
        for (Long long1 : users) {
            List<Chat> chats= new ArrayList<>();
            chats.add(new Chat(long1, long1, long1+" idli kullanıcı ile olan mesajlar"));
            groupedChat.add(chats);
        }
        
        for (Chat chat : userChats) {
            for (int i=0; i<users.size() ;i++) {
                if(chat.getSenderId()==users.get(i) || chat.getReceiverId()==users.get(i)){
                    groupedChat.get(i).add(chat);
                }
            }
        }

        for(int j=0;j<groupedChat.size();j++){
            groupedChat.get(j).sort((o1, o2) -> o1.getDate().compareTo(o2.getDate()));
        }

        List<Chat> birinci= groupedChat.get(0);
        return groupedChat;
    }


}
