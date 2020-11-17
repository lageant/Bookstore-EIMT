package mk.ukim.finki.emt.lab.repository;

import mk.ukim.finki.emt.lab.model.Cart;
import mk.ukim.finki.emt.lab.model.ChargeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ChargeRequestRepository extends JpaRepository<ChargeRequest, Long> {
    @Modifying
    @Transactional
    @Query("update ChargeRequest c set c.cart=?2 where c.id=?1")
    int setCartId(Long request_id, Cart cart);
}
