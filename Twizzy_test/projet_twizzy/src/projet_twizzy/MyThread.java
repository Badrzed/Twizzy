package projet_twizzy;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

public class MyThread extends Thread {
	private String video;
	private static int compte = 0;
	
	static {
		//System.loadLibrary("opencv_ffmpeg2413");
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
	}
	
	public MyThread(String video) {
		this.video = video;
		//return;
	}
	public void run() {
		LectureVideo(this.video);
	}
	public static void LectureVideo(String nomVideo) {
		//System.load("C:\\Users\\STAGIAIRE\\Downloads\\opencv\\build\\x64\\vc14\\bin\\opencv_ffmpeg2413_64.dll");
		System.load("C:\\Users\\idris\\Downloads\\opencv\\build\\x64\\vc14\\bin\\opencv_ffmpeg2413_64.dll");
		VideoCapture camera = new VideoCapture(nomVideo);
		
		camera.open(nomVideo);
    	Mat frame = new Mat();
    	if (!camera.isOpened()) {
    		System.out.println("camera is closed my thread");
    	}else {
    		while (camera.read(frame) & (compte<10)) {
        		System.out.println("Frame Obtained");
        		//Highgui.imwrite("camera"+Integer.toString(compte)+".png", frame);
        		Run nouvelleimage=new Run("camera0.png");
        		nouvelleimage.go();
        		compte=compte+1;
        		}
    	}
    	}
	
}
