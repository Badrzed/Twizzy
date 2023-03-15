package projet_twizzy;



import org.opencv.core.*;

public class Main{
	
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Run nouvelleimage=new Run("p10.png");
		nouvelleimage.go();
		/*DetectionImage.ImShow("pascontour",image.getimageread());
		DetectionImage.ImShow("contour",image.getContours());*/
			}

}