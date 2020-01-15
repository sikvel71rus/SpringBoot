package com.example.sweater.Controller;
import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;


@Controller
public class MainController {
    @Autowired
    private MessageRepo messageRepo;

    @Value("${upload.path}")
    private String uploadpath;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }
    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Message> messages = messageRepo.findAll();
        if (filter != null && !filter.isEmpty()){
            messages = messageRepo.findByTag(filter);
        }else {
            messages = messageRepo.findAll();
        }
        model.addAttribute("messages", messages);
        model.addAttribute("filter",filter);
        return "main";
    }
    @PostMapping("/main")
    public String add(
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag, Map<String, Object> model) throws IOException {
        //Сохранили
        Message message = new Message(text, tag, user);
        if (file!=null &&!file.getOriginalFilename().isEmpty()){
            File uploadDir = new File(uploadpath);

           if(!uploadDir.exists()){
               uploadDir.mkdir();
           }
            String uuidFile = String.valueOf(UUID.randomUUID());
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadpath + "/"+resultFilename));
            message.setFilename(resultFilename);
        }
        messageRepo.save(message);
        //Конец сохранения
        //Взяли из репозитория, положили в модель и отдали пользователю
        Iterable<Message> messages = messageRepo.findAll();
        model.put("messages", messages);
        return "main";
    }
    @PostMapping("/filter")
    public String filter(@RequestParam String filter, Map<String, Object> model){
        Iterable<Message> messages;
        if (filter != null && !filter.isEmpty()){
            messages = messageRepo.findByTag(filter);
        }else {
            messages = messageRepo.findAll();
        }
        model.put("messages", messages);
        return "main";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam String text, Map<String, Object> model){
        Message message = messageRepo.findById(Integer.valueOf(text));
        if (message!= null){
            messageRepo.delete(message);
        }
        Iterable<Message> messages = messageRepo.findAll();
        model.put("messages", messages);
        return "main";

    }
}
