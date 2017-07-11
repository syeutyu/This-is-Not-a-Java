import com.pi4j.io.gpio.*;

import java.util.HashMap;
import java.util.Map;
import com.planb.networking.*;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		CheckThread TestThread = new CheckThread(RaspiPin.GPIO_08,"TestButton",false);
		CheckThread fireThread = new CheckThread(RaspiPin.GPIO_07,"FireButton",true);
		
		TestThread.run();
		fireThread.run();
		
		
	}

	synchronized public static void alarm(boolean isFireOrTest) {
		
		HttpClient client = new HttpClient("http://10.156.145.113", 3000);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("00000001", isFireOrTest);
		Response response = client.post("/", null, obj);
	}
}
