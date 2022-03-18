#include <stdio.h>

unsigned int words[50];
unsigned int candidate_alpha = 0;
unsigned int redable_alpha = 0;
unsigned int max_alpha = (1 << 25);

void get_words(int n)
{
	int i, j;
	char word[16];
	unsigned int bit;

	for (i = 0; i < n; i++)
	{
		words[i] = 0;

		scanf("%s", word);
		for (j = 0; word[j]; j++)
		{
			bit = (1 << (word[j] - 'a'));
			words[i] |= bit;
			candidate_alpha |= bit;
		}
	}
}

int is_readable_word(unsigned int word)
{
	if ((word & redable_alpha) == word)
		return (1);
	return (0);
}

int max(int a, int b)
{
	return (a > b ? a : b);
}

int	count_redable_words(int n, int k, unsigned int pivot_alpha)
{
	int cnt;
	int i;

	cnt = -1;

	if (k == 0)
	{
		cnt = 0;
		for (i = 0; i < n; i++)
			cnt += is_readable_word(words[i]);
		return (cnt);
	}

	while (pivot_alpha <= max_alpha)
	{
		if (pivot_alpha & candidate_alpha)
		{
			redable_alpha |= pivot_alpha;

			cnt = max(cnt, count_redable_words(n, k - 1, pivot_alpha << 1));
			redable_alpha &= ~pivot_alpha;
		}
		pivot_alpha = pivot_alpha << 1;
	}

	if (cnt < 0)
	{
		cnt = 0;
		for (i = 0; i < n; i++)
			cnt += is_readable_word(words[i]);
	}

	return (cnt);
}

int main()
{
	int n, k;
	
	scanf("%d %d", &n, &k);
	if (n <= 0 || k < 5)
	{
		printf("0\n");
		return (0);
	}

	get_words(n);

	// 모든 단어에 'anta', 'tica'가 포함되어있음
	redable_alpha |= (1 << ('a' - 'a'));
	redable_alpha |= (1 << ('n' - 'a'));
	redable_alpha |= (1 << ('t' - 'a'));
	redable_alpha |= (1 << ('i' - 'a'));
	redable_alpha |= (1 << ('c' - 'a'));
	k -= 5;

	// a n t i c 는 고정이므로 나머지 알파벳만 고려
	candidate_alpha &= ~redable_alpha;

	printf("%d\n", count_redable_words(n ,k, 2)); //start to check from 'b'

	return (0);
}
