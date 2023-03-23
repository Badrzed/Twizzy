package projet_twizzy;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

//import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

public class Run {
		private List<Init> imageRef; //image reference
		private Init imageTraite; //image qu'on veut comparer
		private String stringImage; //image qu'on veut comparer
		
		public Run(String stringImage) {
			this.stringImage = stringImage;
			setImageRef();
			setimageTraite(stringImage);
		}
		
		public void go(){
			Mat dect= DetectionImage.detection_ball(imageTraite);
			imageTraite.changeimageread(dect);
			List<Integer> listedesref=new ArrayList<>();
			int indicemin=0;		
			for (int i=0;i<imageRef.size();i++) {
				System.out.println(i);
				String a=DetectionImage.misealecchelle3(imageRef.get(i).getFichier(),imageTraite);
				try {
				    Integer b = Integer.parseInt(a);
				    listedesref.add(b);
				} catch (NumberFormatException e) {
				    System.out.println("Error parsing " + a + " to an Integer");
				    e.printStackTrace();
				}
			}
			for(int i=0;i<listedesref.size();i++) {
				if(listedesref.get(i)<=listedesref.get(indicemin)) {
					indicemin=i;	
				}}
			DetectionImage.misealecchelle(imageRef.get(indicemin).getFichier(),imageTraite);
			switch(indicemin) {
			case 0:
				System.out.println("panneau30");
				break;
			
			case 1:
				System.out.println("panneau50");
				break;
			case 2:
				System.out.println("panneau70");
				break;
			case 3:
				System.out.println("panneau90");
				break;
			case 4:
				System.out.println("pannea110");
				break;
			case 5:
				System.out.println("double");
				break;
			default:
				System.out.println("no panel");
				break;
			}
			}
			
	
		
		
		public void setstringImage(String stringImage) {
			this.stringImage = stringImage;
		}
		
		public String getstringImage() {
			return stringImage;
		}
		

	

		public List<Init> getImageRef() {
			return imageRef;
		}

		public void setImageRef() {
			 List<Init> imageRef=new ArrayList<>();
					Init a1=new Init("ref30.png");
					Init a2=new Init("ref50.png");
					Init a3=new Init("ref70.png");
					Init a4=new Init("ref90.png");
					Init a5=new Init("ref110.png");
					Init a6=new Init("refdouble.png");
					imageRef.add(a1);
					imageRef.add(a2);
					imageRef.add(a3);
					imageRef.add(a4);
					imageRef.add(a5);
					imageRef.add(a6);
			this.imageRef = imageRef;
		}
	
public void setimageTraite(String stringimage) {
	this.imageTraite = new Init(stringimage);
}
}
