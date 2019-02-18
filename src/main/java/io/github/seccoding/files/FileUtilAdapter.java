package io.github.seccoding.files;

/**
 * 
 * FileUtils에서 제공하지 않는 다른 기능을 추가하기 위한 Template.
 * FileFunctionable interface를 Template Class로 사용해 사용할 수 있음.
 * File-IO에서 발생할 수 있는 모든 Exception 을 RuntimeException으로 변환해 다시 던짐.
 * 기 발생하던 Exception은 RuntimeException 의 getCause()로 받아올 수 있다.
 * 
 * @author Minchang Jang (mcjang1116@gmail.com)
 *
 */
public class FileUtilAdapter {
	
	public static void doFunc(FileFunctionable functionable) {
		try {
			functionable.doFunc();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
}
