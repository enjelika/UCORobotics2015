import java.io.File;
import lejos.nxt.*;
import lejos.robotics.subsumption.*;
import lejos.util.Delay;
import lejos.robotics.navigation.*;

public class BehaviorProximity implements Behavior {

	File myFile = new File("gotcha.wav");
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
		System.out.println("Gotcha!");
		Sound.playSample(myFile, 100);
		robot.stop();
		Delay.msDelay(500);
		robot.travel(10);
		//90 degree turn adjusted due to friction of balance tire & front tires
		robot.rotate(165); 
	}
}
