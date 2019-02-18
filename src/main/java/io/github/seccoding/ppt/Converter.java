package io.github.seccoding.ppt;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.poi.sl.draw.Drawable;
import org.apache.poi.sl.usermodel.Slide;
import org.apache.poi.sl.usermodel.SlideShow;

import io.github.seccoding.files.FileUtils;

public class Converter {

	private String destinationDirectory;

	private SlideShow slideShow;
	private Dimension pageSize;
	private List<Slide> slides;

	private String fileName;
	private double imageSizeZoomValue;
	private AffineTransform affineTransform;

	private String outputFileType;

	private String path;
	private String outputFolder;

	public Converter(String destDir) {
		this.destinationDirectory = destDir;
	}

	public Result convert(File powerpointFile, String path, String type) {
		outputFileType = type;

		this.path = path + "\\";
		if (path == null || path.length() == 0) {
			this.path = "";
		}

		setFileName(powerpointFile);
		setSlideShow(powerpointFile);
		setSlideSize();
		setSlides();
		setZoomImageSize(2);

		createDirectory();
		createImages();

		FileUtils.copy(powerpointFile.getAbsolutePath(), outputFolder + powerpointFile.getName());

		Result convertResult = new Result();
		convertResult.setFileName(this.fileName);
		convertResult.setOutputFolder(outputFolder);
		convertResult.setPageSize(slides.size());
		convertResult.setOriginalFilePath(outputFolder + powerpointFile.getName());

		return convertResult;
	}

	private void setFileName(File powerpointFile) {
		fileName = powerpointFile.getName();
		fileName = fileName.substring(0, fileName.lastIndexOf("."));
	}

	private void setSlideShow(File powerpointFile) {
		slideShow = SlideShowFactory.getSlideShow(powerpointFile);
	}

	private void setSlideSize() {
		pageSize = slideShow.getPageSize();
	}

	private void setSlides() {
		slides = slideShow.getSlides();
	}

	private void setZoomImageSize(double zoom) {
		imageSizeZoomValue = zoom;
		affineTransform = new AffineTransform();
		affineTransform.setToScale(zoom, zoom);
	}

	private void createDirectory() {
		File dir = new File(destinationDirectory + path + fileName + "\\");
		if (!dir.exists()) {
			dir.mkdirs();
		}

		outputFolder = dir.getAbsolutePath() + "\\";
	}

	private void createImages() {
		int slidesSize = slides.size();

		for (int i = 0; i < slidesSize; i++) {
			BufferedImage img = createBufferedImage();
			Graphics2D graphics = setImageGraphicOptions(img);

			// 이미지 그리기
			slides.get(i).draw(graphics);
			writeImage(i, img);
		}
	}

	private BufferedImage createBufferedImage() {
		int imageWidth = (int) Math.ceil(pageSize.width * imageSizeZoomValue);
		int imageHeight = (int) Math.ceil(pageSize.height * imageSizeZoomValue);
		return new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
	}

	private Graphics2D setImageGraphicOptions(BufferedImage img) {
		Graphics2D graphics = img.createGraphics();

		graphics.setPaint(Color.WHITE);
		// graphics.setComposite(AlphaComposite.DstOver);
		graphics.fill(new Rectangle2D.Float(0, 0, pageSize.width, pageSize.height));
		graphics.setTransform(affineTransform);

		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

		return graphics;
	}

	private void writeImage(int index, BufferedImage img) {
		// 파일로 저장
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(outputFolder + (index + 1) + "." + outputFileType);
			ImageIO.write(img, outputFileType, out);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}

	}

	public static class Result {
		private String originalFilePath;
		private String outputFolder;
		private String fileName;
		private int pageSize;

		public String getOriginalFilePath() {
			return originalFilePath;
		}

		public void setOriginalFilePath(String originalFilePath) {
			this.originalFilePath = originalFilePath;
		}

		public String getOutputFolder() {
			return outputFolder;
		}

		public void setOutputFolder(String outputFolder) {
			this.outputFolder = outputFolder;
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public int getPageSize() {
			return pageSize;
		}

		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}
	}

}
