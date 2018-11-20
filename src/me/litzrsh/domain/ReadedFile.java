package me.litzrsh.domain;

/**
 * @author 윤지영 <litzrsh@gmail.com>
 * @date   2018-11-20
 * @description
 *     텍스트 파일에서 읽어들인 파일
 */
public class ReadedFile {
	private String filepath;
	private String filename;
	private String filearea;

	public ReadedFile() {
		super();
	}

	public ReadedFile(final String filepath, final String filename, final String filearea) {
		super();
		this.filepath = filepath;
		this.filename = filename;
		this.filearea = filearea;
	}

	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFilearea() {
		return filearea;
	}
	public void setFilearea(String filearea) {
		this.filearea = filearea;
	}
}
