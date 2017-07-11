import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;

public class CheckThread {
	final GpioController gpio;
	final GpioPinDigitalInput button;
	boolean isTestOrFireButton;

	CheckThread(Pin pinNum, String pinName, boolean isTestOrFireButton) {
		this.isTestOrFireButton = isTestOrFireButton;
		gpio = GpioFactory.getInstance();
		button = gpio.provisionDigitalInputPin(pinNum, pinName);
	}

	void run() {
		while (true) {
			if (button.getState().isHigh()) {
				System.out.println("Button State is High");
				Main.alarm(isTestOrFireButton);
			} else if (button.getState().isLow()) {
				System.out.println("Button State is LOW");
				try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
			}
		}
	}
}
