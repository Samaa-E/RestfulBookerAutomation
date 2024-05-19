import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.given;

public class RestfulBookerAutomation {
    private static final String BASE_URL = "https://restful-booker.herokuapp.com";

    public static void main(String[] args) {
        RestAssured.baseURI = BASE_URL;

        // Step 1: Create Token
        String token = createToken();

        // Step 2: Create Booking
        createBooking(token);

        // Step 3: Get Booking
        getBooking(token, 1); // Replace 1 with the actual booking ID
    }

    private static String createToken() {
        String token = given()
                .contentType(ContentType.JSON)
                .body("{\"username\": \"admin\", \"password\": \"password123\"}")
                .post("/auth")
                .then()
                .extract()
                .path("token");
        System.out.println("Token: " + token);
        return token;
    }

    private static void createBooking(String token) {
        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body("{\"firstname\": \"John\", \"lastname\": \"Doe\", \"totalprice\": 100, \"depositpaid\": true, \"bookingdates\": {\"checkin\": \"2024-05-16\", \"checkout\": \"2024-05-18\"}, \"additionalneeds\": \"Breakfast\"}")
                .post("/booking")
                .then()
                .statusCode(200);
        System.out.println("Booking created successfully.");
    }

    private static void getBooking(String token, int bookingId) {
        given()
                .header("Authorization", "Bearer " + token)
                .get("/booking/" + bookingId)
                .then()
                .statusCode(200)
                .log().all();
    }
}
