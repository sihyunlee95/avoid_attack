import javax.swing.*;

public class Application
{
   public static void main(String[] args){
       JFrame frame = new JFrame("AVOID and ATTACK by Sihyun Lee, Columbia SEAS '18");
       
       Content con = new Content();
       frame.add(con);

       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setVisible(true);
       frame.setSize(700+frame.getInsets().left + frame.getInsets().right, 600+frame.getInsets().top + frame.getInsets().bottom);
 
       long currentTime = System.currentTimeMillis();
       while (true){
            long frameStart = System.currentTimeMillis();
            con.update(frameStart - currentTime);
            frame.repaint();
            currentTime = frameStart;
       }
   }
}
