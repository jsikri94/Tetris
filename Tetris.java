/*
I wasn't able to implement Point-Inside-Polygon to change shape in PAUSE
mode and couldn't implement changing height and width of main area.
I also haven't tried the part for extra credit.
*/
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import javax.swing.Timer;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;





public class Tetris{
	public static void main(String args[]){
		JFrame frame = new JFrame("Tetris Part 3");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1400,700);
		frame.add(new TPanel());
		frame.setVisible(true);
	}
}





class TPanel extends JPanel implements ActionListener, ChangeListener{

	//Defining the 7 main tetris pieces
	private boolean squarepiece[][] = {{false,false,false,false},{true,true,false,false},{true,true,false,false},{false,false,false,false}};
	private boolean straightpiece1[][] = {{false,false,false,false},{false,false,false,false},{true,true,true,true},{false,false,false,false}};
	private boolean lpieceone1[][] = {{false,false,false,false},{true,false,false,false},{true,true,true,false},{false,false,false,false}};
	private boolean lpiecetwo1[][] = {{false,false,false,false},{false,false,true,false},{true,true,true,false},{false,false,false,false}};
	private boolean spieceone1[][] = {{false,false,false,false},{false,true,true,false},{true,true,false,false},{false,false,false,false}};
	private boolean spiecetwo1[][] = {{false,false,false,false},{true,true,false,false},{false,true,true,false},{false,false,false,false}}; 
	private boolean tpiece1[][] = {{false,false,false,false},{false,true,false,false},{true,true,true,false},{false,false,false,false}};

	//Defining the rotated forms of the tetris pieces
	private boolean straightpiece2[][] = {{false,true,false,false},{false,true,false,false},{false,true,false,false},{false,true,false,false}};
	private boolean straightpiece3[][] = {{false,false,false,false},{true,true,true,true},{false,false,false,false},{false,false,false,false}};
	private boolean straightpiece4[][] = {{false,false,true,false},{false,false,true,false},{false,false,true,false},{false,false,true,false}};
	private boolean lpieceone2[][] = {{false,false,false,false},{false,true,true,false},{false,true,false,false},{false,true,false,false}};
	private boolean lpieceone3[][] = {{false,false,false,false},{false,false,false,false},{true,true,true,false},{false,false,true,false}};
	private boolean lpieceone4[][] = {{false,false,false,false},{false,true,false,false},{false,true,false,false},{true,true,false,false}};
	private boolean lpiecetwo2[][] = {{false,false,false,false},{false,true,false,false},{false,true,false,false},{false,true,true,false}};
	private boolean lpiecetwo3[][] = {{false,false,false,false},{false,false,false,false},{true,true,true,false},{true,false,false,false}};
	private boolean lpiecetwo4[][] = {{false,false,false,false},{true,true,false,false},{false,true,false,false},{false,true,false,false}};
	private boolean spieceone2[][] = {{false,false,false,false},{true,false,false,false},{true,true,false,false},{false,true,false,false}};
	private boolean spiecetwo2[][] = {{false,false,false,false},{false,false,true,false},{false,true,true,false},{false,true,false,false}};
	private boolean tpiece2[][] = {{false,false,false,false},{false,true,false,false},{false,true,true,false},{false,true,false,false}};
	private boolean tpiece3[][] = {{false,false,false,false},{false,false,false,false},{true,true,true,false},{false,true,false,false}};
	private boolean tpiece4[][] = {{false,false,false,false},{false,true,false,false},{true,true,false,false},{false,true,false,false}};

	private boolean aa[][] = {{false,true,false,false},{true,true,false,false},{false,false,false,false},{false,false,false,false}};
	private boolean ba[][] = {{false,false,false,false},{true,false,false,false},{false,true,true,false},{false,false,false,false}};
	private boolean ca[][] = {{false,false,false,false},{true,true,true,false},{false,false,false,false},{false,false,false,false}};
	private boolean da[][] = {{false,false,false,false},{false,false,true,false},{false,true,false,false},{false,false,false,false}};
	private boolean ea[][] = {{false,false,false,false},{false,true,false,false},{false,true,false,false},{false,false,false,false}};
	private boolean fa[][] = {{false,false,false,false},{false,true,false,false},{true,false,true,false},{false,false,false,false}};
	private boolean ga[][] = {{false,false,false,false},{true,false,false,false},{false,false,false,false},{false,false,false,false}};
	private boolean ha[][] = {{false,false,false,false},{false,false,true,false},{false,true,false,false},{true,false,false,false}};

