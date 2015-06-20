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
		// Create List 100 of particles
		for(int i = 0; i < 100; i++) {
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
		// loop through each particle for updating and rendering
		for(int i = 0; i < this.particles.size(); i++){
			// update the acceleration for each particle
			this.particles.get(i).setAcceleration();
			// render each particle
			this.particles.get(i).draw(g);
		}
	}
}
