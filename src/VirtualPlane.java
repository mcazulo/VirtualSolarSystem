import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.JPanel;


public class VirtualPlane extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {
	ArrayList<Particle> particles;
	// particle's position relative to this point
	private Vector globalOriginPos;
	private Vector pressedPos;
	private Vector pressedPosDelta;
	private Vector draggedPos;
	private Vector draggedPosDelta;
	// flags for mouse controls
	private boolean mouseClicked;
	private boolean mousePressed;
	private boolean mouseReleased;
	// particle's size relative to this point
	private int globalScale;
	
	public VirtualPlane() {
		globalScale = 1;
		globalOriginPos = new Vector(0,0);
		// set default control flags
		mouseClicked = false;
		mousePressed = false;
		mouseReleased = true;
		// add listeners
		addMouseMotionListener(this);
		addMouseListener(this);
		addMouseWheelListener(this);
	}
	
	public void paint(Graphics g) {
		// set the background to black
		g.setColor(Color.black);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.white);
		g.drawString("Number of Objects: " + getNumberOfParticles(), 50, 50);
		
		/******************** Begining following code to be better sectioned later Part 1 *****************************
		**************************************************************************************************************/
		//globalOriginPos = new Vector((-(1)*(particles.get(1)).getPos().getX() + ((this.getWidth()/2.0) / globalScale))
		//							,(-(1)*particles.get(1).getPos().getY() + ((this.getHeight()/2.0)/ globalScale)) );
		//Scale Relative position
		//globalOriginPos.multiply(globalScale);
		/******************** End following code to be better sectioned later Part 1 ***********************************
		***************************************************************************************************************/
		
		// loop through each particle for updating and rendering
		for(int i = 0; i < this.particles.size(); i++){
			if(!particles.get(i).isRemoved()){
				// apply global scale
				particles.get(i).getPos().multiply(globalScale);
				// draw circle
				g.setColor(particles.get(i).getColor());
				g.fillOval((int)globalOriginPos.getX() + (int)(particles.get(i).getPos().getX() - (particles.get(i).getSize() / 2.0))
							,(int)globalOriginPos.getY() + (int)(particles.get(i).getPos().getY() - (particles.get(i).getSize() / 2.0))
							,particles.get(i).getSize() * globalScale
							,particles.get(i).getSize() * globalScale);
				// bring the global scale back so it doesn't accumulate
				particles.get(i).getPos().divide(globalScale);
			}
		}
		
		/******************** Begining following code to be better sectioned later Part 1 **************************
		***********************************************************************************************************/
		//descale relative position so it doesnt accumulate
		//globalOriginPos.divide(globalScale);
		/******************** End following code to be better sectioned later Part 1 *******************************
		************************************************************************************************************/
	}
	
	public int getNumberOfParticles(){
		int number = 0;
		for(int i = 0; i < particles.size(); i++){
			if(!particles.get(i).isRemoved()){
				number++;
			}
		}
		return number;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		draggedPos = new Vector(e.getX(), e.getY());
		draggedPosDelta = Vector.subtract(draggedPos, pressedPos);
		globalOriginPos = draggedPosDelta;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mousePressed = true;
		mouseReleased = false;
		pressedPos = new Vector(e.getX(), e.getY());
		pressedPos.subtract(globalOriginPos);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mousePressed = false;
		mouseReleased = true;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {

		globalOriginPos.divide(globalScale);
		
		int notches = e.getWheelRotation();
		globalScale += notches;
		if(globalScale > 200) {
			globalScale = 200;
		}
		if(globalScale < 1) {
			globalScale = 1;
		}
		
		globalOriginPos.multiply(globalScale);
		
	}
	
	public Vector getGlovalOriginPos(){
		return globalOriginPos;
	}
	
	public void setGlobalOriginPos(Vector globalPos){
		globalOriginPos = globalPos;
	}
	
	public void setParticles(ArrayList<Particle> particles){
		this.particles = particles;
	}
	
}
