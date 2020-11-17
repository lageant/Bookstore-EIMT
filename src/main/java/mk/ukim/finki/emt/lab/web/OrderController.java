package mk.ukim.finki.emt.lab.web;

import mk.ukim.finki.emt.lab.service.OrderService;
import mk.ukim.finki.emt.lab.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @PostMapping(params = "addOrder", path = "/products")
    public String addOrderFromProductList(@Valid @ModelAttribute("orderForm") OrderDTO orderForm,
                                          BindingResult bindingResult,
                                          Model model, RedirectAttributes attr) {
        if(bindingResult.hasErrors()){
            attr.addFlashAttribute("org.springframework.validation.BindingResult.orderForm", bindingResult);
            attr.addFlashAttribute("orderForm",orderForm);
            return  "redirect:/products";
        }
        try{
            orderService.save(orderForm);
        }
        catch (NullPointerException e) {
            System.out.println("An order on that product was already made");
        }
        return "redirect:/products";
    }

    @PostMapping(params = "addOrder", path = "/product/{id}")
    public String addOrderFromProductId(@Valid @ModelAttribute("orderForm") OrderDTO orderForm,
                                        BindingResult bindingResult,
                                        Model model, @PathVariable("id") Long id, RedirectAttributes attr) {
        if(bindingResult.hasErrors()){
            attr.addFlashAttribute("org.springframework.validation.BindingResult.orderForm", bindingResult);
            attr.addFlashAttribute("orderForm",orderForm);
            return  "redirect:/product/{id}";
        }
        try{
            orderService.save(orderForm);
        }
        catch (NullPointerException e) {
            System.out.println("An order on that product was already made");
        }
        return "redirect:/product/{id}";
    }
}
