import eu.k5.dread.soapui.SoapuiContext;
import eu.k5.dread.soapui.SoapuiContext.RestRequestContext;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import static io.restassured.RestAssured.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(Dependent.DependsOnTestWatcher.class)
@TestMethodOrder(Dependent.DependsOnMethodOrder.class)
public class caseTest {
    private SoapuiContext context;

    @BeforeAll
    public void init(){
        this.context = new SoapuiContext(this);
        initcreateResource();
    }

    private void initcreateResource(){
        RestRequestContext request=context.requestStep("createResource");
        request.url("${#TestCase#baseUrl}/resource").request("{ \"value\": \"${=\"String\"x}\"}\n");
    }

    @Test
    @DisplayName("createResource")
    public void createResource(){
        RestRequestContext request = context.requestStep("createResource");
        Response response = given()
                .header("headerP", "headerV")
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .body(request.request())
                .post(request.url())
                .then()
                .extract().response();
        request.status(response.statusCode()).response(response.asString());
        request.assertStatus(200);
        request.assertJsonPathExists("$.id", "true");
    }

}