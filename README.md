Packet-switching-network-memory-manage-algorithm
================================================

패킷 스위칭 네트워크에서 수신하는 메시지를 조립하기 위해 필요한 최소한의 버퍼의 크기를 계산하는 알고리즘.
-----------------------------------------------------------------------------------------------------------

패킷 스위칭 네트워크에서는 정보를 작은 단위로 다룹니다. 긴 메시지의 경우 라우팅 전에 여러 개의 패킷으로 분할하게 됩니다. 각 패킷이 다른 경로를 통해서 전달되거나, 패킷 도착 시간이 다르거나, 패킷의 순서가 뒤바뀌어 도착할 수 있기 때문에 수신하는 컴퓨터에서는 패킷을 올바르게 조립해서 원래의 메시지를 만들어야 합니다.

패킷을 수신하는 컴퓨터는 순서가 뒤바뀌어 도착하는 패킷을 담기 위한 메모리 버퍼를 사용합니다. 이 문제에서 여러분은 수신하는 메시지를 조립하기 위해 필요한 버퍼의 최소 크기를 계산하는 프로그램을 만들어야 합니다. 문제를 풀기 위해 수신하는 메시지의 개수(N), 패킷의 개수(M), 각 패킷이 메시지에서 어느 부분 데이터를 가지고 있는지, 각 메시지의 크기, 패킷이 도착하는 순서가 주어집니다.

각 패킷이 수신 컴퓨터에 도착하면, 패킷은 버퍼에 저장되거나 또는 바로 버퍼 외부 영역으로 옮겨집니다. 버퍼에 저장되어 있는 모든 패킷들은 필요할 때는 언제나 버퍼에서 버퍼 외부 영역으로 옮겨질 수 있습니다. 패킷이 버퍼 외부 영역으로 옮겨졌다면 패킷이 “버퍼를 통과했다”라고 이야기 합니다. 메시지의 모드 패킷이 버퍼를 통과했다면 메시지가 “버퍼를 통과했다”라고 이야기 합니다.

어떤 메시지의 패킷 이든 관계 없이 패킷은 버퍼를 통과하기 위해서는 순서대로 정렬되어야 합니다. 예를 들어, 어떤 메시지의 3에서 5까지의 바이트를 담고 있는 패킷의 경우, 같은 메시지의 6에서 10까지의 바이트를 담고 있는 패킷이 버퍼를 통과하기 전에 버퍼를 통과해야만 합니다. 메시지는 어떠한 순서든 버퍼를 통과하는 것이 가능합니다. 하지만, 메시지에 속하는 모든 패킷들은 순서대로 버퍼를 통과해야만 합니다.(하지만 동시에 통과할 필요는 없습니다) 참고로 실제 버퍼 시스템과는 다르지만, 이 문제에서 사용되는 방법을 이용하면 수신되는 모든 패킷에 대해 앞을 내다보며 판단이 가능합니다.

패킷은 헤더와 데이터로 구성됩니다. 헤더는 3개의 숫자로 구성되어 있고 그것은 메시지의 번호, 패킷에 포함되어 있는 데이터의 시작 바이트 번호, 패킷에 포함되어 있는 데이터의 마지막 바이트 번호를 나타냅니다. 어떤 메시지이건 첫 바이트 번호는 1입니다.

예를 들어, 다음 그림을 보면 3개의 메시지(크기가 10, 20, 5 바이트)와 5개의 패킷을 볼 수 있습니다. 이 예에서는 최소 버퍼 크기는 10 바이트 입니다. 패킷 #1과 패킷 #2는 도착하자 마자 버퍼에 저장됩니다.(10 바이트) 그리고 패킷 #3은 바로 버퍼를 통과합니다. 패킷 #4도 바로 버퍼를 통과하고 패킷 #2가 버퍼에서 빠져나옵니다. 마지막으로 패킷 #5도 바로 버퍼를 통과하고 패킷 #1이 버퍼에서 빠져나옵니다.

![그림](https://lh6.googleusercontent.com/-VLm0OCc7V_o/UvRkZe7E_uI/AAAAAAAAA8I/wLumcOi579w/w584-h138-no/%25EC%25BA%25A1%25EC%25B2%25983.PNG)

### 입력
입력 파일은 여러 개의 테스트 케이스들을 담고 있습니다. 각 테스트 케이스 첫 줄에는 정수 두 개, N(1 ≤N≤5)과 M(1≤M≤1000)이 들어있습니다. 두 번째 줄에는 메시지들의 크기(바이트) 정보를 담고 있는 N개의 정수가 들어있습니다.(첫 번째 정수는 메시지 #1의 크기, 두 번째 정수는 메시지 #2의 크기, …) 그 다음 줄부터 M개의 줄에는 정수 3개가 들어 있는데, 패킷이 속하는 메시지 번호와 패킷에 담긴 데이터가 메시지의 어느 부분인지 알 수 있는, 데이터의 시작과 마지막의 바이트 번호입니다. 패킷 데이터는 64바이트를 넘지 않습니다.

마지막 테스트 케이스 다음에는 두 개의 0을 포함하는 줄이 따라옵니다.

### 출력
각 테스트 케이스에 대해서, 테스트 케이스 번호(1로 시작하는)와 원본 메시지를 재조립하는데 필요한 최소 버퍼 크기(바이트)를 출력합니다. 각 테스트 케이스의 출력 다음에는 공백 라인을 하나씩 두고, 출력 포맷은 출력 예제의 포맷을 사용합니다.


### 입/출력 예제
```
C:\>p2.exe  (프로그램 실행)
3 3         (사용자 입력)         (메시지수 패킷이넘어오는횟수) 
5 5 5                           (#1메시지크기 #2메시지크기 #3메시지크기)
1 1 5                           1번째패킷(#1메시지 #1메시지데이터시작 #1메시지마지막바이트)
2 1 5                           2번째패킷(#2메시지 #2메시지데이터시작 #2메시지마지막바이트)
3 1 5       (사용자 입력 끝)      3번째패킷(#3메시지 #3메시지데이터시작 #3메시지마지막바이트)
Case 1: 0   (결과 출력)

3 5         (사용자 입력)         (메시지수 패킷이넘어오는횟수) 
10 20 5                         (#1메시지크기 #2메시지크기 #3메시지크기)
2 16 20                         1번째패킷(#2메시지 #2메시지데이터시작 #2메시지마지막바이트)
1 6 10                          2번째패킷(#1메시지 #1메시지데이터시작 #1메시지마지막바이트)
3 1 5                           3번째패킷(#3메시지 #3메시지데이터시작 #3메시지마지막바이트)
1 1 5                           4번째패킷(#1메시지 #1메시지데이터시작 #1메시지마지막바이트)
2 1 15      (사용자 입력 끝)      5번째패킷(#2메시지 #2메시지데이터시작 #2메시지마지막바이트)
Case 2: 10  (결과 출력)

0 0         (사용자 입력)
```
