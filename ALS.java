import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
//import java.awt.Image;

public class ALS extends JFrame{
    public ALS(){
	setSize(500, 500);
	setTitle("Lonely Satellite");
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	MyJPanel myJPanel = new MyJPanel();
	Container c = getContentPane();
	c.add(myJPanel);
	setVisible(true);
	setResizable(false);
    }

    public static void main(String[] args){
	new ALS();
    }

    public class MyJPanel extends JPanel implements ActionListener,MouseListener,MouseMotionListener,MouseWheelListener,KeyListener{

	final int start = 0;
	final int play = 1;
	final int game_over = 2;
	final int game_clear = 3;

	Timer timer;
	int phase = 0;
	int num_color = 0;
	double my_x,my_y;
	int px,py;
	int earth_width,earth_height;
	int earth_life = 5;
	double my_arg;
	double end_rad = 0.5;
	int my_dir = 1;
	int n = 36;
	int k = 0;
	int num_missile = 5;
	int num_of_alive = n;
	int num_of_wave = 4;
	double teki_x[] = new double[n];
	double teki_y[] = new double[n];
	double teki_arg[] = new double[n];
	int teki_rad[] = new int[n];
	int teki_initx[] = new int[n];
	int teki_inity[] = new int[n];
	int teki_px[] = new int[n];
	int teki_py[] = new int[n];
	int teki_move[] = new int[n];
	int teki_alive[] =new int[n]; 
	int teki_life[] = new int[n];
	double my_missile_arg[] = new double[num_missile];
	double my_missile_rad[] = new double[num_missile];
	double my_missile_x[] = new double[num_missile];
	double my_missile_y[] = new double[num_missile];
	int my_missile_inix[] = new int[num_missile];
	int my_missile_iniy[] = new int[num_missile];
	int my_missile_px[] = new int[num_missile];
	int my_missile_py[] = new int[num_missile];	
	int missile_flag[] = new int[num_missile];

	int wave_rad[] = new int[num_of_wave];
	int wave_flag[] = new int[num_of_wave];
	int wave_color[] = new int[num_of_wave];
	int wave_num = 0;
	int wave_move = 10;
	int wave_speed = 1;
	boolean damage = false;

	Dimension d;
	Random rnd = new Random();
	
	Image earth,bomb;
	
	public MyJPanel(){
	    int i = 0;
	    setBackground(Color.black);
	    addMouseListener(this);
	    addMouseMotionListener(this);
	    addMouseWheelListener(this);
	    setFocusable(true);
	    addKeyListener(this);

	    d = getSize();
	    earth_life = 5;
	    wave_num = 0;

	    for (i = 0; i < n; i++) {
		teki_alive[i] = 1;
		teki_life[i]  = 1;
		teki_rad[i] = 100 + 10*i;
		teki_arg[i] = i * 360 / n;
		teki_initx[i] = (int)(250*Math.sin(teki_arg[i])+250);
		teki_inity[i] = (int)(250-250*Math.cos(teki_arg[i]));	       
	    }

	    for (i = 0; i < num_missile;i++){
		missile_flag[i] = 0;
		my_missile_rad[i] = 10;
	    }

	    for (i = 0; i < num_of_wave;i++){
		wave_flag[i] = 0;
		wave_rad[i] = 0;
		wave_color[i] = rnd.nextInt(5);
	    }
						
	    ImageIcon icon1 = new ImageIcon("bomb.png");
	    bomb = icon1.getImage();

	    ImageIcon icon2 = new ImageIcon("earth.png");
	    earth = icon2.getImage();
	    earth_width = earth.getWidth(this);
	    earth_height = earth.getHeight(this);		
	      
	    timer = new Timer(100,this);
	    timer.start();
	}
	