	//Defining HashMaps and other variables that will be used all over the program
	private boolean main_area[][] = new boolean[20][10];
	Random rand = new Random();
	HashMap<Integer, boolean[][]> pieces = new HashMap<Integer, boolean[][]>();
	HashMap<Integer, Color> pieceColors = new HashMap<Integer, Color>();
	TreeMap<Integer, int[]> pieceCoord = new TreeMap<>();
	ArrayList<Integer> temp = new ArrayList<Integer>();
	public boolean inside_mainarea = false;
	public boolean start_game = false;
	public int centerX, centerY, maxX, maxY, pixelSize, size;
	Rectangle mainarea, nextpiecearea, quitarea, pausearea, startarea;
	public int main_area_num = rand.nextInt(7)+1, next_area_num = rand.nextInt(7)+1;
	BlockSquare bs1, bs2;
	Timer timer;
	boolean flag_timer = false;
	int block_i, block_j, block_ID, block_x, block_y;
	public static int pieceCount =0;
	Color currentColor;
	boolean[][] currentPiece = new boolean[2][4];
	JSlider slider1, slider3, slider2;
	JLabel scf, r, spf;
	public int lines = 0, i_lines = 0, level =1, score = 0, fs = 500;
	public int count_blocks = 0;
	public int remove_blocks = 3;
	public int m,n;
	public double s;
	public boolean terminated = false;

