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
	
	public static void main(String[] args) throws IOException
	{
		//module number check if no number, create number
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
				//alarm(true);
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
				//alarm(false);
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	//send alarm to server
    public static void alarm(boolean isFireOrTest) {
		HttpClient client = new HttpClient("http://13.124.193.226", 3000);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("model","room");
		obj.put("bool", isFireOrTest);
		client.get("/ras/send", null,obj );
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
