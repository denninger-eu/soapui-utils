package exp;

import com.eviware.soapui.impl.wsdl.actions.project.AddWsdlAction;
import com.eviware.x.form.XFormDialog;
import com.eviware.x.form.XFormFactory;
import com.eviware.x.form.support.ADialogBuilder;
import com.eviware.x.form.support.AField;
import com.eviware.x.form.support.AForm;
import com.eviware.x.impl.swing.SwingFormFactory;

public class ImexView {
    ImexView() {
        XFormFactory.Factory.instance = new SwingFormFactory();

        XFormDialog dialog = ADialogBuilder.buildDialog(Form.class);
        dialog.show();
    }

    public static void main(String[] args) {
        new ImexView();

    }

    @AForm(
            name = "Form.Title",
            description = "Form.Description",
            helpUrl = "/Working-with-Projects/new-project.html",
            icon = "/applications-system.png"
    )
    public interface Form {
        @AField(
                description = "Form.InitialWsdl.Description",
                type = AField.AFieldType.FILE
        )
        String INITIALWSDL = AddWsdlAction.messages.get("Form.InitialWsdl.Label");
    }
}
