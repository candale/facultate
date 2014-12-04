#include <iostream>
#include <fstream>
#include <time.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>

using namespace std;

long int gcd(long int a, long int b) {
	long int rem;

    /* Check For Proper Input */
    if(a == 0) {
		return b;
	}
	else if(b == 0) {
		return a;
	}
    else if((a < 0) || (b < 0)) {
        return -1;
    }

    do
    {
        rem = a % b;
        if(rem == 0)
            break;
        a = b;
        b = rem;
    }
    while(1);

    return b;
}

long int function1(long int x, long int n) {
	return (x * x - 1) % n;
}

long int factorize(long n, long int (*func)(long int, long int) ) {
	long x = 2;
	long y = 2;
	long d = 1;

	while(d == 1) {
		x = func(x, n);
		y = func(func(y, n), n);
		d = gcd(abs(x - y), n);
	}

	if(d == n) {
		return -1;
	}

	return d;
}

void run_test_p() {

}

int main(int argc, char ** argv) {
	seed(time(null));
	long int n = atol(argv[1]);
	printf("%ld\n", factorize(n, function1));

	return 0;
}