package com.bejlka.deliveryservice.service;

import com.bejlka.deliveryservice.domain.Delivery;
import com.bejlka.deliveryservice.domain.DeliveryMapping;
import com.bejlka.deliveryservice.domain.Status;
import com.bejlka.deliveryservice.domain.dto.DeliveryDTO;
import com.bejlka.deliveryservice.domain.dto.DeliveryRequestDTO;
import com.bejlka.deliveryservice.exception.CustomException;
import com.bejlka.deliveryservice.repository.DeliveryRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeliveryService {
    DeliveryRepository deliveryRepository;
    DeliveryMapping deliveryMapping;

    public DeliveryDTO createDelivery(DeliveryRequestDTO deliveryRequestDTO) {
        Delivery delivery = new Delivery();
        delivery.setOrderId(deliveryRequestDTO.getOrderId());
        delivery.setUserId(deliveryRequestDTO.getUserId());
        delivery.setRestaurantAddress(deliveryRequestDTO.getRestaurantAddress());
        delivery.setDeliveryAddress(deliveryRequestDTO.getDeliveryAddress());
        delivery.setOrderDate(deliveryRequestDTO.getOrderDate());
        delivery.setStatus(Status.RECEIVED);
        return deliveryMapping.DeliveryToDTO(deliveryRepository.save(delivery));
    }

    public DeliveryDTO updateStatusDelivery(Long id) {
        Optional<Delivery> optionalDelivery = deliveryRepository.findById(id);
        if (optionalDelivery.isPresent()) {
            Delivery delivery = optionalDelivery.get();
            if (delivery.getStatus().equals(Status.DONE)) {
                return deliveryMapping.DeliveryToDTO(delivery);
            }
            if (delivery.getStatus().equals(Status.DELIVERY)) {
                delivery.setDeliveryDate(Instant.now());
                delivery.setStatus(Status.DONE);
            }
            if (delivery.getStatus().equals(Status.RECEIVED)) {
                delivery.setStatus(Status.DELIVERY);
            }
            return deliveryMapping.DeliveryToDTO(deliveryRepository.save(delivery));
        }
        throw new CustomException(HttpStatus.BAD_REQUEST, "Не удалось найти доставку по заказу: " + id);
    }

    public DeliveryDTO delivery(Long id) {
        Optional<Delivery> optionalDelivery = deliveryRepository.findById(id);
        return optionalDelivery.map(deliveryMapping::DeliveryToDTO).orElse(null);
    }

    public List<DeliveryDTO> deliveryAll(Long id) {
        Optional<List<Delivery>> optionalDeliveryList = deliveryRepository.findByUserId(id);
        if (optionalDeliveryList.isPresent()) {
            return deliveryMapping.listDeliveryToDTO(optionalDeliveryList.get());
        }
        throw new CustomException(HttpStatus.NOT_FOUND, "У пользователя нет доставок");
    }
}
