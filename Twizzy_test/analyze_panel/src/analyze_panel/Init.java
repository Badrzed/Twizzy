package analyze_panel;

import java.io.File;

import org.opencv.core.*;
import org.opencv.highgui.*;
import org.opencv.imgproc.*;

public class Init {
	
	public static Mat ReadImage(String fichier) {
		File f = new File(fichier);
		Mat m = Highgui.imread(f.getAbsolutePath());
		return m;
	}	

	public static Mat seuilplusieur(Mat hsv_image) {
		Mat threshold_img=new Mat();
		Mat threshold_img1=new Mat();
		Mat threshold_img2=new Mat();
		Core.inRange(hsv_image, new Scalar(0,100,100), new Scalar(10,255,255), threshold_img1);
		Core.inRange(hsv_image, new Scalar(160,100,100), new Scalar(179,255,255), threshold_img2);
		Core.bitwise_or(threshold_img1, threshold_img2, threshold_img);
		Imgproc.GaussianBlur(threshold_img, threshold_img, new Size(9,9), 2,2);
		return threshold_img;
	}
	
	public static Mat hsvimagemaker(Mat m) {
		Mat hsv_image= Mat.zeros(m.size(),m.type());
		Imgproc.cvtColor(m,hsv_image, Imgproc.COLOR_BGR2HSV);
		return hsv_image;
	}
	
	public static void init() {
		
	}
}
