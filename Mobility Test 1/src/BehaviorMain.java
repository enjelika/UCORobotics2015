import lejos.robotics.subsumption.*;
import lejos.robotics.navigation.*;
import lejos.nxt.*;

public class BehaviorMain {

	public static void main (String[] args){
		
		// Creates the ultrasonic sensor and pilot objects
		UltrasonicSensor us = 
				new UltrasonicSensor(SensorPort.S1);
		
		DifferentialPilot robot = 
				new DifferentialPilot(5.6F, 13.0F, Motor.B, Motor.C, true);
		// ------------------------------------------------
		
		robot.setTravelSpeed(25);
		robot.setRotateSpeed(40);
		
		
		/* Places Behaviors into an array, with the lowest priority
		*  Behavior taking the lowest array index.
		*     Textbook page 279	
		*/
		
		Behavior b1 = new BehaviorForward(robot);
		Behavior b2 = new BehaviorProximity(us, robot);
		
		Behavior[] bArray = {b1,b2};
		
		/*  Creates the Arbitrator and the final line
		 *  starts the Arbitrator process.
		 * */
		Arbitrator arby = new Arbitrator(bArray);
		arby.start();
	}
}
