#include <iostream>
#include <fstream>
#include <time.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>

using namespace std;
char pollard[] = "pollard";
char classical[] = "classical";

long long gcd(long long a, long long b) {
	long long rem;

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

long long function1(long long x, long long n) {
	return (x * x - 1) % n;
}

long long factorize(long n, long long (*func)(long long, long long) ) {
	long x = 2;
	long y = 2;
	long d = 1;
	cout << "Values: ";

	while(d == 1) {
		x = func(x, n);
		y = func(func(y, n), n);
		d = gcd(abs(x - y), n);
	}

	if(d == n) {
		return -1;
	}
	cout << d << " || ";

	return d;
}

long long classsical(long long n) {
	cout << "Values: ";
	long long x  = 2;
	while(x * x <= n) {
		if(n % x == 0) {
			cout << x << " ";
			n /= x;
		} else {
			x ++;
		}
	}

	if(n > 1) {
		cout << n << " ";
	}
	cout << " || ";
}

int get_len(long long value){
	int l=1;
	while(value>9)
	{ 
		l++; 
		value/=10; 
	}
	return l;
}

void run_test_p() {
	char pollard_str[4096];
	char classical_str[4096];
	clock_t t;
	float current;
	for(int i = 0; i < 20; i ++) {
		long long n = RAND_MAX * (rand() + 1) + rand() * rand();
		if(n < 0) {
			n *= -1;
		}

		t = clock();
		factorize(n, function1);
		current = ((float)clock() - t);
		cout << "POLLARD  || Time: " << current << "|| Length: " << get_len(n) << " || " << n << "\n";
		sprintf(pollard_str, "%s,%f", pollard, current);

		t = clock();
		classsical(n);
		current = ((float)clock() - t);
		cout << "CLASSICAL  || Time: " << current << "|| Length: " << get_len(n) << " || " << n << "\n";
		sprintf(classical_str, "%s,%f", classical, current);
	}


	ofstream file;
	file.open("data.csv");
	file << pollard_str << "\n";
	file.close();
}

int main() {
	srand(time(NULL));
	run_test_p();
	return 0;
}