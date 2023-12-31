### 미션 해결 전략

#### 1. 본인이 이해하고 구현한 내용에 기반해 '다른 근무자와 순서를 바꿔야 하는 경우'를 자신만의 예시를 들어 설명하세요. (필수)

- 예시 1) 평일 순번: 준팍,도밥,고니,수아,루루,글로,솔로스타,우코,슬링키,참새,도리
- 예시 2) 휴일 순번: 수아,루루,글로,솔로스타,우코,슬링키,참새,도리,준팍,도밥,고니

해당 예시가 있다면, 아래와 같이 비상 근무표가 배정됩니다.

```
월요일 : 준팍
화요일 : 도밥
수요일 : 고니
목요일 : 수아
금요일 : 루루
토요일 : 수아
일요일 : 루루
```

그러나 만약에 금요일이 법정 공휴일이기에 휴일 순번으로 돌아가게 된다면, 비상 근무표는 아래와 같이 조정됩니다.

```
월요일 : 준팍
화요일 : 도밥
수요일 : 고니
목요일 : 수아
금요일 : 수아
토요일 : 루루
일요일 : 글로
```

이렇게 되면 수아가 연속 2일 근무하게 되므로 요구사항에 어긋나게 됩니다.
따라서 이러한 경우 다음 근무자와 순서를 바꾸어 근무하게 됩니다. 순서를 바꾼 비상 근무표는 아래와 같습니다.

```
월요일 : 준팍
화요일 : 도밥
수요일 : 고니
목요일 : 수아
금요일 : 루루
토요일 : 수아
일요일 : 글로
```

이렇게 됨에 따라 수아가 연속 2일 근무하는 상황을 피할 수 있게 됩니다.

제가 구현한 내용에 따르면, Java의 Deque를 사용하여 비상 근무자를 배정하는 기능을 구현하였습니다.
Deque를 사용하여 비상 근무자를 배정하는 로직은 아래와 같습니다.

1. 근무자를 순번대로 비상 근무표에 추가합니다. 근무자의 순번을 `turn`, 비상 근무표의 근무자의 순서를 `result`라고 칭하겠습니다.
2. 먼저 아무 근무자도 추가된 상태가 아니라면 비상 근무표에 근무자를 추가하고, 해당 근무자의 순번은 맨 뒤로 돌아갑니다.

```java
    if(result.isEmpty()){
        String worker=turn.popFront();
        result.addBack(worker);
        turn.addBack(worker);
        }
```

3. 근무자가 한 명이라도 추가된 상태라면, 마지막으로 추가된 근무자와 현재 추가하려는 근무자의 일치 여부를 검사합니다.

```java
result.getBack().equals(turn.getFront())
```

4. 마지막으로 추가한 근무자가 현재 넣으려는 근무자(turns.getFirst())와 동일하면,
    1. 현재 근무자를 우선 꺼내어 둡니다.
    2. 제일 앞에 있는 근무자를 result에 추가합니다.
    3. 2번에서 추가한 근무자를 맨 뒤의 순번으로 옮깁니다.
    4. 우선 꺼내어 둔 근무자를 다시 turns의 앞에 추가합니다.

```java
        String holding=turn.popFront();
        String worker=turn.popFront();
        result.addBack(worker);
        turn.addBack(worker);
        turn.addFront(holding);
```

5. 만약, 마지막으로 추가한 근무자가 현재 넣으려는 근무자(turns.getFirst())와 동일하지 않다면, 맨 앞의 순번의 근무자를 result에 추가하고, 맨 뒤의 순번으로 옮깁니다.

```java
        String worker=turn.popFront();
        result.addBack(worker);
        turn.addBack(worker);
```

#### 2. 요구사항에서 제시한 앞의 날짜부터 순서를 변경하는 방법 외에 다른 방법이 있다면 어떤 방식이 있는지, 이 방법은 기존에 제시된 방식과 비교해 어떤 차이가 있는지 설명하세요. (선택)

다른 방법으로는, 비상 근무자를 배정할 때 2번 이상 중복되는 근무자가 있는 경우 기존에 배정한 근무자의 배정을 취소하고, 다음 근무자로 배정하는 방법이 있습니다.
기존 방식에서는 중복되는 근무자가 있는 경우 해당 근무자의 배정을 지연시키고 다른 근무자를 우선 투입하는 방식으로 구현하였기에 설계가 복잡해지는 단점이 있었지만,
기존에 배정한 근무자의 배정을 취소하고 순번을 넘겨, 다음 근무자로 배정하는 방식으로 구현하면 설계가 단순해지는 장점이 있습니다.
