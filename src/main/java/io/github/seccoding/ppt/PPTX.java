package io.github.seccoding.ppt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.sl.usermodel.SlideShow;
import org.apache.poi.xslf.usermodel.XMLSlideShow;

public class PPTX {

	public SlideShow getSlideShow(File pptFile) {
		try {
			return new XMLSlideShow(new FileInputStream(pptFile));
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
}
