/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.edu.undec.sisgap.controller.view;

import ar.edu.undec.sisgap.model.Beneficiario;
import java.io.IOException;
import java.io.Serializable;
import java.util.Hashtable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author mPY
 */
@ManagedBean(name = "reportesController")
@SessionScoped
public class reportesController implements Serializable{
    
    public reportesController() {
    }
    
//    // **************************  REPORTES  **********************************
//        public void pdf(String archivoReporte) throws JRException, IOException {
//
//            // Obtengo la ruta absoluta del archivo compilado del reporte
//            String rutaJasper = FacesContext.getCurrentInstance().getExternalContext().getRealPath("secure/reportes/" + archivoReporte +".jasper");
//
//            // Fuente de datos del reporte
//            JRBeanArrayDataSource beanArrayDataSource = new JRBeanArrayDataSource(new Beneficiario[]{this.getSelected()});
//
//            
//            //Agregando los parametros
//            Hashtable<String, Object> parametros = new Hashtable<String, Object>();
//            parametros.put("idBeneficiario", this.getSelected().getId());
//
//            // Llenamos el reporte
//            JasperPrint jasperPrint = JasperFillManager.fillReport(rutaJasper, parametros, beanArrayDataSource);
//
//            // Generamos el archivo a descargar
//            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
//            httpServletResponse.addHeader("Content-disposition", "attachment; filename=beneficiario.pdf");
//            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
//            JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
//            FacesContext.getCurrentInstance().responseComplete();
//        }
//
//        public void pdfLista(String archivoReporte) throws JRException, IOException {
//
//            // Ruta absoluta del archivo compilado del reporte
//            String rutaJasper = FacesContext.getCurrentInstance().getExternalContext().getRealPath("secure/reportes/" + archivoReporte +".jasper");
//
//            // Fuente de datos del reporte
//            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(this.getFacade().findAll());
//
//            // Llenamos el reporte con la fuente de datos
//            JasperPrint jasperPrint = JasperFillManager.fillReport(rutaJasper, null, beanCollectionDataSource);
//
//            // Generamos el archivo a descargar
//            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
//            httpServletResponse.addHeader("Content-disposition", "attachment; filename=listaBeneficiarios.pdf");
//            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
//            JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
//            FacesContext.getCurrentInstance().responseComplete();
//        }
//
//        public void imprimir(String archivoReporte) throws JRException, IOException {
//
//            // Obtengo la ruta absoluta del archivo compilado del reporte
//            String rutaJasper = FacesContext.getCurrentInstance().getExternalContext().getRealPath("secure/reportes/" + archivoReporte +".jasper");
//
//            // Fuente de datos del reporte
//            JRBeanArrayDataSource beanArrayDataSource = new JRBeanArrayDataSource(new Beneficiario[]{this.getSelected()});
//
//            //Agregando los parametros
//            Hashtable<String, Object> parametros = new Hashtable<String, Object>();
//            parametros.put("idBeneficiario", this.getSelected().getId());
//
//            // Llenamos el reporte
//            //JasperPrint jasperPrint = JasperFillManager.fillReport(rutaJasper, parametros, beanArrayDataSource);
//            String archivo = JasperFillManager.fillReportToFile(rutaJasper, parametros, beanArrayDataSource);
//
//            if (archivo != null) {
//                JasperPrintManager.printReport(
//                        archivo, true);
//            }
//        }
//
//        public void imprimirLista(String archivoReporte) throws JRException, IOException {
//
//            // Ruta absoluta del archivo compilado del reporte
//            String rutaJasper = FacesContext.getCurrentInstance().getExternalContext().getRealPath("secure/reportes/" + archivoReporte +".jasper");
//
//            // Fuente de datos del reporte
//            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(this.getFacade().findAll());
//
//            // Llenamos el reporte con la fuente de datos
//            String archivo = JasperFillManager.fillReportToFile(rutaJasper, null, beanCollectionDataSource);
//
//            if (archivo != null) {
//                JasperPrintManager.printReport(
//                        archivo, true);
//            }
//
//        }
    
}