	//Constructor
	public TPanel(){
		pieces.put(1,squarepiece);
		pieces.put(2,straightpiece1);
		pieces.put(3,lpieceone1);
		pieces.put(4,lpiecetwo1);
		pieces.put(5,spieceone1);
		pieces.put(6,spiecetwo1);
		pieces.put(7,tpiece1);
		pieces.put(8,straightpiece2);
		pieces.put(9,straightpiece3);
		pieces.put(10,straightpiece4);
		pieces.put(11,lpieceone2);
		pieces.put(12,lpieceone3);
		pieces.put(13,lpieceone4);
		pieces.put(14,lpiecetwo2);
		pieces.put(15,lpiecetwo3);
		pieces.put(16,lpiecetwo4);
		pieces.put(17,spieceone2);
		pieces.put(18,spiecetwo2);
		pieces.put(19,tpiece2);
		pieces.put(20,tpiece3);
		pieces.put(21,tpiece4);

		pieceColors.put(1,new Color(0,176,80));
		pieceColors.put(2,new Color(0,176,240));
		pieceColors.put(3,new Color(0,112,192));
		pieceColors.put(4,Color.RED);
		pieceColors.put(5,Color.YELLOW);
		pieceColors.put(6,new Color(112,48,160));
		pieceColors.put(7,new Color(255,192,0));
		pieceColors.put(8,new Color(0,176,240));
		pieceColors.put(9,new Color(0,176,240));
		pieceColors.put(10,new Color(0,176,240));
		pieceColors.put(11,new Color(0,112,192));
		pieceColors.put(12,new Color(0,112,192));
		pieceColors.put(13,new Color(0,112,192));
		pieceColors.put(14,Color.RED);
		pieceColors.put(15,Color.RED);
		pieceColors.put(16,Color.RED);
		pieceColors.put(17,Color.YELLOW);
		pieceColors.put(18,new Color(112,48,160));
		pieceColors.put(19,new Color(255,192,0));
		pieceColors.put(20,new Color(255,192,0));
		pieceColors.put(21,new Color(255,192,0));

		//defining the initial pieces
		bs1 = new BlockSquare(pieceColors.get(main_area_num),-1,4);
		bs2 = new BlockSquare(pieceColors.get(next_area_num),0,17);

		timer = new Timer(fs,this);
		slider1 = new JSlider(1,10);
		slider2 = new JSlider(20,50);
		slider3 = new JSlider(1,10);
		scf = new JLabel("Scoring factor");
		r = new JLabel("Number of rows needed to advance to next level");
		spf = new JLabel("Speed factor of falling pieces");
		slider1.setMajorTickSpacing(1);
		slider1.setMinorTickSpacing(1);
		slider1.setPaintTicks(true);
		slider1.setPaintLabels(true);
		slider1.setName("scoringfactor");
		slider2.setMajorTickSpacing(5);
		slider2.setMinorTickSpacing(1);
		slider2.setPaintTicks(true);
		slider2.setPaintLabels(true);
		slider2.setName("rows");
		slider3.setPaintTicks(true);
		slider3.setPaintLabels(true);
		slider3.setName("speedfactor");
		slider3.setMajorTickSpacing(1);
		slider3.setMinorTickSpacing(1);
				
		if(start_game==false){
			add(scf);
			add(slider1);
			add(r);
			add(slider2);
			add(spf);
			add(slider3);
			m = slider1.getValue();
			n = slider2.getValue();
			s = slider3.getValue();
			//System.out.println(m+" "+n+" "+s);
		}
		slider1.addChangeListener(this);
		slider2.addChangeListener(this);
		slider3.addChangeListener(this);

		addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent me){
				if(mainarea.contains(me.getPoint()) && start_game){
					inside_mainarea = true;
					timer.stop();
				} else if(start_game){
					inside_mainarea = false;
					timer.start();
				}
				repaint();
			}
		});

		addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent me){
				if(quitarea.contains(me.getPoint()) && start_game){
					System.exit(0);
				}
				else if(startarea.contains(me.getPoint()) && !start_game){
					start_game = true;
					removeAll();
					timer.setRepeats(true);
					timer.start();
					repaint();
				}
				else if(me.getModifiers()==MouseEvent.BUTTON3_MASK && !(mainarea.contains(me.getPoint())) && !(flag_timer) && !(pieceRight()) && start_game){
					bs1.movePieceRightClick();
					if(!(pieceBelow()))
						repaint();
				}
				else if(!(mainarea.contains(me.getPoint())) && !(flag_timer) && !(pieceLeft()) && start_game){
					bs1.movePieceLeftClick();
					if(!(pieceBelow()))
						repaint();
				}
				
			} 
		});	

		addMouseWheelListener(new MouseAdapter(){
			public void mouseWheelMoved(MouseWheelEvent mwe){
				int notches = mwe.getWheelRotation();
				if(!(mainarea.contains(mwe.getPoint()))){
				if(notches<0){//mouse scroll forward ie away from the user, rotate shape clockwise
						if(main_area_num == 2){
							main_area_num = 8;
							repaint();
						}else if(main_area_num ==3){
							main_area_num = 11;
							repaint();
						}else if(main_area_num ==4){
							main_area_num = 14;
							repaint();
						}else if(main_area_num ==5){
							main_area_num = 17;
							repaint();
						}else if(main_area_num ==6){
							main_area_num = 18;
							repaint();
						}else if(main_area_num ==7){
							main_area_num = 19;
							repaint();
						}else if(main_area_num ==8){
							main_area_num = 9;
							repaint();
						}else if(main_area_num ==9){
							main_area_num = 10;
							repaint();
						}else if(main_area_num ==10){
							main_area_num = 2;
							repaint();
						}else if(main_area_num ==11){
							main_area_num = 12;
							repaint();
						}else if(main_area_num ==12){
							main_area_num = 13;
							repaint();
						}else if(main_area_num ==13){
							main_area_num = 3;
							repaint();
						}else if(main_area_num ==14){
							main_area_num = 15;
							repaint();
						}else if(main_area_num ==15){
							main_area_num = 16;
							repaint();
						}else if(main_area_num ==16){
							main_area_num = 4;
							repaint();
						}else if(main_area_num ==17){
							main_area_num = 5;
							repaint();
						}else if(main_area_num ==18){
							main_area_num = 6;
							repaint();
						}else if(main_area_num ==19){
							main_area_num = 20;
							repaint();
						}else if(main_area_num ==20){
							main_area_num = 21;
							repaint();
						}else if(main_area_num ==21){
							main_area_num = 7;
							repaint();
						}
					}
					else{//mouse scroll backwards ie towards user, rotate counter clockwise
						if(main_area_num == 2){
							main_area_num = 10;
							repaint();
						}else if(main_area_num ==3){
							main_area_num = 13;
							repaint();
						}else if(main_area_num ==4){
							main_area_num = 16;
							repaint();
						}else if(main_area_num ==5){
							main_area_num = 17;
							repaint();
						}else if(main_area_num ==6){
							main_area_num = 18;
							repaint();
						}else if(main_area_num ==7){
							main_area_num = 21;
							repaint();
						}else if(main_area_num ==8){
							main_area_num = 2;
							repaint();
						}else if(main_area_num ==9){
							main_area_num = 8;
							repaint();
						}else if(main_area_num ==10){
							main_area_num = 9;
							repaint();
						}else if(main_area_num ==11){
							main_area_num = 3;
							repaint();
						}else if(main_area_num ==12){
							main_area_num = 11;
							repaint();
						}else if(main_area_num ==13){
							main_area_num = 12;
							repaint();
						}else if(main_area_num ==14){
							main_area_num = 4;
							repaint();
						}else if(main_area_num ==15){
							main_area_num = 14;
							repaint();
						}else if(main_area_num ==16){
							main_area_num = 15;
							repaint();
						}else if(main_area_num ==17){
							main_area_num = 5;
							repaint();
						}else if(main_area_num ==18){
							main_area_num = 6;
							repaint();
						}else if(main_area_num ==19){
							main_area_num = 7;
							repaint();
						}else if(main_area_num ==20){
							main_area_num = 19;
							repaint();
						}else if(main_area_num ==21){
							main_area_num = 20;
							repaint();
						}
					}
				}
			}
		});
	}

	//Changed slider values are stored in variables
	public void stateChanged(ChangeEvent ce){
		JSlider source = (JSlider)ce.getSource();
		if(!source.getValueIsAdjusting()){
			String name = source.getName();
			if(name.equals("scoringfactor")){
				m = source.getValue();
			}else if(name.equals("speedfactor")){
				s = 0.1*(source.getValue());
			}else{
				n = source.getValue();
			}
		}
	}
	//Timer related actions performed here
	public void actionPerformed(ActionEvent ae){
		if((bs1.i+bs1.imax)!=19 && pieceBelow()==true){
		repaint();
		timer.stop();
		flag_timer = true;
		for(int k=0 ; k<4;k++){
			for(int l=0 ; l<4;l++){
				if (pieces.get(main_area_num)[k][l]==true) {
					main_area[bs1.i+k][bs1.j+l] = true;
				}
				
			}
		}
		}else if(bs1.i+bs1.imax==19){//add other stopping condition here as well, if there is a piece below
			repaint();
			timer.stop();
			flag_timer = true;
			for(int k=0 ; k<4;k++){
				for(int l=0 ; l<4;l++){
					if (pieces.get(main_area_num)[k][l]==true) {
						main_area[bs1.i+k][bs1.j+l] = true;
					}
				}
			}
		}else{
			bs1.movePieceTimer();
			revalidate();
			repaint();
		}		
	}
	//Checks if there is a piece below the current piece
	public boolean pieceBelow(){
		boolean below = false;
		for(int u=bs1.imin; u<=bs1.imax; u++){
			for(int v=bs1.jmin; v<=bs1.jmax; v++){
				if(pieces.get(main_area_num)[u][v]==true){
					if(main_area[u+bs1.i+1][v+bs1.j]==true)
						below = true;
				}
			}
		}
		return below; 
	}
	//Checks if there is a piece to the left of the current piece
	public boolean pieceLeft(){
		boolean left = false;
		for(int u=0;u<=bs1.imax;u++){
			if(bs1.j+bs1.jmin-1<=0){
				left = false;
			}
			else if(main_area[bs1.i+u][bs1.j+bs1.jmin-1]==true){
				left = true;
			}
		}
		return left; 
	}
	//Checks if there is a piece to the right of the current piece
	public boolean pieceRight(){
		boolean right = false;
		for(int u=0;u<=bs1.imax;u++){
			if(bs1.j+bs1.jmax+1>=10){
				right = false;
			}
			else if(main_area[bs1.i+u][bs1.j+bs1.jmax+1]==true){
				right = true;
			}
		}
		return right; 
	}




	//Initializing different dimensions and canvas variables
	void initDim(){
		Dimension d = getSize();
		maxX = d.width - 1;
		maxY = d.height - 1;
        centerX = maxX/2;
        centerY = maxY/2;
        pixelSize = 1;
        size = (int)maxX/60;
        mainarea = new Rectangle(centerX-15*size, centerY-10*size, 10*size, 20*size);
        nextpiecearea = new Rectangle(centerX, centerY-10*size,8*size, 4*size);
        quitarea = new Rectangle(centerX, centerY+9*size, 4*size,size);
        pausearea = new Rectangle(centerX-13*size, centerY-size,6*size,size);
        startarea = new Rectangle(centerX-4*size, centerY,8*size,size);
	}
	//Drawing different areas on the canvas
	public void drawAreas(Graphics g){
		Graphics2D g2 = (Graphics2D)g;

		g2.setColor(Color.BLACK);  
		g2.draw(mainarea);
    	g2.draw(nextpiecearea);
    	g2.draw(quitarea);
    	g.drawString("QUIT",centerX+size,centerY+10*size);
    	g.drawString("Level:", centerX+size, centerY);
    	g.drawString("Lines:", centerX+size, centerY+2*size);
    	g.drawString("Score:", centerX+size, centerY+4*size);
    	g.drawString(level+"",centerX+7*size,centerY);
    	g.drawString(lines+"",centerX+7*size,centerY+2*size);
    	g.drawString(score+"",centerX+7*size,centerY+4*size);
	}
	//Changing main are and next area pieces after earlier piece has reached the bottom
	public void animatePieces(){
		if(!terminated){
			main_area_num = next_area_num;
			bs1 = new BlockSquare(pieceColors.get(main_area_num),-1,4);
			next_area_num = rand.nextInt(7)+1;
			bs2 = new BlockSquare(pieceColors.get(next_area_num),0,17);
			timer.start();
			flag_timer = false;
		}
	}
	//Checks if a row is full and shifts the remaining above area downwards
	public void rowCompleted(){
		for(int row_i = 0; row_i<20; row_i++){
			for(int col_j = 0; col_j<10; col_j++){
				if(main_area[row_i][col_j]==true){
					count_blocks++;
				}
			}
			if(count_blocks==10){
				for(int index = 0; index<temp.size(); index+=3){
					
					if(temp.get(index)==row_i){
						while(remove_blocks>0){
							temp.remove(index);
							remove_blocks--;
						}
						count_blocks--;
						remove_blocks = 3;
						index-=3;
					}
				}
				if(count_blocks==0){
					lines++;
					score = score + level*m;
					i_lines++;
					if(i_lines==n){
						i_lines = 0;
						level++;
						fs = (int)Math.round(fs*(1.0 + level*s));
						timer.setDelay(fs);
					}
					for(int index = 0; index<temp.size(); index+=3){//all squares have been removed from completed row
						int temp_val = temp.get(index);
						if(temp_val<row_i)
							temp.set(index,temp_val+1);
					}
					for(int bool_index = row_i; bool_index>0; bool_index--){
						for(int bool_col = 0; bool_col<10; bool_col++){
							main_area[bool_index][bool_col] = main_area[bool_index-1][bool_col];
							main_area[0][bool_col] = false;
						}
					}
					
				}
			}
			count_blocks = 0;
			repaint();
		}
	}
	//Draws the blocks on the canvas permanently after they have stopped
	public void paintPieces(Graphics g){
		for(int w=0;w<20;w++){
    		for(int e=0;e<10;e++){
		    	if(main_area[w][e]==true){//permanently paint the pieces

		    		for(int k=0; k<temp.size(); k+=3){
		    			block_i = temp.get(k);
		    			block_j = temp.get(k+1);
		    			block_ID = temp.get(k+2);
		    			if(w==block_i && e==block_j || w==block_i+1 && e==block_j || w==block_i && e==block_j+1 || w==block_i+1 && e==block_j+1 || w==block_i && e==block_j+2 || w==block_i && e==block_j+3 || w==block_i+1 && e==block_j+2 || w==block_i+1 && e==block_j+3){
			    			currentPiece = pieces.get(block_ID);
			    			currentColor = pieceColors.get(block_ID);

			    			block_y = centerY - ((10-block_i)*size);
							block_x = centerX - ((15-block_j)*size);
							g.setColor(currentColor);
							g.fillRect(block_x,block_y,size,size);
							g.setColor(Color.BLACK);
							g.drawRect(block_x,block_y,size,size);
						}	
		    		}
		    	}
		    }
	    }
	}

    @Override
    protected void paintComponent(Graphics g){
    	initDim();
    	super.paintComponent(g);
    	Graphics2D g2 = (Graphics2D)g;
    	Font text_font = new Font("Arial",1,size);

    	g2.setFont(text_font);

    	if(start_game==false){
    		g2.setColor(Color.RED);
    		g2.draw(startarea);
    		g.drawString("START GAME",centerX-4*size,centerY+size);
    	}
    	if(start_game==true){
    		drawAreas(g);

	    	bs1.calcBounds(pieces.get(main_area_num));

	    	for(int p_row=0; p_row<4; p_row++){
	    		for(int p_col=0; p_col<4; p_col++){
	    			if(bs1.i==-1 && pieceBelow()){
	    				timer.stop();
	    				terminated = true;
	    			}
	    			else if(pieces.get(main_area_num)[p_row][p_col]==true){
	    				bs1.paintTinySquare(g,size,centerX,centerY, p_row, p_col);
	    			}
	    		}
	    	}
	    	for(int p_row=0; p_row<4; p_row++){
	    		for(int p_col=0; p_col<4; p_col++){
	    			if(pieces.get(next_area_num)[p_row][p_col]==true && !terminated){
	    				bs2.paintTinySquare(g,size,centerX,centerY, p_row, p_col);
	    			}
	    		}
	    	}

	    	if(flag_timer){
	    		for(int p_row = 0; p_row<4; p_row++){
	    			for(int p_col = 0; p_col<4; p_col++){
	    				if(pieces.get(main_area_num)[p_row][p_col]==true){
	    					temp.add(bs1.i+p_row);
	    					temp.add(bs1.j+p_col);
	    					temp.add(main_area_num);
	    				}
	    			}
	    		}
				pieceCount+=1;
				animatePieces();
				
	    	}
	    	rowCompleted();
	    	paintPieces(g);	
		    

	    	if(inside_mainarea){
	    		g2.setColor(new Color(0,112,192));
	    		g2.draw(pausearea);
				g.drawString("PAUSE",centerX-12*size,centerY);
	    	}
	    }
    }
}

