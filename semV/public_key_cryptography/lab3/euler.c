#include <stdlib.h>
#include <stdio.h>
#include <math.h>

int is_prime(int n) {
	if(n <= 1) {
		return -1;
	}

	int i;
	for(i = 2; i <= sqrt(n); i++) {
		if(n % i == 0) {
			return 0;
		}
	}

	return 1;
}

float euler_f(int n) {
	int last_prime = 0, i = 2;
	float e = n;

	while(n != 1) {
		if(n % i == 0 && is_prime(i)) {
			if(last_prime != i) {
				e = e * (1.0 - 1.0 / i);
				last_prime = i;
			}
			n = n / i;
		} else {
			i += 1;
		}
	}

	return e;
}

int main(int argn, char ** argv) {
	int number = atoi(argv[1]);
	int bound  = atoi(argv[2]);
	int i;
	printf("Numbers that match: ");
	for(i = 1; i < bound; i ++)  {
		if(euler_f(i) == number) {
			printf("%d, ", i);
		}
	}
	printf("\n");
	return 0;
}