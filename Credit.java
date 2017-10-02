import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class Credit{
    double xp;//position of center
    double yp;//position of center
    double xv;//x velocity
    double yv;//y velocity
    int credit;
    int r;
    boolean valid;//keeps whether credit is valid
    Color c;
    public Credit(int xpos, int ypos, int vx, int vy, int size, int cr){
        //valid=false;
        valid=true;
        xp=xpos;
        yp=ypos;
        xv=vx;
        yv=vy;
        r=size;
        credit=cr;
        if(credit>0) c=new Color(0,255,0);//extra credit 1pt, green
        else if(credit==-1) c=new Color(255,255,0); //1pt deduction, yellow
        else if(credit==-2) c=new Color(0,255,255); //2pt deduction, cyan
        else if(credit==-3) c=new Color(0,0,255); //3pt deduction, blue
        else if(credit==-4) c=new Color(255,0,255); //4pt deduction, magenta
        else if(credit==-5) c=new Color(255,0,0); //5pt deduction, red
        else{ c=new Color(0,0,0); valid=false;//disappears, black, invalid credit
        }
    }
    public void update(long time, Student s){
        recolor();
        if(!valid) return;
        xp+=time*xv/1000.0;
        yp+=time*yv/1000.0;
        if(xp<r&&xv<0){
            xv=-xv;
            if(credit==+1) credit--;
            else credit++;
            xp=r+1;
        }
        else if(xp>600-r&&xv>0){
            xv=-xv;
            xp=600-r-1;
            if(credit==+1) credit--;
            else credit++;
        }
        else if(yp<15&&yv<0){
            yv=-yv;
            if(credit==+1) credit--;
            else credit++;
            yp=r+1;
        }
        else if(yp>600-r&&yv>0){
            yv=-yv;
            yp=600-r-1;
            if(credit==+1) credit--;
            else credit++;
        }//bounce-off mechanism that is similar to that of Teacher class
        //every bounce off leads to 1point less deduction
        if((xp-s.xp)*(xp-s.xp)+(yp-s.yp)*(yp-s.yp)<(r+s.r)*(r+s.r)){
            s.grade=s.grade+credit;
            credit=0;
        }//if collided, take points off and invalidate credit
    }
    public void recolor(){//assign appropriate colors to deductions
        int a=180;
        if(credit>0) {c=new Color(0,255,0,a);}//extra credit 1pt, green
        else if(credit==-1) c=new Color(255,255,0,a); //1pt deduction, yellow
        else if(credit==-2) c=new Color(0,255,255,a); //2pt deduction, cyan
        else if(credit==-3) c=new Color(0,0,255,a); //3pt deduction, blue
        else if(credit==-4) c=new Color(255,0,255,a); //4pt deduction, magenta
        else if(credit==-5) c=new Color(255,0,0,a); //5pt deduction, red
        else{ c=new Color(0,0,0); xp=1000;yp=1000; xv=0; yv=0; valid=false;
        }//if invalid, hide credit out of display (making black color is not enough because it overlaps with other credits)
    }
    public void draw(Graphics g){
        if(!valid) return;//don't draw invalid credits
        g.setColor(c);
        g.fillOval((int)(xp-r),(int)( yp-r), 2*r, 2*r);//draw credit
    }
}