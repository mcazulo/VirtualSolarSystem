import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;


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
		// key bindings for controls
		this.getInputMap().put(KeyStroke.getKeyStroke("UP"),"upThrust");
		this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"),"downThrust");
		this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"),"leftThrust");
		this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"),"rightThrust");
		this.getActionMap().put("upThrust", upThrust);
		this.getActionMap().put("downThrust", downThrust);
		this.getActionMap().put("leftThrust", leftThrust);
		this.getActionMap().put("rightThrust", rightThrust);
		
	}
	
	public void paint(Graphics g) {
		// set the background to black
		g.setColor(Color.black);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.white);
		g.drawString("Number of Objects: " + getNumberOfParticles(), 50, 50);
		
		/******************** Begining following code to be better sectioned later Part 1 *****************************
		**************************************************************************************************************/
		globalOriginPos = new Vector((-(1)*(particles.get(5)).getPos().getX() + ((this.getWidth()/2.0) / globalScale))
									,(-(1)*particles.get(5).getPos().getY() + ((this.getHeight()/2.0)/ globalScale)) );
		//Scale Relative position
		globalOriginPos.multiply(globalScale);
		/******************** End following code to be better sectioned later Part 1 ***********************************
		***************************************************************************************************************/
		
		// loop through each particle for updating and rendering
		for(int i = 0; i < this.particles.size(); i++){
			if(!particles.get(i).isRemoved()){
				// apply global scale
				particles.get(i).getPos().multiply(globalScale);
				
				// draw SpaceCraft
				if((particles.get(i) instanceof SpaceCraft)){
					// draw rectangle
					g.setColor(particles.get(i).getColor());
					g.fillRect( (int)globalOriginPos.getX() + (int)(particles.get(i).getPos().getX() - (particles.get(i).getSize() / 2.0))
								,(int)globalOriginPos.getY() + (int)(particles.get(i).getPos().getY() - (particles.get(i).getSize() / 2.0))
								,particles.get(i).getSize() / 5 * globalScale
								,(particles.get(i).getSize() / 5 * globalScale) * 2);
				} else{
					// draw circle
					g.setColor(particles.get(i).getColor());
					g.fillOval((int)globalOriginPos.getX() + (int)(particles.get(i).getPos().getX() - (particles.get(i).getSize() / 2.0))
								,(int)globalOriginPos.getY() + (int)(particles.get(i).getPos().getY() - (particles.get(i).getSize() / 2.0))
								,particles.get(i).getSize() * globalScale
								,particles.get(i).getSize() * globalScale);
				}
				// bring the global scale back so it doesn't accumulate
				particles.get(i).getPos().divide(globalScale);
			}
		}
		
		/******************** Begining following code to be better sectioned later Part 1 **************************
		***********************************************************************************************************/
		//descale relative position so it doesnt accumulate
		globalOriginPos.divide(globalScale);
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
	
	public ArrayList<Particle> getParticles(){
		return particles;
	}
	
	Action upThrust = new AbstractAction(){
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			SpaceCraft craft = (SpaceCraft) particles.get(5);
			craft.upThrust();
		}
	};
	
	Action downThrust = new AbstractAction(){
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			SpaceCraft craft = (SpaceCraft) particles.get(5);
			craft.downThrust();
		}
	};
	
	Action leftThrust = new AbstractAction(){
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			SpaceCraft craft = (SpaceCraft) particles.get(5);
			craft.leftThrust();
		}
	};
	
	Action rightThrust = new AbstractAction(){
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			SpaceCraft craft = (SpaceCraft) particles.get(5);
			craft.rightThrust();
		}
	};
}
