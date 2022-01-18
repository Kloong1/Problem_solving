import java.util.*;
import java.io.*;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int numOfWords;
        numOfWords = Integer.parseInt(br.readLine());

        ArrayList<String> wordList = new ArrayList<>(numOfWords);
        for (int i = 0; i < numOfWords; i++)
            wordList.add(br.readLine());

        //알파벳과 그 알파벳의 priority를 저장해 둔 배열
        //priority값이 클 수록 더 높은 숫자를 배정받는다
        Alphabet[] alphaArr = new Alphabet[26];
        for (int i = 0; i < 26; i++)
            alphaArr[i] = new Alphabet((char)('A' + i));

        //wordList를 탐색하며 각 알파벳의 priority를 구한다
        getAlphabetPriority(alphaArr, wordList);

        //priority 값이 큰 순서대로 알파벳을 정렬
        Arrays.sort(alphaArr, Comparator.reverseOrder());

        System.out.println(getWordSum(alphaArr, wordList));

        br.close();
    }

    //알파벳의 priority를 구하는 함수
    static void getAlphabetPriority(Alphabet[] alphaArr, ArrayList<String> wordList)
    {
        int priority = 1;

        //1의 자리의 priority는 1, 10의 자리의 priority는 10
        //이것을 모든 word의 알파벳에 대해 누적해준다
        for (String word: wordList)
        {
            for (int i = word.length() - 1; i >= 0; i--)
            {
                alphaArr[word.charAt(i) - 'A'].priority += priority;
                priority *= 10;
            }
            priority = 1;
        }
    }

    //단어의 합을 구하는 함수
    static int getWordSum(Alphabet[] alphaArr, ArrayList<String> wordList)
    {
        int sum = 0;
        int digit = 1;

        int[] alphaNum = new int[26];

        //priority가 큰 순서대로 알파벳에 9부터 0까지의 수를 부여한다
        for (int i = 0; i <= 9; i++)
            alphaNum[alphaArr[i].alphabet - 'A'] = 9 - i;

        //부여한 값을 가지고 단어의 합을 계산한다
        for (String word : wordList)
        {
            for (int i = word.length() - 1; i >= 0; i--)
            {
                sum += alphaNum[word.charAt(i) - 'A'] * digit;
                digit *= 10;
            }
            digit = 1;
        }

        return sum;
    }
}

class Alphabet implements Comparable<Alphabet>
{
    char alphabet;
    int priority;

    Alphabet(char alphabet)
    {
        this.alphabet = alphabet;
        this.priority = 0;
    }

    public int compareTo(Alphabet o)
    {
        return Integer.compare(this.priority, o.priority);
    }
}
