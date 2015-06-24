import java.awt.Color;


public class SpaceCraft extends Particle {
	
	public SpaceCraft(int x, int y, Vector velocity, double mass) {
		super(x,y, velocity, mass);
		super.setColor(Color.red);
		super.setSim(true);
	}
	
	public void rightThrust(){
		this.getAcceleration().setX(this.getAcceleration().getX() + this.getTimeStep() * .00000000002);
	}
	public void leftThrust(){
		this.getAcceleration().setX(this.getAcceleration().getX() - this.getTimeStep() * .00000000002);
	}
	public void upThrust(){
		this.getAcceleration().setY(this.getAcceleration().getY() - this.getTimeStep() * .00000000002);
	}
	public void downThrust(){
		this.getAcceleration().setY(this.getAcceleration().getY() + this.getTimeStep() * .00000000002);
	}
	

}
