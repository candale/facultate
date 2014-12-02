import os
import re


class Compile:

	def __init__(self, path):
		# ======= patterns ======== #
		# matches words
		self.__is_word = re.compile("\w+")
		# matches operators
		self.__is_comparison = re.compile("[<>=+\-*!]+")
		# matches control symbols
		self.__if_ctrl = re.compile("[{}()\.]+")
		# is constant pattern
		self.__is_constant = re.compile('("[\d\w ]+"|\d+)')
		# operations pattern
		self.__is_operation = re.compile('[+\-*/]')
		# regex to separate line
		self.__separator_pat = re.compile('("[\w\d ]+"|\w+|\d+|[<>=+\-*!]+|[{}();\.]{1})')

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
		status = True
		if not os.path.isfile(self.__path):
			self.__create_error(0, "File not found (%s)" % self.__path)
			status = False

		return status


	def compile(self):
		if not self._run_initial_checks():
			return self.__errors

		with open(self.__path) as program_file:
			for line in program_file:
				self._parse_line(line)
				self.__line += 1

		print self.__pif
		print self.__symbols
		for err in self.__errors:
			print err

	def _parse_line(self, line):
		tokens = [tok.strip() for tok in self.__separator_pat.findall(line)]
		if len("".join(tokens)) != len(line)
		#print tokens
		# for each word in current line
		index = 0
		# for each token
		while index < len(tokens):
			token = tokens[index]
			# if it is a standard word
			if self.__is_word.match(token):
				# if the word si reserved
				if token in self.__codes:
					code = self.__codes[token]
					# if we're about to declare a variable
					if code in self.__type_range:
						# if no error was encoutered in parsing the assignment
						if not self.__parse_var_creation(tokens[index:]):
							self.__create_error(1, "invalid declaration syntax; see previous errors")
							return
						index += 3
						continue

					# if we're about to have a for repetitive loop
					if code == self.__codes["for"]:
						if not self.__parse_for_loop(tokens[index:]):
							self.__create_error(1, "invalid repetitive syntax; see previous errors")
							return
						index += 6
						continue

					# if we're about to have a condition
					if code == self.__codes["if"]:
						res = self.__parse_condition(tokens)
						if not res:
							self.__create_error(1, "invalid condition syntax; see previous errors")
							return
						index += res
						continue

				# this is assignment
				elif self._get_symbol(token) != None:
					# __parse_assignment returnes the nnumber of tokens it consumed
					index += self.__parse_assignment(tokens)
					
				# the word is unknown 
				else:
					self.__create_error(1, "undefined variable %s" % token)
			# if we have a control character { } ( ) .
			elif self.__if_ctrl.match(token):
				if token in self.__codes:
					self.__add_to_pif(token)
				else:
					self.__create_error(1, "unknown symbol %s" % token)
			else:
				self.__create_error(1, "unknown keyword %s" % token)
				break
			index +=1

	def __parse_for_loop(self, tokens):
		self.__add_to_pif("for")
		res = self.__parse_assignment(tokens[1:], loop=True)
		if res:
			token = tokens[res + 1]
			if token == "to":
				self.__add_to_pif(token)
				token = tokens[res + 2]
				if not self.__add_to_pif(token):
					self.__create_error(1, "unknown variable or constant %s" % token)
			else:
				self.__create_error(1, "malformed for loop")
				return False
		else:
			self.__create_error(1, "malformed for loop")
			return False

		return True

	def __parse_condition(self, tokens):
		# if variable|constant operator variable|constant then
		try:
			self.__add_to_pif(tokens[0])
			# parse the comparison part of the condition
			res = self.__parse_cmp(tokens[1:])
			if res:
				# add two to res because there are two tokens that it did not count: if and ==
				token = tokens[res + 2]
				if token == "then":
					self.__add_to_pif(token)
				else:
					self.__create_error(1, "malformed if statement")
					return False

		except IndexError as e:
			self.__create_error(1, "invalid syntax")
			return False

		# return the next unparsed token
		return res + 3

	def __parse_cmp(self, tokens):
		try:
			token = tokens[0]
			# if lvalue is a valid word and it is in the symbol table
			res = self.__parse_rvalue(tokens)
			if res:
				token = tokens[res]
				# if we have a valid operator
				if self.__is_comparison.match(token) and token in self.__codes:
					self.__add_to_pif(token)
					# if the rvalue is a valid word and it in the symbol table
					res1 = self.__parse_rvalue(tokens[res + 1:])
					if not res1:
						self.__create_error(1, "unknown rvalue %s" % token)
						return False
				else:
					self.__create_error(1, "invalid comparison")
					return False
			else:
				self.__create_error(1, "unknown lvalue %s" % token)
				return False
		except IndexError as e:
			self.__create_error(1, "invalid comparison")
			return False

		return res + res1

	def __parse_rvalue(self, tokens):
		try:
			index = 0
			pharantese = 0
			# ( for open pharanteses, ) for close pharanteses, o for operator, v for variable
			last_type = None

			while index < len(tokens):
				token = tokens[index]
				if token == ';':
					break

				if (self.__is_word.match(token) or self.__is_constant.match(token)) and token not in self.__codes:
					if not (self.__add_to_pif(token) and last_type != ")" and last_type != "v"):
						self.__create_error(1, "variable or constant cannot apppear after another variable of )")
						return False
					last_type = 'v'
				elif self.__is_operation.match(token):
					if not (self.__add_to_pif(token) and last_type != 'o' and last_type != '('):
						self.__create_error(1, "operator cannot appear after another operator or (")
						return False
					last_type = 'o'
				elif token == '(':
					if not (self.__add_to_pif(token) and last_type != 'v' and last_type != ')'):
						self.__create_error(1, "'(' cannnot appear after a variable or ')'")
						return False
					pharantese += 1
					last_type = '('
				elif token == ')':
					if not (self.__add_to_pif(token) and last_type != '(' and last_type != 'o'):
						self.__create_error(1, "')' canoot appear after a variable or a '('")
						return False
					pharantese -= 1
					last_type = ')'
				else:
					break
				index += 1
			if pharantese != 0:
				self.__create_error(1, "unbalanced pharantese")
				return False
		except IndexError as e:
			self.__create_error(1, "invalid syntax (out of range)")
			return False

		return index

	def __parse_expression(self, tokens):
		pharantese = 0
		index = 0
		for token in tokens:
			if not (token == '(' or token == ')'):
				# parse the next comparison
				if self.__parse_cmp_op(tokens[index:index + 3], op=True):
					pass

	def __parse_assignment(self, tokens, loop=False):
		# for variable = constant to constant
		try:
			index = 0;
			token = tokens[index]
			if self.__is_word.match(token):
				self.__add_to_pif(token, decl=True)
				token = tokens[index + 1]

				if token == "=":
					self.__add_to_pif(token) # add the =
					index += 2
					# __parse_rvalue returns the number of tokens it consumed ot False if it encounnterd an error
					index += self.__parse_rvalue(tokens[index:])
					token = tokens[index]
					if not (token == ';' and self.__add_to_pif(token)):
						if not loop:
							self.__create_error(1, "invalid syntax")
							return False
				else:
					self.__create_error(1, "invalid syntax")
					return False
			else:
				self.__create_error(1, "invalid syntax")
				return False
		except IndexError as e:
			self.__create_error(1, "invalid syntax (out of range)")
			return False

		return index


	def __add_to_pif(self, token, decl=False):
		# if it is reserved word
		if token in self.__codes:
			self.__pif.append((self.__codes[token], -1))
		elif self.__is_word.match(token) or self.__is_constant.match(token):
			# if is constant
			if self.__is_constant.match(token):
				symbol_code = self.__add_symbol_with_check(token)
				self.__pif.append((1, symbol_code))
			# if the variable is now declared
			elif decl:
				symbol_code = self.__add_symbol_with_check(token)
				self.__pif.append((0, symbol_code))
			elif self._get_symbol(token) != None:
				symbol_code = self._get_symbol(token)
				self.__pif.append((0, symbol_code))
			else:
				return False
		else:
			return False

		return True


	def __get_constat_type(self, token):
		if re.findall("\d+", token)[0] == token:
			return self.__codes["int"]
		elif re.findall('"[\w\d ]+"', token)[0] == token:
			return self.__codes["string"]
		elif re.findall("'\w'")[0] == token:
			return self.__codes["char"]
		else:
			return None


	def __add_symbol_with_check(self, symbol):
		symbol_code = self._get_symbol(symbol)
		if symbol_code == None:
			symbol_code = self.__symb_no
			self._add_symbol(symbol)

		return symbol_code


	def __parse_var_creation(self, tokens, loop=False):
		# if the index goes out of bounds
		try:
			index = 1
			token = tokens[index]
			# if the next token is a standard word
			if self.__is_word.match(token):

				# the next token should be a ; or a .
				token = tokens[index + 1]
				if token != ";" and token != ".":
					self.__create_error(0, "invalid syntax")
					return False

				# add the instructions to the pif
				self.__add_to_pif(tokens[0])
				self.__add_to_pif(tokens[1], decl=True)
				self.__add_to_pif(tokens[2])
			else:
				self.__create_error(0, "invalid vairiable name %s" % token)
				return False
		except IndexError as e:
			self.__create_error(1, "invalid syntax (out of range)")
			return False

		return True


	def _add_symbol(self, symb):
		self.__symbols.append((self.__symb_no, symb))
		self.__symb_no +=1 
		# if not self.__symbols:
		# 	self.__symbols.append((self.__symb_no, symb))
		# 	return

		# for index in xrange(0, len(self.__symbols)):
		# 	if self.__symbols[index][1] == symb:
		# 		return
		# 	elif symb < self.__symbols[index]:
		# 		self.__symbols.insert(index - 1, (self.__symb_no, symb))
		# 		self.__symb_no += 1


	def _get_symbol(self, symb, pos=None):
		list = self.__symbols
		for index in range(0, len(self.__symbols)):
			if self.__symbols[index][1] == symb:
				return self.__symbols[index][0]
		# if self.__symbols == []:
		# 	return

		# if pos == None:
		# 	pos = len(self.__symbols) / 2
		# print pos
		# a = len(self.__symbols)
		# print self.__symbols

		# if self.__symbols[pos][1] == symb:
		# 	return pos
		# elif symb < self.__symbols[pos][1] and pos / 2 != pos:
		# 	return self._get_symbol(symb, pos / 2)
		# elif pos + (len(self.__symbols) - pos) / 2 != pos:
		# 	return self._get_symbol(symb, pos + (len(self.__symbols) - pos) / 2)


	def __create_error(self, err_type, err_text):
		self.__errors.append(("%s:%s %s") % (self.__error_types[err_type], self.__line, err_text))




compile = Compile("/home/andrei/workspace/facultate/semV/compilatoare/lab1/file")
compile.compile()