package com.ob.commonUtil.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ObExcelUtils {
	
	private static final Logger logger = Logger.getLogger(ObExcelUtils.class);
	/** 
     * Excel 2003 
     */  
    private final static String XLS = "xls";  
    /** 
     * Excel 2007 
     */  
    private final static String XLSX = "xlsx";  
    /** 
     * 分隔符 
     */  
    private final static String SEPARATOR = "|";  
	
	/**
	 * 根据数据创建一个sheet表
	 * @param sheetName
	 * @param headers
	 * @param list
	 * @return
	 */
	public static HSSFSheet createSheetWithData(String sheetName,String[] headers,List<List<String>> list){		
		HSSFSheet sheet = createSheetWithHander(sheetName,headers);
		int index = 0;
		for(List<String> ls:list){
			index++;
			HSSFRow row = sheet.createRow(index);
			int row_index = 0;
			for(String s:ls){
				HSSFCell cell = row.createCell(row_index);
//				HSSFRichTextString text = new HSSFRichTextString(s);
	            cell.setCellValue(s);
				row_index++;
			}
		}
		return sheet;
	}
	
	
	/**
	 * 创建一个带表头的空Sheet，并返回此sheet
	 * @param sheetName
	 * @param headers
	 * @return
	 */
	public static HSSFSheet createSheetWithHander(String sheetName,String[] headers){
		 // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(sheetName);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(15);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);
                
        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        return sheet;
	}
	
	
	public static List<String[]> extractDataFromExcel(File file, int ignoreRows,String type){
		try {
			if(type.toLowerCase().endsWith(XLSX)){
				return extractDataFromExcel2007(file,ignoreRows);
			}else{
				return extractDataFromExcel2003(file,ignoreRows);
			}
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 从excel中提取数据
	 * @param file
	 * @param ignoreRows  读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
	 * @throws IOException 
	 */
	public static List<String[]> extractDataFromExcel2003(File file, int ignoreRows) throws IOException{
		List<String[]> result = new ArrayList<String[]>();
		int rowSize = 0;
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		// 打开HSSFWorkbook
		POIFSFileSystem fs = new POIFSFileSystem(in);	
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		HSSFCell cell = null;	
		//循环sheet
		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
			HSSFSheet st = wb.getSheetAt(sheetIndex);
			// 第一行为标题，不取
			for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
				HSSFRow row = st.getRow(rowIndex);
				if (row == null) {
	                  continue;
	            }
				int tempRowSize = row.getLastCellNum() + 1;
				if (tempRowSize > rowSize) {
					rowSize = tempRowSize;
				}
				String[] values = new String[rowSize];
				Arrays.fill(values, "");
				boolean hasValue = false;
				for (int columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
					String value = "";
					cell = row.getCell(columnIndex);
					if (cell != null) {
						switch (cell.getCellType()) {
						case HSSFCell.CELL_TYPE_STRING:
		//					value = cell.getStringCellValue();
							value = cell.getRichStringCellValue().getString();
							break;
						case HSSFCell.CELL_TYPE_NUMERIC:
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								Date date = cell.getDateCellValue();
								if (date != null) {
									value = new SimpleDateFormat("yyyy-MM-dd").format(date);									
								}else{
									value = "";
								}
							}else{
								value = String.valueOf(cell.getNumericCellValue());
							}			
							break;
						case HSSFCell.CELL_TYPE_FORMULA:
							// 导入时如果为公式生成的数据则无值
							/*if (!cell.getStringCellValue().equals("")) {
	                            value = cell.getStringCellValue();
	                         } else {
	                            value = cell.getNumericCellValue() + "";
	                         }*/
							value="";
							break;
						case HSSFCell.CELL_TYPE_BLANK:
							break;
						case HSSFCell.CELL_TYPE_ERROR:
	                        value = "";
	                        break;
						case HSSFCell.CELL_TYPE_BOOLEAN:
							value = (cell.getBooleanCellValue() == true ? "Y": "N");
	                         break;
						default:
							value = "";
						}
					}
					if (columnIndex == 0 && value.trim().equals("")) {
	                     break;
	                }
					values[columnIndex] = value.trim();
	                hasValue = true;
				}
				
				if (hasValue) {
	                result.add(values);
	            }
			}
		}
		in.close();
		
		return result;
	}
	
	public static List<String[]> extractDataFromExcel2007(File file, int ignoreRows) throws IOException, InvalidFormatException{
		List<String[]> result = new ArrayList<String[]>();
		int rowSize = 0;
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
		// 打开HSSFWorkbook
		XSSFWorkbook wb = new XSSFWorkbook(file);
		XSSFCell cell = null;	
		//循环sheet
		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
			XSSFSheet st = wb.getSheetAt(sheetIndex);
			// 第一行为标题，不取
			for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
				XSSFRow row = st.getRow(rowIndex);
				if (row == null) {
	                  continue;
	            }
				int tempRowSize = row.getLastCellNum() + 1;
				if (tempRowSize > rowSize) {
					rowSize = tempRowSize;
				}
				String[] values = new String[rowSize];
				Arrays.fill(values, "");
				boolean hasValue = false;
				for (int columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
					String value = "";
					cell = row.getCell(columnIndex);
					if (cell != null) {
						switch (cell.getCellType()) {
						case XSSFCell.CELL_TYPE_STRING:
						//	value = cell.getStringCellValue();
							value = cell.getRichStringCellValue().getString();
							break;
						case XSSFCell.CELL_TYPE_NUMERIC:
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								Date date = cell.getDateCellValue();
								if (date != null) {
									value = new SimpleDateFormat("yyyy-MM-dd").format(date);									
								}else{
									value = "";
								}
							}else{
			//					value = String.valueOf(cell.getNumericCellValue());
								value = cell.getRawValue();
							}			
							break;
						case XSSFCell.CELL_TYPE_FORMULA:
							// 导入时如果为公式生成的数据则无值
							/*if (!cell.getStringCellValue().equals("")) {
	                            value = cell.getStringCellValue();
	                         } else {
	                            value = cell.getNumericCellValue() + "";
	                         }*/
							value="";
							break;
						case XSSFCell.CELL_TYPE_BLANK:
							break;
						case XSSFCell.CELL_TYPE_ERROR:
	                        value = "";
	                        break;
						case XSSFCell.CELL_TYPE_BOOLEAN:
							value = (cell.getBooleanCellValue() == true ? "Y": "N");
	                         break;
						default:
							value = "";
						}
					}
					if (columnIndex == 0 && value.trim().equals("")) {
	                     break;
	                }
					values[columnIndex] = value.trim();
	                hasValue = true;
				}
				
				if (hasValue) {
	                result.add(values);
	            }
			}
		}
		in.close();
		
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		File file = new File("C:\\Users\\dys\\Desktop\\20151010211748.xls");
		for(String[] s:extractDataFromExcel(file,1,"xls")){
			System.out.println(s[4]);
		}
	}

}
