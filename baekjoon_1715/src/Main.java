import java.util.*;
import java.io.*;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        //N개의 카드 덱 사이즈를 읽어서 PriorityQueue에 넣는다 (오름차순 정렬)
        PriorityQueue<Long> pq = readSizeOfDecks();

        long compareCnt = 0; //총 비교 횟수
        long deck1, deck2;

        //PQ에 덱이 하나 남을 때 까지 반복한다
        while (pq.size() > 1)
        {
            //PQ에서 덱 두개를 뽑는다
            deck1 = pq.poll();
            deck2 = pq.poll();

            //두 덱을 합친다.
            //합치면서 비교를 해야하므로 비교를 해야 하므로 비교 횟수를 누적시켜주고
            //덱이 한 개만 남을 때 까지 계속 합쳐야하므로 합친 덱을 다시 PQ에 넣는다.
            compareCnt += deck1 + deck2;
            pq.add(deck1 + deck2);
        }

        //누적된 비교 횟수 출력
        System.out.println(compareCnt);
    }

    static PriorityQueue<Long> readSizeOfDecks() throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        //덱 개수 입력
        int numOfDecks;
        numOfDecks = Integer.parseInt(br.readLine());

        //반환할 PriorityQueue
        PriorityQueue<Long> pq = new PriorityQueue<>(numOfDecks);

        //덱의 크기를 입력받아서 PQ에 넣는다. 오름차순 정렬된다.
        for (int i = 0; i < numOfDecks; i++)
            pq.add(Long.parseLong(br.readLine()));

        return pq;
    }
}
