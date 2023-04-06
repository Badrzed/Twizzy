package projet_twizzy;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MyThread extends Thread {
	private String video;
	
	static {
		System.loadLibrary("opencv_ffmpeg2413");
	}
	
	public MyThread(String video) {
		this.video = video;
		return;
	}
	
	public void run() {
		VideoPlayer.LectureVideo(this.video);
	}
}
