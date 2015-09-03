//import java.io.File;
import lejos.nxt.*;
import lejos.robotics.subsumption.*;
import lejos.util.Delay;
import lejos.robotics.navigation.*;

public class BehaviorProximity implements Behavior {

	UltrasonicSensor us;
	RotateMoveController robot;
	
	public BehaviorProximity(UltrasonicSensor us, RotateMoveController p){
		this.us = us;
		this.robot = p;
	}
	
	public boolean takeControl(){
		int dist = us.getDistance();
		return (dist < 40);
	}
	
	public void suppress(){
		robot.stop();
	}
	
	public void action(){ 
		Sound.beep();
		System.out.println("Object detected " + us.getDistance() + " cms!");
		robot.stop();
		Delay.msDelay(500);
		robot.travel(-25); //back up from obstacle
	}
}
