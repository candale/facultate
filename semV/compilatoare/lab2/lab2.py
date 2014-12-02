import copy
import os
import re

grammar = None
auto = None
separate_words = re.compile('[^_]+')

def get_printable(str):
    return " ".join([word for word in separate_words.findall(str)])

def _read_int(str):
    try:
        return int(raw_input(str))
    except ValueError:
        pass

def read_gr_key():
    global grammar
    terminals, non_terminals, productions = [], [], []

    no_non_term = int(raw_input('number of non terminals: '))
    for index in xrange(0, no_non_term):
        non_terminals.append(raw_input('non terminal: '))

    no_terminals = int(raw_input('number of terminals: '))
    for index in xrange(0, no_terminals):
        terminals.append(raw_input('terminal: '))

    no_productions = int(raw_input('number of productions'))
    for index in xrange(0, no_productions):
        line = raw_input('production: ')
        toks = line.split("->")
        parent = toks[0]

        # get children
        toks = toks[1].split("|")
        prod = {parent: [[ind_term for ind_term in term] for term in toks]}

        productions.append(prod)

    grammar = {'non-term': non_terminals, 'term': terminals, 'prds': productions}


def read_gr_file():
    global grammar
    file_path = raw_input("file path: ")
    # file exists?

    with open(file_path, "r") as grammar_file:
        line = grammar_file.readline().strip()
        non_terminals = [symbol for symbol in line.split()]

        line = grammar_file.readline().strip()
        terminals = [symbol for symbol in line.split()]

        productions = []
        for line in grammar_file:
            line = line.strip()
            # get parent
            toks = line.split("->")
            parent = toks[0]

            # get children
            toks = toks[1].split("|")
            prod = {parent: [[ind_term for ind_term in term] for term in toks]}

            productions.append(prod)

    grammar = {"non-term": non_terminals, "term": terminals, "prds": productions}
    print "done"

def is_regular_grammar():
    if grammar is None:
        print "No grammar is loaded"
        return

    for prd in grammar['prds']:
        key = prd.keys()[0]
        # left-hand side must belong to non-terminals
        if key not in grammar['non-term']:
            print "Nope, not regular"
            return False

        # at least one in right-hand side must be in terminals
        for l in prd[key]:
            if len(l) > 2:
                print "Nope, not regular"
                return False
            if l[0] not in grammar['term']:
                print "Nope, not regular"
                return False
            if len(l) == 2 and l[1] not in grammar['non-term']:
                print "Nope, not regular"
                return False

    print "Yup, is regular"

def print_terminals():
    if grammar is None:
        print "No grammar is loaded"
        return

    str = ", ".join([term for term in grammar["term"]])
    print str

def print_non_terminals():
    if grammar is None:
        print "No grammar is loaded"
        return

    str = ", ".join([non_term for non_term in grammar["non-term"]])
    print str

def print_productions():
    if grammar is None:
        print "No grammar is loaded"
        return

    string = ""
    for prd in grammar["prds"]:
        key = prd.keys()[0]

        prd_str = "%s -> " % key
        str_list = ["".join(let_list) for let_list in prd[key]]
        prd_str = "%s %s" % (prd_str, "|".join(str_list))
        string = "%s%s\n" % (string, prd_str)

        targets = []
        for tar in prd[key]:
            targets.append(tar[0])
            if len(tar) == 2:
                targets.append(tar[1])
            else:
                targets.append('');

        for index, val in enumerate(targets):
            if index % 2 == 1:
                if val in auto['final_states']:
                    print "%s -> %s" % (key, targets[index - 1])

    print string


def print_menu():
    for index, item in enumerate(functions):
        if index % 2 == 0:
            print "%s -- %s" % (index / 2 + 1, get_printable(item))

