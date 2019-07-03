package org.casadocodigo.loja.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;
import java.util.Calendar;
import java.util.Date;

@FacesConverter(forClass = Calendar.class)
public class CalendarHtml5Converter implements Converter {

     private static DateTimeConverter originalConverter = new DateTimeConverter();

     static {
         originalConverter.setPattern("yyyy-MM-dd");
     }

     @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
         Date date = (Date) originalConverter.getAsObject(facesContext, uiComponent, value);
         if(date == null) {
             return null;
         }

         Calendar newCalendar = Calendar.getInstance();
         newCalendar.setTime(date);
         return newCalendar;
     }

     @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
         if(value == null) {
             return null;
         }
         Calendar calendar = (Calendar) value;
         return originalConverter.getAsString(facesContext, uiComponent, calendar.getTime());
     }
}