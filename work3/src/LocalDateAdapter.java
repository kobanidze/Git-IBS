import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


class LocalDateAdapter {
    public static Date parse(String candidate) {
        List<SimpleDateFormat> knownPatterns = new ArrayList<SimpleDateFormat>();
        knownPatterns.add(new SimpleDateFormat("dd.MM.yyyy"));
        knownPatterns.add(new SimpleDateFormat("dd.MM,yy"));
        knownPatterns.add(new SimpleDateFormat("dd/MM/yyyy"));
        knownPatterns.add(new SimpleDateFormat("dd/MM/yy"));


        for (SimpleDateFormat pattern : knownPatterns) {
            try {
                return new Date(pattern.parse(candidate).getTime());
            } catch (ParseException pe) {
                //
            }
        }
        System.err.println("No known Date format found: " + candidate);
        return null;
    }
}