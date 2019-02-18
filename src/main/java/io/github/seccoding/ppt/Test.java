package io.github.seccoding.ppt;

import java.io.File;

import io.github.seccoding.ppt.Converter.Result;

public class Test {

	public static void main(String[] args) {
		Converter converter = new Converter("C:\\Result\\");
		Result result = converter.convert(
				new File("C:\\Test PowerPoint.pptx"), "Output Path Dir", "png"
		);
		
		System.out.println(result.getFileName());
		System.out.println(result.getOutputFolder());
		System.out.println(result.getPageSize());
		System.out.println(result.getOriginalFilePath());
	}
	
}