def read_auto_file(text=None):
    global auto

    if text is not None:
        with open("auto", "w") as f:
            f.write(text)
        file_path = "auto"
    else:
        file_path = raw_input('file path: ')

    with open(file_path, "r") as auto_file:
        line = auto_file.readline().strip()
        states = line.split()

        line = auto_file.readline().strip()
        alphabet = line.split()

        line = auto_file.readline().strip()
        final_states = line.split()

        init_state = auto_file.readline()

        transitions = {}
        for line in auto_file:
            line = line.strip()
            if line == '':
                continue

            toks = re.findall('[^-:\s]', line)
            state_a, state_b, vals = toks[0], toks[1], toks[2:]
            transitions[(state_a, state_b)] = vals

    auto = {"states": states, "alphabet": alphabet, "final_states": final_states, \
            "transitions": transitions, "init_state": init_state}
    print auto


def read_auto_key():
    text = ""
    text = "%s\n" % raw_input("states(separated by space): ")
    text += "%s\n" % raw_input("alphabet(separated by space): ")
    text += "%s\n" % raw_input('final states(separated by space): ')
    print "type transitions of the form: state_a-state_b:value. when done press enter"
    line = None
    while line is not '':
        line = raw_input("transition: ")
        text += "%s\n" % line

    read_auto_file(text)

def print_auto_states():
    print ", ".join(auto["states"])

def print_auto_alphabet():
    print ", ".join(auto["alphabet"])

def print_auto_transitions():
    string = ""
    for tran in auto['transitions']:
        state_a, state_b = tran
        str_t = "(%s, %s) --> %s\n" %(state_a, ", ".join(auto['transitions'][tran]), state_b)
        string += str_t

    print string

def grammar_to_automata():
    global auto
    if not grammar:
        print "No grammar is loaded"
        return

    auto = {}
    auto['states'] = copy.copy(grammar['non-term'])
    auto['states'].append('k')

    auto['alphabet'] = copy.copy(grammar['term'])
    auto['transitions']    = {}
    auto['final_states'] = []
    for rel in grammar['prds']:
        key = rel.keys()[0]
        for tar in rel[key]:
            if len(tar) == 2:
                auto['transitions'][(key, tar[1])] = tar[0]
            elif len(tar) == 1:
                auto['transitions'][(key, 'K')] = tar[0]
                auto['final_states'].append(key)

    print auto

def auto_to_grammar():
    global grammar
    if not auto:
        print "No automata is loaded"
        return

    grammar = {}
    grammar['non-term'] = auto['states']
    grammar['term'] = auto['alphabet']
    grammar['final_states'] = auto['final_states']
    grammar['init'] = auto['init_state']
    grammar['prds'] = {}

    prds = []
    for tran in auto['transitions']:
        state_a = tran[0]
        state_b = tran[1]
        vals = auto['transitions'][tran]
        lst = []
        for val in vals:
            lst.append([val, state_b])
        prds.append({state_a: lst})

    grammar['prds'] = prds

def print_auto_final_states():
    print ", ".join(auto['final_states'])


functions  = ['read_grammar_from_keyboard', read_gr_key, \
              'read_grammer_from_file', read_gr_file, \
              'print terminals', print_terminals, \
              'print non_terminals', print_non_terminals, \
              'print_productions', print_productions, \
              'read_automata_from_file', read_auto_file, \
              'read_automata_from_keyboard', read_auto_key, \
              'print_automata_states', print_auto_states, \
              'print_automata_alphabet', print_auto_alphabet, \
              'print_automata_transitions', print_auto_transitions, \
              'print_automata_final_states', print_auto_final_states, \
              'is_regular_grammar', is_regular_grammar, \
              'from_grammar_to_automata', grammar_to_automata, \
              'from_automata_to_grammmar', auto_to_grammar, \
              'exit', exit]

def main():
    print_menu()
    function_names = [key for key in functions]
    while True:
        command = int(raw_input(">>> "))

        if command < len(functions) / 2 + 1:
            functions[command * 2 - 1]()
        else:
            print "no such command: %s" % command


if __name__ == "__main__":
    main()