package com.wedsite.sale.controller;



import com.wedsite.sale.entity.UserEntity;
import com.wedsite.sale.service.UserService;
import com.wedsite.sale.service.dto.UserDTO;
import com.wedsite.sale.service.mapper.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * The type Account action controller.
 */
@Controller
public class AccountActionController {

    @Autowired
    private UserService userService;





    @Autowired
    private UserConverter userConverter;


    /**
     * "/home", "/" that is URL go to page Home
     *
     * @param model     the Model to progress add attribute for web
     * @param principal when user login have princical
     * @return pageHome string
     */
    @RequestMapping(value = {"/home", "/"})
    public String hello(Model model, Principal principal) {

        // After the user has logged in, the principal will have the principal
        UserEntity userEntity = userService.findByUserName(principal.getName());
        Long userid = userEntity.getUserId();
        return "pageHome";
    }

    /**
     * /login-->URL page Login
     *
     * @return page login
     */
    @RequestMapping(value = "/login")
    public String login(@RequestParam(required = false) String message, final Model model) {
        if (message != null && !message.isEmpty()) {
            if (message.equals("max_session")) {
                model.addAttribute("message", "This accout has been login from another device!");
            }
            if (message.equals("logout")) {
                model.addAttribute("message", "Logout!");
            }
            if (message.equals("error")) {
                model.addAttribute("message", "Login Failed! Please try again");
            }
        }
        return "user/pageLogin";
    }

    /**
     * /loginA -->URL Login Action
     * When User login success -> pageHome
     *
     * @return Page Home
     */
    @PostMapping(value = {"/loginA"})
    public String loginA() {

        return "redirect:/";
    }

    /**
     * Log out
     * If User Login fail --> page Login
     *
     * @return Page Login
     */
    @GetMapping("/logout")
    public String logout() {
        return "user/pageLogin";
    }

    /**
     * Page Registration
     * Go to the Page Registration
     *
     * @param model the model
     * @return PageRegistration string
     */
    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "user/pageRegistration";
    }

    /**
     * /registration Url--> Page Registration Action
     *
     * @param userDTO       the user dto
     * @param bindingResult //"BindingResult". This is Spring's object holding the result of validation and binding and containing the possible errors//“BindingResult”. Đây là đối tượng của Spring giữ kết quả của việc xác nhận và ràng buộc và chứa các lỗi có thể đã xảy ra
     * @param model         the model
     * @return pageLogin //when success --> pageLogin
     * @Valid: this is an Annotation in spring mvc used to bind the object or Parameters are bound to perform form validation.
     */
    @PostMapping("/registration")
    public String regisAction(@Valid @ModelAttribute("userDTO") UserDTO userDTO, BindingResult bindingResult, Model model) {
        //use jpa find email on database
        UserEntity email = userService.findByUserEmail(userDTO.getEmail());
        //Compare entered emails With email data
        if (null != email) {
            bindingResult.rejectValue("email", "error.email", "Email is already used please enter another email");//truong truyen vao+ ten + message
        }
        //use jpa find username on database
        UserEntity userName = userService.findByUserName(userDTO.getUserName());
        //Compare entered Username With Username data
        if (null != userName) {
            bindingResult.rejectValue("userName", "error.userName", "Username is already used ,please enter another Username");
        }
        //if the error sends a message validates ->pageRegistration
        if (bindingResult.hasErrors()) {
            return "user/pageRegistration";
        } else {
            //save User
            userService.saveUser(userConverter.convertUserEntity(userDTO));
            //Add message Succsess --> Attribute
            model.addAttribute("successMessage", "Successfully ^^ ");
            //come to pagaLogin
            return "user/pageLogin";
        }
    }

    @GetMapping("/**/{path:[^\\.]*}")
    public String forward() {
        return "forward:/block  ";
    }

    /**
     * Come to pageBlock
     *
     * @param model the model
     * @return pageBlock string
     */
    @RequestMapping(value = "/block")
    public String block(Model model) {
        return "user/pageBlock";
    }

}
