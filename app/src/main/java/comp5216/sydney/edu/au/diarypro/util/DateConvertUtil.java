package comp5216.sydney.edu.au.diarypro.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConvertUtil {

    /**
     * convert the number of month to the english abbr
     * @param month
     * @param day
     * @return
     */
    public static String convertFromInt(int month, int day) {
        String monthEnglish;
        switch (month) {
            case 0:
                monthEnglish = "Jan";
                break;
            case 1:
                monthEnglish = "Feb";
                break;
            case 2:
                monthEnglish = "Mar";
                break;
            case 3:
                monthEnglish = "Apr";
                break;
            case 4:
                monthEnglish = "May";
                break;
            case 5:
                monthEnglish = "Jun";
                break;
            case 6:
                monthEnglish = "Jul";
                break;
            case 7:
                monthEnglish = "Aug";
                break;
            case 8:
                monthEnglish = "Sep";
                break;
            case 9:
                monthEnglish = "Oct";
                break;
            case 10:
                monthEnglish = "Nov";
                break;
            case 11:
                monthEnglish = "Dec";
                break;
            default:
                monthEnglish = "";
        }

        return monthEnglish + " " + day;
    }

    /**
     * get the current date in a abbr format e.g. Oct 16
     * @return
     */
    public static String getCurrentDate() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(date);
        String[] split = format.split("-");
        String month = split[1];
        String day = split[2];
        return convertFromInt(Integer.parseInt(month)-1,Integer.parseInt(day));
    }
}
