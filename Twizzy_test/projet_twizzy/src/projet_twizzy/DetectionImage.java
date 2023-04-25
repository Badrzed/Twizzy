package projet_twizzy;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.*;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class DetectionImage {
	
	public  void rgb(Init object) {
		Mat dst;
		dst = Mat.zeros(object.getimageread().size(),object.getimageread().type());
		Vector<Mat> chans =new Vector<>();
		
		Mat empty=Mat.zeros(object.getimageread().size(),CvType.CV_8UC1);
		for(int i=0;i<object.getchannels().size();i++) {
			chans.removeAllElements();
			for(int j=0; j<object.getchannels().size();j++) {
				if(j!=i) {
					chans.add(empty);}
				else {chans.add(object.getchannels().get(i));
					
				}
			}
		
		Core.merge(chans, dst);
	}}
	
	public  void rgb2hsv(Init object) {
		Mat output = object.gethsv();
		Core.split(object.getimageread(),object.getchannels());
		double[][] hsv_values= {{1,255,255},{179,1,255},{179,0,1}};
		for(int i=0;i<3;i++) {
			Mat[] chans=new Mat[3];
			for(int j=0;j<3;j++) {
				Mat empty=Mat.ones(object.getimageread().size(),CvType.CV_8UC1);
				Mat comp =Mat.ones(object.getimageread().size(),CvType.CV_8UC1);
				Scalar v= new Scalar(hsv_values[i][j]);
				Core.multiply(empty, v, comp);
				chans[j]=comp;
			}
			chans[i]=object.getchannels().get(i);
			Mat dst=Mat.zeros(output.size(),output.type());
			Mat res=Mat.ones(dst.size(),dst.type());
			Core.merge(Arrays.asList(chans), dst);
			Imgproc.cvtColor(dst, res, Imgproc.COLOR_HSV2BGR);
		}
	}
	
	
	public  void detectioncerclerouge(Init object) {
		List<MatOfPoint>contours=object.getDetectercontourlist();
		MatOfPoint2f matOfPoint2f =new MatOfPoint2f();
		float[] radius =new float[1];
		Point center= new Point();
		for(int c=0;c<contours.size();c++) {
			MatOfPoint contour = contours.get(c);
			double contourArea =Imgproc.contourArea(contour);
			matOfPoint2f.fromList(contour.toList());
			Imgproc.minEnclosingCircle(matOfPoint2f, center, radius);
			if((contourArea/(Math.PI*radius[0]*radius[0]))>=0.8) {
				Core.circle(object.getimageread(), center, (int)radius[0], new Scalar(0,255,0),2);
			}
		}

		
	}	
	public static Mat detection_ball(Init object) {
		Mat matricenull=new Mat();
		List<MatOfPoint>contours=object.getDetectercontourlist();
		MatOfPoint2f matOfPoint2f =new MatOfPoint2f();
		float[] radius =new float[1];
		Point center= new Point();
		for(int c=0;c<contours.size();c++) {
			MatOfPoint contour = contours.get(c);
			double contourArea =Imgproc.contourArea(contour);
			matOfPoint2f.fromList(contour.toList());
			Imgproc.minEnclosingCircle(matOfPoint2f, center, radius);
			if((contourArea/(Math.PI*radius[0]*radius[0]))>=0.8) {
				//Core.circle(object.getimageread(), center, (int)radius[0], new Scalar(0,255,0),2);
				Rect rect =Imgproc.boundingRect(contour);
				//Core.rectangle(object.getimageread(), new Point(rect.x,rect.y), new Point(rect.x+(double)rect.width,rect.y+(double)rect.height), new Scalar(0,255,0),2);
				Mat tmp=object.getimageread().submat(rect.y,rect.y+rect.height,rect.x,rect.x+rect.width);
				Mat ball=Mat.zeros(tmp.size(),tmp.type());
				//ImShow("ball",tmp);
				tmp.copyTo(ball);
				return ball;
				
			}
			else {
				
				
			}
		}
		return matricenull;
		
	}
	
	public static  void misealecchelle(String panel,Init object) {
		Mat sroadSign=Highgui.imread(panel);
		Mat sObject=new Mat();
		Imgproc.resize(object.getimageread(),sObject,sroadSign.size());
		Mat grayObject=new Mat(sObject.rows(),sObject.cols(),sObject.type());
		Imgproc.cvtColor(sObject,  grayObject, Imgproc.COLOR_BGRA2GRAY);
		Core.normalize(grayObject, grayObject, 0, 255, Core.NORM_MINMAX);
		Mat graySign = new Mat(sroadSign.rows(), sroadSign.cols(), sroadSign.type());
		Imgproc.cvtColor(sroadSign,  graySign,  Imgproc.COLOR_BGRA2GRAY);
		Core.normalize(graySign,  graySign, 0, 255, Core.NORM_MINMAX);	
		//Extraction des caractéristiques
		FeatureDetector orbDetector = FeatureDetector.create(FeatureDetector.ORB);
		DescriptorExtractor orbExtractor = DescriptorExtractor.create(DescriptorExtractor.ORB);
		MatOfKeyPoint objectKeyPoints = new MatOfKeyPoint();
		orbDetector.detect(grayObject, objectKeyPoints);
		MatOfKeyPoint signKeypoints = new MatOfKeyPoint();
		orbDetector.detect(graySign, signKeypoints);
		Mat objectDescriptor = new Mat(object.getimageread().rows(), object.getimageread().cols(), object.getimageread().type());
		orbExtractor.compute(grayObject, objectKeyPoints, objectDescriptor);
		Mat signDescriptor = new Mat(sroadSign.rows(), sroadSign.cols(), sroadSign.type());
		orbExtractor.compute(grayObject, signKeypoints, signDescriptor);	
			//matching
		MatOfDMatch matchs = new MatOfDMatch();
		DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);
		matcher.match(objectDescriptor, signDescriptor, matchs);
		//System.out.println(matchs.dump());
		Mat matchedImage = new Mat(sroadSign.rows(), sroadSign.cols()*2, sroadSign.type());
		Features2d.drawMatches(sObject,  objectKeyPoints,  sroadSign,  signKeypoints,  matchs,  matchedImage);
		ImShow("Test",matchedImage);
		}
	public static void ImShow(String title,Mat m) {
		MatOfByte matOfByte= new MatOfByte();
		Highgui.imencode(".png",m,matOfByte);
		byte[] byteArray = matOfByte.toArray();
		BufferedImage bufImage=null;
		try {
			InputStream in =new ByteArrayInputStream(byteArray);
			bufImage=ImageIO.read(in);
			JFrame frame = new JFrame();
			frame.setTitle(title);
			frame.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
			frame.pack();
			frame.setVisible(true);
		}catch(Exception e) {
			e.printStackTrace();}}

	
