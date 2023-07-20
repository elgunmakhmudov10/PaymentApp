package com.example.paymentsappingress.service;

import com.example.paymentsappingress.dto.request.BookingRequest;
import com.example.paymentsappingress.dto.response.BookingResponse;
import com.example.paymentsappingress.model.Booking;
import com.example.paymentsappingress.model.CourseType;
import com.example.paymentsappingress.model.Payment;
import com.example.paymentsappingress.model.User;
import com.example.paymentsappingress.repository.BookingRepository;
import com.example.paymentsappingress.repository.PaymentRepository;
import com.example.paymentsappingress.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;

    private final UserRepository userRepository;

    private final PaymentRepository paymentRepository;

    private final ModelMapper modelMapper;

//    private final RedisTemplate<String, Object> redisTemplate;

    /**
     *    Her bir method ucun Redis ve caching yazilmisdir sadece commente qoyulmusdur.
     */
    public List<BookingResponse> findAll() {
//        var bookingResponseFromCache = (List<BookingResponse>) redisTemplate.opsForValue().get("findAll");
//        if (bookingResponseFromCache != null) {
//            return bookingResponseFromCache;
//        }
        List<BookingResponse> bookingResponses = bookingRepository
                .findAll()
                .stream()
                .map(booking -> modelMapper.map(booking, BookingResponse.class))
                .collect(Collectors.toList());

//        redisTemplate.opsForValue().set("findAll", bookingResponses, Duration.ofSeconds(60000));

        return bookingResponses;


    }


    public BookingResponse findById(Long bookingId) {
//        var bookingResponseFromCache = (BookingResponse) redisTemplate.opsForValue().get(String.valueOf(bookingId));
//        if (bookingResponseFromCache != null) {
//            return bookingResponseFromCache;
//        }
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException(
                String.format("Booking not found by id -%s", bookingId)
        ));
        BookingResponse bookingResponse = modelMapper.map(booking, BookingResponse.class);
//        redisTemplate.opsForValue().set(String.valueOf(bookingId), bookingResponse, Duration.ofSeconds(60000));
        return bookingResponse;

    }

    public BookingResponse save(BookingRequest bookingRequest, Long userId  ) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException(
                String.format("User not found by id -%s", userId)
        ));

        Booking booking = modelMapper.map(bookingRepository, Booking.class);
        booking.setUser(user);
        return modelMapper.map(bookingRepository.save(booking), BookingResponse.class);
    }

    public BookingResponse update(BookingRequest bookingRequest, Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException(
                String.format("Booking not found by id -%s", bookingId)
        ));

        modelMapper.map(bookingRequest, booking, "map");
        booking.setId(bookingId);

        BookingResponse bookingResponse = modelMapper.map(bookingRepository.save(booking), BookingResponse.class);

//        redisTemplate.opsForValue().set(String.valueOf(bookingId), bookingResponse, Duration.ofSeconds(60000));

        return bookingResponse;
    }


    public void delete(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException(
                String.format("Booking not found by id -%s", bookingId)
        ));

        bookingRepository.delete(booking);

//        redisTemplate.opsForValue().getAndDelete(String.valueOf(bookingId));
    }
}




