package com.bejlka.paymentservice.domain;

import com.bejlka.paymentservice.domain.dto.PaymentDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentDTO paymentToDTO(Payment payment);

    List<PaymentDTO> listPaymentToDTO(List<Payment> payments);
}
