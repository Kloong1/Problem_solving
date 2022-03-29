import java.util.*;

public class MergeSort
{
    static int[] sortedArr;

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
        sortedArr = new int[arr.length];
        mergeSort(arr, 0, arr.length - 1);
    }

    private static void mergeSort(int[] arr, int left, int right)
    {
        if (left >= right)
            return;

        int mid = (left + right) / 2;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    private static void merge(int[] arr, int left, int mid, int right)
    {
        int leftIdx, rightIdx, sortedIdx;
        leftIdx = left;
        rightIdx = mid + 1;
        sortedIdx = left;

        while (leftIdx <= mid && rightIdx <= right)
            sortedArr[sortedIdx++] = arr[leftIdx] < arr[rightIdx] ? arr[leftIdx++] : arr[rightIdx++];

        if (leftIdx > mid)
        {
            while (rightIdx <= right)
                sortedArr[sortedIdx++] = arr[rightIdx++];
        }
        else
        {
            while (leftIdx <= mid)
                sortedArr[sortedIdx++] = arr[leftIdx++];
        }

        while (left <= right)
        {
            arr[left] = sortedArr[left];
            left++;
        }
    }
}