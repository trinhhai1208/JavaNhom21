package frontend.utils;

import java.awt.Image;

import javax.swing.ImageIcon;

public class ImageProcess {
	//xử lý hình ảnh
	public static ImageIcon scaled(String fileName,int width,int height) {
		ImageIcon logo=new ImageIcon(fileName);
		Image logoImg=logo.getImage();
		Image scaledLogo=logoImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon Logo=new ImageIcon(scaledLogo);
		return Logo;
	}
}
