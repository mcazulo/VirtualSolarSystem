import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;


public class Particle {
	//final double G = 6.673e-11;
	private final double G = .03;
	private static int classId = 0;
	
	public static final double timeStep = .01;
	
	private int id;
	private Color color;
	private double mass;
	private int size;
	private Vector pos;
	private Vector vel;
	private Vector acc;
	private ArrayList<Particle> particles;
	
	public Particle() {
		this.id = classId++;
		Random rand = new Random();
		this.mass = rand.nextDouble() * 20000;
		calcSize();
		color = new Color(rand.nextInt(255)
							,rand.nextInt(255)
							,rand.nextInt(255));
		pos = new Vector(400 + rand.nextInt(400), 400 + rand.nextInt(400));
		vel = new Vector(0, 0);
		acc = new Vector(0,0);
		particles = new ArrayList<Particle>();
	}
	
	public Vector getPos() {
		return pos;
	}
	
	public int getId() {
		return id;
	}
	
	public void draw(Graphics g) {
		simulate();
		render(g);
	}
	
	public void setAcceleration() {
		for(int i = id; i < particles.size(); i++) {
			// don't attract yourself
			if(i != id){
				Vector vec = new Vector(particles.get(i).getPos().getX() - pos.getX()
										,particles.get(i).getPos().getY() - pos.getY());
				double dis = Vector.distance(pos, particles.get(i).getPos());
				vec.divide(dis); // vec is now a unit vector in the direction this force will add
				vec.multiply(mass); // 1 * m
				vec.multiply(particles.get(i).mass); // m * M
				vec.multiply(G); // m * M * G 
				vec.divide(dis);
				vec.divide(dis); // G * m * M / d / d
				acc.add(vec);
				vec.multiply(-1); // pulls in opposite direction for other particle
				particles.get(i).getAcceleration().add(vec);
			}
		}
	}
	
	public void simulate() {
		acc.divide(mass); // Since F = m * a, a = F / m
		vel.timeStepAdd(acc, timeStep);
		pos.timeStepAdd(vel, timeStep);
		acc.setX(0);
		acc.setY(0);
	}
	
	public void render(Graphics g) {
		g.setColor(color);
		// draw circles
		g.fillOval((int)pos.getX()
					,(int)pos.getY()
					,size
					,size);
		/*
		// draw dots
		g.fillRect((int)pos.getX()
				,(int)pos.getY()
				,size
				,size);
		*/
	}
	
	public void calcSize(){
		size = (int) (mass / 1000);
		if(size < 10){
			size = 10;
		}
	}
	
	public void setParticles(ArrayList<Particle> particles){
		this.particles = particles; 
	}
	
	public Vector getAcceleration(){
		return this.acc;
	}
	
	public void setVelocity(Vector vel){
		this.vel = vel;
	}
	
	public void setAcceleration(Vector acc){
		this.acc = acc;
	}
	
	public ArrayList<Particle> getParticles(){
		return this.particles;
	}
	
	public double getMass(){
		return this.mass;
	}
	
	public void setMass(double mass){
		this.mass = mass;
		calcSize();
	}
}
