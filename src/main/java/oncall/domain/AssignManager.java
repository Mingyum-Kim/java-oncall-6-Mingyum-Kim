package oncall.domain;

import java.time.DayOfWeek;
import oncall.domain.constants.CustomDayOfWeek;
import oncall.domain.constants.Holiday;
import oncall.domain.constants.Month;

public class AssignManager {
    private Workers weekday;
    private Workers weekend;

    public AssignManager(Workers weekday, Workers weekend) {
        this.weekday = weekday;
        this.weekend = weekend;
    }

    public Workers assign(Month month, DayOfWeek startDayOfWeek) {
        Workers result = new Workers();
        for (int day = 1; day <= month.getDays(); day++) {
            DayOfWeek dayOfWeek = startDayOfWeek.plus(day - 1); // 현재 요일

            // 평일인 경우
            if (isWeekday(month, day, dayOfWeek)) {
                // 평일 근무자를 배치한다.
                add(result, weekday);
            }

            // 주말 혹은 법정 공휴일인 경우
            if (!isWeekday(month, day, dayOfWeek)) {
                // 휴일 근무자를 배치한다.
                add(result, weekend);
            }
        }
        return result;
    }

    private void add(Workers result, Workers workers) {
        if (result.isEmpty()) {
            String worker = workers.popFront();
            result.addBack(worker);
            workers.addBack(worker);
            return;
        }

        if (result.getBack().equals(workers.getFront())) {
            String holding = workers.popFront();
            String worker = workers.popFront();
            result.addBack(worker);
            workers.addBack(worker);
            workers.addFront(holding);
            return;
        }

        String worker = workers.popFront();
        result.addBack(worker);
        workers.addBack(worker);
    }

    /**
     * 해당 날짜가 평일인지 확인하는 메서드
     * </p>
     * 주말이거나 법정 공휴일이라면 false, 그 외에는 true를 반환한다.
     */
    private boolean isWeekday(Month month, int day, DayOfWeek dayOfWeek) {
        if (Holiday.isHoliday(month, day) || CustomDayOfWeek.isWeekend(dayOfWeek)) {
            return false;
        }
        return true;
    }
}


