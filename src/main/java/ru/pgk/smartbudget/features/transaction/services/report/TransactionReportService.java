package ru.pgk.smartbudget.features.transaction.services.report;

import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

public interface TransactionReportService {

    ResponseEntity<byte[]> generatedReport(Long userId, LocalDate startDate, LocalDate endDate);
}
