/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.model.excel;

import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.AttributedString;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

/**
 *
 * @author alabkovich
 */
public abstract class ExcelDocument extends AbstractXlsxView {

    protected String path;
    protected XSSFWorkbook wbx = new XSSFWorkbook();
    protected XSSFSheet sheet;

    public XSSFWorkbook getWorkbook(String sourcePath, String sheetName) {
        InputStream is = null;
        OutputStream os = null;
        path = System.getProperty("catalina.home")
                + File.separator
                + getFileFullName(sourcePath);

        try {
            is = new FileInputStream(sourcePath);
            os = new FileOutputStream(path);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            wbx = new XSSFWorkbook(path);
            if (sheetName != null) {
                sheet = wbx.getSheet(sheetName);
            } else {
                sheet = wbx.getSheetAt(0);
            }
        } catch (IOException ex) {
            Logger.getLogger(ExcelInvoice.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(ExcelOrder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        System.out.println("File open in - " + path);
        return wbx;
    }

    public XSSFWorkbook getWorkbook(String sourcePath) {
        return getWorkbook(sourcePath, null);
    }

    protected String getStringCellValue(String STRcellRef) {
        CellReference cellRef = new CellReference(STRcellRef);
        String sheetName = cellRef.getSheetName();
        if (sheetName == null) {
            sheetName = sheet.getSheetName();
        }
        return wbx.getSheet(sheetName).getRow(cellRef.getRow()).getCell(cellRef.getCol()).getStringCellValue();
    }

    protected void setCellValue(String STRcellRef, String value) {
        getCell(STRcellRef).setCellValue(value);
    }

    protected void setCellValue(String STRcellRef, Double value) {
        getCell(STRcellRef).setCellValue(value);
    }

    protected void setCellValue(String STRcellRef, Date value) {
        getCell(STRcellRef).setCellValue(value);
    }

    protected void setCellValue(int row, int col, String value) {
        getCell(row, col).setCellValue(value);
    }

    protected void setCellValue(int row, int col, Double value) {
        getCell(row, col).setCellValue(value);
    }

    protected void setCellValue(int row, int col, Date value) {
        getCell(row, col).setCellValue(value);
    }

    protected void replaceTextInCell(String STRcellRef, String marker, String text) {
        String str = getStringCellValue(STRcellRef);

        setCellValue(STRcellRef, str.replaceAll(marker, text));
    }

    protected void addRowUnder(int rowNum) {
        sheet.shiftRows(rowNum, sheet.getLastRowNum(), 1, true, false);
    }

    protected void setRowHeight(Cell cell) {
        final short rowHeight = 240;
        Sheet curSheet = cell.getSheet();
        String cellValue = cell.getStringCellValue();
        if (cellValue.equals("")) {
            return;
        }
        // Create Font object with Font attribute (e.g. Font family, Font size, etc) for calculation
        Font excelFont = wbx.getFontAt(cell.getCellStyle().getFontIndex());
        java.awt.Font currFont = new java.awt.Font(excelFont.getFontName(), 0, excelFont.getFontHeightInPoints());
        AttributedString attrStr = new AttributedString(cellValue);
        attrStr.addAttribute(TextAttribute.FONT, currFont);

        // Use LineBreakMeasurer to count number of lines needed for the text
        FontRenderContext frc = new FontRenderContext(null, true, true);
        LineBreakMeasurer measurer = new LineBreakMeasurer(attrStr.getIterator(), frc);
        int nextPos = 0;
        int lineCnt = 0;
        int columnWhidth = 0;

        for (CellRangeAddress address : curSheet.getMergedRegions()) {
            if (address.isInRange(cell)) {
                for (int i = address.getFirstColumn(); i < address.getLastColumn(); i++) {
                    columnWhidth += curSheet.getColumnWidth(i);
                }
                break;
            }
        }

        while (measurer.getPosition() < cellValue.length()) {
            nextPos = measurer.nextOffset(columnWhidth / 48.481f); // mergedCellWidth is the max width of each line
            lineCnt++;
            measurer.setPosition(nextPos);
        }

        Row currRow = curSheet.getRow(cell.getRowIndex());
        int currRowLines = currRow.getHeight() / rowHeight;
        if (currRowLines < lineCnt) {
            currRow.setHeight((short) (rowHeight * lineCnt));
        }
    }

    protected void setRowHeight(int row, int col) {
        Cell cell = sheet.getRow(row).getCell(col);
        setRowHeight(cell);
    }

    //не работает с многострочными объедененными регионами
    private void setTableCellParrentProperties(Cell cell) {
        CellRangeAddress mergeRegionWidth = null;

        Sheet curSheet = cell.getSheet();
        Cell cellAbove = curSheet.getRow(cell.getRowIndex() - 1).getCell(cell.getColumnIndex());
        CellStyle cellStyle = cellAbove.getCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cell.setCellStyle(cellStyle);

        for (CellRangeAddress address : curSheet.getMergedRegions()) {
            if (address.isInRange(cellAbove)) {
                mergeRegionWidth = new CellRangeAddress(
                        cell.getRowIndex(),
                        cell.getRowIndex(),
                        address.getFirstColumn(),
                        address.getLastColumn()
                );
                curSheet.addMergedRegion(mergeRegionWidth);
            }
        }

        if (mergeRegionWidth != null) {
            for (int i = mergeRegionWidth.getFirstColumn() + 1; i <= mergeRegionWidth.getLastColumn(); i++) {
                Cell clearCell = curSheet.getRow(mergeRegionWidth.getFirstRow()).createCell(i);
                clearCell.setCellStyle(cellStyle);
            }
        } else {
            cell.setCellStyle(cellStyle);
        }
    }

    private Cell getCell(Sheet sheet, int rowNum, int colNum) {
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            row = sheet.createRow(rowNum);
        }
        Cell cell = row.getCell(colNum);
        if (cell == null) {
            cell = sheet.getRow(rowNum).createCell(colNum);
            setTableCellParrentProperties(cell);
        }

        return cell;
    }

    private Cell getCell(int rowNum, int colNum) {
        return getCell(sheet, rowNum, colNum);
    }

    private Cell getCell(String STRcellRef) {
        CellReference cellRef = new CellReference(STRcellRef);
        String sheetName = cellRef.getSheetName();
        if (sheetName == null) {
            sheetName = sheet.getSheetName();
        }

        return getCell(wbx.getSheet(sheetName), cellRef.getRow(), cellRef.getCol());
    }

    private String getFileFullName(String path) {
        int index = path.lastIndexOf("\\");
        return path.substring(index + 1);
    }
}
