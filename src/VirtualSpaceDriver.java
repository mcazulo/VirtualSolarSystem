import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;

public class VirtualSpaceDriver {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame("VirtualSpace");
		frame.setSize(1480, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		VirtualPlane plane = new VirtualPlane();
		plane.setGlobalOriginPos(new Vector(frame.getWidth() / 2.0
											,frame.getHeight() / 2.0));
		ArrayList<Particle> particles = setParticleList();
		plane.setParticles(particles);
		
		frame.add(plane);
		frame.setVisible(true);
		plane.setVisible(true);
		
		while(true){
			try {
			    Thread.sleep(10); //in milliseconds
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			for(int i = 0; i < particles.size(); i++){
				if(!particles.get(i).isRemoved()){
					// check for collisions and combine
					particles.get(i).collisionDetection();
					// update the acceleration for each particle
					particles.get(i).setAcceleration();
					// update the vectors for each particle
					particles.get(i).update();
				}
			}
			// render each particle
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
		particles.get(0).getPos().setX(0);
		particles.get(0).getPos().setY(0);
		particles.get(0).setMass(910000);
		
		// small planet
		particles.get(1).getPos().setX(250);
		particles.get(1).getPos().setY(0);
		particles.get(1).setMass(100);
		particles.get(1).setVelocity(new Vector(0,9));
		
		// small planet 
		particles.get(2).getPos().setX(400);
		particles.get(2).getPos().setY(0);
		particles.get(2).setMass(100);
		particles.get(2).setVelocity(new Vector(0,8));
		
		// small planet
		particles.get(3).getPos().setX(150);
		particles.get(3).getPos().setY(0);
		particles.get(3).setMass(100);
		particles.get(3).setVelocity(new Vector( 0,12.5));
				
		// Give each particles the list of particles
		for(int i = 0; i < particles.size(); i++) {
			particles.get(i).setParticles(particles);
		}
		return particles;
	}

}
