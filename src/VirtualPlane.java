import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;


public class VirtualPlane extends JPanel {
	ArrayList<Particle> particles;
	
	public VirtualPlane(ArrayList<Particle> particles) {
		this.particles = particles;
	}

	public VirtualPlane(){
		this.particles = new ArrayList<Particle>();
		// Create List of particles
		for(int i = 0; i < 400; i++) {
			this.particles.add(new Particle());
		}
		// Give each particles the list of particles
		for(int i = 0; i < particles.size(); i++) {
			this.particles.get(i).setParticles(particles);
		}
	}
	
	public void paint(Graphics g) {
		// set the background to black
		g.setColor(Color.black);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.white);
		g.drawString("Number of Objects: " + getNumberOfParticles(), 50, 50);
		// loop through each particle for updating and rendering
		for(int i = 0; i < this.particles.size(); i++){
			if(!this.particles.get(i).isRemoved()){
				// check for collisions and combine
				this.particles.get(i).collisionDetection();
				// update the acceleration for each particle
				this.particles.get(i).setAcceleration();
				// render each particle
				this.particles.get(i).draw(g);
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
