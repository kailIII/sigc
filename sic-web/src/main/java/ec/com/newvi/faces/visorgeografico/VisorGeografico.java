/*
 * (c) 2017 NewVi Cia. Ltda.
 *   * 
 */
package ec.com.newvi.faces.visorgeografico;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import org.primefaces.component.api.Widget;

/**
 *
 * @author israelavila
 */
@FacesComponent(value = VisorGeografico.COMPONENT_TYPE)
@ResourceDependencies({
        @ResourceDependency(library = "ol", name = "ol.css"),
        @ResourceDependency(library = "ol", name = "ol.js"),
        @ResourceDependency(library = "primefaces", name = "jquery/jquery.js"),
        @ResourceDependency(library = "primefaces", name = "primefaces.js"),
        @ResourceDependency(library = "newvi", name = "visorgeografico.js") })
public class VisorGeografico extends UIComponentBase implements Widget { 
 
    public static final String COMPONENT_TYPE = "ec.com.newvi.faces.VisorGeografico";
    public static final String COMPONENT_FAMILY = "ec.com.newvi.faces.components";

    @Override
    public String getFamily() {
        return COMPONENT_FAMILY;
    }
 
    public Integer getWidth(){
        return (Integer) this.getStateHelper().eval(PropertyKeys.width,null);
    }
 
    public void setWidth(Integer width){
        this.getStateHelper().put(PropertyKeys.width, width);
    }
 
    public String getWidgetVar() {
        return (String) getStateHelper().eval(PropertyKeys.widgetVar, null);
    }
 
    public void setWidgetVar(String _widgetVar) {
        getStateHelper().put(PropertyKeys.widgetVar, _widgetVar);
    }
 
    @Override
    public String resolveWidgetVar() {
        FacesContext context = getFacesContext();
        String userWidgetVar = (String) getAttributes().get("widgetVar");
 
 
        if (userWidgetVar != null)
            return userWidgetVar;
        else
            return "widget_"
                    + getClientId(context).replaceAll(
                            "-|" + UINamingContainer.getSeparatorChar(context),
                            "_");
    }
 
    protected static enum PropertyKeys {
        width, widgetVar;
    }
}
