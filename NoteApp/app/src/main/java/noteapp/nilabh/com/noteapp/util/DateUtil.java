package noteapp.nilabh.com.noteapp.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import static rx.android.internal.Preconditions.checkArgument;

/**
 * Created by nilabh on 12-07-2017.
 */

public class DateUtil {
    SimpleDateFormat formatter,dayFormatter;

    public String getFormattedDate(Date noteDate){
        formatter = new SimpleDateFormat(" MMM yyyy, h:mm a");
        dayFormatter = new SimpleDateFormat("d");
        String dayStr = dayFormatter.format(noteDate);
        DateUtil dateUtil = new DateUtil();
        dayStr = dayStr + dateUtil.getDayOfMonthSuffix(Integer.parseInt(dayStr));
        String strDate= formatter.format(noteDate);
        return dayStr + strDate;
    }


    String getDayOfMonthSuffix(final int n) {
        checkArgument(n >= 1 && n <= 31, "illegal day of month: " + n);
        if (n >= 11 && n <= 13) {
            return "th";
        }
        switch (n % 10) {
            case 1:  return "st";
            case 2:  return "nd";
            case 3:  return "rd";
            default: return "th";
        }
    }

}
