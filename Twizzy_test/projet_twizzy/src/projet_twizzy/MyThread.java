package projet_twizzy;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

public class MyThread extends Thread {
	private String video;
	
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
		VideoCapture camera = new VideoCapture(nomVideo);
		camera.open(MAX_PRIORITY);
		//camera.open(nomVideo);
    	Mat frame = new Mat();
    	if (!camera.isOpened()) {
    		System.out.println("camera is closed");
    	}else {
    		while (camera.read(frame)) {
        		System.out.println("Frame Obtained");
        		Highgui.imwrite("camera.jpg", frame);
        		//Run nouvelleimage=new Run(frame);
        		//nouvelleimage.go();		 		
        		}
    	}
    	}
	
}
