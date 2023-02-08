package com.freemarker.controller;

import com.freemarker.dto.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @GetMapping("/index")
    public String index(Model model) {
        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setAge(26);
        user1.setEmail("123@qq.com");
        user1.setName("kkk");
        userList.add(user1);

        User user2 = new User();
        user2.setAge(27);
        user2.setEmail("321@qq.com");
        user2.setName("sss");
        userList.add(user2);

        model.addAttribute("userList", userList);
        return "index";
    }

    public static void main(String[] args) {
        String s = "您好，您有一条短信</p>";
        System.out.println(s.replace("<p>", "").replace("</p>", ""));

        System.out.println("RETRIAL_APPROVING".contains("RETRIAL"));
    }
}
