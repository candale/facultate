using namespace std;
#include <iostream>
#include <fstream>
#include <time.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include "InfInt.h"

InfInt binary_gcd(InfInt a, InfInt b) {
	// cout << "a: " << a.toString() << "   b: " << b.toString();

	if(a == b) {
		return a;
	}

	if(a == 0) {
		return b;
	}

	if(b == 0) {
		return a;
	}

	if (a % 2 == 0) { // if a is even
		if(b % 1 == 1) { // if b is odd
			return binary_gcd(a / 2, b);
		} else {
			return binary_gcd(a / 2, b / 2) / 2;
		}
	}

	if(b % 2 == 0) {
		return binary_gcd(a, b / 2);
	}

	if(a > b) {
		return binary_gcd((a - b) / 2, b);
	}

	return binary_gcd((b - a) / 2, a);
}

InfInt euclidian_gcd(InfInt a, InfInt b) {
	InfInt rem;

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
    while(true);

    return b;
}

InfInt difference_gcd(InfInt a, InfInt b) {
    if(a == "0") {
		return b;
	}
	else if(b == "0") {
		return a;
	}
    else if((a < 0) || (b < 0)) {
        return -1;
    }

    while(a != b) {
		//cout << a.toString() << "\n" << b.toString() << "\n\n";
    	if(a > b) {
    		a = a - b;
    	} else {
    		b = b - a;
    	}
    }

    return a;
}

int get_len() {
	int up_lim =  200;
	int down_lim = 100;
	int no_digits;

	return rand() % (up_lim - down_lim) + down_lim;
}

InfInt generate_number(int len) {
	char numb[len];

	for(int i = 0; i < len; i ++) {
		if(i == 0) {
			// so it won't start with 0
			sprintf(numb, "%s%d", numb, rand() % 9 + 1);	
		} else {
			sprintf(numb, "%s%d", numb, rand() % 10);
		}
	}

	InfInt res = numb;
	return res;
}

void run_test() {
	char header[2000];
	char binary_str[2000];
	char euclidian_str[2000];
	char diff_str[2000];

	strcpy(binary_str, "Binary GCD");
	strcpy(euclidian_str, "Euclidian GCD");
	strcpy(diff_str, "Difference GCD");
	strcpy(header, "");

	InfInt a, b;
	clock_t t;
	int len;
	float total = 0;
	float current;

	for(int i = 0; i < 10; i ++) {
		len = get_len();
		cout << "No digits: " << len << "\n";
		cout << "---------" << "\n";
		a = generate_number(len);
		b = generate_number(len);
		sprintf(header, "%s,%d", header, len);

		t = clock();
		binary_gcd(a, b);
		current = ((float)clock() - t);
		cout << "BINARY     || Time: " << current << "\n";
		sprintf(binary_str, "%s,%f", binary_str, current);


		t = clock();
		euclidian_gcd(a, b);
		current = ((float)clock() - t);
		cout << "EUCLIDIAN  || Time: " << current << "\n";
		sprintf(euclidian_str, "%s,%f", euclidian_str, current);

		t = clock();
		difference_gcd(a, b);
		current = ((float)clock() - t);
		cout << "DIFFERENCE || Time: " << current << "\n";
		sprintf(diff_str, "%s,%f", diff_str, current);
	}
	cout << " Total: " << total;

	ofstream file;
	file.open("data.csv");
	file << header << "\n" << binary_str << "\n" << euclidian_str << "\n" << diff_str;
	file.close();
}

int main() {
	srand(time(NULL));
	// InfInt a = "21";
	// InfInt b = "14";
	// cout << euclidian_gcd(a, b).toString();

	run_test();
}