	public void paintComponent(Graphics g){
	    d=getSize();
	    super.paintComponent(g);
	    switch(phase){
	    case start:
		g.setColor(Color.white);
		g.drawString("wheel:move",10,20);
		g.drawString("click:shot",10,40);
		g.drawString("[Z]/[C]:change color",10,60);
		g.drawImage(earth,d.width/2 - earth_width/2,d.height/2 - earth_height/2,this);
		my_x = Math.cos(my_arg)*0.5;
		my_y = Math.sin(my_arg)*0.5;
		px=(int)(d.width/2*my_x+d.width/2);
		py=(int)(d.height/2-d.height/2*my_y);
	    
		switch(num_color){
		case 0:
		    g.setColor(Color.green);
		    break;
		case 1:
		    g.setColor(Color.cyan);
		    break;
		case 2:
		    g.setColor(Color.yellow);
		    break;
		case 3:
		    g.setColor(Color.magenta);
		    break;
		case 4:
		    g.setColor(Color.pink);
		    break;
		}
		g.drawString("Click to Start",d.width/2,3*d.height/4);
		g.drawOval(px,py,10,10);
		g.drawOval(d.width/2-earth_width/2-10,d.height/2-earth_height/2-10,150,150);
		if(my_dir==1){my_arg+=0.1;}
		else{my_arg-=0.1;}
		break;
		
	    case play:
		g.drawImage(earth,d.width/2 - earth_width/2,d.height/2 - earth_height/2,this);
		switch(earth_life){
		case 5:	       
		    break;
		case 4:
		    g.setColor(new Color(255,0,0,20));
		    g.fillOval(d.width/2-earth_width/2-10,d.height/2-earth_height/2-10,150,150);
		    break;	
		case 3:
		    g.setColor(new Color(255,0,0,40));
		    g.fillOval(d.width/2-earth_width/2-10,d.height/2-earth_height/2-10,150,150);		
		    break;
		case 2:
		    g.setColor(new Color(255,0,0,60));
		    g.fillOval(d.width/2-earth_width/2-10,d.height/2-earth_height/2-10,150,150);		
		    break;
		case 1:
		    g.setColor(new Color(255,0,0,80));
		    g.fillOval(d.width/2-earth_width/2-10,d.height/2-earth_height/2-10,150,150);		
		    break;
		case 0:
		    phase = game_over;	       
		}
	    
		my_x = Math.cos(my_arg)*0.5;
		my_y = Math.sin(my_arg)*0.5;
		px=(int)(d.width/2*my_x+d.width/2);
		py=(int)(d.height/2-d.height/2*my_y);
	    
		switch(num_color){
		case 0:
		    g.setColor(Color.green);
		    break;
		case 1:
		    g.setColor(Color.cyan);
		    break;
		case 2:
		    g.setColor(Color.yellow);
		    break;
		case 3:
		    g.setColor(Color.magenta);
		    break;
		case 4:
		    g.setColor(Color.pink);
		    break;
		}
	    
		g.drawOval(px,py,10,10);
		g.drawOval(d.width/2-earth_width/2-10,d.height/2-earth_height/2-10,150,150);
	    
		for(int i = 0; i < num_missile; i++){
		    if(missile_flag[i]==1){
			my_missile_rad[i] += 10;
			g.setColor(Color.white);
			my_missile_x[i] = (my_missile_rad[i]*Math.sin(my_missile_arg[i]+Math.PI/2));
			my_missile_y[i] = (my_missile_rad[i]*Math.cos(my_missile_arg[i]+Math.PI/2));
			my_missile_px[i]= my_missile_inix[i] + (int)my_missile_x[i];
			my_missile_py[i]= my_missile_iniy[i] + (int)my_missile_y[i];
		  
			g.fillOval(my_missile_px[i],my_missile_py[i],10,10);		 
		    
			for(int j = 0; j < n;j++){
			    if(Math.abs(my_missile_px[i]-teki_px[j])<10 && Math.abs(my_missile_py[i]-teki_py[j])<10 && teki_alive[j] == 1) {
				teki_life[j]--;
				if(teki_life[j]==0) {
				    teki_alive[j] = 0;
				    num_of_alive--;
				    if(num_of_alive == 0){
					//System.out.println("GAME CLEAR!");
					phase = 3;
					//System.exit(0);
				    }
				    //System.out.println(num_of_alive);
				    g.drawImage(bomb,teki_px[j]-5, teki_py[j]-5, this);
				}
				missile_flag[i] = 0;
				my_missile_rad[i]=0;
			    }
		    		 
			    if(my_missile_px[i] > d.width || my_missile_px[i] < 0 || my_missile_py[i] > d.height || my_missile_py[i] < 0){
				my_missile_rad[i]=0;
				missile_flag[i] = 0;
			    }
			}
		    }
		}
		
		
		for (int i = 0; i < n; i++){
		    if(teki_alive[i] == 1){
			teki_rad[i] -= 1;
			//System.out.println(teki_rad[0]);
			teki_x[i] = 0.5*teki_rad[i]*Math.sin(teki_arg[i]);
			teki_y[i] = 0.5*teki_rad[i]*Math.cos(teki_arg[i]); 
			teki_px[i]=teki_initx[i] + (int)teki_x[i]+ (int)((Math.random()-0.5)*10);
			teki_py[i]=teki_inity[i] - (int)teki_y[i]+ (int)((Math.random()-0.5)*10);
			g.setColor(Color.red);
			g.drawOval(teki_px[i],teki_py[i], 10,10);
			if(teki_rad[i] < -350){
			    num_of_alive--;
			    earth_life--;
			    teki_alive[i]=0;
			}
		    }
		}

		for(int i=0;i<num_of_wave;i++){
		    if(wave_flag[i]==1){
			switch(wave_color[wave_num]){
			case 0:
			    g.setColor(Color.green);
			    break;
			case 1:
			    g.setColor(Color.cyan);
			    break;
			case 2:
			    g.setColor(Color.yellow);
			    break;
			case 3:
			    g.setColor(Color.magenta);
			    break;
			case 4:
			    g.setColor(Color.pink);
			    break;			
			}
			wave_rad[i] += wave_move;
			//System.out.println(wave_rad[i]);
			if(damage == false){
			    if(wave_rad[i] > 500){
				if(wave_color[i]==num_color){
				    wave_move = -wave_move;

				}else{
				    earth_life--;
				    damage = true;
				}		      
			    }else{
				//wave_rad[i] += wave_move;
			    }
			}

		    
			switch(i){
			case 0:
			    g.drawOval(-d.width/2,-d.height/2,wave_rad[i] ,wave_rad[i]);
			    break;
			case 1:
			    g.drawOval(-d.width/2,3*d.height/2-wave_rad[i],wave_rad[i] ,wave_rad[i]);
			    break;
			case 2:
			    g.drawOval(3*d.width/2-wave_rad[i],3*d.height/2-wave_rad[i],wave_rad[i] ,wave_rad[i]);
			    break;
			case 3:
			    g.drawOval(3*d.width/2-wave_rad[i],-d.height/2,wave_rad[i] ,wave_rad[i]);
			    break;		
			}		    
			if(wave_rad[i]>1000 || wave_rad[i]<0){
			    if(damage){
				damage = false;}
			    else{
				wave_move=-wave_move;
				wave_move+=wave_speed;}
			    wave_flag[i]=0;
			    wave_rad[i]=0;
			    wave_color[wave_num] = rnd.nextInt(5);
			    wave_num++;

			    if(wave_num == num_of_wave){
				wave_num=0;
			    }
			}		 
		    }
		    else {
			wave_flag[wave_num] = 1;
		    }	       
		}
		break;

	    case game_over:
		g.setColor(Color.red);
		g.drawString("GAME OVER",d.width/2,3*d.height/4);
		g.drawString("SPACE to Restart",d.width/2+10,3*d.height/4+20);
		my_x = Math.cos(my_arg)*end_rad;
		my_y = Math.sin(my_arg)*end_rad;
		px=(int)(d.width/2*my_x+d.width/2);
		py=(int)(d.height/2-d.height/2*my_y);
	    
		switch(num_color){
		case 0:
		    g.setColor(Color.green);
		    break;
		case 1:
		    g.setColor(Color.cyan);
		    break;
		case 2:
		    g.setColor(Color.yellow);
		    break;
		case 3:
		    g.setColor(Color.magenta);
		    break;
		case 4:
		    g.setColor(Color.pink);
		    break;
		}
	    
		g.drawOval(px,py,10,10);

		if(my_dir==1){my_arg+=0.1;}
		else{my_arg-=0.1;}
		end_rad+=0.01;
		break;

	    case game_clear:
		g.drawImage(earth,d.width/2 - earth_width/2,d.height/2 - earth_height/2,this);
		my_x = Math.cos(my_arg)*0.5;
		my_y = Math.sin(my_arg)*0.5;
		px=(int)(d.width/2*my_x+d.width/2);
		py=(int)(d.height/2-d.height/2*my_y);
	    
		switch(rnd.nextInt(5)){
		case 0:
		    g.setColor(Color.green);
		    break;
		case 1:
		    g.setColor(Color.cyan);
		    break;
		case 2:
		    g.setColor(Color.yellow);
		    break;
		case 3:
		    g.setColor(Color.magenta);
		    break;
		case 4:
		    g.setColor(Color.pink);
		    break;
		}
		g.drawOval(px,py,10,10);
		g.drawOval(d.width/2-earth_width/2-10,d.height/2-earth_height/2-10,150,150);
		g.drawString("Congratulations!",d.width/2,3*d.height/4);
		g.drawString("SPACE to Exit",d.width/2+10,3*d.height/4+20);
		if(my_dir==1){my_arg+=0.1;}
		else{my_arg-=0.1;}
		break;
		
	    }
	}

