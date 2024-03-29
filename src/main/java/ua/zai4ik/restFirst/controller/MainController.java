package ua.zai4ik.restFirst.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@CrossOrigin(origins = {"http://localhost:3000", "http://fast-pupils.gl.at.ply.gg:12088"})
@RestController
public class MainController {

    HashMap<String, Integer> online = new HashMap<>() {{
        put("online", 0);
    }};

    ArrayList<HashMap<String, String>> onlineList = new ArrayList<>() {{
    }};

    ArrayList<HashMap<String, String>> chat = new ArrayList<>() {{
        add(new HashMap<>() {{
            put("id", "0");
            put("userName", "3ai4ik");
            put("message", "Hello, world!");
        }});
    }};

    ArrayList<HashMap<String, String>> chatMessage = new ArrayList<>() {{
        add(new HashMap<>() {{
            put("message", "Hello, world!");
        }});
    }};

//    @SendTo("/topic/online")
    @GetMapping("/api/online")
    public HashMap<String, Integer> getOnline() {
        return online;
    }

    @PostMapping("/api/online")
    public void setOnline(@RequestParam Integer online) {
        this.online.put("online", online);
        messagingTemplate.convertAndSend("/topic/online", this.online);
    }

    @GetMapping("/api/onlineList")
    public ArrayList<HashMap<String, String>> getOnlineList() {
        return onlineList;
    }

    @PostMapping("/api/addOnlineList")
    public void addOnlineList(@RequestParam String userName) {
        onlineList.add(new HashMap<>() {{
            put("userName", userName);
        }});
        messagingTemplate.convertAndSend("/topic/addOnlineList", onlineList);
    }

    @PostMapping("/api/removeOnlineList")
    public void removeOnlineList(@RequestParam String userName) {
        for (int i = 0; i < onlineList.size(); i++) {
            if (onlineList.get(i).containsValue(userName)) {
                onlineList.remove(i);
            }
        }
        messagingTemplate.convertAndSend("/topic/removeOnlineList", onlineList);
    }

//    @SendTo("/topic/chat")
    @GetMapping("/api/chat")
    public ArrayList<HashMap<String, String>> getChat() {
        return chat;
    }

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/api/chat")
    public void addMessage(@RequestParam String userName, @RequestParam String message) {
        if (chat.size() > 11) {
            chat.remove(0);
        }

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", String.valueOf(Integer.parseInt(chat.get(chat.size() - 1).get("id")) + 1));
        hashMap.put("userName", userName);
        hashMap.put("message", message);

        chat.add(hashMap);

        messagingTemplate.convertAndSend("/topic/chat", chat);
    }

    @PostMapping("api/chatMessage")
    public void setChatMessage(@RequestParam String message) {
        chatMessage.get(0).put("message", message);

        messagingTemplate.convertAndSend("/topic/chatMessage", message);
    }

}
