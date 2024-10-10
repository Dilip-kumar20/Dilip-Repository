package Java;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import io.restassured.specification.RequestSpecification;
public class Utils {
	//RequestSpecification reqspe;
	public static final String PROPERTIES_FILE_PATH="src/test/resources/"; 
	public static final String baseLine=Utils.PROPERTIES_FILE_PATH;
	static Properties properies;
	String url="https://restful-booker.herokuapp.com";


	public String parseJson(String fileName) throws IOException,ParseException
	{
		JSONParser parse=new JSONParser();
		Object obj=parse.parse(new FileReader(fileName));
		return obj.toString();
	}
	
}
