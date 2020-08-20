/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fractal.drawer;

import fractal.drawer.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
/**
 *
 * @author Owner
 */
public class Fractal {
    private static Canvas c = new Canvas();
    public static int type = 0;
    public static int recursions = 10;
    public static int lobeCount = 2;
    public static int size = 100;
    
    public Fractal(Canvas canvas) {
        c = canvas;
    }
    
    //draw fractal each iteration
    public static void draw(Canvas canvas, int x, int y) {
        c = canvas;
        
        //draw the line and regular line fractal
        if (type == 0) {
            //get end points of lines
            int startX = c.getWidth()/8;
            int startY = (7*c.getHeight())/8;
            int middleX = x;
            int middleY = y;
            int endX = (7*c.getWidth())/8;
            int endY = (7*c.getHeight())/8;
            
            int xRelToStart = x - startX;
            int yRelToStart = y - startY;
            int xRelToEnd = x - endX;
            int yRelToEnd = y - endY;
            
            double length1 = Math.sqrt((xRelToStart*xRelToStart)+(yRelToStart*yRelToStart));
            double length2 = Math.sqrt((xRelToEnd*xRelToEnd)+(yRelToEnd*yRelToEnd));
            double tempLength1 = Math.sqrt((xRelToStart*xRelToStart)+(yRelToStart*yRelToStart));
            double tempLength2 = Math.sqrt((xRelToEnd*xRelToEnd)+(yRelToEnd*yRelToEnd));
            double angle1 = getAngle(startX, startY, x, y);
            double angle2 = getAngle(x, y, endX, endY);
            double tempAngle1 = getAngle(startX, startY, x, y);;
            double tempAngle2 = getAngle(x, y, endX, endY);;
            
            int xDif = 0;
            int yDif = 0;
            //make guide lines
            line(startX, startY, x, y);
            line(x, y, endX, endY);
            
            //move guide points to starting line position
            startX = c.getWidth()/2;
            startY = c.getHeight()/2;
            middleX = startX + xRelToStart;
            middleY = startY + yRelToStart;
            endX = (7*c.getWidth())/8;
            endY = c.getHeight()/2;
            
//            //iteration 1
//                line(startX, startY, startX + xRelToStart, startY + yRelToStart);
//                line(startX + xRelToStart, startY + yRelToStart, endX, endY);
//            //iteration 2
//                //something's wrong, I can feel it
//                line(startX, startY, startX + xRelToStart/2, (startY + yRelToStart)/2);
//                line(startX + xRelToStart/2, (startY + yRelToStart)/2, startX + (2*xRelToStart)/2, (startY + (2*yRelToStart))/2);
//                line(startX + (2*xRelToStart)/2, (startY + (2*yRelToStart))/2, startX + (3*xRelToStart)/2, (startY + (3*yRelToStart))/2);
//                line(startX + (3*xRelToStart)/2, (startY + (3*yRelToStart))/2, endX, endY);
            for(int i = 0; i < recursions; i++) {
                tempAngle1 = angle1;
                tempAngle2 = angle2;
                
                xDif = 0;
                yDif = 0;
                for (int j = 0; j < Math.pow(2, i); j++) {
                    //draw the same bent lines on previous lines
                    startX = (int)(startX + xDif);
                    startY = (int)(startY + yDif);
                    middleX = (int)(startX + tempLength1 * Math.cos(tempAngle1));
                    middleY = (int)(startY + tempLength1 * Math.sin(tempAngle1));
                    endX = (int)(middleX + tempLength2 * Math.cos(tempAngle2));
                    endY = (int)(middleY + tempLength2 * Math.sin(tempAngle2));
                    
                    tempLength1 *= (length1/(length1+length2));
                    tempLength2 *= (length2/(length1+length2));
                    
                    tempAngle1 += angle1;
                    tempAngle2 += angle2;
                    
                    //get difference in x from each recursion, compound them, and then use them next iteration
                    xDif += (int)(tempLength1*Math.cos(tempAngle1) + tempLength2*Math.cos(tempAngle2));
                    yDif += (int)(tempLength1*Math.sin(tempAngle1) + tempLength2*Math.sin(tempAngle2));
                    
                    line(startX, startY, middleX, middleY);
                    line(middleX, middleY, endX, endY);
                }
            }
            
        //draw a circle fractal with lobeCount # lobes
        } else if (type == 1) {
            int dist = 0;
            circle(x, y, size);
            size /= lobeCount;
            for (int i = 0; i<recursions-1; i++) {
                circle(x + dist*lobeCount, y + dist*lobeCount, size);
//                size /= lobeCount;
                dist += size;
            }
            
        //draw a square fractal with lobeCount # lobes x4
        } else if (type == 2){
            rect(x, y, size, size);
        } else {
            System.out.println("error choosing fractal drawing type");
        }
        
        
    }
    
    //fractal tools
    public static double getAngle(int x1, int y1, int x2, int y2) {
        double angle = (double) Math.toRadians(Math.atan2(y1-y2, x1-x2));

        if(angle < 0){
            angle += 360;
        }

        return angle;
    }
    public static void branch(int length) {
        
    }
    
    //class drawing tools
    public static void line(int x1, int y1, int x2, int y2) {
        c.getGraphics().drawLine(x1, y1, x2, y2);
    }
    public static void circle(int x, int y, int r) {
        Graphics2D g2 = (Graphics2D) c.getGraphics();
        Shape circle = new Ellipse2D.Double(x, y, r, r);
        g2.draw(circle);
    }
    public static void rect(int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) c.getGraphics();
        Shape rect = new Rectangle(x, y, x + width, y + height);
        g2.draw(rect);
    }
    public static void fillRect(int x1, int y1, int x2, int y2) {
        Graphics2D g2 = (Graphics2D) c.getGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(x1, y1, x2, y2);
    }
    public static void clearCanvas() {
        fillRect(0, 0, c.getWidth(), c.getHeight());
    }
}
