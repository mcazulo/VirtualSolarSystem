import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;


public class VirtualSpaceDriver {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame("VirtualSpace");
		frame.setSize(1920, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		VirtualPlane plane = new VirtualPlane();
		frame.add(plane);
		frame.setVisible(true);
		plane.setVisible(true);
		
		while(true){
			try {
			    Thread.sleep(10); //in milliseconds
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			plane.repaint();
		}
	}
	
	public static ArrayList<Particle> setParticleList(){
		ArrayList<Particle> particles = new ArrayList<Particle>();
		particles.add(new Particle());
		particles.add(new Particle());
		particles.add(new Particle());
		//Sun Particle
		particles.get(0).getPos().setX(600);
		particles.get(0).getPos().setY(400);
		particles.get(0).setMass(900000);
		
		// small planet
		particles.get(1).getPos().setX(1600);
		particles.get(1).getPos().setY(450);
		particles.get(1).setMass(5000);
		particles.get(1).setVelocity(new Vector(0,2));
		
		// small satellite 
		particles.get(2).getPos().setX(1750);
		particles.get(2).getPos().setY(450);
		particles.get(2).setMass(10);
		particles.get(2).setVelocity(new Vector(0,2.5));
				
		// Give each particles the list of particles
		for(int i = 0; i < particles.size(); i++) {
			particles.get(i).setParticles(particles);
		}
		return particles;
	}

}
