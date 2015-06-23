import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;


public class VirtualPlane extends JPanel implements MouseListener, MouseMotionListener {
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
	
	public VirtualPlane(ArrayList<Particle> particles) {
		this.particles = particles;
		globalScale = 1;
		globalOriginPos = new Vector(0,0);
		// set default control flags
		mouseClicked = false;
		mousePressed = false;
		mouseReleased = true;
		// add listeners
		addMouseMotionListener(this);
		addMouseListener(this);
	}
	
	public void paint(Graphics g) {
		// set the background to black
		g.setColor(Color.black);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.white);
		g.drawString("Number of Objects: " + getNumberOfParticles(), 50, 50);
		// loop through each particle for updating and rendering
		for(int i = 0; i < this.particles.size(); i++){
			if(!particles.get(i).isRemoved()){
				// draw circle
				g.setColor(particles.get(i).getColor());
				g.fillOval((int)globalOriginPos.getX() + (int)(particles.get(i).getPos().getX() - (particles.get(i).getSize() / 2.0))
							,(int)globalOriginPos.getY() + (int)(particles.get(i).getPos().getY() - (particles.get(i).getSize() / 2.0))
							,particles.get(i).getSize()
							,particles.get(i).getSize());
			}
		}
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
	
}
