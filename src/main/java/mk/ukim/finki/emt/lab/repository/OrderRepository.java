package mk.ukim.finki.emt.lab.repository;

import mk.ukim.finki.emt.lab.model.Cart;
import mk.ukim.finki.emt.lab.model.Order;
import mk.ukim.finki.emt.lab.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
    @Modifying
    @Transactional
    @Query("update Order o set o.quantity=?2 where o.id=?1")
    int setQuantityFor(Long order_id,Integer q);

    List<Order> findByCartAndProduct(Cart cart, Product product);
}