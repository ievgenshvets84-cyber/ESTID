def make_entry(key, pos, cefr, cat, de, ru, uk, es, fr, it, pt, zh, ja, ko, en=None):
    if en is None:
        en = key
    return {
        "key": key,
        "pos": pos,
        "cefr": cefr,
        "cat": cat,
        "trans": {
            "de": de, "ru": ru, "uk": uk, "es": es, "fr": fr,
            "it": it, "pt": pt, "zh": zh, "ja": ja, "ko": ko, "en": en
        }
    }

e = make_entry("to speak", "verb", "A1", "Action Verbs",
               "sprechen", "говорить", "говорити", "hablar", "parler",
               "parlare", "falar", "说话", "話す", "말하다")
print("Sample entry:", e)
