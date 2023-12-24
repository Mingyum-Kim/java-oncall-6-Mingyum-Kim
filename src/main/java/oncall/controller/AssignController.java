package oncall.controller;

import java.time.DateTimeException;
import java.time.Month;
import java.util.List;
import java.util.function.Supplier;
import oncall.controller.dto.DateInfo;
import oncall.controller.dto.MonthAndStartDayOfWeek;
import oncall.controller.dto.WorkersInfo;
import oncall.domain.AssignManager;
import oncall.domain.Workers;
import oncall.domain.constants.CustomDayOfWeek;
import oncall.exception.CustomException;
import oncall.exception.ErrorMessage;
import oncall.view.InputView;
import oncall.view.OutputView;
import oncall.view.console.ConsoleWriter;

public class AssignController {
    private final InputView inputView;
    private final OutputView outputView;

    public AssignController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        DateInfo dateInfo = retry(this::getDateInfo);
        WorkersInfo workersInfo = retry(this::getWorkersInfo);

        AssignManager assignManager = new AssignManager(
                workersInfo.weekday(),
                workersInfo.weekend()
        );
        Workers result = assignManager.assign(
                dateInfo.month(),
                dateInfo.startDayOfWeek().toDayOfWeek()
        );
        outputView.printResult(dateInfo.month(), dateInfo.startDayOfWeek().toDayOfWeek(), result);
    }

    private Month getMonth(int month) {
        try {
            return Month.of(month);
        } catch (DateTimeException e) {
            throw CustomException.from(ErrorMessage.INVALID_INPUT_ERROR);
        }
    }

    private DateInfo getDateInfo() {
        MonthAndStartDayOfWeek monthAndStartDayOfWeek = inputView.readMonthAndStartDayOfWeek();
        Month month = getMonth(monthAndStartDayOfWeek.month());
        CustomDayOfWeek dayOfWeek = CustomDayOfWeek.from(monthAndStartDayOfWeek.startDayOfWeek());
        return new DateInfo(month, dayOfWeek);
    }

    private WorkersInfo getWorkersInfo() {
        List<String> weekday = inputView.readWeekdayWorkers();
        List<String> weekend = inputView.readWeekendWorkers();
        return new WorkersInfo(
                Workers.from(weekday),
                Workers.from(weekend)
        );
    }

    private static <T> T retry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                ConsoleWriter.printlnMessage(e.getMessage());
            }
        }
    }
}
