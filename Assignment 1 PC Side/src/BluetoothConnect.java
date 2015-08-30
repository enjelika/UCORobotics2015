import lejos.nxt.LCD;
import lejos.pc.comm.*;
import lejos.pc.*;

public class BluetoothConnect {
	
	public static void main(String[] args) throws Exception{
		
		NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
		NXTInfo nxtInfo = new NXTInfo(NXTCommFactory.BLUETOOTH, "NXT", "");
		
	}
}