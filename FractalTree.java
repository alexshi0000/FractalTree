import java.util.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;

public class FractalTree extends Thread{

	static int depth = 6, split = 20, wave = 0, len = 10, branches = 2, offset = 0, v = 0, h = 0, foliage = 0, segment = 0, special = 0;	//so much to play with
	static JFrame frame;
	static JSlider depth_slider, split_slider, wave_slider, len_slider, branch_slider, offset_slider, v_slider, h_slider, foliage_slider, segment_slider, special_slider;
	static boolean changed = false;
	static boolean day = true;
	static long counter = 0;

	public static class Tree extends JPanel{
		private static final long serialVersionUID = 1L;
		static int length;																			//static vars, these need to be called recursively so we cant use static members
		static int width;
		
		public void paintComponent(Graphics g2){													//overloaded method called when Tree is added to our frame
			Graphics2D g = (Graphics2D) g2;
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);	//gotta make it look pretty :)
			g.setColor(new Color(25,20,30));
			g.fillRect(0,0,frame.getWidth(),frame.getHeight());
			length = depth;
			width = split;
			int angle = -90+wave;																	//the reason we set this as negative is because this is a reverse fractal tree, java graphics is opposite to coordinate system for y axis
			drawTree(g,frame.getWidth()/2,(int)(frame.getHeight()*0.80),length,angle,-1);
		}
		
		public void drawTree(Graphics g, int x1, int y1, int length, int angle, int d){					//recursive draw tree method
			if(length <= 0) return;
			if(length == depth) g.setColor(new Color(140,80,20));
			else if(length >= depth/2) g.setColor(new Color(150,90,30));							//conditionals for some added graphical features
			else if(length == 1) g.setColor(new Color(100,255,30));
			else g.setColor(new Color(0,225,55));
			int x2 = x1 + (int) (Math.cos(Math.toRadians(angle)) * length * len) + h;				//length is length of the line len is magnitude
			int y2 = y1 + (int) (Math.sin(Math.toRadians(angle)) * length * len) + v;				//equal to h and v tuning translation on each node
			g.drawLine(x1,y1,x2,y2); 
			if(length == 5){
				for(int i = 0; i < branches+foliage; i++){
					if(i % branches == special) drawTree(g,x2,y2,length-1-segment,(angle - width) + (width*2)/(branches+foliage-1)*i + offset,d);		//leafs, in special case one brach offset
					else drawTree(g,x2,y2,length-1,(angle - width) + (width*2)/(branches+foliage-1)*i + offset,d);
				}		
			}
			else{
				for(int i = 0; i < branches; i++){
					if(i % branches == special) drawTree(g,x2,y2,length-1-segment,(angle - width) + (width*2)/(branches-1)*i + offset,d);				//branching, offset is change in angle right left
					else drawTree(g,x2,y2,length-1,(angle - width) + (width*2)/(branches-1)*i + offset,d); 
				}	
			}																																		//width is the angle of each branching node
		}
	}

	public static void init(){
		
		frame = new JFrame();
		frame.setBounds(0, 0,1200,900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);										//JFrame stuffs
		frame.setTitle("Fractal Tree");
		frame.setSize(1000,800);
		
		depth_slider = new JSlider(JSlider.VERTICAL,2,14,6);
		split_slider = new JSlider(JSlider.HORIZONTAL,0,360,20);
		wave_slider = new JSlider(JSlider.HORIZONTAL,-360,360,0);										//lots of sliders
		len_slider = new JSlider(JSlider.VERTICAL,2,50,10);
		branch_slider = new JSlider(JSlider.VERTICAL,2,5,2);
		offset_slider = new JSlider(JSlider.HORIZONTAL,-90,90,0);
		v_slider = new JSlider(JSlider.VERTICAL,-50,50,0);
		h_slider = new JSlider(JSlider.HORIZONTAL,-50,50,0);
		segment_slider = new JSlider(JSlider.VERTICAL,0,5,0);
		foliage_slider = new JSlider(JSlider.HORIZONTAL,0,5,0);
		special_slider = new JSlider(JSlider.HORIZONTAL,0,5,0);
		
		depth_slider.setBackground(new Color(25,20,30));
		split_slider.setBackground(new Color(25,20,30));											
		wave_slider.setBackground(new Color(25,20,30));
		len_slider.setBackground(new Color(25,20,30));
		branch_slider.setBackground(new Color(25,20,30));
		offset_slider.setBackground(new Color(25,20,30));
		h_slider.setBackground(new Color(25,20,30));
		v_slider.setBackground(new Color(25,20,30));
		segment_slider.setBackground(new Color(25,20,30));
		foliage_slider.setBackground(new Color(25,20,30));
		special_slider.setBackground(new Color(25,20,30));
		
		JPanel control1 = new JPanel(new BorderLayout());			
		JPanel control2 = new JPanel(new BorderLayout());			
		JPanel option1 = new JPanel(new BorderLayout());
		JPanel option2 = new JPanel(new BorderLayout());
		JPanel bottom = new JPanel(new BorderLayout());
		JPanel side = new JPanel(new BorderLayout());
		
		control1.add(split_slider,BorderLayout.WEST);
		control1.add(offset_slider,BorderLayout.CENTER);
		control1.add(wave_slider,BorderLayout.EAST);
		control2.add(foliage_slider,BorderLayout.WEST);
		control2.add(special_slider,BorderLayout.CENTER);
		control2.add(h_slider,BorderLayout.EAST);
		option1.add(depth_slider,BorderLayout.NORTH);
		option1.add(len_slider);
		option2.add(v_slider,BorderLayout.NORTH);
		option2.add(branch_slider,BorderLayout.CENTER);
		option2.add(segment_slider,BorderLayout.SOUTH);
		bottom.add(control1,BorderLayout.CENTER);
		bottom.add(control2, BorderLayout.SOUTH);
		side.add(option1,BorderLayout.CENTER);
		side.add(option2,BorderLayout.EAST);
		frame.add(bottom,BorderLayout.SOUTH);
		frame.add(side,BorderLayout.EAST);
		
		Event e = new Event();																		
		depth_slider.addChangeListener(e);
		split_slider.addChangeListener(e);
		wave_slider.addChangeListener(e);
		len_slider.addChangeListener(e);
		offset_slider.addChangeListener(e);
		branch_slider.addChangeListener(e);
		v_slider.addChangeListener(e);
		h_slider.addChangeListener(e);
		segment_slider.addChangeListener(e);
		foliage_slider.addChangeListener(e);
		special_slider.addChangeListener(e);
		frame.setVisible(true);
		frame.add(new Tree());
	}
	
	public static class Event implements ChangeListener{											//handling events in thew sliders
		public void stateChanged(ChangeEvent e){													//overloaded method for handling changes
			depth = depth_slider.getValue();
			split = split_slider.getValue();
			len = len_slider.getValue();
			wave = wave_slider.getValue();
			branches = branch_slider.getValue();
			offset = offset_slider.getValue();
			v = v_slider.getValue();
			h = h_slider.getValue();
			foliage = foliage_slider.getValue();
			special = special_slider.getValue();
			segment = segment_slider.getValue();
			changed = true;
		}
	}
		
	public static void main(String[] args){															//main driver
		init();
		while(true){
			if(counter > 45){
				day = !day;
				counter = 0;
			}
			if(changed){
				frame.add(new Tree());
				frame.setVisible(true);
				changed = false;
			}
			try{
				Thread.sleep(150);																	//fractal trees take a while to compute so 
			} catch (Exception e){
				
			}
			counter++;
		}
	}
}