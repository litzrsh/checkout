package me.litzrsh.controller;

import me.litzrsh.service.CheckoutService;

/**
 * 커밋 내용을 읽어들여 스테이지 반영을 위한 Excel파일을 작성한다
 * @author 윤지영 <litzrsh@gmail.com>
 * @date   2018-11-20
 */
public class MainController {
	/**
	 * 메인 함수
	 * @param args
	 */
	public static void main(String[] args) {
		// 서비스에서 메소드를 불러온다
		CheckoutService.makeCheckoutFileList();
	}
}
