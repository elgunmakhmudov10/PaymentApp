package com.example.paymentsappingress.controller;

import com.example.paymentsappingress.dto.request.BookingRequest;
import com.example.paymentsappingress.dto.response.BookingResponse;
import com.example.paymentsappingress.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1/bookings")
@RestController
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @GetMapping
    public ResponseEntity<List<BookingResponse>> findAll() {
        return new ResponseEntity<>(bookingService.findAll(), HttpStatus.OK);

    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> findById(@PathVariable Long bookingId) {
        return new ResponseEntity<>(bookingService.findById(bookingId), HttpStatus.OK);
    }

    @PostMapping("/payments/{paymentId}/users/{userId}")
    public ResponseEntity<BookingResponse> save(@RequestBody BookingRequest bookingRequest,
                                                @PathVariable Long paymentId,
                                                @PathVariable Long userId) {
        return new ResponseEntity<>(bookingService.save(bookingRequest, paymentId, userId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{bookingId}")
    public void delete(@PathVariable Long bookingId) {
        bookingService.delete(bookingId);
    }
}

