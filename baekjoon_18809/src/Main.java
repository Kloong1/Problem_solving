import java.io.*;
import java.util.*;

public class Main {

    static int rows, cols;
    static int greens, reds;
    static Stat[][] map;
    static ArrayList<Area> candidateAreas;

    public static void main(String[] args) throws IOException {

        readAll();

        boolean[] candidateFlag = new boolean[candidateAreas.size()];

        System.out.println(spreadCultureMedia(candidateFlag, greens, reds, 0));
    }

    //배양액을 뿌릴 수 있는 모든 경우를 탐색해서, 배양액을 다 뿌린 경우 countFlowers()를 호출하는 메소드
    static int spreadCultureMedia(boolean[] candidateFlag, int greens, int reds, int candidateIdx) {

        int flowerCnt = 0;

        //배양액을 전부 뿌린 경우 꽃이 몇개 자라는지 숫자 세서 반환
        if (greens == 0 && reds == 0)
            return countFlowers(candidateFlag);

        //남은 후보 토양에 배양액을 전부 뿌려도 배양액을 전부 사용할 수 없는 경우
        if (candidateFlag.length - candidateIdx < greens + reds)
            return 0;

        //초록색 배양액을 뿌린다
        if (greens > 0) {
            candidateFlag[candidateIdx] = true;
            candidateAreas.get(candidateIdx).stat = Stat.GREEN;

            flowerCnt = Math.max(flowerCnt,
                    spreadCultureMedia(candidateFlag, greens - 1, reds, candidateIdx + 1));

            candidateFlag[candidateIdx] = false;
            candidateAreas.get(candidateIdx).stat = Stat.CULTURE_SOIL;
        }

        //빨간색 배양액을 뿌린다
        if (reds > 0) {
            candidateFlag[candidateIdx] = true;
            candidateAreas.get(candidateIdx).stat = Stat.RED;

            flowerCnt = Math.max(flowerCnt,
                    spreadCultureMedia(candidateFlag, greens, reds - 1, candidateIdx + 1));

            candidateFlag[candidateIdx] = false;
            candidateAreas.get(candidateIdx).stat = Stat.CULTURE_SOIL;
        }

        //배양액을 뿌리지 않는다
        flowerCnt = Math.max(flowerCnt,
                spreadCultureMedia(candidateFlag, greens, reds, candidateIdx + 1));

        return flowerCnt;
    }

    //bfs로 꽃이 몇 송이 자라는지 세는 메소드
    static int countFlowers(boolean[] candidateFlag) {

        int flowerCnt = 0;

        Queue<Area> areaQueue = new LinkedList<>(); //bfs에 쓸 queue
        Stat[][] tempMap = new Stat[rows][cols];

        //map을 복사해서 써야된다.
        for (int row = 0; row < rows; row++)
            System.arraycopy(map[row], 0, tempMap[row], 0, map[row].length);

        //선택된 후보 토양에 배양액을 뿌린다
        for (int i = 0; i < candidateFlag.length; i++) {
            if (!candidateFlag[i])
                continue;

            Area area = candidateAreas.get(i);
            tempMap[area.row][area.col] = area.stat;
            areaQueue.add(new Area(area.row, area.col, area.stat)); //areaQueue 초기화
        }

        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        boolean[][] spreadCurRound = new boolean[rows][cols]; //현재 round에 배양액이 뿌려졌는지 표시하는 배열. round는 문제에서 "초"를 의미함.
        int lastRoundAreas;

        lastRoundAreas = areaQueue.size(); //지난 round에 옮겨간 배양액의 개수를 저장하는 변수
        Queue<Area> lastRoundAreasQueue = new LinkedList<>(); //지난 round에 배양액이 옮겨갔거나 꽃이 핀 area를 임시로 저장하는 queue

        //bfs로 배양액이 옮겨가는 걸 시뮬레이션.
        while (!areaQueue.isEmpty()) {
            Area area = areaQueue.poll();

            lastRoundAreas--;

            for (int[] dir : dirs) {
                int nextRow = area.row + dir[0];
                int nextCol = area.col + dir[1];

                if (!isInMap(nextRow, nextCol))
                    continue;

                //호수거나, 같은 색의 배양액이 이미 있거나, 꽃인 경우 패스
                if (tempMap[nextRow][nextCol] == Stat.WATER || tempMap[nextRow][nextCol] == area.stat || tempMap[nextRow][nextCol] == Stat.FLOWER)
                    continue;

                //그냥 땅인경우 배양액이 옮겨간다
                if (tempMap[nextRow][nextCol] == Stat.SOIL || tempMap[nextRow][nextCol] == Stat.CULTURE_SOIL)
                {
                    tempMap[nextRow][nextCol] = area.stat;
                    spreadCurRound[nextRow][nextCol] = true; //현재 round에 퍼진 배양액이라고 표시해줌
                    lastRoundAreasQueue.add(new Area(nextRow, nextCol, area.stat)); //임시 큐에 넣는 것 유의
                    continue;
                }

                //다른 색의 배양액인데, 해당 배양액이 현재 round에 퍼진게 아니라 이전 round에 퍼진 다른색의 배양액이면 패스
                if (!spreadCurRound[nextRow][nextCol])
                    continue;

                //현재 round에 퍼진 다른 색의 배양액인 경우 꽃이 핀다.
                tempMap[nextRow][nextCol] = Stat.FLOWER;
                flowerCnt++;
            }

            //이전 round의 배양액을 전부 상하좌우로 퍼트린 경우
            //다음 round로 넘어가기 전에 변수 초기화를 하고, areaQueue에 현재 round에 배양액이 퍼진 땅을 넣어줘야 한다
            if (lastRoundAreas == 0) {
                lastRoundAreas = lastRoundAreasQueue.size();

                //현재 round에 배양액이 퍼져서 꽃이 핀 땅은 queue에 넣어주면 안됨.
                while (!lastRoundAreasQueue.isEmpty()){
                    Area lastRoundArea = lastRoundAreasQueue.poll();
                    if (tempMap[lastRoundArea.row][lastRoundArea.col] == Stat.FLOWER)
                        lastRoundAreas--;
                    else
                        areaQueue.add(lastRoundArea);
                }
                for (boolean[] row: spreadCurRound)
                    Arrays.fill(row, false);
            }
        }

        return flowerCnt;
    }

    static boolean isInMap(int row, int col)
    {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    static void readAll() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        rows = Integer.parseInt(st.nextToken());
        cols = Integer.parseInt(st.nextToken());
        greens = Integer.parseInt(st.nextToken());
        reds = Integer.parseInt(st.nextToken());

        map = new Stat[rows][cols];

        candidateAreas = new ArrayList<>(10);
        Stat[] stats = Stat.values();

        for (int row = 0; row < rows; row++)
        {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < cols; col++)
            {
                map[row][col] = stats[Integer.parseInt(st.nextToken())];
                if (map[row][col] == Stat.CULTURE_SOIL)
                    candidateAreas.add(new Area(row, col, Stat.CULTURE_SOIL));
            }
        }
    }
}

class Area {
    int row;
    int col;

    public Area(int row, int col, Stat stat) {
        this.row = row;
        this.col = col;
        this.stat = stat;
    }

    Stat stat;
}

enum Stat {
    WATER,
    SOIL,
    CULTURE_SOIL,
    GREEN,
    RED,
    FLOWER
}