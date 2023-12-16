package oncall.domain;

import java.util.ArrayDeque;
import java.util.Deque;

public class Workers {
    private Deque<String> workers;

    public Workers() {
        this.workers = new ArrayDeque<>();
    }

    public Workers(Deque<String> workers) {
        this.workers = workers;
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
}
