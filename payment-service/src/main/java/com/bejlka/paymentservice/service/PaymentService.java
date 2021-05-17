package com.bejlka.paymentservice.service;

import com.bejlka.paymentservice.domain.*;
import com.bejlka.paymentservice.domain.dto.PaymentDTO;
import com.bejlka.paymentservice.domain.dto.PaymentRequestDTO;
import com.bejlka.paymentservice.exception.CustomException;
import com.bejlka.paymentservice.repositiry.PaymentRepository;
import com.bejlka.paymentservice.utils.MyRandom;
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
public class PaymentService {

    PaymentRepository paymentRepository;
    PaymentMapper paymentMapper;
    MyRandom myRandom;

    public PaymentDTO payment(Long id) {
        Optional<Payment> optionalPayment = paymentRepository.findById(id);
        if (optionalPayment.isPresent()) {
            return paymentMapper.paymentToDTO(optionalPayment.get());
        }
        throw new CustomException(HttpStatus.NOT_FOUND, "чек с таким id не найден: " + id);
    }

    public List<PaymentDTO> paymentsAll(Long id) {
        Optional<List<Payment>> optionalPaymentList = paymentRepository.findByUserId(id);
        if (optionalPaymentList.isPresent()) {
            return paymentMapper.listPaymentToDTO(optionalPaymentList.get());
        }
        throw new CustomException(HttpStatus.NOT_FOUND, "Чеки с пользователя с id не найдены: " + id);
    }

    public PaymentDTO createPayment(PaymentRequestDTO paymentRequestDTO) {
        Payment payment = new Payment();
        System.out.println(paymentRequestDTO.toString());
        payment.setUserId(paymentRequestDTO.getUserId());
        payment.setOrderId(paymentRequestDTO.getOrderId());
        System.out.println(payment);
        if (myRandom.getRandomBoolean()) {
            payment.setStatus(Status.SUCCESS);
        } else {
            payment.setStatus(Status.FAIL);
        }
        payment.setPaymentDate(Instant.now());
        return paymentMapper.paymentToDTO(paymentRepository.save(payment));
    }

    public PaymentDTO updatePayment(Long id) {
        Optional<Payment> optionalPayment = paymentRepository.findById(id);
        if (optionalPayment.isPresent()) {
            optionalPayment.get().setStatus(Status.SUCCESS);
            return paymentMapper.paymentToDTO(paymentRepository.save(optionalPayment.get()));
        }
        throw new CustomException(HttpStatus.NOT_FOUND, "Чек с таким id не найден: " + id);
    }
}
