"""
A trie (pronounced as "try") or prefix tree is a tree data structure used to
efficiently store and retrieve keys in a dataset of strings. There are various
applications of this data structure, such as autocomplete and spellchecker.

Implement the Trie class:
    Trie() Initializes the trie object.
    void insert(String word) Inserts the string word into the trie.
    boolean search(String word) Returns true if the string word is in the trie
                                (i.e., was inserted before), and false otherwise.
    boolean startsWith(String prefix) Returns true if there is a previously inserted
                                string word that has the prefix, and false otherwise.

Example 1:
Input
["Trie", "insert", "search", "search", "startsWith", "insert", "search"]
[[], ["apple"], ["apple"], ["app"], ["app"], ["app"], ["app"]]
Output
[null, null, true, false, true, null, true]

Explanation
Trie trie = new Trie();
trie.insert("apple");
trie.search("apple");   // return True
trie.search("app");     // return False
trie.startsWith("app"); // return True
trie.insert("app");
trie.search("app");     // return True


Constraints:
    1 <= word.length, prefix.length <= 2000
    word and prefix consist only of lowercase English letters.
    At most 3 * 10^4 calls in total will be made to insert, search, and startsWith.
"""
class Trie:

    _alphabet = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
                 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z']

    class Node:
        def __init__(self):
            self.children = [None]*len(Trie._alphabet)
            self.terminal = False

    def __init__(self):
        self._root = self.Node()

    def insert(self, word: str) -> None:
        p = self._root

        for s in word:
            i = ord(s)-ord('a')
            if not p.children[i]:
                c = self.Node()
                p.children[i] = c
                p = c
            else:
                p = p.children[i]

        p.terminal = True

    def search(self, word: str) -> bool:
        p = self._root

        for s in word:
            i = ord(s) - ord('a')
            if not p.children[i]:
                return False
            else:
                p = p.children[i]

        return p.terminal

    def startsWith(self, prefix: str) -> bool:
        p = self._root

        for s in prefix:
            i = ord(s) - ord('a')
            if not p.children[i]:
                return False
            else:
                p = p.children[i]

        return True


# without additional structure and pointers: faster and occupies less space
class Trie2:

    def __init__(self):
        self.root = {}

    def insert(self, word: str) -> None:

        cur = self.root

        for letter in word:
            if letter not in cur:
                cur[letter] = {}
            cur = cur[letter]

        cur['*'] = ''

    def search(self, word: str) -> bool:

        cur = self.root
        for letter in word:
            if letter not in cur:
                return False
            cur = cur[letter]

        return '*' in cur

    def startsWith(self, prefix: str) -> bool:

        cur = self.root
        for letter in prefix:
            if letter not in cur:
                return False
            cur = cur[letter]

        return True


if __name__ == '__main__':
    t = Trie()
    assert not t.insert('apple')
    assert t.search('apple')
    assert not t.insert('app')
    assert t.startsWith('app')
    assert not t.insert('app')
    assert t.search('app')
    assert not t.insert('prefix')

    actions = ["insert","startsWith","search","insert","startsWith","search","insert",
               "startsWith","search","insert","startsWith","search","insert","startsWith",
               "search","insert","startsWith","search"]
    data = [["p"],["pr"],["p"],["pr"],["pre"],["pr"],["pre"],["pre"],["pre"],
            ["pref"],["pref"],["pref"],["prefi"],["pref"],["prefi"],["prefix"],
            ["prefi"],["prefix"]]

    t = Trie()
    for i, a in enumerate(actions):
        print(a, data[i][0])
        getattr(t, a)(data[i][0])
