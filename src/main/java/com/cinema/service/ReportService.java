package com.cinema.service;

import com.cinema.model.Booking;
import com.cinema.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {
    private final BookingRepository bookingRepository;
    public ReportService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Map<String, Object> getSalesReport(LocalDate from, LocalDate to) {
        List<Booking> bookings = bookingRepository.findConfirmedByDateRange(from, to);

        double totalRevenue = bookings.stream()
            .mapToDouble(b -> b.getPayment() != null ? b.getPayment().getAmount() : 0)
            .sum();

        Map<String, Object> report = new HashMap<>();
        report.put("totalBookings", bookings.size());
        report.put("totalRevenue", totalRevenue);
        report.put("period", from + " to " + to);
        return report;
    }
}
