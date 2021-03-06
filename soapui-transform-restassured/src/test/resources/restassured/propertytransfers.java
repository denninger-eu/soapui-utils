import eu.k5.dread.soapui.SoapuiContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(Dependent.DependsOnTestWatcher.class)
@TestMethodOrder(Dependent.DependsOnMethodOrder.class)
public class caseTest {
    private SoapuiContext context;

    @BeforeAll
    public void init(){
        this.context = new SoapuiContext(this);
    }

    @Test
    @DisplayName("transfer")
    public void transfer(){
        context.transfer("#createResource#Response","$.id","JSONPATH").to("#Project#projectProperty");
        context.transfer("#createResource#Response","$.id","JSONPATH").to("#TestSuite#suiteProperty");
        context.transfer("#createResource#Response","$.id","JSONPATH").to("#TestCase#caseProperty");
        context.transfer("#Properties#dynamicScript").to("#Groovy2#script");
    }

}