package analyze_panel;

import java.io.File;
import java.util.Vector;

import org.opencv.core.*;
import org.opencv.highgui.*;
import org.opencv.imgproc.*;

public class Init {
	private static Mat imageread;
	private static  Mat seuilplusieurdone;
	private static Mat hsvimagemade;
	private static Vector<Mat> channelsmade;
	private static String fichier;
	
	public Init(Mat imageread,Mat hsvimagemade,String fichier) {

		this.setImageread(setimageread(fichier));
		this.hsvimagemade=hsvimagemaker(imageread);
		this.channelsmade=channels(imageread);
		this.seuilplusieurdone=seuilplusieur(hsvimagemade);
		
	}
	
	
	
	public Vector<Mat> channels(Mat m) {
		Vector<Mat> channel = new Vector<>();
		Core.split(m,  channel);
		return channel;
	}

	
 	public Mat ReadImage(String fichier) {
		File f = new File(fichier);
		return Highgui.imread(f.getAbsolutePath());
		
	}	

	public  Mat seuilplusieur(Mat hsv_image) {
		Mat threshold_img=new Mat();
		Mat threshold_img1=new Mat();
		Mat threshold_img2=new Mat();
		Core.inRange(hsv_image, new Scalar(0,100,100), new Scalar(10,255,255), threshold_img1);
		Core.inRange(hsv_image, new Scalar(160,100,100), new Scalar(179,255,255), threshold_img2);
		Core.bitwise_or(threshold_img1, threshold_img2, threshold_img);
		Imgproc.GaussianBlur(threshold_img, threshold_img, new Size(9,9), 2,2);
		return threshold_img;
	}

public Mat setimageread(String fichier) {
	return ReadImage(fichier);
}
public Mat hsvimagemaker(Mat m) {
		Mat hsv_image= Mat.zeros(m.size(),m.type());
		Imgproc.cvtColor(m,hsv_image, Imgproc.COLOR_BGR2HSV);
		return hsv_image;
	}
	
	public static  Mat getimage() {
		return getImageread();
		
	}
	public Mat getimageseuil() {
		return seuilplusieurdone;
	}
	public Mat gethsv() {
		return hsvimagemade;
	}
	public Vector<Mat> getchannels(){
		return channelsmade;
	}



	public String getFichier() {
		return fichier;
	}



	public void setFichier(String fichier) {
		this.fichier = fichier;
	}



	public static Mat getImageread() {
		return Init.imageread;
	}



	public void setImageread(Mat imageread) {
		Init.imageread = imageread;
	}
	
}
