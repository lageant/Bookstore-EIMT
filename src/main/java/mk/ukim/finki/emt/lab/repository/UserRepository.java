package mk.ukim.finki.emt.lab.repository;

import mk.ukim.finki.emt.lab.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository < User, Long > {
    User findByUsername(String username);
}
