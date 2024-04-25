package ru.pgk.smartbudget.features.transaction.services.report;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pgk.smartbudget.features.expenseCategory.entitites.ExpenseCategoryEntity;
import ru.pgk.smartbudget.features.transaction.entitites.TransactionEntity;
import ru.pgk.smartbudget.features.transaction.services.TransactionService;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.pgk.smartbudget.common.apache.poi.RowExpansions.createCellValue;
import static ru.pgk.smartbudget.common.apache.poi.RowExpansions.createHighlightedCellValue;
import static ru.pgk.smartbudget.common.date.DateExpansions.toDefaultDateFormat;

@Service
@RequiredArgsConstructor
public class TransactionReportServiceImpl implements TransactionReportService {

    private final TransactionService transactionService;

    @Override
    @SneakyThrows
    @Transactional
    public ResponseEntity<byte[]> generatedReport(Long userId, LocalDate startDate, LocalDate endDate) {
        List<TransactionEntity> transactions = transactionService.getAll(userId, startDate, endDate);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Report");

        setHeaderReport(sheet);
        setDataReport(transactions, sheet);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        byte[] bytes = outputStream.toByteArray();

        outputStream.close();
        workbook.close();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("filename", "report.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(bytes);
    }

    private void setHeaderReport(Sheet sheet) {
        Row row = sheet.createRow(0);
        createHighlightedCellValue(row, "Сумма в базовой валюте");
        createHighlightedCellValue(row, "Базовый код валюты");
        createHighlightedCellValue(row, "Сумма");
        createHighlightedCellValue(row, "Код валюты");
        createHighlightedCellValue(row, "Категория");
        createHighlightedCellValue(row, "Описание");
        createHighlightedCellValue(row, "Дата");
    }

    private void setDataReport(List<TransactionEntity> transactions, Sheet sheet) {
        int rowNum = sheet.getLastRowNum()+1;
        double sumAmountInBaseCurrency = 0.0;

        Map<ExpenseCategoryEntity, List<TransactionEntity>> transactionsByGroup =
                transactions.stream().collect(Collectors.groupingBy(TransactionEntity::getCategory));

        for(Map.Entry<ExpenseCategoryEntity, List<TransactionEntity>> item : transactionsByGroup.entrySet()) {
            double sumAmountInBaseCurrencyByCategory = 0.0;

            for (TransactionEntity transaction : item.getValue()) {
                Row row = sheet.createRow(rowNum++);
                createCellValue(row, transaction.getAmountInBaseCurrency());
                createCellValue(row, transaction.getBaseCurrencyCode().getCode());
                createCellValue(row, transaction.getAmountInCurrency());
                createCellValue(row, transaction.getCurrencyCode() != null ? transaction.getCurrencyCode().getCode() : "-");
                createCellValue(row, transaction.getCategory().getName());
                createCellValue(row, transaction.getDescription());
                createCellValue(row, toDefaultDateFormat(transaction.getDate()));

                sumAmountInBaseCurrencyByCategory += transaction.getAmountInBaseCurrency();
            }
            Row row = sheet.createRow(rowNum++);
            createHighlightedCellValue(row, (short) 7, "Сумма: " + sumAmountInBaseCurrencyByCategory);

            sumAmountInBaseCurrency += sumAmountInBaseCurrencyByCategory;
        }

        Row row = sheet.createRow(rowNum+1);
        createHighlightedCellValue(row, "Общая сумма: " + sumAmountInBaseCurrency);
    }
}
