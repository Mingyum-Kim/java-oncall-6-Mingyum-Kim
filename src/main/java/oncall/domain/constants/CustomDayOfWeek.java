package oncall.domain.constants;

import java.time.DayOfWeek;

public enum CustomDayOfWeek {
    MONDAY(false, "월요일"),
    TUESDAY(false, "화요일"),
    WEDNESDAY(false, "수요일"),
    THURSDAY(false, "목요일"),
    FRIDAY(false, "금요일"),
    SATURDAY(true, "토요일"),
    SUNDAY(true, "일요일");

    private final boolean isWeekend;
    private final String name;

    CustomDayOfWeek(boolean isWeekend, String name) {
        this.isWeekend = isWeekend;
        this.name = name;
    }

    public static boolean isWeekend(DayOfWeek dayOfWeek) {
        for (CustomDayOfWeek customDayOfWeek : CustomDayOfWeek.values()) {
            if (customDayOfWeek.name().equals(dayOfWeek.name())) {
                return customDayOfWeek.isWeekend;
            }
        }
        throw new IllegalStateException("요일이 올바르지 않습니다.");
    }
}
