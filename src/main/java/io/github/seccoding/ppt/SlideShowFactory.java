package io.github.seccoding.ppt;

import java.io.File;

import org.apache.poi.sl.usermodel.SlideShow;

public class SlideShowFactory {

	public static SlideShow getSlideShow(File pptFile) {
		
		String fileName = pptFile.getName();
		if ( fileName.endsWith(".ppt") ) {
			return new PPT().getSlideShow(pptFile);
		}
		else if ( fileName.endsWith(".pptx") ) {
			return new PPTX().getSlideShow(pptFile);
		}
		
		throw new RuntimeException("PowerPoint 파일이 아닙니다.");
		
	}
	
}
