package me.litzrsh.commons;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 프로그램에서 사용되는 상수 집합
 *
 * @author 윤지영 <litzrsh@gmail.com>
 * @date   2018-11-20
 */
public class Constants {
	public static final String   RAW_TEXT_FILE_PATH         = "./datafile.txt";                                               // 파일목록이 담긴 txt파일
	public static final String   EXCEL_TEMPLATE_FILENAME    = "./template.xlsx";                                              // 엑셀템플릿 파일
	public static final String   BASE_SOURCE_PATH           = "C:/beautynet_dev/workspace";                                   // 소스 워크스페이스
	public static final String   BASE_TARGET_PATH           = "C:/beautynet_dev/workspace/z_dev";                             // 타겟 워크스페이스
	public static final String[] FILE_EXTENSION_LIST        = new String[] { "jpg", "jpeg", "png", "gif", "xlsx", "xls" };    // 강제로 신규파일로 만들기 위한 확장자 목록
	public static final String   CURRENT_TIME               = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());      // 지금은 몇시인가
	public static final String   OUTPUT_EXCEL_FILENAME      = "./output/"+CURRENT_TIME+"/"+CURRENT_TIME+"_적용파일목록.xlsx"; // 지금은 몇시인가를 통해 출력 엑셀파일 이름을 만든다
}
