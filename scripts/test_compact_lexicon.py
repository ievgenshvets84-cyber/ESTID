import sys
import json
sys.path.append("scripts")
from vocab_a1 import A1_WORDS
from vocab_a2 import A2_WORDS
from vocab_b1 import B1_WORDS
from vocab_b2_c1_c2 import B2_WORDS, C1_WORDS, C2_WORDS
from vocab_expansion import EXPANSION_WORDS
from vocab_500_final import FINAL_WORDS

all_words = []
seen = set()
for w_list in [A1_WORDS, A2_WORDS, B1_WORDS, B2_WORDS, C1_WORDS, C2_WORDS, EXPANSION_WORDS, FINAL_WORDS]:
    for item in w_list:
        k = item["key"].strip().lower()
        if k not in seen:
            seen.add(k)
            all_words.append(item)

print("Total words:", len(all_words))

# Format each entry: key|pos|cefr|cat|de:val,en:val,...
encoded_lines = []
for item in all_words:
    k = item["key"].strip()
    pos = item.get("pos", "noun")
    cefr = item.get("cefr", "A1")
    cat = item.get("cat", "General")
    trans_pairs = [f"{lang}:{val.strip()}" for lang, val in sorted(item["trans"].items())]
    trans_str = ";".join(trans_pairs)
    encoded = f"{k}|{pos}|{cefr}|{cat}|{trans_str}"
    encoded_lines.append(encoded)

print("Sample line:")
print(encoded_lines[0])
print(encoded_lines[10])
print("Total characters:", sum(len(x) for x in encoded_lines))
