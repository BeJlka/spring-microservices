package com.bejlka.deliveryservice.repository;

import com.bejlka.deliveryservice.domain.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    Optional<List<Delivery>> findByUserId(Long id);
}
