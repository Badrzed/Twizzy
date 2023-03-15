import javax.swing.*;
import javax.media.*;
import java.net.*;
import java.io.*;
 
 
public class Videos
{
     @SuppressWarnings("deprecation")
	public static void videos() throws Exception
     {
          Player player;
          JFrame jframe=new JFrame();
          jframe.setSize(300,300);
          JPanel jpanel=new JPanel();
          jpanel.setLayout(new BoxLayout(jpanel,BoxLayout.Y_AXIS));
          player=Manager.createRealizedPlayer(new MediaLocator(new File("C:\\Users\\valen\\Documents\\cours\\2A_ENSEM\\S8\\Twizzy\\Twizzy_test\\image_ panneaux\\video1.avi").toURL()));
          player.setMediaTime(new Time(30.0));
          jpanel.add(player.getVisualComponent());
          Control controls[]=player.getControls();
          CachingControl cc=null;
          for(int i=0;i<controls.length;i++)
          {
               if(controls[i] instanceof CachingControl)
               {
                    cc=(CachingControl) controls;
 
                    jpanel.add(cc.getProgressBarComponent());    
 
                    System.out.println("Found CachingControl");
 
               }
 
          }
 
          if(player.getControlPanelComponent()==null)
 
          {
 
               System.out.println("Null");
 
          }
 
          else
 
          {
 
               jpanel.add(player.getControlPanelComponent());
 
          }
 
          jframe.setContentPane(jpanel);
 
          jframe.setVisible(true);
 
          player.start();
 
     }
 
}