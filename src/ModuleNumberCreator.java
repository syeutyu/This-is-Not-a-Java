import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ModuleNumberCreator {
	private FileReader reader;
	String ModuleNum;
	boolean isFirst;
	
	public ModuleNumberCreator() throws IOException {
		try{
			reader = new FileReader("/home/pi/Desktop/test.txt");
		}catch(FileNotFoundException e)
		{
			System.out.println("File Not Found.");
			ModuleNum = createFile();
			System.out.println("ModuleNum : "+ModuleNum);
			isFirst = true;
			return ;
		}
		isFirst = false;
		BufferedReader br = new BufferedReader(reader);
		ModuleNum = br.readLine();
		System.out.println("Module Number: "+ModuleNum);
	}

	public String createFile() throws IOException
	{
		String num = createNum();
		System.out.println(num);
		FileWriter writer = new FileWriter("/home/pi/Desktop/test.txt");
		BufferedWriter bw= new BufferedWriter(writer);
		bw.write(num);
		bw.flush();
		bw.close();
		writer.close();
		System.out.println("FileCreate");
		return num;
	}

	public String createNum()
	{
		return String.valueOf(System.currentTimeMillis());
	}
	public String getModuleNum()
	{
		return ModuleNum;
	}
	public boolean getIsFirst()
	{
		return isFirst;
	}
}