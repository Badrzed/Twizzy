package projet_twizzy;

import java.util.ArrayList;
import java.util.List;

//import org.opencv.core.Core;
import org.opencv.core.Mat;

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
			for (int i=0;i<imageRef.size();i++) {
				DetectionImage.misealecchelle(imageRef.get(i).getFichier(),imageTraite );
				System.out.println("\n");
				System.out.println("\n");
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
