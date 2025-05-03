package framework;

import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

public class ExcelUtils {

    Workbook wb;

    @SneakyThrows
    public void removeDataFromExcelSheet_ExceptMetaData(String filePath, String sheetName)
    {
        File f1 = new File(filePath);

        if (f1.exists() == false)
            throw new GenericExceptions("File: " + filePath + " not found");

        else {
//            if (filePath.endsWith(".xls"))
//                wb = new HSSFWorkbook(new FileInputStream(filePath));
//
//            else if (filePath.endsWith(".xlsx"))
//                wb = new XSSFWorkbook(new FileInputStream(filePath));

            wb= filePath.endsWith(".xls") ? new HSSFWorkbook(new FileInputStream(filePath)) : new XSSFWorkbook(new FileInputStream(filePath));
        }

        Sheet sheet=null;

        if(sheetName.split(",").length>1)
        {
            for(String indSheet:sheetName.split(","))
            {
                sheet = wb.getSheet(indSheet);

                if (sheet == null)
                    throw new GenericExceptions("Unable to find the sheet: " + sheetName);

                else {

                    int noOfRows = sheet.getPhysicalNumberOfRows();

                    for (int i = noOfRows; i > 0; i--) {
                        Row row = sheet.getRow(i);
                        if (row != null)
                            sheet.removeRow(row);
                    }
                }

                FileOutputStream fos = new FileOutputStream(filePath);
                wb.write(fos);

                fos.close();
            }

        }

        else {
            sheet = wb.getSheet(sheetName);

            if (sheet == null)
                throw new GenericExceptions("Unable to find the sheet: " + sheetName);

            else {

                int noOfRows = sheet.getPhysicalNumberOfRows();

                for (int i = noOfRows; i > 0; i--) {
                    Row row = sheet.getRow(i);
                    if (row != null)
                        sheet.removeRow(row);
                }
            }

            FileOutputStream fos=new FileOutputStream(filePath);
            wb.write(fos);

            fos.close();
        }

    }

    @SneakyThrows
    public List<Map<String,String>> readCompleteDataFromExcel(String filePath, String sheetName)
    {
        File f1=new File(filePath);

        if(!f1.exists())
            throw new GenericExceptions("File is not found in the given path: "+filePath);

        if(filePath.endsWith(".xls"))
            wb=new HSSFWorkbook(new FileInputStream(filePath));

        else if(filePath.endsWith(".xlsx"))
            wb=new XSSFWorkbook(new FileInputStream(filePath));

        Sheet sheet=Optional.ofNullable(wb.getSheet(sheetName)).orElseThrow(() -> new GenericExceptions("Sheet does not exist in the given file"));

        int noOfRows=sheet.getPhysicalNumberOfRows();
        int noOfColumns=sheet.getRow(0).getPhysicalNumberOfCells();

        //List<Map> object will take of storing all rows of data
        List<Map<String,String>> excelData=new ArrayList<>();

        Map<String, String> map=null; //Store individual rows of data

        for(int i=1;i<noOfRows;i++)
        {
            map=new HashMap<String,String>(); //We are creating a new map object for each and every row
            for(int j=0;j<noOfColumns;j++)
            {
                String columnName=sheet.getRow(0).getCell(j).getStringCellValue();

                Cell cell=Optional.ofNullable(sheet.getRow(i).getCell(j)).orElse(null);
                String cellValue=Optional.ofNullable(cell)
                        .map(Cell::getStringCellValue)
                        .orElse("");

                map.put(columnName,cellValue);
            }

            excelData.add(map);
        }

        return excelData;
    }

