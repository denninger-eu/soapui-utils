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
        initget_Resource();
    }

    private void initget_Resource(){
        RestRequestContext request = context.requestStep("get Resource");
        request.url("${#TestCase#baseUrl}/resource/${template}");
        request.property("template", "${#Project#projectProperty}");
    }

    @Test
    @DisplayName("get Resource")
    public void get_Resource(){
        RestRequestContext request = context.requestStep("get Resource");
        Response response = given()
                .header("Accept", "application/json")
                .get(request.url())
                .then()
                .extract().response();
        request.status(response.statusCode()).response(response.asString());
        request.assertJsonPathExists("$.id", "true");
    }

}