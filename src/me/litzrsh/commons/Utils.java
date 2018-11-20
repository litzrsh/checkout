package me.litzrsh.commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import me.litzrsh.domain.ReadedFile;

/**
 * 프로그램에서 사용되는 공용 메소드 목록
 * @author 윤지영 <litzrsh@gmail.com>
 * @date   2018-11-20
 */
public class Utils {
	/**
	 * 신규파일로 취급해야하는 확장자인지 확인
	 * @param filepath
	 *     파일경로
	 * @return
	 */
	public static Boolean checkForcedNewFile(final String filepath) {
		List<String> compareList = Arrays.asList(Constants.FILE_EXTENSION_LIST);
		File         file        = new File(Constants.BASE_TARGET_PATH+filepath);
		String       extension   = FilenameUtils.getExtension(filepath);
		if (!file.exists()) {
			return true;
		} else if (compareList.contains(extension.toLowerCase())) {
			return true;
		}
		return false;
	}

	/**
	 * 파일 영역을 가져온다 (CO/BO/FO/MO/Petra)
	 * @param filepath
	 *     파일경로
	 * @return
	 */
	public static String getFilearea(final String filepath) {
		if (filepath.contains("BeautyNet_Common")) {
			return "CO";
		} else if (filepath.contains("BeautyNet_Office")) {
			return "BO";
		} else if (filepath.contains("BeautyNet_FrontWeb")) {
			return "FO";
		} else if (filepath.contains("BeautyNet_Mobile")) {
			return "MO";
		}
		return "Petra";
	}

	/**
	 * 신규작성된 파일을 복사한다
	 * @param filepath
	 *     파일경로
	 * @throws Exception
	 */
	public static void copyFile(final String filepath) throws Exception {
		File  file            = new File(Constants.BASE_SOURCE_PATH+filepath);
		String outputFilepath = "./output/"+Constants.CURRENT_TIME+"/"+Constants.CURRENT_TIME+"_신규파일"+filepath;
		// 파일이 존재하지 않음
		if (!file.exists()) {
			System.out.println("[ERROR] 파일 `"+Constants.BASE_SOURCE_PATH+filepath+"'은(는) 존재하지 않는 파일입니다.");
			return;
		} else {
			FileUtils.copyFile(file, new File(outputFilepath));
		}
	}

	/**
	 * 데이터를 엑셀파일로 쓴다
	 * @param oldFileList
	 *     기존파일목록
	 * @param newFileList
	 *     신규파일목록
	 * @throws Exception
	 */
	public static void writeExcelFile(final List<ReadedFile> oldFileList, final List<ReadedFile> newFileList) throws Exception {
		// 템플릿 파일을 불러들임
		final XSSFWorkbook workbook   = new XSSFWorkbook(new FileInputStream(new File(Constants.EXCEL_TEMPLATE_FILENAME)));
		final XSSFSheet    oldSheet   = workbook.getSheetAt(0);  // 기존파일목록시트
		final XSSFSheet    newSheet   = workbook.getSheetAt(1);  // 신규파일목록시트
		writeExcelSheet(oldSheet, oldFileList);
		writeExcelSheet(newSheet, newFileList);
		(new File(FilenameUtils.getFullPath(Constants.OUTPUT_EXCEL_FILENAME))).mkdirs();
		FileOutputStream fos = new FileOutputStream(new File(Constants.OUTPUT_EXCEL_FILENAME));
		workbook.write(fos);
		fos.close();
		workbook.close();
	}

	/**
	 * 데이터를 시트에 쓴다
	 * @param sheet
	 *     목록을 적을 시트
	 * @param fileList
	 *     파일목록
	 * @throws Exception
	 */
	private static void writeExcelSheet(final XSSFSheet sheet, final List<ReadedFile> fileList) throws Exception {
		if (fileList.isEmpty()) {
			return;
		}
		// 목록 사이즈
		int length                      = fileList.size();
		Map<String, Integer> mergeStart = new HashMap<String, Integer>();
		Map<String, Integer> mergeEnd   = new HashMap<String, Integer>();
		String               cursor     = null;
		for (int i = 0; i < length; i++) {
			ReadedFile vo   = fileList.get(i);
			XSSFRow    row  = null;
			if (cursor == null || !vo.getFilearea().equals(cursor)) {
				mergeStart.put(vo.getFilearea(), i+1);
			}
			if (i > 0) {
				row = sheet.createRow(i+1);
				row.createCell(0).setCellStyle(sheet.getRow(1).getCell(0).getCellStyle());
				row.createCell(1).setCellStyle(sheet.getRow(1).getCell(1).getCellStyle());
				row.createCell(2).setCellStyle(sheet.getRow(1).getCell(2).getCellStyle());
			} else {
				row = sheet.getRow(i+1);
			}
			row.getCell(0).setCellValue(vo.getFilearea());
			row.getCell(1).setCellValue(vo.getFilepath());
			row.getCell(2).setCellValue(vo.getFilename());
			mergeEnd.put(vo.getFilearea(), i+1);
			cursor = vo.getFilearea();
		}
		for (String key : mergeStart.keySet()) {
			Integer range = Integer.parseInt(mergeEnd.get(key).toString()) - Integer.parseInt(mergeStart.get(key).toString());
			System.out.println("[MESSAGE] 범위:"+key+", ("+mergeStart.get(key)+" - "+mergeEnd.get(key)+") :: "+range);
			if (range > 0) {
				sheet.addMergedRegion(new CellRangeAddress(mergeStart.get(key), mergeEnd.get(key), 0, 0));
			}
		}
	}
}
