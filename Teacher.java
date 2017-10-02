import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.lang.Math;
import java.util.Random;
import java.io.*;
import java.util.*;
import javax.imageio.*;
import java.awt.image.*;
public class Teacher{
    //BufferedImage img;
    BufferedImage win;
    double xp;//position of center
    double yp;//position of center
    double xv;//x velocity
    double yv;//y velocity
    int whip; //resistance power (whining resistance)
    int r; //radius
    int damage;
    int damtemp;//temporary storage of damage value
    int state; //boolean-ish variable that returns game progress
    int stage;
    Color c=new Color(127,127,127);
    public Teacher(int xpos, int ypos, int vx, int vy, int size, int dam, int st){
        xp=xpos;
        yp=ypos;
        xv=vx;
        yv=vy;
        r=size;
        whip=100;
        damage=dam;
        damtemp=dam;
        stage=st;
        state=0;
        try{
            //img=ImageIO.read(new File ("mr_russell.jpg")); //read is a static method
            win=ImageIO.read(new File ("pass.jpg"));
        }
        catch (Exception e){
            System.out.println("error");
        }
    }
    public void update(long time,Student s){
        //modify velocity if necessary
        if(whip<=0){
            xv=0;yv=0;
            state=2;
            return;
        }//state=2, round cleared
        
        if(s.grade<51){xv=0; yv=0; state=3; return;}//state=3, game over
        xp+=time*xv/1000.0;
        yp+=time*yv/1000.0;//update position
        if(xp<r&&xv<0){
            xv=-xv;
            xp=r+1;
        }
        else if(xp>600-r&&xv>0){
            xv=-xv;
            xp=600-r-1;
        }
        if(yp<r&yv<0){
            yv=-yv;
            yp=r+1;
        }
        else if(yp>600-r&&yv>0){
            yv=-yv;
            yp=600-r-1;
        }
        //bounceoff
        //r+1 or -r-1 terms are made in order to let one call of update method suffice for bouncing off
        
        if((xp-s.xp)*(xp-s.xp)+(yp-s.yp)*(yp-s.yp)<(r+s.r)*(r+s.r)){//collision detected
            //s.grade=s.grade-damage;
            whip=whip-(damage);
            if(damage>0) {xv=xv*1.005;yv=yv*1.005;}//each damage is +0.5% increase in speed
            damage=0;//after one damage, no further damages unless the Student goes out of radius
            state=1;//overlap state
        }
        else{
            damage=damtemp;
            state=0;//un-overlap state
        }
    }
    public void draw(Graphics g){
        if(whip<=0){
            g.drawImage(win,0,0,null);
        }
        Color k;
        if(state==0){
            k=new Color(255,255,255);
            g.setColor(k);//white(normal) for non-overlap state
        }
        else if(state==1){
            k=new Color(30,30,50);
            g.setColor(k);//blue indication for overlap state
        }
        else if(state==2){
            k=new Color(0,255,0);
            g.setColor(k);//green when stage cleared
        }
        else{
            k=new Color(255,0,0);
            g.setColor(k);//red when stage failed
        }
        g.drawOval((int)(xp-r),(int)(yp-r),2*r,2*r);
        String s=String.valueOf(stage);
        g.drawString("Lv."+s,(int)xp-10,(int)yp+5);//display Lv value
        
        k=new Color(0,0,255);
        g.setColor(k);
        g.fillRect(650,(int) (560-5.6*(whip)), 50, 600);//display whip value graphically
        
        k=new Color(0,0,180);
        g.setColor(k);
        g.fillRect(650,560,50,40);
        String x=String.valueOf(whip);
        k=new Color(255,255,255);
        g.setColor(k);
        g.drawString(x,660,590);
            //display whip value numerically
        
        
    }
    public void Initiate(Credit[] c, int maxindex, double diff){
        if(xv==0 && yv==0) return;//if game over or cleared, do nothing
        for(int i=0;i<maxindex;i++){
                Random rng=new Random();
                int level=350-3*whip;
                int xdirection=(rng.nextInt(2)*2)-1;
                int ydirection=(rng.nextInt(2)*2)-1;
                int vxRand=xdirection*(rng.nextInt(level)+30);
                int vyRand=ydirection*(rng.nextInt(level)+30);
                int crRand=rng.nextInt(7)-5;
                c[i]=new Credit((int)xp,(int)yp,(int)(diff*vxRand),(int)(diff*vyRand),10,crRand);//generate random Credits centered on Teacher
                //Student cannot whine carelessly around the Teacher because if the teacher Initiates student will get all deduction at once if Student is very near to Teacher
        }
        for(int i=maxindex;i<c.length;i++){
            c[i]=new Credit(1000,1000,0,0,10,0);
        }//over desired number, all credits are void
    }
    public void PatternA1(Credit[] c, double diff){
        
        if(xv==0 && yv==0) return;
        for(int i=0;i<c.length;i++){
            if(c[i].credit==+1){
                c[i]=new Credit(1000,1000,0,0,10,0);
            }
        }
        if(whip<=25){
            PatternB(c,diff);
            return; //ultimate super-attack pattern if whip<=25 (angry)
        }
        int ra=14;
        int v=(int)(diff*450);
        double k=Math.sqrt(2);//cos 45'=sin45'=k
        for(int i=0;i<3;i++){
            c[8*i]=new Credit((int) xp, (int) yp, v,0,ra,-1);
            c[8*i+1]=new Credit((int) xp, (int) yp, 0,v,ra,-1);
            c[8*i+2]=new Credit((int) xp, (int) yp, -v,0,ra,-1);
            c[8*i+3]=new Credit((int) xp, (int) yp, 0,-v,ra,-1);
            c[8*i+4]=new Credit((int) xp, (int) yp, (int) (v/k),(int) (v/k),ra,-1);
            c[8*i+5]=new Credit((int) xp, (int) yp, (int) (-v/k),(int) (v/k),ra,-1);
            c[8*i+6]=new Credit((int) xp, (int) yp, (int) (v/k),(int) (-v/k),ra,-1);
            c[8*i+7]=new Credit((int) xp, (int) yp, (int) (-v/k),(int) (-v/k),ra,-1);
        }
        //octagonal attack 1
        //octagonal attacks corresponds to quizzes: each yellow (1pt) in quiz is effectively 4pt deduction in 100 scale
        //4 deductions are superposed
    }
    public void PatternA2(Credit[] c, double diff){
        if(xv==0 && yv==0) return;
        for(int i=0;i<c.length;i++){
            if(c[i].credit==+1){
                c[i]=new Credit(1000,1000,0,0,10,0);
            }
        }
        if(whip<=25){
            PatternB(c,diff);
            return;
        }
        int r=14;
        int v=(int)(diff*450);
        double cos=0.92387953251; //cos 22.5'
        double sin=0.38268343237; //sin 22.5'
        for(int i=0;i<3;i++){
            c[8*i]=new Credit((int) xp, (int) yp, (int) (cos*v),(int) (sin*v),r,-1);
            c[8*i+1]=new Credit((int) xp, (int) yp, (int) -(cos*v),(int) (sin*v),r,-1);
            c[8*i+2]=new Credit((int) xp, (int) yp, (int) (cos*v),(int) -(sin*v),r,-1);
            c[8*i+3]=new Credit((int) xp, (int) yp, (int) -(cos*v),(int) -(sin*v),r,-1);
            c[8*i+4]=new Credit((int) xp, (int) yp, (int) (sin*v),(int) (cos*v),r,-1);
            c[8*i+5]=new Credit((int) xp, (int) yp, (int) -(sin*v),(int) (cos*v),r,-1);
            c[8*i+6]=new Credit((int) xp, (int) yp, (int) (sin*v),(int) -(cos*v),r,-1);
            c[8*i+7]=new Credit((int) xp, (int) yp, (int) -(sin*v),(int) -(cos*v),r,-1);
        }
        //octagonal attack 2
    }
    //alternates btw Pattern A1 and Pattern A2, make game little harder
    public void PatternB(Credit[] c, double diff){
        int ra=10;
        int v=(int) (1000*diff);
        double k=Math.sqrt(2);
        int i=0;
            c[8*i]=new Credit((int) xp, (int) yp, v,0,ra,-1);
            c[8*i+1]=new Credit((int) xp, (int) yp, 0,v,ra,-1);
            c[8*i+2]=new Credit((int) xp, (int) yp, -v,0,ra,-1);
            c[8*i+3]=new Credit((int) xp, (int) yp, 0,-v,ra,-1);
            c[8*i+4]=new Credit((int) xp, (int) yp, (int) (v/k),(int) (v/k),ra,-1);
            c[8*i+5]=new Credit((int) xp, (int) yp, (int) (-v/k),(int) (v/k),ra,-1);
            c[8*i+6]=new Credit((int) xp, (int) yp, (int) (v/k),(int) (-v/k),ra,-1);
            c[8*i+7]=new Credit((int) xp, (int) yp, (int) (-v/k),(int) (-v/k),ra,-1);
    }
    //much faster version of PatternA1
}
