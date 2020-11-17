package mk.ukim.finki.emt.lab.web;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import mk.ukim.finki.emt.lab.model.Cart;
import mk.ukim.finki.emt.lab.model.ChargeRequest;
import mk.ukim.finki.emt.lab.service.CartService;
import mk.ukim.finki.emt.lab.service.ChargeRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Controller
public class ChargeController {

    @Autowired
    ChargeRequestService chargeRequestService;

    @Autowired
    private CartService cartService;

    @PostMapping("/charge/{id}")
    public String chargeWithCartId(ChargeRequest chargeRequest, Model model,
                                   @PathVariable("id") Long id) throws StripeException {
        chargeRequest.setDescription("Example charge");
        chargeRequest.setCurrency(ChargeRequest.Currency.EUR);
        Charge charge = chargeRequestService.charge(chargeRequest);
        model.addAttribute("id", charge.getId());
        model.addAttribute("status", charge.getStatus());
        model.addAttribute("chargeId", charge.getId());
        model.addAttribute("balance_transaction", charge.getBalanceTransaction());

        chargeRequest.setTime_stamp(new Date());
        Cart cart = cartService.findById(id).get();
        chargeRequest.setCart(cart);

        chargeRequestService.save(chargeRequest);
        //After the transaction has been saved,
        //the cart is no longer active
        cartService.changeIsActive(cart.getId(),false);

        return "result";
    }

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "result";
    }
}