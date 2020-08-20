/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

package fractal.drawer;

/*
 * MouseMotionEventDemo.java
 *
 */
import fractal.drawer.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.awt.GridLayout;

import javax.swing.*;

public class FractalDrawer extends JPanel implements MouseMotionListener {
    
    JTextArea textArea;
    static final String NEWLINE = System.getProperty("line.separator");
    
    //create the Canvas and Fractal
    private static Canvas c = new Canvas();
    private static Fractal f = new Fractal(c);
    //save the mouse's location within the canvas
    public static int mouseX = c.getWidth()/2;
    public static int mouseY = c.getHeight()/2;
    public static boolean inCanvas = false;
    
    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GUI();
            }
        });
    }
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void GUI() {
        //create the GUI and canvas
        //Creating the Frame
        JFrame frame = new JFrame("Fractal Drawer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);

        //Creating the MenuBar and save as button
        JMenuBar mb = new JMenuBar();
        JButton save = new JButton("Save As");
        mb.add(save);

        //Creating the panel at bottom and adding components
        JPanel panel = new JPanel(); // the panel is not visible in output
        JLabel label = new JLabel("Fractal-Drawing Method");
        //buttons
        JButton lineType = new JButton("Line");
        JButton shapeType = new JButton("Change Shape");
        JButton addLobe = new JButton("Add Lobe");
        JButton addRecursionSize = new JButton("Increase Recursion Size");
        JButton removeRecursionSize = new JButton("Decrease Recursion Size");
        
        //add button listeners
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //save picture of canvas in some way
            }
        });
        lineType.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Fractal.type = 0;
            }
        });
        shapeType.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Fractal.type < 2)
                    Fractal.type++;
                else
                    Fractal.type = 1;
            }
        });
        addLobe.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Fractal.lobeCount < 10)
                    Fractal.lobeCount++;
                else
                    Fractal.lobeCount = 1;
            }
        });
        addRecursionSize.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Fractal.recursions < 20)
                    Fractal.recursions++;
                else
                    Fractal.recursions = 1;
            }
        });
        removeRecursionSize.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Fractal.recursions > 0)
                    Fractal.recursions--;
                else
                    Fractal.recursions = 50;
            }
        });
        
        // Components added to the bottom panel using Flow Layout
        panel.add(label);
        panel.add(lineType);
        panel.add(shapeType);
        panel.add(addLobe);
        panel.add(addRecursionSize);
        panel.add(removeRecursionSize);
        
        // add mouseMotionListener to the frame 
        c.addMouseMotionListener(new FractalDrawer());

        //Adding Components to the frame.
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.getContentPane().add(BorderLayout.CENTER, c);
        frame.setVisible(true);
        //set the canvas up
        stroke(Color.BLACK);
        c.setBackground(Color.WHITE);
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    public FractalDrawer() {
        addMouseMotionListener(this);
    }
    
    //class getters
    public static Canvas getCanvas() {
        return c;
    }
    public static int getCanvasWidth() {
        return c.getWidth();
    }
    public static int getCanvasHeight() {
        return c.getHeight();
    }
    
    //class setters
    public static void stroke(Color theColor) {
        c.getGraphics().setColor(theColor);
    }
    public void setBackground(Color theColor) {
        c.setBackground(theColor);
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
    public static void rect(int x1, int y1, int x2, int y2) {
        Graphics2D g2 = (Graphics2D) c.getGraphics();
        Shape rect = new Rectangle(x1, y1, x2, y2);
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

    //mouseMovedListener methods
    void eventOutput(String eventDescription, MouseEvent e) {
        System.out.println(eventDescription
                + " (" + e.getX() + "," + e.getY() + ")"
                + " detected on "
                + e.getComponent().getClass().getName());
    }
    
    public void mouseMoved(MouseEvent e) {
//        eventOutput("Mouse moved to", e);
    }
    
    public void mouseDragged(MouseEvent e) {
        //debugging
//        eventOutput("Mouse dragged to", e);
        
//        int range = 100;
//        //only function if the cursor is clicked and dragged some distance from the last point of contact, for continuity sake
//        if(Math.abs(mouseX - e.getX()) <= range && Math.abs(mouseY - e.getY()) <= range) {
        //for class
        mouseX = e.getX();
        mouseY = e.getY();

        //clear canvas
        fillRect(0, 0, c.getWidth(), c.getHeight());
        //draw fractal
        Fractal.draw(c, e.getX(), e.getY());
//        }
    }
}

/*                                 Sources:
********************************************************************************
Template:                       SLOHS, SLO, CA.
Drawing tools:                  I don't remember
Mouse events:                   https://www.geeksforgeeks.org/mouselistener-mousemotionlistener-java/
Mouse Motion Listener Tutorial: https://docs.oracle.com/javase/tutorial/uiswing/examples/events/index.html#MouseMotionEventDemo
Button Listeners:               https://stackoverflow.com/questions/21879243/how-to-create-on-click-event-for-buttons-in-swing/21879526
Angle Calculation:              https://stackoverflow.com/questions/9970281/java-calculating-the-angle-between-two-points-in-degrees

*/