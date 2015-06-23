import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;


public class VirtualPlane extends JPanel {
	ArrayList<Particle> particles;
	
	public VirtualPlane(ArrayList<Particle> particles) {
		this.particles = particles;
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
				g.fillOval((int)(particles.get(i).getPos().getX() - (particles.get(i).getSize() / 2.0))
							,(int)(particles.get(i).getPos().getY() - (particles.get(i).getSize() / 2.0))
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
}
