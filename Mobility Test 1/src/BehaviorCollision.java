import lejos.robotics.subsumption.*;
import lejos.robotics.navigation.*;
import lejos.util.Delay;
import lejos.nxt.*;

public class BehaviorCollision implements Behavior {

	boolean isUpToSpeed = false;
	RotateMoveController robot;
	
	public BehaviorCollision (RotateMoveController p){
		robot = p;
		Motor.B.setStallThreshold(10, 100);
		Motor.C.setStallThreshold(10, 100);
	}
	
	public boolean takeControl(){
		if (Motor.B.isStalled() | Motor.C.isStalled()){
			Delay.msDelay(200);
			return true;
		} else {
			return false;
		}
	}
	
	public void suppress(){
		robot.stop();
	}
	
	public void action() {
		Sound.twoBeeps();
		robot.travel(-25);
		robot.rotate(-135);
	}
}
