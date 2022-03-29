import java.util.*;

public class QuickSort
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        StringTokenizer st = new StringTokenizer(sc.nextLine());

        int[] arr = new int[st.countTokens()];
        for (int i = 0; i < arr.length; i++)
            arr[i] = Integer.parseInt(st.nextToken());

        System.out.println();
        System.out.println("Before");
        System.out.println(Arrays.toString(arr));
        System.out.println();

        sort(arr);

        System.out.println("After");
        System.out.println(Arrays.toString(arr));
        System.out.println();
    }

    public static void sort(int[] arr)
    {
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int left, int right)
    {
        if (left >= right)
            return;

        int pivotIdx = partition(arr, left, right);

        quickSort(arr, left, pivotIdx - 1);
        quickSort(arr, pivotIdx + 1,  right);
    }

    private static int partition(int[] arr, int left, int right)
    {
        int pivot;
        int lowIdx, highIdx;
        int temp;

        pivot = arr[left];
        lowIdx = left + 1;
        highIdx = right;

        while (true)
        {
            while (arr[lowIdx] <= pivot && lowIdx < right)
                lowIdx++;

            while (arr[highIdx] >= pivot && highIdx > left)
                highIdx--;

            if (lowIdx < highIdx)
            {
                temp = arr[lowIdx];
                arr[lowIdx] = arr[highIdx];
                arr[highIdx] = temp;
            }
            else
                break;
        }

        arr[left] = arr[highIdx];
        arr[highIdx] = pivot;

        return highIdx;
    }
}