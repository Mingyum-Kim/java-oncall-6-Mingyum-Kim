package oncall.domain;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import oncall.exception.CustomException;
import oncall.exception.ErrorMessage;

public class Workers {
    private Deque<String> workers;

    public Workers() {
        this.workers = new ArrayDeque<>();
    }

    private Workers(Deque<String> workers) {
        this.workers = workers;
    }

    public static Workers from(List<String> workers) {
        Validator.validate(workers);
        Deque<String> deque = new ArrayDeque<>(workers);
        return new Workers(deque);
    }

    /**
     * 제일 앞에 있는 요소를 반환하는 메서드
     */
    public String getFront() {
        return workers.getFirst();
    }

    /**
     * 제일 뒤에 있는 요소를 반환하는 메서드
     */
    public String getBack() {
        return workers.getLast();
    }

    /**
     * 제일 앞에 있는 요소를 제거하여 반환하는 메서드
     */
    public String popFront() {
        return workers.removeFirst();
    }

    /**
     * 제일 앞에 요소를 삽입하는 메서드
     */
    public void addFront(String worker) {
        workers.addFirst(worker);
    }

    /**
     * 제일 뒤에 요소를 삽입하는 메서드
     */
    public void addBack(String worker) {
        workers.addLast(worker);
    }

    public boolean isEmpty() {
        return workers.isEmpty();
    }

    private static class Validator {
        private static void validate(List<String> workers) {
            for (String worker : workers) {
                validateLength(worker, 1, 5);
            }
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
