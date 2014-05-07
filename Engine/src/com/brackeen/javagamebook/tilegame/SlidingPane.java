package com.brackeen.javagamebook.tilegame;
/**
 *
 * @author alexgarza
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
public class SlidingPane extends JFrame {
    public SoundClip pium;
    
public static boolean empezar=false;
public static  int x=1;
private SlidePane slidePane = new SlidePane();
private SoundClip Musicafondo;
private SoundClip laugh;
private Image fondo;
    public SlidingPane() {
        Musicafondo = new SoundClip("sounds/slicey.wav");
        Musicafondo.setLooping(true);
        Musicafondo.play();
        fondo = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("images/FondoMenu.png"));
        
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }
                JButton slideButton = new JButton("Instrucciones");
                slideButton.setBackground(Color.BLUE);
                slideButton.setOpaque(false);
                slideButton.setForeground(Color.BLUE);
                slideButton.setFont(new Font("Dialog", 1, 30));
                
                
                
                
                
                JButton slideButtonS = new JButton("Start");
                slideButtonS.setSize(500,500);
                slideButtonS.setBackground(Color.BLUE);
                slideButtonS.setOpaque(false);
                slideButtonS.setForeground(Color.BLUE);
                slideButtonS.setFont(new Font("Dialog", 1, 30));
                JButton slideButtonH = new JButton("About Us");
                slideButtonH.setSize(500,500);
                      slideButtonH.setBackground(Color.BLUE);
                slideButtonH.setOpaque(false);
                slideButtonH.setForeground(Color.BLUE);
                slideButtonH.setFont(new Font("Dialog", 1, 30));
                
                slideButtonS.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    //pium.play();
                x=0;                 
                    }
                });
                
                slideButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //pium.play();
                         JOptionPane.showMessageDialog(null, "Utiliza las flechas para mover al personaje, encontrar la llave y escapar de la mansion");
                    }
                });
                
                slideButtonH.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //pium.play();
                        slidePane.slide();
                    }
                });
                JFrame frame = new JFrame("Menu");
                try {
                    frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("images/FondoMenu.png")))));
                } catch (IOException ex) {
                    Logger.getLogger(SlidingPane.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                JPanel glassPane = new JPanel(null);
                glassPane.setOpaque(false);
                glassPane.add(slidePane);
                glassPane.setBackground(Color.BLUE);
                frame.setGlassPane(glassPane);
                glassPane.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(400,0,0,20);
                //frame.add(new JLabel("Lights Out"), gbc);
                gbc.gridx=100;
                gbc.gridy=300;
                slideButtonS.setBounds(100,300,200,200);
                
                
                
                
              
                frame.add(slideButton,gbc);
                gbc.gridx=200;
                gbc.gridy=300;
                frame.add(slideButtonS,gbc);
                  gbc.gridx=300;
                gbc.gridy=300;
                frame.add(slideButtonH,gbc);
                frame.setSize(1000, 800);
                frame.setLocationRelativeTo(null);
                
                
                frame.setVisible(true);
            }
            private void add(JPanel p, String SOUTH) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
    public class SlidePane extends JPanel {
        private long startTime = -1;
        private int runTime = 1000;
        private int startX;
        private int targetX;
        private boolean slideIn = false;
        private Timer slideTimer;
        public SlidePane() {
            setBackground(Color.blue);
            setBorder(new LineBorder(Color.BLACK));
            setLocation(-getPreferredSize().width, 0);
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(20,20,20,20);
            JLabel label = new JLabel("TEAM ARKA");
            JLabel label2 = new JLabel("Members:");
            JLabel label3 = new JLabel("Kevin Estrada");
            JLabel label4 = new JLabel("Alejandro Garza");
            JLabel label5 = new JLabel("Rubén Pacheco");
            JLabel label6 = new JLabel("Angel González");
            
            
            
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Dialog", 1, 30));
            label2.setForeground(Color.WHITE);
            label2.setFont(new Font("Dialog", 1, 30));
            label3.setForeground(Color.WHITE);
            label3.setFont(new Font("Dialog", 1, 20));
            label4.setForeground(Color.WHITE);
            label4.setFont(new Font("Dialog", 1, 20));
            label5.setForeground(Color.WHITE);
            label5.setFont(new Font("Dialog", 1, 20));
            label6.setForeground(Color.WHITE);
            label6.setFont(new Font("Dialog", 1, 20));
            
            gbc.gridx=0;
            gbc.gridy=1;
            add(label,gbc);
            gbc.gridx=0;
            gbc.gridy=2;
            add(label2,gbc);
            gbc.gridx=0;
            gbc.gridy=3;
            add(label3,gbc);
            gbc.gridx=0;
            gbc.gridy=4;
            add(label4,gbc);
            gbc.gridx=0;
            gbc.gridy=5;
            add(label5,gbc);
            gbc.gridx=0;
            gbc.gridy=6;
            add(label6,gbc);
            
            slideTimer = new Timer(40, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    long diff = System.currentTimeMillis() - startTime;
                    double progress = (double)diff / (double)runTime;
                    if (progress >= 1d) {
                        progress = 1d;
                        slideTimer.stop();
                        startTime = -1;
                    }
                    Container parent = getParent();
                    int height = parent.getHeight();
                    setSize(getPreferredSize().width, height);
                    int x = calculateProgress(startX, targetX, progress);
                    setLocation(x, 0);
                    revalidate();
                    repaint();
                }
            });
        }
        protected int calculateProgress(int startValue, int endValue, double fraction) {
            int value = 0;
            int distance = endValue - startValue;
            value = (int) Math.round((double) distance * fraction);
            value += startValue;
            return value;
        }
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 400);
        }
        public void slide() {
            slideTimer.stop();
            startTime = System.currentTimeMillis();
            slideIn = !slideIn;
            startX = getX();
            targetX = 0;
            if (!slideIn) {
                targetX = -getPreferredSize().width;
            }
            slideTimer.start();
        }
    }
    
    
}