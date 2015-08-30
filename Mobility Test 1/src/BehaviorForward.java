import lejos.robotics.subsumption.*;
import lejos.robotics.navigation.*;
import lejos.util.Delay;

public class BehaviorForward implements Behavior {

	DifferentialPilot robot;
	
	public BehaviorForward(DifferentialPilot p){
		this.robot = p;
	}
	
	/*  This will always return true no matter what.
	 *  Higher level behaviors will be able to cut in 
	 *  on this behavior when needed.
	 *     Textbook: page 277
	 * */
	public boolean takeControl(){
		Delay.msDelay(200);
		return true;
	}
	
	/*  The main action of this behavior is to reverse 
	 *  and turn when the robot comes within 40 cm of
	 *  an object.
	 * */
	public void suppress(){
		robot.stop();
	}
	
	public void action(){
		if(!robot.isMoving()) robot.forward(); //robot.backward();
		Delay.msDelay(200);
	}
}