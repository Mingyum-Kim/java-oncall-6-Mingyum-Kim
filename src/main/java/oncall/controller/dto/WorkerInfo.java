package oncall.controller.dto;

import java.util.List;

public record WorkerInfo(
        List<String> weekday,
        List<String> weekend
) {
}
