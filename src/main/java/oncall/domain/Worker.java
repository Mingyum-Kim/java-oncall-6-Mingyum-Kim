package oncall.domain;

import java.util.Objects;
import oncall.exception.CustomException;
import oncall.exception.ErrorMessage;

public class Worker {
    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 5;
    private final String name;

    private Worker(String name) {
        Validator.validate(name);
        this.name = name;
    }

    public static Worker from(String name) {
        return new Worker(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Worker worker = (Worker) o;
        return Objects.equals(name, worker.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }

    private static class Validator {
        private static void validate(String name) {
            validateLength(name, MIN_LENGTH, MAX_LENGTH);
        }

        public static void validateLength(String name, int start, int end) {
            if (isInvalidRange(name, start, end)) {
                throw CustomException.from(ErrorMessage.INVALID_INPUT_ERROR);
            }
        }

        private static boolean isInvalidRange(String name, int start, int end) {
            int length = name.length();
            return length < start || length > end;
        }
    }
}
