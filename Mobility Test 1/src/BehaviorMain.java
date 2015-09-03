import lejos.robotics.subsumption.*;
import lejos.robotics.navigation.*;
import lejos.nxt.*;
import lejos.nxt.comm.*;

import java.io.*;

public class BehaviorMain {

	public static void main (String[] args) throws Exception {

//		//  The following int arrays are used to hold the commands 
//		//  sent by the PC to the NXT brick
//		int[] command = new int[3];
//		int[] reply = new int[8];
		
		//This flag is used to indicate if the while loop is to keep looping
		boolean keepItRunning = true;
		
		//Status messages on the LCD screen
		String connected = "Connected";
		String notConnected = "NOT CONNECTED";
		String waiting = "Waiting...";
		String closing = "Closing...";
		
		//The various streams used to facilitate communication between the
		//  PC and the NXT
		DataInputStream dis = null;
		DataOutputStream dos = null;
		BTConnection btc = null;
		
		// Creates the ultrasonic sensor and pilot objects
		UltrasonicSensor us = 
				new UltrasonicSensor(SensorPort.S1);
		
		//LegacyPilot (formerly TachoPilot class 
		//    from lejos.robotics.navigation.TachoPilot)		
		//  DifferentialPilot is recommended to use
		DifferentialPilot robot = 
				new DifferentialPilot(4.32F, 12.75F, Motor.B, Motor.C, true);
		robot.setTravelSpeed(25);
		robot.setRotateSpeed(40);
		
		/* Places Behaviors into an array, with the lowest priority
		*  Behavior taking the lowest array index.
		*     Textbook page 279	
		*/
		//Behavior b1 = new BehaviorForward(robot);
		Behavior b2 = new BehaviorProximity(us, robot);
		Behavior b3 = new BehaviorCollision(robot);
		Behavior[] bArray = {/*b1,*/b2,b3};
		
		// "Waiting..." will be displayed on the NXT's LCD 
		//   and will sound two beeps
		LCD.drawString(waiting, 0, 0);
		LCD.refresh();
		Sound.twoBeeps();
		
		//Wait for a Bluetooth connection
		btc = Bluetooth.waitForConnection();
		LCD.drawString("btc.available() = " + btc.available(), 0, 0);
		System.out.println("btc.available() = " + btc.available());
		LCD.refresh();
		
		//Set-up the I/O streams
		dis = btc.openDataInputStream();
		dos = btc.openDataOutputStream();
		Sound.beepSequenceUp(); //Bluetooth connection was successful!
		
		//The while loop for PC control while Bluetooth connected
		while(keepItRunning){			
			if(btc.available() == 0){
				/*  Creates the Arbitrator and the final line
				 *  starts the Arbitrator process.
				 * */
				Arbitrator arby = new Arbitrator(bArray);
				arby.start();
				
				//Display connection
				LCD.clear();
				LCD.drawString(connected, 0, 0);
				Thread.sleep(100);
				
//				Byte n = -1;
//				n = dis.readByte();
//				LCD.clear();
//				System.out.println("Byte received = " + n);
			} else {
				Sound.beep();
				LCD.clear();
				LCD.drawString(notConnected, 0, 0);
				Thread.sleep(100);
				keepItRunning = false;
			}
		}
		
		//Close the I/O Streams & the Bluetooth Connection
		dis.close();
		dos.close();
		Thread.sleep(100); //wait for data to drain
		LCD.clear();
		LCD.drawString(closing, 0, 0);
		LCD.refresh();
		btc.close();
		LCD.clear();
	}
}
