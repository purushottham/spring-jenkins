package com.i9.complience.utility;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.i9.complience.model.AuthorizationDTO;
import com.i9.complience.model.Data;
import com.i9.complience.model.DocumentDTO;

public class AuthroziationDetails {

	public List<AuthorizationDTO> loadAuthroziationDetails(String inputFilePath) {
		String fileName = "E://I9Services.xlsx";
		List<AuthorizationDTO> authDtos = new ArrayList<AuthorizationDTO>();
		int totalRows = 321;
		int totalColumns = 0;
		try {
			FileInputStream file = new FileInputStream(new File(inputFilePath));
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheet("Section 1");
			totalColumns = sheet.getRow(0).getLastCellNum();
			Map<Integer, List<Data>> map = new LinkedHashMap<>();
			for (int i = 1; i < totalColumns; i++) {
				List<Data> listData = new ArrayList<>();
				for (int j = 1; j <= totalRows; j++) {
					listData.add(new Data(j, i));
				}
				map.put(i, listData);
				i++;
			}

			for (Entry<Integer, List<Data>> d : map.entrySet()) {
				List<Data> val = d.getValue();
				AuthorizationDTO authDto = new AuthorizationDTO();
				Map<Integer, String> excelData = new HashMap<>();
				for (int i = 0; i < val.size(); i++) {
					excelData.put(i, getCellData(sheet, val.get(i)));
				}
				if (excelData != null) {
					authDto.setFirstName(excelData.get(0));
					authDto.setLastName(excelData.get(1));
					authDto.setDateOfBirth(DateUtlity.convertStringToDate(excelData.get(2)));
					authDto.setPhoneNumber(excelData.get(3));
					authDto.setSsn(excelData.get(4));
					authDto.setEmailId(excelData.get(5));
					authDto.setCurrentAddress(excelData.get(6));
					authDto.setWorkLocation(excelData.get(7));
					authDto.setLocalOrRemoteEmployment(excelData.get(8));
					authDto.setDateOfEmployment(DateUtlity.convertStringToDate(excelData.get(9)));
					authDto.setDateOfExit(DateUtlity.convertStringToDate(excelData.get(10)));
					authDto.setIsI9DoneInThePast(excelData.get(11));
					authDto.setIsI9Current(excelData.get(12));
					authDto.setImmigrationStatus(excelData.get(13));
					authDto.setListA(excelData.get(14));
					List<DocumentDTO> ldto = new ArrayList<>();
					DocumentDTO dto = new DocumentDTO(excelData.get(15), excelData.get(16), excelData.get(17),
							excelData.get(18), excelData.get(19), excelData.get(20));
					DocumentDTO dto1 = new DocumentDTO(excelData.get(21), excelData.get(22), excelData.get(23),
							excelData.get(24), excelData.get(19), excelData.get(25));
					DocumentDTO dto2 = new DocumentDTO(excelData.get(27), excelData.get(28), excelData.get(29),
							excelData.get(30), excelData.get(19), excelData.get(31));
					DocumentDTO dto3 = new DocumentDTO(excelData.get(33), excelData.get(34), excelData.get(35),
							excelData.get(36), excelData.get(19), excelData.get(37));
					ldto.add(dto);
					ldto.add(dto1);
					ldto.add(dto2);
					ldto.add(dto3);
					authDto.setDocumetDTOList(ldto);
					authDto.setNotes(excelData.get(38));

				}
				authDtos.add(authDto);
			}
			// System.out.println(authDtos);
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return authDtos;

	}

	private static String getCellData(XSSFSheet sheet, Data d) throws Exception {

		Row row = sheet.getRow(d.getRow());
		Cell cell = row.getCell(d.getCol());

		if (cell != null) {
			switch (cell.getCellTypeEnum()) {
			case NUMERIC:
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					return DateUtlity.convertDateToString(cell.getDateCellValue());
				}
				return String.valueOf(cell.getNumericCellValue());
			case FORMULA:
				return String.valueOf(cell.getNumericCellValue());
			case STRING:
				return String.valueOf(cell.getStringCellValue());
			case BLANK:
				return "";
			default:
				return String.valueOf(cell.getBooleanCellValue());
			}
		}
		return "";

	}

}
