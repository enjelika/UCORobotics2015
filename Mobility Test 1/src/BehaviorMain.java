import lejos.robotics.subsumption.*;
import lejos.robotics.navigation.*;
import lejos.nxt.*;
import lejos.nxt.comm.*;
//import lejos.nxt.remote.ErrorMessages;

import java.io.*;

public class BehaviorMain extends LCPBTResponder{
	
	//Status messages on the LCD screen
	public static String connected = "Connected";
	public static String notConnected = "Not Connected";
	public static String waiting = "Waiting...";
	public static String closing = "Closing...";
	//public static byte closeConn = 0000;
	
	public static void main (String[] args) throws Exception {

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
		//Behavior b2 = new BehaviorProximity(us, robot);
		//Behavior b3 = new BehaviorCollision(robot);
		//Behavior[] bArray = {/*b1,*/b2,/*b3*/};
		
		//Main loop
		while(true){
			// "Waiting..." will be displayed on the NXT's LCD 
			//   and will sound two beeps
			LCD.drawString(waiting, 0, 0);
			LCD.refresh();
			Sound.twoBeeps();
			
			//Wait/Listen for a Bluetooth connection
			btc = Bluetooth.waitForConnection();
			btc.setIOMode(NXTConnection.RAW);
			
			//Set-up the I/O streams for read/write data
			dis = btc.openDataInputStream();
			dos = btc.openDataOutputStream();
			
			//Bluetooth connection was successful!
			LCD.clear();
			LCD.drawString(connected, 0, 0);
			LCD.refresh();
			Sound.beepSequenceUp(); 
			Thread.sleep(5000);
			dos.write(69);
			dos.flush();
						
			//This flag is used to indicate if the while loop is to keep looping
			boolean keepItRunning = true;
			
			//The while loop for read data
			while(keepItRunning){		
				//Print out received byte(s) to LCD screen
				//This number will be a literal number from the Android device
				Byte n = -1;
				n = dis.readByte();
				LCD.clear();
				System.out.println("Byte received = " + n);
				LCD.refresh();
				
				if(n != 99){
					/*  Creates the Arbitrator and the final line
					 *  starts the Arbitrator process.
					 * */
//					Arbitrator arby = new Arbitrator(bArray);
//					arby.start();
					
					//Display connection
					LCD.clear();
					LCD.drawString(connected, 0, 0);
					LCD.refresh();
				} else {
					Sound.beep();
					LCD.clear();
					Thread.sleep(250);
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
}
