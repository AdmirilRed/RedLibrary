
package redlibrarian.GUI.textVerification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JFormattedTextField.AbstractFormatter;


// @Author MadProgrammer

public class DateLabelFormatter extends AbstractFormatter {

    private final String datePattern;
    private final SimpleDateFormat dateFormatter;

    public DateLabelFormatter() {
        this.datePattern = "MM-dd-yyyy";
        this.dateFormatter = new SimpleDateFormat(datePattern);
    }

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }

}