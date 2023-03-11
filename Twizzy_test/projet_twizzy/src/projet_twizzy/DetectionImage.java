package projet_twizzy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Vector;

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
	
	public  Mat detectercontours(Init object) {
		
		int thresh=100;
		Mat canny_output=new Mat();
		List<MatOfPoint>contours= new ArrayList<>();
		MatOfInt4 hierarchy= new MatOfInt4();
		Imgproc.Canny(object.getimageseuildone(),canny_output,thresh,thresh*(double)2);
		Imgproc.findContours(canny_output, contours, hierarchy,Imgproc.RETR_EXTERNAL,Imgproc.CHAIN_APPROX_SIMPLE);
		Mat drawing=Mat.zeros(canny_output.size(),CvType.CV_8UC3);
		object.setRand();
		for(int i=0;i<contours.size();i++) {
			Scalar color= new Scalar(object.getRand().nextInt(255 -0 +1),object.getRand().nextInt(255 -0 +1),object.getRand().nextInt(255 -0 +1));
			Imgproc.drawContours(drawing, contours,i, color,1,8,hierarchy,0,new Point());
		}
		return  drawing;
	}
	
	public  List<MatOfPoint> detectercontourlist(Init object) {
		int thresh=100;
		Mat canny_output=new Mat();
		List<MatOfPoint>contours= new ArrayList<>();
		MatOfInt4 hierarchy= new MatOfInt4();
		Imgproc.Canny(object.getimageseuildone(),canny_output,thresh,thresh*(double)2);
		Imgproc.findContours(canny_output, contours, hierarchy,Imgproc.RETR_EXTERNAL,Imgproc.CHAIN_APPROX_SIMPLE);
		Mat drawing=Mat.zeros(canny_output.size(),CvType.CV_8UC3);
		Random rand= new Random();
		for(int i=0;i<contours.size();i++) {
			Scalar color= new Scalar(rand.nextInt(255 -0 +1),rand.nextInt(255 -0 +1),rand.nextInt(255 -0 +1));
			Imgproc.drawContours(drawing, contours,i, color,1,8,hierarchy,0,new Point());
		}
		
		return  contours;
	}
	
	public  void detectioncerclerouge(Init object) {
		List<MatOfPoint>contours=detectercontourlist(object);
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
		//return m;
		
	}	
	public  Mat detection_ball(Init object) {
		Mat matricenull=new Mat();
		List<MatOfPoint>contours=detectercontourlist(object);
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
				Rect rect =Imgproc.boundingRect(contour);
				Core.rectangle(object.getimageread(), new Point(rect.x,rect.y), new Point(rect.x+(double)rect.width,rect.y+(double)rect.height), new Scalar(0,255,0),2);
				Mat tmp=object.getimageread().submat(rect.y,rect.y+rect.height,rect.x,rect.x+rect.width);
				Mat ball=Mat.zeros(tmp.size(),tmp.type());
				tmp.copyTo(ball);
				return ball;
				
			}
			else {
				System.out.println("couldn't find ball");
				
			}
		}
		return matricenull;
		
	}
	
	public  void misealecchelle(String panel,Init object) {
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
			System.out.println(matchs.dump());
			Mat matchedImage = new Mat(sroadSign.rows(), sroadSign.cols()*2, sroadSign.type());
			Features2d.drawMatches(sObject,  objectKeyPoints,  sroadSign,  signKeypoints,  matchs,  matchedImage);
		}
	

	}


