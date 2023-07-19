package com.example.paymentsappingress.controller;

import com.example.paymentsappingress.PaymentsAppIngressApplication;
import com.example.paymentsappingress.dto.request.PaymentRequest;
import com.example.paymentsappingress.dto.response.PaymentResponse;
import com.example.paymentsappingress.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("v1/payments")
@RestController
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    @GetMapping
    public ResponseEntity<List<PaymentResponse>> findAll() {
        return new ResponseEntity<>(paymentService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> findById(@PathVariable Long paymentId) {
        return new ResponseEntity<>(paymentService.findById(paymentId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> save(@RequestBody PaymentRequest paymentRequest) {
        return new ResponseEntity<>(paymentService.save(paymentRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> update(@PathVariable Long paymentId,
                                                  @RequestBody PaymentRequest paymentRequest) {
        return new ResponseEntity<>(paymentService.update(paymentRequest, paymentId), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{paymentId}")
    public void delete(@PathVariable Long paymentId) {
        paymentService.delete(paymentId);
    }
}



