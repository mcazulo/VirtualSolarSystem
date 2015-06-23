import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;


public class Particle {
	//final double G = 6.673e-11;
	private final double G = .03;
	private static int classId = 0;
	private static final double timeStep = .2;
	
	private int id;
	private boolean removed;
	private Color color;
	private double mass;
	private int size;
	private Vector pos;
	private Vector vel;
	private Vector acc;
	private ArrayList<Particle> particles;
	
	public Particle() {
		this.id = classId++;
		removed = false;
		Random rand = new Random();
		this.mass = rand.nextDouble() * 1000;
		calcSize();
		color = new Color(rand.nextInt(255)
							,rand.nextInt(255)
							,rand.nextInt(255));
		pos = new Vector(rand.nextInt(1400), rand.nextInt(1200));
		vel = new Vector(0, 0);
		acc = new Vector(0,0);
		particles = new ArrayList<Particle>();
	}
	
	public Particle(int xPos, int yPos, Vector initVel, double mass){
		this.id = classId++;
		removed = false;
		this.mass = mass;
		calcSize();
		pos = new Vector(xPos, yPos);
		vel = initVel;
		acc = new Vector(0,0);
		particles = new ArrayList<Particle>();
		Random rand = new Random();
		color = new Color(rand.nextInt(255)
				,rand.nextInt(255)
				,rand.nextInt(255));
	}
	
	public Vector getPos() {
		return pos;
	}
	
	public int getId() {
		return id;
	}
	
	public void setAcceleration() {
		for(int i = id; i < particles.size(); i++) {
			// don't attract yourself
			if(i != id && !particles.get(i).isRemoved()){
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
	
	public void update() {
		acc.divide(mass); // Since F = m * a, a = F / m
		vel.timeStepAdd(acc, timeStep);
		pos.timeStepAdd(vel, timeStep);
		acc.setX(0);
		acc.setY(0);
	}
	
	public Color getColor(){
		return color;
	}
	
	public boolean isRemoved(){
		return removed;
	}
	
	public void remove(){
		removed = true;
	}
	
	public void unRemove(){
		removed = false;
	}
	
	public void calcSize(){
		size = (int) (mass / 10000);
		if(size < 5){
			size = 5;
		}
	}
	
	public void collisionDetection(){
		for(int i = id; i < particles.size(); i++){
			if(i != id && !particles.get(i).isRemoved()){
				double dis = Vector.distance(pos, particles.get(i).getPos());
				if (dis < getRadius() + particles.get(i).getRadius()) {
				    if(mass < particles.get(i).getMass()){
				    	particles.get(i).setMass(mass + particles.get(i).getMass());
				    	remove();
				    	particles.get(i).calcSize();
				    } else {
				    	mass += particles.get(i).getMass();
				    	particles.get(i).remove();
				    	calcSize();
				    }
				}
			}
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
	
	public int getSize(){
		return size;
	}
	
	public double getRadius(){
		return (double)size / 2;
	}
}
