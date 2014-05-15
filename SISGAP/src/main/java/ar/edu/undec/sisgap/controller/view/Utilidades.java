package ar.edu.undec.sisgap.controller.view;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;

@ManagedBean(name = "utilidades")
@SessionScoped
public class Utilidades {

    public void preProcesarPDF(Object documento) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) documento;
        //pdf.open();
        pdf.setPageSize(PageSize.A4.rotate());
        pdf.setMargins(2, 2, 2, 2);
        pdf.setHtmlStyleClass("font-family:\"Arial\",Georgia,Serif;");
    }
}
