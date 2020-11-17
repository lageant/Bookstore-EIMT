package mk.ukim.finki.emt.lab.service;

import mk.ukim.finki.emt.lab.model.Cart;
import mk.ukim.finki.emt.lab.model.Order;
import mk.ukim.finki.emt.lab.model.Product;
import mk.ukim.finki.emt.lab.model.User;
import mk.ukim.finki.emt.lab.repository.OrderRepository;
import mk.ukim.finki.emt.lab.web.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository OrderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CartService cartService;

    public Order save(Order order) { return OrderRepository.save(order); }

    public Order save(OrderDTO orderForm) throws NullPointerException{
        //Create the new order
        Order order = new Order();
        order.setQuantity(orderForm.getQuantity());

        //Find the product
        Long product_id = orderForm.getProduct_id();
        Product product = productService.findById(product_id).get();
        order.setProduct(product);

        //Find the user
        User user = userService.findByUsername(orderForm.getUsername());
        //Find the user's active cart
        List<Cart> carts = cartService.findByUserAndActive(user);
        //If there is no active cart
        if(carts.isEmpty())
        {
            //Create a new active cart
            Cart cart = new Cart();
            cart.setUser(user);
            cartService.save(cart);

            //Add the order to the cart
            order.setCart(cart);
        }
        else
        {
            //There was an active cart
            //But, we need to check if
            //An order on the same product
            //Has been made
            Cart cart = carts.get(0);
            List<Order> olderOrders = OrderRepository.findByCartAndProduct(cart,product);

            //If there are no older orders
            //On the same product in the
            //same cart, add order to cart
            if(olderOrders.isEmpty())
                order.setCart(cart);
            //Else throw an error and don't
            //add to cart
            else
            {
                //System.out.println("An order on that product was already made");
                return null;
            }

        }
        return OrderRepository.save(order);
    }

    public void delete(Long order_id) { OrderRepository.deleteById(order_id);}

    public int update(Long order_id,Integer quantity) {return  OrderRepository.setQuantityFor(order_id,quantity);}

    public List<Order> findAll() {
        return OrderRepository.findAll();
    }
}