	public void actionPerformed(ActionEvent e){
	    repaint();
	}

	public void mouseMoved(MouseEvent me){
	}
	
	public void mouseDragged(MouseEvent e){
	}

	public void mouseEntered(MouseEvent me){
	}

	public void mouseExited(MouseEvent me){
	}

	public void mousePressed(MouseEvent e){
	}

	public void mouseReleased(MouseEvent e){
	}

	public void mouseClicked(MouseEvent e){
	    switch(phase){
	    case 0:
		phase = 1;
		break;
	    case 1:
		if(k == num_missile) k = 0;
		if (missile_flag[k] == 0){
		    my_missile_arg[k] = my_arg;
		    my_missile_inix[k] = (int)px;
		    my_missile_iniy[k] = (int)py;
		    missile_flag[k] = 1;
		    k++;
		}
		break;
	    case 2:
		break;
 	    case 3:
		break;				
	    }	    
	}

	public void mouseWheelMoved(MouseWheelEvent e){
	    switch(phase){
	    case start:
		if(e.getWheelRotation()>=0){
		    my_dir=1;
		}else{
		    my_dir=-1;
		}
		break;
	    case play:
		my_arg += e.getWheelRotation()*0.05;
	    case game_over:
		if(e.getWheelRotation()>=0){
		    my_dir=1;
		}else{
		    my_dir=-1;
		}
		break;
	    case game_clear:
		if(e.getWheelRotation()>=0){
		    my_dir=1;
		}else{
		    my_dir=-1;
		}
		break;
	    }
	    
	}
	
