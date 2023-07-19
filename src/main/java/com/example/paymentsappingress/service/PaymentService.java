package com.example.paymentsappingress.service;

import com.example.paymentsappingress.dto.request.PaymentRequest;
import com.example.paymentsappingress.dto.response.PaymentResponse;
import com.example.paymentsappingress.model.Payment;
import com.example.paymentsappingress.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final ModelMapper modelMapper;
    private final PaymentRepository paymentRepository;

    public List<PaymentResponse> findAll() {
        return paymentRepository
                .findAll()
                .stream()
                .map(payment -> modelMapper.map(payment, PaymentResponse.class))
                .collect(Collectors.toList());

    }


    @Cacheable(key = "#paymentId", cacheNames = "payment")
    public PaymentResponse findById(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new RuntimeException(
                String.format("Payment not found by id -%s", paymentId)
        ));
        return modelMapper.map(payment, PaymentResponse.class);
    }


    public PaymentResponse save(PaymentRequest paymentRequest) {
        Payment payment = modelMapper.map(paymentRequest, Payment.class);
        return modelMapper.map(paymentRepository.save(payment), PaymentResponse.class);
    }


    public PaymentResponse update(PaymentRequest paymentRequest, Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new RuntimeException(
                String.format("Payment not found by id -%s", paymentId)
        ));
        return modelMapper.map(paymentRepository.save(payment), PaymentResponse.class);
    }


    public void delete(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new RuntimeException(
                String.format(" Payment not found by id -%s", paymentId)
        ));
        paymentRepository.delete(payment);
    }

}