    @SneakyThrows
    public String readDataFromExcelFile(String filePath, String sheetName, String column, String...rowIdentifier) {

        wb = null;
        FileOutputStream fos;

        File f1 = new File(filePath);

        if (!f1.exists()) //Checking if the file does not exist
        {
            throw new GenericExceptions("File does not exist in the given path");
        }

        if (filePath.endsWith(".xls")) {
            wb = new HSSFWorkbook(new FileInputStream(filePath));
        } else if (filePath.endsWith(".xlsx")) {
            wb = new XSSFWorkbook(new FileInputStream(filePath));
        }

        Sheet sheet = wb.getSheet(sheetName);

        if (sheet == null) {
            throw new GenericExceptions("Sheet does not exist in the given file");
        }

        int columnNo = -1;

        int noOfColumns = sheet.getRow(0).getPhysicalNumberOfCells();
        for (int i = 0; i < noOfColumns; i++) {
            if (sheet.getRow(0).getCell(i).getStringCellValue().equalsIgnoreCase(column)) {
                columnNo = i;
                break;
            }
        }

        if (columnNo == -1) {
            throw new GenericExceptions("Column does not exist in the given sheet");
        }

        if (rowIdentifier.length == 0) {
            int noOfRows = sheet.getPhysicalNumberOfRows();
            return sheet.getRow(noOfRows - 1).getCell(columnNo).getStringCellValue();
        } else {
            //RowIdentifier --> ColumnName-RowData (Sample_Column-Sample_Data)

            //Getting the column name from the row identifier
            String expectedColumnName = rowIdentifier[0].split("-")[0]; //Sample_Column

            //Getting the row data from the row identifier
            String expectedRowData = rowIdentifier[0].split("-")[1];  //Sample_Data

            //Getting the number of columns present in the sheet
            noOfColumns = sheet.getRow(0).getPhysicalNumberOfCells();

            int expectedColumnNo = -1;
            for (int i = 0; i < noOfColumns; i++) {
                String existingColumn = sheet.getRow(0).getCell(i).getStringCellValue(); //Getting the column name from the sheet

                if (existingColumn.equalsIgnoreCase(expectedColumnName)) //Checking if the column name is already present in the sheet
                {
                    expectedColumnNo = i; //If the column name is present in the sheet, then we are storing the column number in the variable
                    break; //Breaking the loop if the column name is found
                }
            }

            if (expectedColumnNo == -1) //If the column is not present
            {
                throw new GenericExceptions("Given Column Name: " + column + " is not present in the sheet: " + sheetName);
            }

            int noOfRows = sheet.getPhysicalNumberOfRows(); //Getting the number of rows present in the sheet

            int expectedRowNo = -1;
            //expectedColumnNo --> 1
            for (int i = 0; i < noOfRows; i++) {
                String existingRowOfData = sheet.getRow(i).getCell(expectedColumnNo).getStringCellValue(); //Getting the row data from the sheet
                if (existingRowOfData.equalsIgnoreCase(expectedRowData)) //Checking if the row data is already present in the sheet
                {
                    expectedRowNo = i; //If the row data is present in the sheet, then we are storing the row number in the variable
                    break; //Breaking the loop if the row data is found
                }
            }

            if (expectedRowNo == -1) {
                throw new GenericExceptions("Given Row Data: " + expectedRowData + " is not present in the sheet: " + sheetName);
            }

            return sheet.getRow(expectedRowNo).getCell(columnNo).getStringCellValue(); //Returning the cell value from the sheet
        }

    }


