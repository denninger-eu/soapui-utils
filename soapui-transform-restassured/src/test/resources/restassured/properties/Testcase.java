import eu.k5.dread.soapui.SoapuiContext;
import eu.k5.dread.soapui.SoapuiContext.PropertyHolder;
class caseTest {
    private SoapuiContext context;
    public void init(){
        this.context = new SoapuiContext();
        initProperties();
    }
    private void initProperties(){
        PropertyHolder step=context.propertiesStep("Properties");
        step.setProperty("key","keyValue");
        step.setProperty("date","${=java.time.LocalDateTime.now()}");
        step.setProperty("dynamicScript",""test"");
    }
}