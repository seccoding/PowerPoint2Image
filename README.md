# PowerPoint2Image
Apache-POI를 사용해 슬라이드를 이미지로 변환합니다.<br/> 
Apache-POI의 문제인지 알 수 없으나, 텍스트 박스의 위치가 약간씩 조정될 수 있습니다.

## 사용 방법
### maven dependency에 PowerPoint2Image-1.0.0.jar 파일을 추가할 경우
1. PowerPoint2Image-1.0.0.jar파일을 C:\에 복사합니다.
1. Maven 명령어를 이용해 .m2 Repository 에 PowerPoint2Image-1.0.0.jar 를 설치(저장)합니다.<pre>mvn install:install-file -Dfile=C:\PowerPoint2Image-1.0.0.jar -DgroupId=io.github.seccoding -DartifactId=PowerPointer2Image -Dversion=1.0.0 -Dpackaging=jar</pre>
1. 본인의 Project/pom.xml 에 dependency를 추가합니다.<pre>
	&lt;dependency&gt;
	&nbsp;&nbsp;&nbsp;&nbsp;&lt;groupId&gt;io.github.seccoding&lt;/groupId&gt;
	&nbsp;&nbsp;&nbsp;&nbsp;&lt;artifactId&gt;PowerPointer2Image&lt;/artifactId&gt;
	&nbsp;&nbsp;&nbsp;&nbsp;&lt;version&gt;1.0.0&lt;/version&gt;
	&lt;/dependency&gt;
</pre>

### 소스코드를 사용할 경우
1. Clone or Download 를 클릭합니다.
1. Download ZIP 을 클릭해 소스코드를 다운로드 받습니다.
1. PowerPointer2Image/pom.xml의 dependencies를 본인의 Project/pom.xml 에 붙혀넣습니다.
1. PowerPointer2Image/src 이하의 자바코드를 본인의 Project에 붙혀넣습니다. 
---

## 사용 방법
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