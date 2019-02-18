# PowerPoint2Image
Apache-POI를 사용해 슬라이드를 이미지로 변환합니다.<br/> 
Apache-POI의 문제인지 알 수 없으나, 텍스트 박스의 위치가 약간씩 조정될 수 있습니다.

<pre>
package io.github.seccoding.ppt;

import java.io.File;

import io.github.seccoding.ppt.Converter.Result;

public class Test {

	public static void main(String[] args) {
		Converter converter = new Converter("C:\\Result\\"); // 이미지 파일이 생성될 Root Directory
		Result result = converter.convert(
				new File("C:\\Test PowerPoint.pptx"), // 이미지로 변환할 ppt, pptx 파일 경로
				"Output Path Dir", // 이미지 파일이 생성될 Leaf Directory
				"png" // 이미지 타입
		);
		
		System.out.println(result.getFileName());
		System.out.println(result.getOutputFolder());
		System.out.println(result.getPageSize());
		System.out.println(result.getOriginalFilePath());
	}
	
}
</pre>