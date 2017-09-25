import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.RaspiPin;
import com.planb.networking.HttpClient;

public class CheckFromArduino {
	
	static GpioController gpio;
	static GpioPinDigitalInput signal;
	static GpioPinDigitalInput button;
	static GpioPinDigitalOutput vcc;
	static boolean alarmIsSending;
	static boolean testIsSending;
	static int alarmCount;
	static int testCount;
	
	public static void main(String[] args) throws IOException
	{
		//module number check if no number, create number
		alarmIsSending = false;
		testIsSending = false;
		alarmCount = 0;
		testCount = 0;
		ModuleNumberCreator mnc = new ModuleNumberCreator();
		if(true == mnc.isFirst)
		{
			//Register(mnc.getModuleNum());
		}
		
		//Setting Pins
		gpio = GpioFactory.getInstance();
		signal = gpio.provisionDigitalInputPin(RaspiPin.GPIO_07, "signal");
		button = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, "button");
		vcc = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00,"vcc");
		vcc.high();
		
		//pin state check loop
		while(true)
		{
			System.out.print("pin State is: ");
			if(signal.getState().isHigh())
			{
				System.out.println("HIGH");
				//alarm(mnc.getModuleNum);
			}
			else
			{
				System.out.println("LOW");
			}
			System.out.print("Button State is: ");
			
			//button Low is pressed
			if(button.getState().isHigh())
			{
				System.out.println("HIGH");
			}
			else
			{
				System.out.println("LOW");
				//test(mnc.getModuleNum());
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(alarmIsSending == true)
			{
				if(alarmCount >=5)
				{
					alarmIsSending = false;
					alarmCount = 0;
				}
				alarmCount++;
				
			}
			if(testIsSending == true)
			{
				if(testCount >=5)
				{
					testIsSending = false;
					testCount = 0;
				}
				testCount++;
			}
			
		}
	}
	
	//send alarm to server
    public static void alarm(String num) {
    	if(alarmIsSending == true){return;}
		HttpClient client = new HttpClient("http://13.124.193.226", 3000);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("model",num);
		obj.put("bool", true);
		client.get("/ras/send", null,obj );
		CheckFromArduino.alarmIsSending = true;
	}
    
    public static void test(String num) {
    	if(testIsSending == true){return;}
		HttpClient client = new HttpClient("http://13.124.193.226", 3000);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("model",num);
		obj.put("bool", false);
		client.get("/ras/send", null,obj );
		CheckFromArduino.testIsSending = true;
	}
    
    //send register id to server
    public static void Register(String num)
    {
		HttpClient client = new HttpClient("http://13.124.193.226", 3000);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("model",num);
		client.get("/ras/set", null,obj);
    }
}
