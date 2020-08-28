import eu.k5.dread.soapui.SoapuiContext;
class caseTest {
    private SoapuiContext context;
    public void init(){
        this.context = new SoapuiContext();
    }
    public void transfer(){
        context.transfer("#createResource#Response","$.id","JSONPATH").to("#Project#projectProperty");
        context.transfer("#createResource#Response","$.id","JSONPATH").to("#TestSuite#suiteProperty");
        context.transfer("#createResource#Response","$.id","JSONPATH").to("#TestCase#caseProperty");
        context.transfer("#Properties#dynamicScript").to("#Groovy2#script");
    }
}