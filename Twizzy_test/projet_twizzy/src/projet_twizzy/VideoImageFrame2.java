package projet_twizzy;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.opencv.core.Mat;
import org.opencv.highgui.*;
import org.opencv.highgui.Highgui;
//import org.opencv.imgcodecs.Imgcodecs;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

class VideoImageFrame2 extends JFrame {

    private JFXPanel jfxPanel;
    
    private GridPane gridPane;
    private MediaPlayer mediaPlayer;
    private ImageView imageView;
    private Scene scene;
    public String videoPath;
    public String imagePath;
    public MyThread myThread;
   

    public VideoImageFrame2() {
        setTitle("Video Image App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(1200, 600);

        jfxPanel = new JFXPanel();
        add(jfxPanel, BorderLayout.CENTER);

        Platform.runLater(() -> {
            initJavaFX();
        });

        JPanel controlPanel = new JPanel();
        JButton loadVideoButton = new JButton("Charger vidéo");
        JButton loadImageButton = new JButton("Charger image");
        JButton playPauseButton = new JButton("Lancer/Pause");
        controlPanel.add(loadVideoButton);
        controlPanel.add(loadImageButton);
        controlPanel.add(playPauseButton);
        add(controlPanel, BorderLayout.SOUTH);

        loadVideoButton.addActionListener(e -> loadVideo());
        loadImageButton.addActionListener(e -> loadImage());
        playPauseButton.addActionListener(e -> togglePlayPause());
    }
    private void initJavaFX() {
       
        gridPane = new GridPane();
       
        scene = new Scene(gridPane);

        jfxPanel.setScene(scene);
    }
    private void loadImage() {
    	imagePath ="https://s2.coinmarketcap.com/static/img/coins/64x64/1.png";
        if (imagePath.equalsIgnoreCase("")) {
        	 JFileChooser fileChooser = new JFileChooser();
             fileChooser.setFileFilter(new FileNameExtensionFilter("Fichiers image", "jpg", "png", "bmp", "gif"));
             int returnValue = fileChooser.showOpenDialog(null);
        	if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                Platform.runLater(() -> {
                    Image image = new Image(selectedFile.toURI().toString());
                    imageView = new ImageView(image);
                    imageView.setPreserveRatio(true);
                    imageView.setFitHeight(1200);
                    imageView.setFitWidth(600);
            
              
                    gridPane.add(imageView, 0, 0);
                    
                });
            }
        }else {
                Platform.runLater(() -> {
                    Image image = new Image(imagePath);
                    imageView = new ImageView(image);
                    imageView.setPreserveRatio(true);
                    imageView.setFitHeight(1200);
                    imageView.setFitWidth(600);
            
              
                    gridPane.add(imageView, 0, 0);
                    
                });
            
        }
        
    }
    private void loadVideo() {
       
        //videoPath="https://arche.univ-lorraine.fr/pluginfile.php/1004382/mod_resource/content/0/video1.mp4";
        //videoPath ="video.mp4";
        videoPath = "C:/Users/idris/Downloads/videoFinale.mp4";
        if (videoPath.equalsIgnoreCase("")) {
        	 JFileChooser fileChooser = new JFileChooser();
             fileChooser.setFileFilter(new FileNameExtensionFilter("Fichiers vidéo", "mp4", "avi", "flv", "mkv", "mov"));
             int returnValue = fileChooser.showOpenDialog(null);
        	 if (returnValue == JFileChooser.APPROVE_OPTION) {
                 File selectedFile = fileChooser.getSelectedFile();
                 Platform.runLater(() -> {
                     if (mediaPlayer != null) {
                         mediaPlayer.stop();
                     }
                     Media media = new Media(selectedFile.toURI().toString());
                     //MyThread myThread = new MyThread(selectedFile.toURI().toString());
                     //System.out.println(selectedFile.toURI().toString());
                     //myThread.run();
                     
                     mediaPlayer = new MediaPlayer(media);
                     MediaView mediaView = new MediaView(mediaPlayer);
                     mediaView.setPreserveRatio(true);
                     mediaView.setFitHeight(1200);
                     mediaView.setFitWidth(900);
                   
                     gridPane.add(mediaView, 1, 0);
                 });
                 MyThread myThread = new MyThread(videoPath);
                 myThread.run();
             }
        }else {
                 Platform.runLater(() -> {
                     if (mediaPlayer != null) {
                         mediaPlayer.stop();
                     }
                     Media media = new Media(new File(videoPath).toURI().toString());
                     //Media media = new Media(videoPath);
                     
                     System.out.println(new File(videoPath).toURI().toString());
                    
                     mediaPlayer = new MediaPlayer(media);
                     MediaView mediaView = new MediaView(mediaPlayer);
                     mediaView.setPreserveRatio(true);
                     //mediaView.setFitHeight(1200);
                     //mediaView.setFitWidth(900);
                   
                     gridPane.add(mediaView, 1, 0);
                 });
                 MyThread myThread = new MyThread(videoPath);
                 //MyThread myThread = new MyThread(new File(videoPath).toURI().toString());
                 myThread.run();
            
        }
       
    }
    private void togglePlayPause() {
        if (mediaPlayer != null) {
            MediaPlayer.Status status = mediaPlayer.getStatus();
            if (status == MediaPlayer.Status.PLAYING) {
                mediaPlayer.pause();
            } else if (status == MediaPlayer.Status.PAUSED || status == MediaPlayer.Status.STOPPED || status == MediaPlayer.Status.READY) {
                mediaPlayer.play();
            }
        }
    }
    public static void LectureVideo(String nomVideo) {
    	VideoCapture camera = new VideoCapture(nomVideo);
    	camera.open(NORMAL);
    	Mat frame = new Mat();
    	//camera.open(nomVideo);
    	if (!camera.isOpened()) {
    		System.out.println("camera is closed");
    	}else { 		
        		if (camera.read(frame)){
                    System.out.println("Frame Obtained");
                    System.out.println("Captured Frame Width " + frame.width() + " Height " + frame.height());
                    //Imgcodecs.imwrite("c://capture/camera.jpg", frame);
                    //System.out.println("OK");
                    
                
    	}
    	}
    	}
    public static void LectureVideo2(String nomVideo) {
    	VideoCapture camera = new VideoCapture(0);
    	Mat frame = new Mat();
    	if (!camera.isOpened()) {
    		System.out.println("camera is closed");
    	}else {
    		while (camera.read(frame)) {
        		System.out.println("Frame Obtained");
        		//Run nouvelleimage=new Run(frame);
        		//nouvelleimage.go();		 		
        		}
    	}
    	
    	}
    
}
