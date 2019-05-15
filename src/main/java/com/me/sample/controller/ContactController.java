package com.me.sample.controller;

import com.me.sample.model.Contact;
import com.me.sample.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class ContactController {

    @Autowired
    private ContactRepository contactRepo;

    @GetMapping("/show/")
    public String contact(Model model,
                          @RequestParam(defaultValue = "0" )int page ,
                          @RequestParam(defaultValue = "id")String sort,
                          @RequestParam(defaultValue = "ASC")String order){

        switch (order){
            case "ASC": model.addAttribute("data",contactRepo.findAll(PageRequest.of(page,4,Sort.by(Sort.Direction.DESC,sort))));
            break;

            case "DESC": model.addAttribute("data",contactRepo.findAll(PageRequest.of(page,4,Sort.by(Sort.Direction.ASC,sort))));
            break;
        }



//            model.addAttribute("data",contactRepo.findAll(PageRequest.of(page,4,Sort.by(Sort.Direction.DESC,sort))));

        model.addAttribute("currentPage",page);

        return "/index";
    }

    @GetMapping("/insert/")
    public String insert(){
        return "/insert";
    }

    @PostMapping("/insert/")
    public String insert(@Valid Contact c){

        contactRepo.save(c);

        return "redirect:/show/";
    }

    @GetMapping("/edit/")
    public String edit(@RequestParam int id,Model model){
        model.addAttribute("data",contactRepo.findById(id).orElse(new Contact()));
        return "/edit";
    }

    @PostMapping("/edit/")
    public String edit(@Valid Contact contact){
        contactRepo.save(contact);

        return "redirect:/show/";
    }

    @GetMapping("/delete/")
    public String delete(@RequestParam Integer id){

        contactRepo.deleteById(id);

        return "redirect:/show/";
    }



//    @GetMapping("/{id}")
//
//    public Contact findOne(Integer id){
//
//        return contactRepo.findById(id).orElse(new Contact());
//    }



}
