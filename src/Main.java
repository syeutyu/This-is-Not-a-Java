import com.pi4j.io.gpio.*;

import java.util.HashMap;
import java.util.Map;
import com.planb.networking.*;

public class Main {


	public static void main(String[] args) {
		
		//HttpClient client = new HttpClient("http://10.156.145.113", 3000);
		HttpClient client = new HttpClient("http://52.79.54.33", 3000);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("spot","rooms,false");
		//Response response = client.post("/Ras/Send", null, obj);
		Response response = client.get("/Ras/Send", null,obj );
		System.out.println("send success");
	}
}
