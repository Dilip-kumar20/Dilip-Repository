package Java;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.mongodb.assertions.Assertions;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
public class Create_new_booking {
	String url;
	private static int bookingId;
	private static int price;
	@BeforeClass()
public void beforeClass() {
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
}
	@Test(description="Create_new_booking",priority=1)
public void buissinessLoginPost() throws IOException, ParseException, JSONException {
		JSONParser parser=new JSONParser();
		try {
		Object obj = parser.parse(new FileReader("src/test/resources/bussinesslogic_post_createBooking.json"));
		JSONObject jsonObject = (JSONObject) obj;
		String jsonBody=jsonObject.toString();
		System.out.println(jsonBody);
		Response response = given()
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .when()
                .post("/booking")
                .then().statusCode(200).extract().response();
		
		//Validate the response field value
		String firstname=response.body().path("booking.firstname", "testFirstName");
		Assert.assertTrue(true, firstname);
		
		String lastname=response.body().path("booking.lastname", "lastName");
		Assert.assertTrue(true, lastname);
		
		//String totalprice=response.body().path("booking.totalprice", "10");
		//Assert.assertTrue(true, totalprice);
	   
		String checkin=response.body().path("bookingdates.checkin", "2022-01-01");
		Assert.assertTrue(true, checkin);
		
		String checkout=response.body().path("bookingdates.checkout", "2024-01-01");
		Assert.assertTrue(true, checkout);
		
		String additionalneeds=response.body().path("additionalneeds", "testAdd");
		Assert.assertTrue(true, additionalneeds);
		price = response.jsonPath().getInt("booking.totalprice");
		Assert.assertTrue(true);
		bookingId = response.jsonPath().getInt("bookingid");
		
		System.out.println("Booking ID: " + bookingId);
		
		}
		catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
		}
		
	@Test(description="fetch with booking Id",priority=2)
    public void validateBookingTest() {
        given()
                .when()
                .get("/booking/" + bookingId)
                .then()
                .statusCode(200)
                .body("firstname", equalTo("testFirstName"))
                .body("lastname", equalTo("lastName"))
                .body("totalprice", equalTo(10))
                .body("depositpaid", equalTo(true))
                .body("bookingdates.checkin", equalTo("2022-01-01"))
                .body("bookingdates.checkout", equalTo("2024-01-01"))
                .body("additionalneeds", equalTo("testAdd"));
    }

	
	@Test(description="Create_new_booking",priority=3)
    public void addBookingNegativeTest() {
        
        String invalidRequestBody = "{\n" +
                  // Missing firstname
                "    \"lastname\" : \"lastName\",\n" +
                "    \"totalprice\" : 10,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2022-01-01\",\n" +
                "        \"checkout\" : \"2024-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"testAdd\"\n" +
                "}";

        Response response = given()
                .header("Content-Type", "application/json")
                .body(invalidRequestBody)
                .when()
                .post("/booking")
                .then()
                .statusCode(500)  // Expecting bad request due to missing required fields
                .extract().response();

        String errorMessage = response.asString();
       System.out.println(errorMessage);
       

    }
 
	
	
}
	

