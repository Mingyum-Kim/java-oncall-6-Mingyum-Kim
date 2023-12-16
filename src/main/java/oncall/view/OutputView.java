package oncall.view;

import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import oncall.domain.Workers;
import oncall.domain.constants.CustomDayOfWeek;
import oncall.domain.constants.Holiday;
import oncall.domain.constants.Month;
import oncall.view.console.ConsoleWriter;

public class OutputView {
    public void printResult(Month month, DayOfWeek startDayOfWeek, Workers result) {
        for (int day = 1; day <= month.getDays(); day++) {
            String worker = result.popFront().getName();
            List<String> resultMessages = List.of(
                    month.getMonth() + "월",
                    day + "일",
                    getDayOfWeek(month, day, startDayOfWeek),
                    worker
            );
            ConsoleWriter.printlnMessage(
                    String.join(" ", resultMessages)
            );
        }
    }

    private String getDayOfWeek(Month month, int day, DayOfWeek startDayOfWeek) {
        DayOfWeek dayOfweek = startDayOfWeek.plus(day - 1);
        String message = dayOfweek.getDisplayName(TextStyle.FULL, Locale.KOREAN)
                .substring(0, 1);
        if (Holiday.isHoliday(month, day) && !CustomDayOfWeek.isWeekend(dayOfweek)) {
            message += "(휴일)";
        }
        return message;
    }
}