public static  Mat misealecchelle2(String panel,Init object) {
	Mat sroadSign=Highgui.imread(panel);
	Mat sObject=new Mat();
	Imgproc.resize(object.getimageread(),sObject,sroadSign.size());
	Mat grayObject=new Mat(sObject.rows(),sObject.cols(),sObject.type());
	Imgproc.cvtColor(sObject,  grayObject, Imgproc.COLOR_BGRA2GRAY);
	Core.normalize(grayObject, grayObject, 0, 255, Core.NORM_MINMAX);
	Mat graySign = new Mat(sroadSign.rows(), sroadSign.cols(), sroadSign.type());
	Imgproc.cvtColor(sroadSign,  graySign,  Imgproc.COLOR_BGRA2GRAY);
	Core.normalize(graySign,  graySign, 0, 255, Core.NORM_MINMAX);	
	//Extraction des caractéristiques
	FeatureDetector orbDetector = FeatureDetector.create(FeatureDetector.ORB);
	DescriptorExtractor orbExtractor = DescriptorExtractor.create(DescriptorExtractor.ORB);
	MatOfKeyPoint objectKeyPoints = new MatOfKeyPoint();
	orbDetector.detect(grayObject, objectKeyPoints);
	MatOfKeyPoint signKeypoints = new MatOfKeyPoint();
	orbDetector.detect(graySign, signKeypoints);
	Mat objectDescriptor = new Mat(object.getimageread().rows(), object.getimageread().cols(), object.getimageread().type());
	orbExtractor.compute(grayObject, objectKeyPoints, objectDescriptor);
	Mat signDescriptor = new Mat(sroadSign.rows(), sroadSign.cols(), sroadSign.type());
	orbExtractor.compute(grayObject, signKeypoints, signDescriptor);	
		//matching
	MatOfDMatch matchs = new MatOfDMatch();
	DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);
	matcher.match(objectDescriptor, signDescriptor, matchs);
	//System.out.println(matchs.dump());
	Mat matchedImage = new Mat(sroadSign.rows(), sroadSign.cols()*2, sroadSign.type());
	Features2d.drawMatches(sObject,  objectKeyPoints,  sroadSign,  signKeypoints,  matchs,  matchedImage);
	ImShow("Test",matchedImage);
	return matchs;
	}
	public static  int misealecchelle3(String panel,Init object) {
		Mat sroadSign=Highgui.imread(panel);
		Mat sObject=new Mat();
		Imgproc.resize(object.getimageread(),sObject,sroadSign.size());
		Mat grayObject=new Mat(sObject.rows(),sObject.cols(),sObject.type());
		Imgproc.cvtColor(sObject,  grayObject, Imgproc.COLOR_BGRA2GRAY);
		Core.normalize(grayObject, grayObject, 0, 255, Core.NORM_MINMAX);
		Mat graySign = new Mat(sroadSign.rows(), sroadSign.cols(), sroadSign.type());
		Imgproc.cvtColor(sroadSign,  graySign,  Imgproc.COLOR_BGRA2GRAY);
		Core.normalize(graySign,  graySign, 0, 255, Core.NORM_MINMAX);	
		//Extraction des caractéristiques
		FeatureDetector orbDetector = FeatureDetector.create(FeatureDetector.ORB);
		DescriptorExtractor orbExtractor = DescriptorExtractor.create(DescriptorExtractor.ORB);
		MatOfKeyPoint objectKeyPoints = new MatOfKeyPoint();
		orbDetector.detect(grayObject, objectKeyPoints);
		MatOfKeyPoint signKeypoints = new MatOfKeyPoint();
		orbDetector.detect(graySign, signKeypoints);
		Mat objectDescriptor = new Mat(object.getimageread().rows(), object.getimageread().cols(), object.getimageread().type());
		orbExtractor.compute(grayObject, objectKeyPoints, objectDescriptor);
		Mat signDescriptor = new Mat(sroadSign.rows(), sroadSign.cols(), sroadSign.type());
		orbExtractor.compute(grayObject, signKeypoints, signDescriptor);	
			//matching
		MatOfDMatch matchs = new MatOfDMatch();
		DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);
		matcher.match(objectDescriptor, signDescriptor, matchs);
		String str= matchs.dump();
		//System.out.println(str);
		String[] sentences = str.split("\\s*(; |, |\\s)\\s*");
		
		/*Ici j'ai pris le matchs.dump qui donne la matrice des matcher et j'ai recuperer les donnees
		 * de la matrice matcher et j'ai fait la moyenne de la deuxieme collonne sachant qu'il il ya 4 colonnes et 266 lignes 
		 * il faut recuperer la deuxieme valeur de chaque lignes sachant qu'on a decouper la matrice en une liste qui contient toutes les valeurs */
		int sum=0;
		for(int i=1;i<sentences.length;i=i+4) {//ici on commence a 1 pour prendre la deuxieme valeur de la ligne 1
			//on itere de 4 en 4 car 4 colonne et on veut toujours la deuxieme colonnne
			try {
			    Integer b = Integer.parseInt(sentences[i]);
			    sum=sum+b;
			} catch (NumberFormatException e) {
			    System.out.println("Error parsing " + sentences[i] + " to an Integer");
			    e.printStackTrace();
			}
			
		}
		//System.out.println(sentences.length);
		return sum/sentences.length;// et donc la fonction renvoie la moyenne de la deuxieme colonne
		
		//System.out.println(matchs.dump());
		/*Mat matchedImage = new Mat(sroadSign.rows(), sroadSign.cols()*2, sroadSign.type());
		Features2d.drawMatches(sObject,  objectKeyPoints,  sroadSign,  signKeypoints,  matchs,  matchedImage);
		ImShow("Test",matchedImage);*/
		//System.out.println();
		
		

	}

