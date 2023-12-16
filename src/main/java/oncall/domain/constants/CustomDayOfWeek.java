package oncall.domain.constants;

import java.time.DayOfWeek;
import java.util.Arrays;
import oncall.exception.CustomException;
import oncall.exception.ErrorMessage;

public enum CustomDayOfWeek {
    MONDAY(false, "월"),
    TUESDAY(false, "화"),
    WEDNESDAY(false, "수"),
    THURSDAY(false, "목"),
    FRIDAY(false, "금"),
    SATURDAY(true, "토"),
    SUNDAY(true, "일");

    private final boolean isWeekend;
    private final String name;

    CustomDayOfWeek(boolean isWeekend, String name) {
        this.isWeekend = isWeekend;
        this.name = name;
    }

    public static CustomDayOfWeek from(String name) {
        return Arrays.stream(CustomDayOfWeek.values())
                .filter(element -> element.name.equals(name))
                .findFirst()
                .orElseThrow(() -> CustomException.from(ErrorMessage.INVALID_INPUT_ERROR));
    }

    public static boolean isWeekend(DayOfWeek dayOfWeek) {
        for (CustomDayOfWeek customDayOfWeek : CustomDayOfWeek.values()) {
            if (customDayOfWeek.name().equals(dayOfWeek.name())) {
                return customDayOfWeek.isWeekend;
            }
        }
        throw new IllegalStateException("요일이 올바르지 않습니다.");
    }

    public DayOfWeek toDayOfWeek() {
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            if (dayOfWeek.name().equals(name())) {
                return DayOfWeek.valueOf(name());
            }
        }
        throw new IllegalStateException("요일이 올바르지 않습니다.");
    }
}
