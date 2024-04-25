package ru.pgk.smartbudget.common.apache.poi;

import org.apache.poi.ss.usermodel.*;

public class RowExpansions {

    public static void createHighlightedCellValue(Row row, Object v) {
        Cell cell = createCellValue(row, v);
        setHighlightedCellStyle(cell);
    }

    public static void createHighlightedCellValue(Row row, Short i, Object v) {
        Cell cell = createCellValue(row, i, v);
        setHighlightedCellStyle(cell);
    }

    public static Cell createCellValue(Row row, Object v) {
        return createCellValue(row, row.getLastCellNum() == -1 ? 0 : row.getLastCellNum(), v);
    }

    public static Cell createCellValue(Row row, Short i, Object v) {
        Cell cell = row.createCell(i);
        cell.setCellValue(v == null ? "-" : v.toString());
        return cell;
    }

    public static void setHighlightedCellStyle(Cell cell) {
        Workbook workbook = cell.getSheet().getWorkbook();
        CellStyle cellStyle = createHighlightedStyle(workbook);
        cell.setCellStyle(cellStyle);
    }

    public static void setHighlightedCellStyle(Row row) {
        Workbook workbook = row.getSheet().getWorkbook();
        CellStyle cellStyle = createHighlightedStyle(workbook);
        row.setRowStyle(cellStyle);
    }

    private static CellStyle createHighlightedStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return cellStyle;
    }
}