public static  int misealecchelle4(String panel,Init object) {
	Mat sroadSign=Highgui.imread(panel);
	Mat sObject=new Mat();
	Imgproc.resize(object.getimageread(),sObject,sroadSign.size());
	Mat grayObject=new Mat(sObject.rows(),sObject.cols(),sObject.type());
	Imgproc.cvtColor(sObject,  grayObject, Imgproc.COLOR_BGRA2GRAY);
	Core.normalize(grayObject, grayObject, 0, 255, Core.NORM_MINMAX);
	Mat graySign = new Mat(sroadSign.rows(), sroadSign.cols(), sroadSign.type());
	Imgproc.cvtColor(sroadSign,  graySign,  Imgproc.COLOR_BGRA2GRAY);
	Core.normalize(graySign,  graySign, 0, 255, Core.NORM_MINMAX);	
	//Extraction des caractéristiques
	FeatureDetector orbDetector = FeatureDetector.create(FeatureDetector.ORB);
	DescriptorExtractor orbExtractor = DescriptorExtractor.create(DescriptorExtractor.ORB);
	MatOfKeyPoint objectKeyPoints = new MatOfKeyPoint();
	orbDetector.detect(grayObject, objectKeyPoints);
	MatOfKeyPoint signKeypoints = new MatOfKeyPoint();
	orbDetector.detect(graySign, signKeypoints);
	Mat objectDescriptor = new Mat(object.getimageread().rows(), object.getimageread().cols(), object.getimageread().type());
	orbExtractor.compute(grayObject, objectKeyPoints, objectDescriptor);
	Mat signDescriptor = new Mat(sroadSign.rows(), sroadSign.cols(), sroadSign.type());
	orbExtractor.compute(grayObject, signKeypoints, signDescriptor);	
		//matching
	MatOfDMatch matchs = new MatOfDMatch();
	DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);
	matcher.match(objectDescriptor, signDescriptor, matchs);
	String str= matchs.dump();
	//System.out.println(str);
	String[] sentences = str.split("\\s*(; |, |\\s)\\s*");
	
	/*Ici j'ai pris le matchs.dump qui donne la matrice des matcher et j'ai recuperer les donnees
	 * de la matrice matcher et j'ai fait la moyenne de la deuxieme collonne sachant qu'il il ya 4 colonnes et 266 lignes 
	 * il faut recuperer la deuxieme valeur de chaque lignes sachant qu'on a decouper la matrice en une liste qui contient toutes les valeurs */
	int sum=0;
	int nombredeterme=sentences.length;
	for(int i=1;i<sentences.length;i=i+4) {//ici on commence a 1 pour prendre la deuxieme valeur de la ligne 1
		//on itere de 4 en 4 car 4 colonne et on veut toujours la deuxieme colonnne
		try {
		    Integer b = Integer.parseInt(sentences[i]);
		    int c=  misealecchelle3(panel,object);
		   if( Math.abs(b-c)<100) {// utiliser d'
		    sum=sum+b;
		    }
		   else{
			   nombredeterme--;
		   }
		} catch (NumberFormatException e) {
		    System.out.println("Error parsing " + sentences[i] + " to an Integer");
		    e.printStackTrace();
		}
		
	}
	//System.out.println(sentences.length);
	return sum/nombredeterme;// et donc la fonction renvoie la moyenne de la deuxieme colonne
	
	//System.out.println(matchs.dump());
	/*Mat matchedImage = new Mat(sroadSign.rows(), sroadSign.cols()*2, sroadSign.type());
	Features2d.drawMatches(sObject,  objectKeyPoints,  sroadSign,  signKeypoints,  matchs,  matchedImage);
	ImShow("Test",matchedImage);*/
	//System.out.println();
	
	

}
public static double misealecchelle5(String panel,Init object) {
	Mat sroadSign=Highgui.imread(panel);
	Mat sObject=new Mat();
	Imgproc.resize(object.getimageread(),sObject,sroadSign.size());
	Mat grayObject=new Mat(sObject.rows(),sObject.cols(),sObject.type());
	Imgproc.cvtColor(sObject,  grayObject, Imgproc.COLOR_BGRA2GRAY);
	Core.normalize(grayObject, grayObject, 0, 255, Core.NORM_MINMAX);
	Mat graySign = new Mat(sroadSign.rows(), sroadSign.cols(), sroadSign.type());
	Imgproc.cvtColor(sroadSign,  graySign,  Imgproc.COLOR_BGRA2GRAY);
	Core.normalize(graySign,  graySign, 0, 255, Core.NORM_MINMAX);	
	Mat tester = new Mat(sObject.rows(),sObject.cols(),sObject.type());
	Core.subtract(graySign, grayObject, tester);
	return Core.norm(tester);

}

}
