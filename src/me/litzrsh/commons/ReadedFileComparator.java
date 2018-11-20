package me.litzrsh.commons;

import java.util.Comparator;

import me.litzrsh.domain.ReadedFile;

/**
 * 정렬을 위한 비교 연산자
 * @author 윤지영 <litzrsh@gmail.com>
 * @date   2018-11-20
 */
public class ReadedFileComparator implements Comparator<ReadedFile> {
	public int compare(ReadedFile a, ReadedFile b) {
		return a.getFilepath().compareTo(b.getFilepath());
	}
}
