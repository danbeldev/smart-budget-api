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
import ru.pgk.smartbudget.features.currency.entities.CurrencyEntity;
import ru.pgk.smartbudget.features.transaction.entitites.TransactionEntity;
import ru.pgk.smartbudget.features.transaction.services.TransactionService;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.pgk.smartbudget.common.apache.poi.RowExpansions.*;
import static ru.pgk.smartbudget.common.date.DateExpansions.toDefaultDateFormat;
import static ru.pgk.smartbudget.common.extensions.NumberExtensions.round;

@Service
@RequiredArgsConstructor
public class TransactionReportServiceImpl implements TransactionReportService {

    private final TransactionService transactionService;

    @Override
    @SneakyThrows
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> generatedReport(Long userId, LocalDate startDate, LocalDate endDate) {
        List<TransactionEntity> transactions = transactionService.getAll(userId, startDate, endDate);

        Map<CurrencyEntity, List<TransactionEntity>> transactionsByCurrencyCode = transactions.stream()
                .collect(Collectors.groupingBy(
                        transaction -> transaction.getCurrencyCode() != null
                                ? transaction.getCurrencyCode() : transaction.getBaseCurrencyCode()
                        )
                );

        Workbook workbook = new XSSFWorkbook();

        for (Map.Entry<CurrencyEntity, List<TransactionEntity>> entry : transactionsByCurrencyCode.entrySet()) {
            Sheet sheet = workbook.createSheet(entry.getKey().getCode());

            setHeaderReport(sheet, entry.getKey().getCode());
            setReportData(entry.getValue(), sheet);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        byte[] bytes = outputStream.toByteArray();

        outputStream.close();
        workbook.close();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("filename", "transaction-report.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(bytes);
    }

    private void setHeaderReport(Sheet sheet, String currencyCode) {
        Row row = sheet.createRow(0);
        createHighlightedCellValue(row, "Сумма (" + currencyCode + ")");
        createHighlightedCellValue(row, "Категория");
        createHighlightedCellValue(row, "Описание");
        createHighlightedCellValue(row, "Дата");

        Row rowSum = sheet.createRow(1);

        createHighlightedCellValue(rowSum, (short) (row.getLastCellNum()+2), "Категория");
        createHighlightedCellValue(rowSum, "Сумма (" + currencyCode + ")");

        createHighlightedCellValue(rowSum, (short) (row.getLastCellNum()+5), "Общая сумма (" + currencyCode + ")");
    }

    private void setReportData(List<TransactionEntity> transactions, Sheet sheet) {
        int rowNum = sheet.getLastRowNum();
        double sumAmountInBaseCurrency = 0.0;
        Map<String, Double> sumByCategories = new HashMap<>();

        for(TransactionEntity transaction : transactions) {
            Row row = getOrCreateRow(sheet, rowNum++);

            Double amountInBaseCurrency = round(transaction.getAmountInCurrency() != null ?
                    transaction.getAmountInCurrency() : transaction.getAmountInBaseCurrency());

            createCellValue(row, (short) 0, amountInBaseCurrency);
            createCellValue(row, (short) 1, transaction.getCategory().getName());
            createCellValue(row, (short) 2, transaction.getDescription());
            createCellValue(row, (short) 3, toDefaultDateFormat(transaction.getDate()));

            Double sumByCategory = sumByCategories.getOrDefault(transaction.getCategory().getName(), 0.0);
            sumByCategories.put(transaction.getCategory().getName(), amountInBaseCurrency + sumByCategory);

            sumAmountInBaseCurrency += amountInBaseCurrency;
        }

        setSumData(sheet, round(sumAmountInBaseCurrency), sumByCategories);
    }

    private void setSumData(Sheet sheet, Double sum, Map<String, Double> sumByCategories) {
        Row rowSum = getOrCreateRow(sheet, 1);
        createCellValue(getOrCreateRow(sheet, 2), (short) (rowSum.getLastCellNum()-1), sum.toString());

        int rowNumber = rowSum.getRowNum() + 1;

        for(Map.Entry<String, Double> expenseCategory : sumByCategories.entrySet()) {
            Row row = sheet.getRow(rowNumber);

            createCellValue(row, (short) (rowSum.getLastCellNum() - 4), expenseCategory.getKey());
            createCellValue(row, (short) (rowSum.getLastCellNum() - 3), expenseCategory.getValue());

            rowNumber+=1;
        }
    }
}
