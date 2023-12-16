package oncall.controller.dto;

import oncall.domain.Workers;

public record WorkerInfo(
        Workers weekday,
        Workers weekend
) {
}
