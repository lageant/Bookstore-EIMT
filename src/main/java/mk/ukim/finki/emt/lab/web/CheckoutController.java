package mk.ukim.finki.emt.lab.web;
import mk.ukim.finki.emt.lab.model.Cart;
import mk.ukim.finki.emt.lab.model.ChargeRequest;
import mk.ukim.finki.emt.lab.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CheckoutController {

    @Autowired
    private CartService cartService;

    @Value("pk_test_c7eX1pfeKCdNlgJQbka4ZwAh00xhL9YnQv")
    private String stripePublicKey;

    @RequestMapping("/checkout")
    public String checkout(Model model) {
        Cart cart = cartService.findActiveByCurrentUser();
        int amount = cartService.calculatePrice(cart);

        model.addAttribute("amount", amount * 100); // in cents
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("currency", ChargeRequest.Currency.EUR);
        model.addAttribute("cart_id",cart.getId());
        return "checkout";
    }
}
