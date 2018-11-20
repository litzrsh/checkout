package me.litzrsh.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import me.litzrsh.commons.Constants;
import me.litzrsh.commons.ReadedFileComparator;
import me.litzrsh.commons.Utils;
import me.litzrsh.domain.ReadedFile;

/**
 * 그냥 만들어본 서비스
 * @author 윤지영 <litzrsh@gmail.com>
 * @date   2018-11-20
 */
public class CheckoutService {
	/**
	 * SVN 커밋 목록으로부터 출력파일을 만든다
	 */
	public static void makeCheckoutFileList() {
		final File rawTextFile = new File(Constants.RAW_TEXT_FILE_PATH);
		// 파일을 찾지 못한 경우, 에러메시지 출력 및 종료
		if (!rawTextFile.exists()) {
			System.out.println("[CRITICAL ERROR] 파일 `"+Constants.RAW_TEXT_FILE_PATH+"'을(를) 찾을 수 없습니다.");
			return;
		}
		FileInputStream fis    = null;
		BufferedReader  reader = null;
		try {
			fis                          = new FileInputStream(rawTextFile);
			reader                       = new BufferedReader(new InputStreamReader(fis));
			String line                  = null;
			List<ReadedFile> oldFileList = new ArrayList<ReadedFile>();
			List<ReadedFile> newFileList = new ArrayList<ReadedFile>();
			while ((line = reader.readLine()) != null) {
				String[] tfp      = line.replaceAll("\\/trunk(\\/skyware)?", "").split(":");
				String   filepath = tfp[0];
				Boolean  isNew    = tfp.length > 1 ? "Y".equals(tfp[1].toUpperCase()) : Utils.checkForcedNewFile(filepath);
				if (isNew) { // 신규파일이다
					newFileList.add(new ReadedFile(filepath, FilenameUtils.getName(filepath), Utils.getFilearea(filepath)));
					// 신규파일은 별도로 복사한다
					Utils.copyFile(filepath);
				} else {     // 기존파일이다
					oldFileList.add(new ReadedFile(filepath, FilenameUtils.getName(filepath), Utils.getFilearea(filepath)));
				}
			}
			oldFileList.sort(new ReadedFileComparator());
			newFileList.sort(new ReadedFileComparator());
			Utils.writeExcelFile(oldFileList, newFileList);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 열린 InputStream을 닫음
				reader.close();
				fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
