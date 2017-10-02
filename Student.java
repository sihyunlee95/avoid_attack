import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class Student{
    public double xp;
    public double yp;
    public double xv;
    public double yv;
    public double stv;//standard velocity
    public int r;
    public int grade;//starts from 100, below 60 is fail
    public Student(int xpos, int ypos, int rad, int vel){
        xp=xpos;
        yp=ypos;
        xv=0;
        yv=0;
        r=rad;
        stv=vel;
        grade=100;
    }
    public void update(long time){
        if(xp<r&&xv<0){
            xv=-xv;
            xp=r+1;
            grade--;
        }
        else if(xp>600-r&&xv>0){
            xv=-xv;
            xp=600-r-1;
            grade--;
        }
        if(yp<15&&yv<0){
            yv=-yv;
            yp=r+1;
            grade--;
        }
        else if(yp>600-r&&yv>0){
            yv=-yv;
            yp=600-r-1;
            grade--;
        }
        //bounce off from wall, each bounce = 1 point deduction
        xp+=xv*time/1000.0;
        yp+=yv*time/1000.0;
        
        if(grade<51){
            xv=0;
            yv=0;
        }//game over!
    }
    public void changeDirection(int dx, int dy){//change directions according to keyboard input
        if(dx==1){
            xv=stv;
            yv=0;
        }
        else if(dx==-1){
            xv=-stv;
            yv=0;
        }
        if(dy==1){
            yv=stv;
            xv=0;
        }
        else if(dy==-1){
            yv=-stv;
            xv=0;
        }
        //change direction according to input
    }
    public void draw(Graphics g){
        Color k=new Color(255,255,255);
        g.setColor(k);
        g.fillOval((int)xp-r,(int)yp-r,2*r,2*r);//draw Student
        k=new Color(255,0,0);
        g.setColor(k);
        g.fillRect(600,(int)(560-(10.98039)*(grade-50)), 50, 10000);//graphically display remaining grade
        
        k=new Color(180,0,0);
        g.setColor(k);
        g.fillRect(600,560,50,40);
        String x=String.valueOf(grade);
        k=new Color(255,255,255);
        g.setColor(k);
        g.drawString(x,610,590);//numerically show current grade
    }
}