import os
import re


class Compile:

	def __init__(self, path):
		"""
			Initialization of variables that will be used to compile the code.
		"""
		# ======= patterns ======== #
		# matches words
		self.__is_word = re.compile("\w+")
		# matches operators
		self.__is_comparison = re.compile("[<>=+\-*!]+")
		# matches control symbols
		self.__if_ctrl = re.compile("[{}()\.]+")
		# is constant pattern
		self.__is_constant = re.compile('(\'[\w\d]{1}\'|"[\d\w ]+"|[+-]*[1-9]{1}[0-9]*|0)$')
		# operations pattern
		self.__is_operation = re.compile('[+\-*/]')
		# regex to separate line
		self.__separator_pat = re.compile('(\'[\w\d]{1}\'|"[\w\d ]+"|\w+|[+-]\d+|[+\-*/]|[<>=!]+|[{}();\.]{1})')
		self.__is_valid_var_name = re.compile("[a-bA-B_]+[a-bA-B0-9_\-]*")

		# ====== data tables ======= #
		# all user defined symbols
		self.__symbols = []
		# program internal form
		self.__pif = []
		self.__symb_no = 1

		# ====== other sutff ======
		# errors
		self.__errors = []
		# path to file
		self.__path = path
		# current line number
		self.__line = 1
		# error types
		self.__error_types = ["fatal error", "compilation error"]
		# the code for constants and ids (from symbol table)
		self.__const_code = 1
		self.__id_code    = 0

		# persistent information about assginments and comparisions
		self.__type_range     = range(6, 9)
		self.__last_stmt_type = None 

		# control indicators
		self.__code_block = 0
		self.__expression_block = 0


		# the codes for each instruction 
		self.__codes = {'program': 2, 
						'var': 3, 'const': 4,
						'array': 5,
						'int': 6, 'char': 7, 'string': 8,
				        'while': 9, 'do': 10, 'if': 11, 'then': 12, 'else': 13, 'for': 14, 'to': 15,
				        'read': 16, 'write': 17, 
				        '{': 18, '}': 19, 
				        '(': 20, ')': 21,
				        '<': 22, '<=': 23, '>=': 24, '>': 25,
				        '!=': 26, '==': 27,
				        '=': 28,
				        '+': 29, '-': 30, '*': 31, '/': 32, 
		         		';': 33, '.': 34, '"': 35}


	def _run_initial_checks(self):
		"""
			Various checks that need to be passed before the compilatio starts
				- the file specified exists
			Postcondition:
				- if the file does not exists an error will be separate
				  and the method return with return value False
		"""
		status = True
		if not os.path.isfile(self.__path):
			self.__create_error(0, "File not found (%s)" % self.__path)
			status = False

		return status


	def compile(self):
		"""
			Method that gets and fetches the lines from the source file
			to the parsing method.
		"""
		if not self._run_initial_checks():
			return self.__errors

		with open(self.__path) as program_file:
			for line in program_file:
				self._parse_line(line)
				self.__line += 1

		print "========================== PIF =========================="
		print self.__pif
		print "======================== SYMBOLS ========================"
		print self.__symbols
		for err in self.__errors:
			print err

	def __is_whole(self, tokens, line):
		"""
			Check if the tokens that made up the line are the same as the line
			(i.e. there are no unkown characters or words)
			Return true if the check passed and false otherwise
		"""
		line_tmp = "".join(tokens)
		for word in line.split():
			if word not in line_tmp:
				return False
		return True


	def _parse_line(self, line):
		"""
			Goes through each token on the line, validates it and adds it to 
			the corresponding table
		"""
		tokens = [tok.strip() for tok in self.__separator_pat.findall(line)]
		#print tokens
		# for each word in current line
		index = 0
		# for each token
		while index < len(tokens):
			token = tokens[index]
			if self.__is_whole(tokens, line):
				self.__add_to_pif(token)
			else:
				self.__create_error(1, "invalid syntax")
				break
			index += 1

	def __add_to_pif(self, token):
		"""
			Adds the given token to the pif, with the corresponding code
		"""
		# if it is reserved word
		if token in self.__codes:
			self.__pif.append((self.__codes[token], -1))
		elif self.__is_word.match(token) or self.__is_constant.match(token):
			# if is constant
			if self.__is_constant.match(token):
				symbol_code = self.__add_symbol_with_check(token)
				self.__pif.append((1, symbol_code))
				return True

			# if the variable is now declared
			if self.__is_valid_var_name.match(token):
				symbol_code = self._get_symbol(token)
				if symbol_code != None:
					self.__pif.append((0, symbol_code))
				else:
					symbol_code = self.__add_symbol_with_check(token)
					self.__pif.append((0, symbol_code))
			else:
				self.__create_error(1, "invalid syntax >> %s" % token)
		else:
			self.__create_error(1, "invalid syntax near >> %s" % token )
			return False

		return True

	def __add_symbol_with_check(self, symbol):
		"""
			
		"""
		symbol_code = self._get_symbol(symbol)
		if symbol_code == None:
			symbol_code = self.__symb_no
			self._add_symbol(symbol)

		return symbol_code


	def _add_symbol(self, symb):
		self.__symbols.append((self.__symb_no, symb))
		self.__symbols = sorted(self.__symbols, key=lambda a: a[1])
		self.__symb_no +=1 


	def _get_symbol(self, symb, pos=None):
		list = self.__symbols
		for index in range(0, len(self.__symbols)):
			if self.__symbols[index][1] == symb:
				return self.__symbols[index][0]


	def __create_error(self, err_type, err_text):
		self.__errors.append(("%s:%s %s") % (self.__error_types[err_type], self.__line, err_text))


compile = Compile("/home/andrei/workspace/facultate/semV/compilatoare/lab1/file")
compile.compile()