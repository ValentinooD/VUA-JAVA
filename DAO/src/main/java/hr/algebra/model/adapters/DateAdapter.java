package hr.algebra.model.adapters;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Date>{
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    
    @Override
    public Date unmarshal(String text) throws Exception {
        return DATE_FORMAT.parse(text);
    }

    @Override
    public String marshal(Date date) throws Exception {
        return DATE_FORMAT.format(date);
    }
    
}
