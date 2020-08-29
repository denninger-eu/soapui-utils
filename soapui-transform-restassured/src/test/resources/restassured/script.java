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
        context.groovyScript("Groovy2").script(context.read("Groovy2.groovy"));
    }

    @Test
    @DisplayName("Groovy2")
    public void Groovy2(){
        context.groovyScript("Groovy2").execute();
    }

}