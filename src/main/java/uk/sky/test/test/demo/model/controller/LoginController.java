package uk.sky.test.test.demo.model.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.sky.test.test.demo.model.service.UserService;
import uk.sky.test.test.demo.model.User;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
    //the MVC (model view controller) logic is defined here!
@Controller
public class LoginController {

    @Autowired
    private UserService userService;
        //defining the model view controller for login page.
    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value={"/registration"}, method = RequestMethod.POST)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;

    }
        //defining the model view controller for registration page
    @RequestMapping(value={"/registration"}, method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if(userExists != null){
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is a user that already exists!");
        }
        if(bindingResult.hasErrors()){
            modelAndView.setViewName("registration");
        }
        else{
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been regsistered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");
        }
        return modelAndView;
    }

    @RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        modelAndView.addObject("UserName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("addminMessage", "Content Available for admins only");
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

}