	public void keyPressed(KeyEvent e){
	    int key = e.getKeyCode();
	    if (key==KeyEvent.VK_RIGHT || key==KeyEvent.VK_Z) {
		num_color+=1;
		if(num_color > 4){
		    num_color = 0;
		}
	    }
	    if (key==KeyEvent.VK_LEFT || key==KeyEvent.VK_C) {
		num_color-=1;
		if(num_color < 0) {
		   num_color = 4;
		}
	    }
	    if (key==KeyEvent.VK_Q) {
		phase = 3;
	    }
	    if (key==KeyEvent.VK_W) {
		phase = 2;
	    }		
	    if (key==KeyEvent.VK_SPACE){
		switch(phase){
		case 0:
		    break;
		case 1:
		    break;
		case 2:
		    earth_life = 5;
		    num_of_alive = n;
		    num_color = 0;
		    wave_num = 0;
		    wave_move = 10;
		    wave_speed = 1;
		    end_rad = 0.5;
		    damage = false;
		    for (int i = 0; i < n; i++) {
			teki_alive[i] = 1;
			teki_life[i]  = 1;
			teki_rad[i] = 100 + 10*i;
			teki_arg[i] = i * 360 / n;
			teki_initx[i] = (int)(250*Math.sin(teki_arg[i])+250);
			teki_inity[i] = (int)(250-250*Math.cos(teki_arg[i]));		
		    }

		    for (int i = 0; i < num_missile;i++){
			missile_flag[i] = 0;
			my_missile_rad[i] = 10;
		    }
		    for (int i = 0; i < num_of_wave;i++){
			wave_flag[i] = 0;
			wave_rad[i] = 0;
			wave_color[i] = rnd.nextInt(5);
		    }
		    phase = 0;
		    break;
		case 3:
		    System.exit(0);
		    break;
		}
	    }
	    
	}
	
	public void keyReleased(KeyEvent e){
	}
	
	public void keyTyped(KeyEvent e){
	}
    }
}
