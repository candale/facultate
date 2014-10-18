import Tkinter
import tkMessageBox
import ttk

class SubstitutionCypher:
	alphabet = "abcdefghijklmnopqrstuvwxyz"
	str_key = "qwertyuiopasdfghjklzxcvbnm"
	key_dict = {'a' : 'q','b' : 'e','c' : 'e','d' : 'r','e' : 't','f' : 'y','g' : 'u','h' : 'i',
		   'i' : 'o','j' : 'p','k' : 'a','l' : 's','m' : 'd','n' : 'f','o' : 'g','p' : 'h',
		   'q' : 'j','r' : 'k','s' : 'l','t' : 'z','u' : 'x','v' : 'c','w' : 'v','x' : 'b',
		   'y' : 'n','z' : 'm'}

	@staticmethod
	def build_key_dict(key):
		if len(set(key)) != len(SubstitutionCypher.alphabet):
			raise ValueError("Key length must be equal to alphabet length")

		return {alphabet_letter : key_letter for alphabet_letter, key_letter in zip(SubstitutionCypher.alphabet, key)}


	@staticmethod
	def encode(text, key_dict_local=None):
		key_dict_local = key_dict_local or SubstitutionCypher.key_dict

		text = text.lower()
		return "".join([key_dict_local[letter] if letter in key_dict_local else letter for letter in text]).upper()

	@staticmethod
	def decode(cyphertext, key):
		cyphertext = cyphertext.lower()
		decription_key_dict = {key_letter : alphabet_letter for key_letter, alphabet_letter in zip(key, SubstitutionCypher.alphabet)}

		return "".join([decription_key_dict[letter] if letter in decription_key_dict else letter for letter in cyphertext])



class simpleapp_tk(Tkinter.Tk):
    def __init__(self, parent):
        Tkinter.Tk.__init__(self,parent)
        self.parent = parent
        self.initialize()

    def initialize(self):
        self.grid()

        #  =============== ENCRYPTION ===============

        # KEY label
        self.label_key = Tkinter.Label(self, anchor="w", fg='black', text="Key: ")
        self.label_key.grid(column=0, row=0, sticky='EW')

        # KEY entry
        self.entry_key_str = Tkinter.StringVar()
        self.entry_key = Tkinter.Entry(self, textvariable=self.entry_key_str)
        self.entry_key.config(width=60)
        self.entry_key.grid(column=1,row=0,sticky='NE')
        self.entry_key_str.set(SubstitutionCypher.str_key)

        # TEXT label
        self.text_label = Tkinter.Label(self, anchor="w", fg="black", text="Text")
        self.text_label.grid(column=0, row=1, sticky="EW")

        # TEXT to encrypt entry
        self.entry_text = Tkinter.Entry(self)
        self.entry_text.config(width=60)
        self.entry_text.grid(column=1, row=1, sticky='ne')

        # CYPHERTEXT label
        self.cyphertext_label = Tkinter.Label(self, anchor='w', fg='black', text='Cyphertext')
        self.cyphertext_label.grid(column=0, row=4, sticky='ew')

        # CCYPHERTEXT entry
        self.entry_cyphertext_str = Tkinter.StringVar()
        self.entry_cyphertext = Tkinter.Entry(self, textvariable=self.entry_cyphertext_str)
        self.entry_cyphertext.grid(column=1, row=4, sticky='ne')
        self.entry_cyphertext.config(width=60)

        # ENCRYPT button
        self.encrypt_button = Tkinter.Button(self, text=u'Encrypt', command=self._OnEncryptButtonClick)
        self.encrypt_button.grid(column=1, row=3, sticky='ne')

        self.separator = ttk.Separator(self, orient=Tkinter.HORIZONTAL)
        self.separator.grid(columnspan=2, row=5, sticky="ew")

        # ============== DECRYPTION ===============

        # DECRYPT label
        self.decrypt_label = Tkinter.Label(self, anchor='w', fg='black', text='Decrypt')
        self.decrypt_label.grid(column=0, row=6, sticky='w')

        # DECRYPT text
        self.decrypt_entry_str = Tkinter.StringVar()
        self.decrypt_entry = Tkinter.Entry(self, textvariable=self.decrypt_entry_str)
        self.decrypt_entry.grid(column=1, row=6, sticky='ne')
        self.decrypt_entry.config(width=60)

        # DECRYPTED label
        self.decrypted_label = Tkinter.Label(self, anchor='w', fg='black', text='Decrypted')
        self.decrypted_label.grid(column=0, row=7, sticky='w')

        # DECRYPTED entry
        self.decrypted_entry_str = Tkinter.StringVar()
        self.decrypted_entry = Tkinter.Entry(self, textvariable=self.decrypted_entry_str)
        self.decrypted_entry.grid(column=1, row=7, sticky='ne')
        self.decrypted_entry.config(width=60)

        # DECRYPT button
        self.encrypt_button = Tkinter.Button(self, text=u'Decrypt', command=self._OnDecryptButtonClick)
        self.encrypt_button.grid(column=1, row=8, sticky='ne')


        self.grid_columnconfigure(1, weight=1)

    def _OnEncryptButtonClick(self):
    	try:
    		key_dict = SubstitutionCypher.build_key_dict(self.entry_key_str.get())
    	except ValueError as e:
    		tkMessageBox.showinfo(str(e))
    		return

    	self.entry_cyphertext_str.set(SubstitutionCypher.encode(self.entry_text.get(), key_dict))

    def _OnDecryptButtonClick(self):
		self.decrypted_entry_str.set(SubstitutionCypher.decode(self.decrypt_entry_str.get(), self.entry_key_str.get()))



if __name__ == "__main__":
    app = simpleapp_tk(None)
    app.title('Substitution Cypher')
    app.mainloop()