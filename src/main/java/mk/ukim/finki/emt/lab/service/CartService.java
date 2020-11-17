package mk.ukim.finki.emt.lab.service;

import mk.ukim.finki.emt.lab.model.Cart;
import mk.ukim.finki.emt.lab.model.Order;
import mk.ukim.finki.emt.lab.model.User;
import mk.ukim.finki.emt.lab.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserServiceImpl userService;

    public Cart save(Cart cart) { return cartRepository.save(cart); }

    public Optional<Cart> findById(Long id) {
        return cartRepository.findById(id);
    }

    public List<Cart> findByUserAndActive(User user) {return cartRepository.findByUserAndIsActiveTrue(user);}

    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    public int changeIsActive(Long cart_id,Boolean newState) {return cartRepository.setIsActive(cart_id,newState);}

    public Cart findActiveByCurrentUser() {
        //Have to use the default user class and SecurityContextHolder
        //in order to get the user details, mainly the username
        //To do: make user class castable to the default user class
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal()).getUsername();

        User currentUser = userService.findByUsername(username);

        List<Cart> cart = this.findByUserAndActive(currentUser);

        //if there is an active cart for the user with orders
        if(!cart.isEmpty() && !cart.get(0).getOrders().isEmpty()) {
            //System.out.println("The cart has orders");
            return cart.get(0);
        }
        else{
            //anything else
            return null;
        }
    }

    public int calculatePrice(Cart cart){
        int sum = 0;
        List<Order> orders = cart.getOrders();
        for (Order order: orders) {
            sum += order.getQuantity() * order.getProduct().getPrice();
        }
        return sum;
    }

    public List<Cart> findNotActiveByCurrentUser() {
        String username = ((org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal()).getUsername();

        User currentUser = userService.findByUsername(username);

        return cartRepository.findByUserAndIsActiveFalse(currentUser);}
}
