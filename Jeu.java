import javax.swing.JFrame;
import javax.swing.Timer;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Color;
import java.awt.event.*;

public class Jeu extends JFrame implements MouseListener, ActionListener {
	private PaletAndBall[] objects;
	private int objectSelectionne;
	private boolean turn, peutCliquer; //turn is true for player 1, false for player 2
	private Timer t;
	private double firstX, firstY;
	private BufferedImage background;
	private Image field;
	private Graphics buffer;
	
	public Jeu() {
		super("SoccerStars");
		objectSelectionne = 42;

		try {
			field = ImageIO.read(new File("field.png"));
		} catch(IOException e) {
			System.out.println("Image d'arriere plan non trouvee");
		}
		
		setSize(1400,800); //1200*600 c'est le terrain, 200 en haut pour score, 100 chaque côté pour les cages
		background = new BufferedImage(1400,800,BufferedImage.TYPE_INT_RGB);
		buffer = background.getGraphics();

		objects = new PaletAndBall[11];
		objects[0] = new Palet(200,500,true);
		objects[1] = new Palet(400,300,true);
		objects[2] = new Palet(400,700,true);
		objects[3] = new Palet(600,400,true);
		objects[4] = new Palet(600,600,true);

		objects[5] = new Ball();
			
		objects[6] = new Palet(1200,500,false);
		objects[7] = new Palet(1000,300,false);
		objects[8] = new Palet(1000,700,false);
		objects[9] = new Palet(900,400,false);
		objects[10] = new Palet(900,600,false);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addMouseListener(this);

		setVisible(true);
		setLayout(null);
				
		t = new Timer(100, this); //every 100ms move() is called on everything
		t.start();
		//TODO: set Launcher
		
		turn = true;
		peutCliquer = true;
	}
	
	public void mouseClicked(MouseEvent e) {
       		//void
    	}
	
	public void mousePressed(MouseEvent e) {
		firstX = e.getX();
	    	firstY = e.getY();
		if(!peutCliquer) objectSelectionne = 42;
		else {
			objectSelectionne = 42;
			for(int i = 0; i < 11; i++) {
				if(objects[i].isInside(firstX,firstY) && !(objects[i].isBall())) {
					objectSelectionne = i;
					//i'm sure that if objectSelectionne != 42 it's not the ball
					//TODO: objects[objectSelectionne].estSelectionne(); 
					//TODO: changer image quand sélectionné
				}
			}
		}
    	}

    public void mouseReleased(MouseEvent e) {
       	double x = e.getX();
       	double y = e.getY();
	if (objectSelectionne != 42 && ((Palet)objects[objectSelectionne]).getTeam() == turn) {
       		objects[objectSelectionne].start(firstX,firstY,x,y);
		turn = !turn;
	}
    }

    public void mouseEntered(MouseEvent e) {
       		//void
    }

    public void mouseExited(MouseEvent e) {
       		//void
    }

    
    public void actionPerformed(ActionEvent e) {
		peutCliquer = true;
		for(int i = 0; i<11; i++) {
			objects[i].move();
			if(objects[i].getSpeed() > 0) peutCliquer = false;
		}
		repaint();
    }

	public void paint(Graphics g) {
		//TODO : do the palets/balls images
		buffer.drawImage(field,0,0,null);
		for(int i = 0; i<11; i++) {
			/*if(i != 5) { //hence not the ball
				if(((Palet)objects[i]).getTeam()) buffer.setColor(Color.RED);
				else buffer.setColor(Color.GREEN);
			} else {
				buffer.setColor(Color.BLACK);
			}
			buffer.fillOval((int)objects[i].getx(),(int)objects[i].gety(),(int)objects[i].getRadius(),(int)objects[i].getRadius()); */
			buffer.drawImage(objects[i].getImage(),(int)objects[i].getx(),(int)objects[i].gety(),null);
		}
		g.drawImage(background,0,0,this);
	}
    	
	public static void main(String[] args) {
		Jeu j = new Jeu();
    	}
}