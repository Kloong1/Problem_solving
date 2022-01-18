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

        //브루트포싱을 편하게 하기 위해 알파벳-숫자 에 대한 map을 사용
        HashMap<Character, Integer> alphaNumMap = new HashMap<>(10);
        //word에 들어있는 모든 알파벳을 저장하는 arraylist
        //이 list를 탐색하면서 재귀함수를 호출 할 것이다
        ArrayList<Character> alphaList = new ArrayList<>(10);

        //word에 들어있는 모든 알파벳을 map에 넣어준다. 숫자는 임의의 값인 0으로 초기화.
        setAlphaNumMap(alphaNumMap, wordList);
        alphaList.addAll(alphaNumMap.keySet()); //알파벳을 list로 저장

        //재귀함수를 돌면서 브루트포싱을 할 때 이미 알파벳에 부여한 숫자는 넘어가야 함
        //그 것을 위해 사용할 배열. 0-9까지의 index는 숫자 0-9를 의미
        boolean[] usedNumArr = new boolean[10];

        System.out.println(
                getMaxValue(alphaList, alphaNumMap, wordList, usedNumArr, 0));
    }

    static int getMaxValue(ArrayList<Character> alphaList, HashMap<Character, Integer> alphaNumMap,
                           ArrayList<String> wordList, boolean[] usedNumArray, int alphaIdx)
    {
        int maxVal = 0;

        //숫자를 전부 대입했을 경우 단어의 합을 구한다
        if (alphaIdx == alphaList.size())
            return getWordSum(alphaNumMap, wordList);

        //현재 알파벳 (alphaIdx에 해당하는 알파벳)에, 아직 대입하지 않은 숫자를 0부터 10까지 탐색하면서
        //하나씩 대입하며 재귀호출을 함
        for (int num = 0; num < 10; num++)
        {
            //이전의 재귀 호출에서 대입한 숫자는 넘어감
            if (usedNumArray[num])
                continue;

            usedNumArray[num] = true;

            //map에 알파벳-숫자를 넣어줌
            alphaNumMap.put(alphaList.get(alphaIdx), num);
            maxVal = Math.max(maxVal,
                    getMaxValue(alphaList, alphaNumMap, wordList, usedNumArray, alphaIdx + 1));

            usedNumArray[num] = false;
        }

        return maxVal;
    }

    static int getWordSum(HashMap<Character, Integer> alphaNumMap, ArrayList<String> wordList)
    {
        int sum = 0;
        int digit = 1;

        for (String word : wordList)
        {
            for (int i = word.length() - 1; i >= 0; i--)
            {
                sum += alphaNumMap.get(word.charAt(i)) * digit;
                digit *= 10;
            }
            digit = 1;
        }

        return sum;
    }

    static void setAlphaNumMap(HashMap<Character, Integer> alphaNumMap, ArrayList<String> wordList)
    {
        for (String word: wordList)
        {
            for (int i = 0; i < word.length(); i++)
                alphaNumMap.put(word.charAt(i), 0);
        }

    }
}