class BlockSquare{

	public int i;
	public int j;
	public int imin, imax, jmin, jmax;
	private int x;
	private int y;
	private Color blockColor;
	public boolean[][] shape;

	public BlockSquare(Color color, int i, int j){
		this.blockColor = color;
		this.i = i;
		this.j = j;
	}

	public void calcBounds(boolean[][] shape){
		imin=10; imax=0; jmin=10; jmax=0;
		for(int k=0 ; k<4;k++){
			for(int l=0 ; l<4;l++){
				if (shape[k][l]==true) {
					if(imin>=k)
						imin = k;
					if(imax<=k)
						imax = k;
					if(jmin>=l)
						jmin = l;
					if(jmax<=l)
						jmax = l;
				}
			}
		}
	}

	public void paintTinySquare(Graphics g, int size, int centerX, int centerY, int row, int col){
		g.setColor(blockColor);
		y = centerY - ((10-(i+row))*size);
		x = centerX - ((15-(j+col))*size);
		g.fillRect(x,y,size,size);
		g.setColor(Color.BLACK);
		g.drawRect(x,y,size,size);
	}

	public void movePieceTimer(){
		if(i+imax<19)
			i++;
	}
	public void movePieceLeftClick(){
		if(j+jmin>0)
			j--;
	}
	public void movePieceRightClick(){
		if(j+jmax<9)
			j++;
	}
}