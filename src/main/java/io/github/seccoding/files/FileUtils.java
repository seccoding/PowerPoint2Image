package io.github.seccoding.files;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

/**
 * File 관련 API를 Mapping 함.
 * File API가 던지는 Exception 을 모두 RuntimeException으로 Mapping 해 던짐.
 * 
 * @author Minchang Jang (mcjang1116@gmail.com)
 */
public class FileUtils {

	/**
	 * 지정된 경로(디렉토리)가 디스크에 존재하지 않는다면 디렉토리를 새로 생성한다.
	 * mkdirs 사용
	 * @param path
	 * @return
	 */
	public static File makeDirectory(String path) {
		File directory = new File(path);
		
		if( !directory.exists() ) {
			directory.mkdirs();
		}
		
		return directory;
	}
	
	/**
	 * 파일을 생성함.
	 * @param path
	 * @return
	 */
	public static File makeFile(final String path) {
		
		final File file = new File(path);
		
		FileUtilAdapter.doFunc(new FileFunctionable(){
			@Override
			public void doFunc() throws IOException {
				file.createNewFile();
			}
		});
		
		return file;
	}
	
	/**
	 * 파일을 복사함.
	 * Channel Copy 이용함.
	 * @param originFilePath 원본 파일
	 * @param destFilePath 복제본 파일
	 */
	public static void copy(final String originFilePath, final String destFilePath) {
		FileUtilAdapter.doFunc(new FileFunctionable(){
			@Override
			public void doFunc() throws IOException {
				Files.copy(new File(originFilePath).toPath(), new File(destFilePath).toPath());
			}
		});
	}
	
	/**
	 * 파일을 이동시킴
	 * Channel Copy 이용함.
	 * @param originFilePath 원본 파일
	 * @param destFilePath 파일이 이동될 위치(파일명 까지 기재)
	 */
	public static void move(final String originFilePath, final String destFilePath) {
		FileUtilAdapter.doFunc(new FileFunctionable(){
			@Override
			public void doFunc() throws IOException {
				CopyOption[] copyOptions = new CopyOption[] {
						StandardCopyOption.REPLACE_EXISTING, // 원본파일이 존재한다면 덮어쓰기
						StandardCopyOption.COPY_ATTRIBUTES // 원본파일의 속성까지 모두 복사
				};
				
				Files.move(new File(originFilePath).toPath(), new File(destFilePath).toPath(), copyOptions);
			}
		});
	}
	
	/**
	 * 파일을 삭제함.
	 * @param path
	 * @return
	 */
	public static boolean deleteFile(String path) {
		File file = new File(path);
		if(file.exists()) {
			if(file.isFile()) {
				return file.delete();
				
			}
			else {
				throw new RuntimeException(path + "는 파일이 아닙니다.");
			}
		}
		else {
			throw new RuntimeException(path + "는 존재하지 않습니다.");
		}
	}
	
	/**
	 * 디렉토리를 삭제함
	 * @param path
	 * @return
	 */
	public static boolean deleteDirectory(String path) {
		
		File file = new File(path);
		if(file.exists()) {
			if(file.isDirectory()) {
				return file.delete();
			}
			else {
				throw new RuntimeException(path + "는 디렉토리가 아닙니다.");
			}
		}
		else {
			throw new RuntimeException(path + "는 존재하지 않습니다.");
		}
	}
	
	/**
	 * 파일을 읽어옴
	 * @param filePath
	 * @return
	 */
	public static String read(String filePath) {
		
		byte[] bytes = null;
		
		try {
			bytes = Files.readAllBytes(new File(filePath).toPath());
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		
		try {
			return new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	
	/**
	 * 지정된 파일에 내용을 추가함
	 * @param path
	 * @param contents
	 */
	public static void append(final String path, final String contents) {
		FileUtilAdapter.doFunc(new FileFunctionable(){
			@Override
			public void doFunc() throws IOException {
				OpenOption[] openOptions = new OpenOption[]{
						StandardOpenOption.APPEND
				};
				
				Files.write(new File(path).toPath(), contents.getBytes(), openOptions);
			}
		});
	}
	
}
 