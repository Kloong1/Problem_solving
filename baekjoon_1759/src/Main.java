import java.io.*;
import java.util.*;

public class Main
{
    //a, e, i, o, u에 해당하는 index의 값이 true인 배열
    static boolean[] isVowelArray;

    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int pwLen, numOfChars;
        st = new StringTokenizer(br.readLine());
        pwLen = Integer.parseInt(st.nextToken());
        numOfChars = Integer.parseInt(st.nextToken());

        //암호에 들어갈 수 있는 알파벳 후보를 저장해 두는 배열
        char[] charCandidates = new char[numOfChars];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < numOfChars; i++)
        {
            char charCandidate = st.nextToken().charAt(0);
            charCandidates[i] = charCandidate;
        }

        //출력을 사전순으로 해야 하기 때문에 알파벳을 미리 사전순으로 정렬해두고
        //오름차순으로 배열을 탐색함
        Arrays.sort(charCandidates);

        //a, e, i, o, u에 해당하는 index의 값을 true로 초기화
        setIsVowelArray();

        printAllPasswordCandidates(new PasswordInfo(pwLen), charCandidates, 0, bw);

        br.close();
        bw.flush();
        bw.close();
    }

    static void printAllPasswordCandidates
            (PasswordInfo pwInfo, char[] charCandidates, int charCandIdx, BufferedWriter bw) throws IOException
    {
        //패스워드가 완성된 경우 (주어진 길이만큼 알파벳을 채워 넣은 경우)
        if (pwInfo.isFullPassword())
        {
            //모음이 1개 이상이고 자음이 2개 이상인 경우에만 출력
            if (pwInfo.isProperPassword())
                bw.write(pwInfo.passwordToString() + "\n");
            return;
        }

        //사전 순서(정렬해 두었으므로 i가 커지는 순서)로 알파벳 배열 탐색하며 password에 알파벳 추가
        for (int i = charCandIdx; i < charCandidates.length; i++)
        {
            //남은 후보 알파벳을 전부 password에 추가해도 password의 길이 조건을 만족하지 못하면
            //추가로 탐색할 필요가 없으므로 종료
            int remainCharCandNum = charCandidates.length - i;
            if (remainCharCandNum + pwInfo.getLength() < pwInfo.getMaxLength())
                return;

            pwInfo.password.add(charCandidates[i]);

            if (isVowel(charCandidates[i]))
            {
                pwInfo.vowelCnt++;
                printAllPasswordCandidates(pwInfo, charCandidates, i + 1, bw);
                pwInfo.vowelCnt--;
            }
            else
            {
                pwInfo.consCnt++;
                printAllPasswordCandidates(pwInfo, charCandidates, i + 1, bw);
                pwInfo.consCnt--;
            }

            pwInfo.removeLastChar();
        }
    }

    //a, e, i, o, u에 해당하는 index의 값을 true로 초기화
    static void setIsVowelArray()
    {
        isVowelArray = new boolean[26];

        isVowelArray['a' - 'a'] = true;
        isVowelArray['e' - 'a'] = true;
        isVowelArray['i' - 'a'] = true;
        isVowelArray['o' - 'a'] = true;
        isVowelArray['u' - 'a'] = true;
    }

    static boolean isVowel(char c)
    {
        return isVowelArray[c - 'a'];
    }
}

class PasswordInfo
{
    ArrayList<Character> password;
    int maxLen;
    int consCnt;
    int vowelCnt;

    PasswordInfo(int maxLen)
    {
        this.maxLen = maxLen;
        this.consCnt = 0;
        this.vowelCnt = 0;
        this.password = new ArrayList<>(maxLen);
    }

    boolean isFullPassword()
    {
        return password.size() == maxLen;
    }

    boolean isProperPassword()
    {
        return consCnt >= 2 && vowelCnt >= 1;
    }

    String passwordToString()
    {
        StringBuilder sb = new StringBuilder();
        for (char c : password)
            sb.append(c);
        return sb.toString();
    }

    void removeLastChar()
    {
        password.remove(password.size() - 1);
    }

    int getLength()
    {
        return password.size();
    }

    int getMaxLength()
    {
        return maxLen;
    }
}