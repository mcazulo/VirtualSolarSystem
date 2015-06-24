import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;


public class Particle {
	//final double G = 6.673e-11;
	private final double G = .032;
	private static int classId = 0;
	private static double timeStep = .25;
	
	//global postion
	private Vector globalOriginPos;
	
	private int id;
	private boolean removed;
	private Color color;
	private double mass;
	private int size;
	private Vector pos;
	private Vector vel;
	private Vector acc;
	private boolean sim;
	private ArrayList<Particle> particles;
	
	public Particle() {
		sim = true;
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
		sim = true;
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
	
	public void setSim(boolean b){
		sim = b;
	}
	
	public boolean getSim(){
		return sim;
	}
	
	public void setAcceleration() {
		for(int i = id; i < particles.size(); i++) {
			// don't attract yourself
			if(i != id && !particles.get(i).isRemoved() && sim && particles.get(i).getSim()){
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
		if(sim){
			acc.divide(mass); // Since F = m * a, a = F / m
			vel.timeStepAdd(acc, timeStep);
			pos.timeStepAdd(vel, timeStep);
		}
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
		size = (int) (mass / 2);
		if(size < 5){
			size = 5;
		}
		if(size > 300){
			size = 300;
		}
	}
	
	public void collisionDetection(){
		for(int i = id; i < particles.size(); i++){
			if(i != id && !particles.get(i).isRemoved()){
				if(!(this instanceof SpaceCraft) && !(particles.get(i) instanceof SpaceCraft)){
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
				} if((this instanceof SpaceCraft) || (particles.get(i) instanceof SpaceCraft)){
					double dis = Vector.distance(pos, particles.get(i).getPos());
					if (dis < getRadius() + particles.get(i).getRadius()) {
						if(this instanceof SpaceCraft){
							sim = false;
							pos.setX(particles.get(i).getPos().getX() + getRadius() + particles.get(i).getRadius());
							pos.setY(particles.get(i).getPos().getY() + getRadius() + particles.get(i).getRadius());
							vel.add(getVelocity());
						} else {				
							particles.get(i).getPos().setX(pos.getX() + getRadius() + particles.get(i).getRadius());
							particles.get(i).getPos().setY(pos.getY() + getRadius() + particles.get(i).getRadius());
							//particles.get(i).setVelocity(vel);
						}
					}else {
						if(this instanceof SpaceCraft){
							sim = true;
						}
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
	
	public Vector getVelocity(){
		return vel;
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
	
	public void setColor(Color col){
		color = col;
	}
	
	public void setTimeStep(double step){
		timeStep = step;
	}
	
	public double getTimeStep(){
		return timeStep;
	}
}
