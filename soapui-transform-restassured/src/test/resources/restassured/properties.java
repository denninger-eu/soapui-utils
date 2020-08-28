import eu.k5.dread.soapui.SoapuiContext;
import eu.k5.dread.soapui.SoapuiContext.PropertyHolder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.ExtendsWith;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendsWith(Dependent.DependsOnTestWatcher.class)
@TestMethodOrder(Dependent.DependsOnMethodOrder.class)
public class caseTest {
    private SoapuiContext context;

    @BeforeAll
    public void init(){
        this.context = new SoapuiContext();
        initProperties();
    }

    private void initProperties(){
        PropertyHolder step=context.propertiesStep("Properties");
        step.setProperty("key","keyValue");
        step.setProperty("date","${=java.time.LocalDateTime.now()}");
        step.setProperty("dynamicScript","\"test\"");
    }

}