package mk.ukim.finki.emt.lab.repository;

import mk.ukim.finki.emt.lab.model.Cart;
import mk.ukim.finki.emt.lab.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
    @Modifying
    @Transactional
    @Query("update Cart c set c.isActive=?2 where c.id=?1")
    int setIsActive(Long cart_id,Boolean newIsActive);

    List<Cart> findByUserAndIsActiveTrue(User user);
    List<Cart> findByUserAndIsActiveFalse(User user);
}