    //How do we pass the data for the row identifier: Reference Column Name and the Reference Row data
    @SneakyThrows
//    public static void writeDataToTheExcelFile(String filePath, String sheetName,int rowNo, int columnNo, String data)
//    {
    public void writeDataToTheExcelFile(String filePath, String sheetName,String columnName, String data,String... rowIdentifier)
    {
        FileOutputStream fos;

        File f1=new File(filePath);

        if(!f1.exists()) //Checking if the file does not exist
        {
            if(filePath.endsWith(".xls"))
            {
                wb=new HSSFWorkbook();
            }

            else if(filePath.endsWith(".xlsx"))
            {
                wb=new XSSFWorkbook();
            }

            //Create that particular excel file in the given path

            fos=new FileOutputStream(filePath);
            wb.write(fos); //Write the data to the file and the reason for using FileOutputStream is that we are passing the data in the form of streams
            fos.close();
        }

        //If the file is already present in the given path, we are going to open that file and read the data that is already present
        if(filePath.endsWith(".xls"))
        {
            //new FileInputStream(filePath) --> Reads the data that is present in the excel file and stores in the workbook file
            wb=new HSSFWorkbook(new FileInputStream(filePath));
        }

        else if(filePath.endsWith(".xlsx"))
        {
            wb=new XSSFWorkbook(new FileInputStream(filePath));
        }

        CellStyle style=wb.createCellStyle(); //It is used to decorate the cell

        /*************************************************************************************************************************************/

        //Step 2: Create a sheet in the workbook if it does not exist else open the existing sheet

        Sheet sheet=wb.getSheet(sheetName); //Getting the sheet object from the workbook based on the sheet name

        if(sheet==null) //Checking if the sheet is present in the workbook or not
            sheet=wb.createSheet(sheetName); //Creating a new sheet in the workbook if it does not exist

        /*************************************************************************************************************************************/

        //Step 3: Checking if the column is present in the sheet or not, if it is not present then create that particular column

        if(sheet.getRow(0)==null)
            sheet.createRow(0); //Creating a new row in the sheet if it does not exist

        int noOfColumns=sheet.getRow(0).getPhysicalNumberOfCells(); //Getting the number of columns present in the sheet

        int columnNo=-1;
        for(int i=0;i<noOfColumns;i++)
        {
            String existingColumn=sheet.getRow(0).getCell(i).getStringCellValue(); //Getting the column name from the sheet

            if(existingColumn.equalsIgnoreCase(columnName)) //Checking if the column name is already present in the sheet
            {
                columnNo=i; //If the column name is present in the sheet, then we are storing the column number in the variable
                break; //Breaking the loop if the column name is found
            }
        }

        if(columnNo==-1) //If the column is not present
        {
            Cell cell=sheet.getRow(0).createCell(noOfColumns); //Creating a new cell in the sheet
            cell.setCellValue(columnName); //Setting the value of the cell to the column name
            columnNo=noOfColumns; //Storing the column number in the variable
        }

        /*************************************************************************************************************************************/

        //Step 4: Write the data to the sheet

        if(rowIdentifier.length==0) {
            int noOfRows = sheet.getPhysicalNumberOfRows(); //Getting the number of rows present in the sheet

            if (sheet.getRow(noOfRows) == null)
                sheet.createRow(noOfRows); //Creating a new row in the sheet if it does not exist

            if (sheet.getRow(noOfRows).getCell(columnNo) == null)
                sheet.getRow(noOfRows).createCell(columnNo); //Creating a new cell in the sheet if it does not exist

            sheet.getRow(noOfRows).getCell(columnNo).setCellValue(data); //Setting the value of the cell to the data
        }

        else
        {
            //RowIdentifier --> ColumnName-RowData (Sample_Column-Sample_Data)

            //Getting the column name from the row identifier
            String expectedColumnName = rowIdentifier[0].split("-")[0]; //Sample_Column

            //Getting the row data from the row identifier
            String expectedRowData = rowIdentifier[0].split("-")[1];  //Sample_Data

            //Getting the number of columns present in the sheet
            noOfColumns = sheet.getRow(0).getPhysicalNumberOfCells();

            int expectedColumnNo = -1;
            for (int i = 0; i < noOfColumns; i++)
            {
                String existingColumn = sheet.getRow(0).getCell(i).getStringCellValue(); //Getting the column name from the sheet

                if (existingColumn.equalsIgnoreCase(expectedColumnName)) //Checking if the column name is already present in the sheet
                {
                    expectedColumnNo = i; //If the column name is present in the sheet, then we are storing the column number in the variable
                    break; //Breaking the loop if the column name is found
                }
            }

            if (expectedColumnNo == -1) //If the column is not present
            {
                throw new GenericExceptions("Given Column Name: "+columnName+" is not present in the sheet: "+sheetName);
            }

            int noOfRows = sheet.getPhysicalNumberOfRows(); //Getting the number of rows present in the sheet

//            if (sheet.getRow(noOfRows) == null)
//                sheet.createRow(noOfRows); //Creating a new row in the sheet if it does not exist
//
//            if (sheet.getRow(noOfRows).getCell(columnNo) == null)
//                sheet.getRow(noOfRows).createCell(columnNo); //Creating a new cell in the sheet if it does not exist

            int expectedRowNo=-1;
            //expectedColumnNo --> 1
            for(int i=0;i<noOfRows;i++)
            {
                String existingRowOfData=sheet.getRow(i).getCell(expectedColumnNo).getStringCellValue(); //Getting the row data from the sheet
                if(existingRowOfData.equalsIgnoreCase(expectedRowData)) //Checking if the row data is already present in the sheet
                {
                    expectedRowNo=i; //If the row data is present in the sheet, then we are storing the row number in the variable
                    break; //Breaking the loop if the row data is found
                }
            }

            if(expectedRowNo==-1)
            {
                throw new GenericExceptions("Given Row Data: "+expectedRowData+" is not present in the sheet: "+sheetName);
            }

            if(sheet.getRow(expectedRowNo).getCell(columnNo)==null)
                sheet.getRow(expectedRowNo).createCell(columnNo); //Creating a new cell in the sheet if it does not exist

            if(data.equalsIgnoreCase("PASSED"))
            {
                style.setFillBackgroundColor(IndexedColors.LIGHT_GREEN.getIndex());
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND); //Determines how do needs to fill the cell with the background color
                Font fontStyling=wb.createFont(); //Creating a font style

                fontStyling.setColor(IndexedColors.WHITE.getIndex());
                fontStyling.setBold(true);

                style.setFont(fontStyling);

            }

            else if(data.equalsIgnoreCase("FAILED"))
            {
                style.setFillBackgroundColor(IndexedColors.RED.getIndex());
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                Font fontStyling=wb.createFont(); //Creating a font style

                fontStyling.setColor(IndexedColors.WHITE.getIndex());
                fontStyling.setBold(true);

                style.setFont(fontStyling);

            }

            sheet.getRow(expectedRowNo).getCell(columnNo).setCellStyle(style); //Setting the cell style to the cell
            sheet.getRow(expectedRowNo).getCell(columnNo).setCellValue(data); //Setting the value of the cell to the data
        }

        /*************************************************************************************************************************************/

        //Step 5: Write the data to the file
        fos=new FileOutputStream(filePath); //Creating a new file output stream to write the data to the file
        wb.write(fos); //Writing the data to the file
        fos.close(); //Closing the file output stream
    }


}
