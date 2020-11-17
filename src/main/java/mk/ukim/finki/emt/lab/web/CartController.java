package mk.ukim.finki.emt.lab.web;

import mk.ukim.finki.emt.lab.model.Cart;
import mk.ukim.finki.emt.lab.model.ChargeRequest;
import mk.ukim.finki.emt.lab.model.Order;
import mk.ukim.finki.emt.lab.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class CartController {
    @Autowired
    private CartService cartService;


    @GetMapping("/purchaseHistory/{id}")
    public String CartInfo (Model model, @PathVariable("id") Long id){
        Cart cart = cartService.findById(id).get();
        List<Order> orderList = cart.getOrders();
        ChargeRequest chargeRequest = cart.getChargeRequest();
        model.addAttribute("orderList",orderList);
        model.addAttribute("chargeRequest",chargeRequest);
        return "Purchase.idform";
    }
}
