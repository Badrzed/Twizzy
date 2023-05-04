package projet_twizzy;

import java.awt.EventQueue;
import java.nio.file.Paths;

import javax.swing.*;

import org.opencv.core.*;
import org.opencv.highgui.*;

public class Main {
	public static void main(String[] args) {
		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	    EventQueue.invokeLater(() -> {
            VideoImageFrame2 frame = new VideoImageFrame2(); 
            frame.setVisible(true);
        });
	    
	    
	    
		//VideoPlayer.launch(args);
		
		/*
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		String path = "video.mp4";
		if (!Paths.get(path).toFile().exists()) {
			System.out.println("file does not exist");
		}
		VideoCapture camera = new VideoCapture(path);
		camera.open(path);
		Mat frame = new Mat();
		
		if (camera.isOpened()) {
			System.out.println("camera is not opened");
		}else {
		while(true) {
			//System.out.println("Looping... \n");
			if (camera.read(frame)) {
				System.out.println("Frame obtained");
				System.out.println("Capture frame width");
				
			}
		}}
*/		
			}

}
