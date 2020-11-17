package mk.ukim.finki.emt.lab.web;


import mk.ukim.finki.emt.lab.model.Cart;
import mk.ukim.finki.emt.lab.model.Role;
import mk.ukim.finki.emt.lab.model.User;
import mk.ukim.finki.emt.lab.repository.RoleRepository;
import mk.ukim.finki.emt.lab.service.CartService;
import mk.ukim.finki.emt.lab.service.OrderService;
import mk.ukim.finki.emt.lab.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @PostConstruct
    public void loadData()
    {
        Role userRole = new Role();
        userRole.setName("ROLE_USER");

        roleRepository.save(userRole);

        User user = new User();
        user.setUsername("user");
        user.setPassword("user");
        user.setRole(userRole);
        userService.save(user);

        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");

        roleRepository.save(adminRole);

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setRole(adminRole);
        userService.save(admin);
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        if(!model.containsAttribute("userForm")) {
            model.addAttribute("userForm", new LoginDTO());
        }
        if(!model.containsAttribute("passwordConfirmError")){
            model.addAttribute("passwordConfirmError", "");
        }
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("userForm") LoginDTO userForm,BindingResult bindingResult,
                               Model model,RedirectAttributes attr) {
        attr.addFlashAttribute("passwordConfirmError","");
        if(bindingResult.hasErrors()){
            attr.addFlashAttribute("org.springframework.validation.BindingResult.userForm", bindingResult);
            attr.addFlashAttribute("userForm",userForm);
            return  "redirect:/registration";
        }
        if(!userForm.getPassword().equals(userForm.getConfirm_password())){
            attr.addFlashAttribute("passwordConfirmError","Password confirmation doesn't match password");
            return  "redirect:/registration";
        }
        userService.register(userForm);
        return "redirect:/products";
    }

    @GetMapping("/profilePage")
    public String userInfo(Model model) {

        Cart cart = cartService.findActiveByCurrentUser();
        //If there is at least one order
        if(cart != null)
            model.addAttribute("currentCartOrders", cart.getOrders());
        else
            model.addAttribute("currentCartOrders", null);

        if(!model.containsAttribute("newOrder")) {
            model.addAttribute("newOrder",new OrderIdDTO());
        }
        List<Cart> cartHistory = cartService.findNotActiveByCurrentUser();
        //If the user has made at least one transaction
        if(cartHistory.isEmpty())
            model.addAttribute("cartHistory",null);
        else
            model.addAttribute("cartHistory",cartHistory);
        return "profilePage";
    }

    @PostMapping(params = "update", path = "/profilePage")
    public String updateOrder(Model model, @Valid @ModelAttribute("newOrder")  OrderIdDTO newOrder,
                              BindingResult bindingResult, RedirectAttributes attr) {
        if(bindingResult.hasErrors()){
            attr.addFlashAttribute("org.springframework.validation.BindingResult.newOrder", bindingResult);
            attr.addFlashAttribute("newOrder",newOrder);
            return  "redirect:/profilePage";
        }
        orderService.update(newOrder.getOrder_id(),newOrder.getQuantity());
        return "redirect:/profilePage";
    }

    @PostMapping(params = "delete", path = "/profilePage")
    public String deleteOrder(Model model,@ModelAttribute OrderIdDTO newOrder) {
        orderService.delete(newOrder.getOrder_id());
        return "redirect:/profilePage";
    }

    @PostMapping(params = "checkout", path = "/profilePage")
    public String finishOrder(Model model) {
        return "redirect:/checkout";
    }

    @GetMapping({"/", "/welcome"})
    public String welcome(Model model) {
        return "redirect:/products";
    }
}