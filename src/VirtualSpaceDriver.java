import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;


public class VirtualSpaceDriver {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame("VirtualSpace");
		frame.setSize(1480, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		VirtualPlane plane = new VirtualPlane(setParticleList());
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
		particles.add(new Particle());
		
		//Sun Particle
		particles.get(0).getPos().setX(600);
		particles.get(0).getPos().setY(450);
		particles.get(0).setMass(910000);
		
		// small planet
		particles.get(1).getPos().setX(850);
		particles.get(1).getPos().setY(450);
		particles.get(1).setMass(100);
		particles.get(1).setVelocity(new Vector(0,9));
		
		// small satellite 
		particles.get(2).getPos().setX(1000);
		particles.get(2).getPos().setY(450);
		particles.get(2).setMass(100);
		particles.get(2).setVelocity(new Vector(0,8));
		
		// Sacrifice object
		particles.get(3).getPos().setX(750);
		particles.get(3).getPos().setY(450);
		particles.get(3).setMass(100);
		particles.get(3).setVelocity(new Vector( 0,12.5));
				
		// Give each particles the list of particles
		for(int i = 0; i < particles.size(); i++) {
			particles.get(i).setParticles(particles);
		}
		return particles;
	}

}
