import os
import sys

from vocab_a1 import A1_WORDS
from vocab_a2 import A2_WORDS
from vocab_b1 import B1_WORDS
from vocab_b2_c1_c2 import B2_WORDS, C1_WORDS, C2_WORDS
from vocab_expansion import EXPANSION_WORDS
from vocab_500_final import FINAL_WORDS

all_words = []
seen_keys = set()

# Combine in level order
for w_list in [A1_WORDS, A2_WORDS, B1_WORDS, B2_WORDS, C1_WORDS, C2_WORDS, EXPANSION_WORDS, FINAL_WORDS]:
    for item in w_list:
        k = item["key"].strip().lower()
        if k not in seen_keys:
            seen_keys.add(k)
            all_words.append(item)

print(f"Total unique consolidated words: {len(all_words)}")
