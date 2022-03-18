import java.util.HashSet;

class Solution {
    public int solution(int N, int number) {
        
        if (N == number)
            return 1;
        
        HashSet<Integer>[] numbersMadeOfN = new HashSet[9];
        initNumbersMadeOfN(numbersMadeOfN, N);
        
        return canGenerateNumberWithNumOfN(numbersMadeOfN, number, N);
    }
    
    static int canGenerateNumberWithNumOfN(HashSet<Integer>[] numbersMadeOfN, int number, int N)
    {
        for (int numOfN = 2; numOfN <= 8; numOfN++)
        {
            for (int subNumOfN = 1; subNumOfN < numOfN; subNumOfN++)
                addNumbersMadeOfN(numbersMadeOfN[numOfN], numbersMadeOfN[subNumOfN], numbersMadeOfN[numOfN - subNumOfN]);
            if (numbersMadeOfN[numOfN].contains(numbe))
                return numOfN;
        }
        
        return -1;
    }
    
    static void addNumbersMadeOfN(HashSet<Integer> numbersMadeOfN, HashSet<Integer> subset1, HashSet<Integer> subset2)
    {
        for (int num1 : subset1)
        {
            for (int num2 : subset2)
            {
                numbersMadeOfN.add(num1 + num2);
                numbersMadeOfN.add(num1 - num2);
                numbersMadeOfN.add(num1 * num2);
                if (num2 != 0)
                	numbersMadeOfN.add(num1 / num2);
            }
        }
    }
    
    static void initNumbersMadeOfN(HashSet<Integer>[] numbersMadeOfN, int N)
    {
        int num = N;
        for (int i = 1; i <= 8; i++)
        {
            numbersMadeOfN[i] = new HashSet<Integer>();
            numbersMadeOfN[i].add(num);
            num = num * 10 + N;
        }
    }
}r
