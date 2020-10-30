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

package drawing.program;

/*
 * MouseMotionEventDemo.java
 *
 */
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
import java.util.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class Drawer extends JPanel implements MouseMotionListener {
    
    private static final int MAX_BRUSH_SIZE = 500;
    //create the Canvas and Brushes
    public static Canvas c = new Canvas();
    //save the mouse's location within the canvas
    public static int mouseX = c.getWidth()/2;
    public static int mouseY = c.getHeight()/2;
    public static boolean consumetherainbowbecometherainbow = false;
    public static Color strokeColor = new Color(0, 0, 0);
    
    public static int type = 0;
    public static int size = 100;
    
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
        // add mouseMotionListener to the frame 
        c.addMouseMotionListener(new Drawer());

        //Creating the MenuBar and save as button
        JMenuBar mb = new JMenuBar();
        JButton save = new JButton("Save Drawing");
        mb.add(save);

        //Creating the panel at bottom and adding components
        JPanel bottomPanel = new JPanel(); // the panel is not visible in output
        JPanel sidePanel = new JPanel();
        JLabel label = new JLabel("Drawing tools");
        
        //toolbar
        JToolBar toolbar = new JToolBar();
        
        //button menus
        JPopupMenu brushMods = new JPopupMenu();
        JButton modsButton = new JButton("Modify Brush Shape/Size");
        JPopupMenu brushColors = new JPopupMenu();
        JButton colorsButton = new JButton("Modify Brush Color");
        
        JButton clearCanvas = new JButton("Clear Canvas");
        
        //brushMod buttons
        JButton brushType = new JButton( "Change Brush");
        JButton addBrushSize = new JButton("Increase Brush Size");
        JButton removeBrushSize = new JButton("Decrease Brush Size");
        
        //brushColor buttons
        JButton red = new JButton("Red");
        JButton green = new JButton("Green");
        JButton blue = new JButton("Blue");
        JButton rainbow = new JButton("Rainbow");
        
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                try {
                //save picture of canvas in some way
//                BufferedImage img;
//                for (int y = 0; y < c.getHeight(); y++) {
//                    for (int x = 0; x < c.getWidth(); x++) {
//                        img.setRGB(x, y, c.getPixelRGB(x, y));
//                    }
//                }
                Container pane = frame;
                BufferedImage img = new BufferedImage(pane.getWidth(), pane.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = img.createGraphics();
                pane.printAll(g2d);
                g2d.dispose();
                saveDrawing(img);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        //add popup menu callers
        modsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //show menu
                brushMods.setVisible(true);
                brushColors.setVisible(false);
            }
        });
        colorsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //show menu
                brushMods.setVisible(false);
                brushColors.setVisible(true);
            }
        });
        
        //add buttonMod listeners
        brushType.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (type < 2)
                    type++;
                else
                    type = 0;
            }
        });
        addBrushSize.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (size < MAX_BRUSH_SIZE)
                    size++;
                else
                    size = 1;
            }
        });
        removeBrushSize.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (size - 2 > 1)
                    size -= 2;
                else
                    size = MAX_BRUSH_SIZE;
            }
        });
        
        //add brushColor listeners
        red.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                consumetherainbowbecometherainbow = false;
                strokeColor = Color.RED;
            }
        });
        green.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                consumetherainbowbecometherainbow = false;
                strokeColor = Color.GREEN;
            }
        });
        blue.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                consumetherainbowbecometherainbow = false;
                strokeColor = Color.BLUE;
            }
        });
        rainbow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                consumetherainbowbecometherainbow = true;
            }
        });
        
        clearCanvas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearCanvas();
            }
        });
        
        //brush modifiers added to brush popup menu
        brushMods.add(brushType);
        brushMods.add(addBrushSize);
        brushMods.add(removeBrushSize);
        
        //brush colors added to brush popup menu
        brushColors.add(red);
        brushColors.add(green);
        brushColors.add(blue);
        brushColors.add(rainbow);
        
//        //fix up popup menus
//        //brushMod
//        JPopupMenu brushMods = createDropDownMenu();
//        ImageIcon icon = new ImageIcon(getClass().getResource("/net/codejava/swing/images/new.gif"));
//        JButton dropDown1 = DropDownButtonFactory.createDropDownButton(icon, popupMenu);
//        //brushColor
//        JPopupMenu brushColors = createDropDownMenu();
//        ImageIcon icon = new ImageIcon(getClass().getResource("/net/codejava/swing/images/new.gif"));
//        JButton dropDown2 = DropDownButtonFactory.createDropDownButton(icon, popupMenu);
        
        //add menus to toolbar
        toolbar.add(modsButton);
        toolbar.add(colorsButton);
        
        //add toolbar to bottom panel
        bottomPanel.add(toolbar);
        //add reset button
        bottomPanel.add(clearCanvas);
        
        //add side popup panel elements
        //doesn't work for some reason
        sidePanel.add(brushMods);
        sidePanel.add(brushColors);
        
        //Adding Components to the frame.
        frame.getContentPane().add(BorderLayout.SOUTH, bottomPanel);
        frame.getContentPane().add(BorderLayout.EAST, sidePanel);
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
    
    public Drawer() {
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
        stroke(strokeColor);
        c.getGraphics().drawLine(x1, y1, x2, y2);
    }
    public static void circle(int x, int y, int r) {
        stroke(strokeColor);
        Graphics2D g2 = (Graphics2D) c.getGraphics();
        Shape circle = new Ellipse2D.Double(x, y, r, r);
        g2.draw(circle);
    }
    public static void rect(int x, int y, int width, int height) {
        stroke(strokeColor);
        Graphics2D g2 = (Graphics2D) c.getGraphics();
        Shape rect = new Rectangle(x, y, width, height);
        g2.draw(rect);
    }
    public static void fillRect(int x1, int y1, int x2, int y2) {
        stroke(strokeColor);
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
        
        //for class
        mouseX = e.getX();
        mouseY = e.getY();
        
        if (consumetherainbowbecometherainbow)
            Rainbow.doIt();

        //paint?
//        paint(e.getX(), e.getY());
        int x = e.getX()-size;
        int y = e.getY()-size;
        
        if (type == 0) {
            circle(x, y, size*2);
        //brush 2
        } else if (type == 1) {
            circle(x, y, size);
        //brush 3
        } else if (type == 2){
            rect(x, y, size, size);
        //more brushes here
        } else {
            System.out.println("error choosing fractal drawing type");
        }
    }
    
    //Other methods
    private static void saveDrawing (BufferedImage img) throws IOException {
        try {
            ImageIO.write(img, "png", new File("Saved Images\\save.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

/*                                 Sources:
********************************************************************************
Template and drawing tools:         SLOHS, SLO, CA.
Mouse events:                       https://www.geeksforgeeks.org/mouselistener-mousemotionlistener-java/
Mouse motion listener Ttutorial:    https://docs.oracle.com/javase/tutorial/uiswing/examples/events/index.html#MouseMotionEventDemo
Button listeners:                   https://stackoverflow.com/questions/21879243/how-to-create-on-click-event-for-buttons-in-swing/21879526
Angle calculation:                  https://stackoverflow.com/questions/9970281/java-calculating-the-angle-between-two-points-in-degrees
Menus:                              https://www.codejava.net/java-se/swing/how-to-create-drop-down-button-in-swing
Menu buttons:                       https://stackoverflow.com/questions/12984207/cannot-convert-current-canvas-data-into-image-in-java
Save As button:                         
*/