def e(key, pos, cefr, cat, de, ru, uk, es, fr, it, pt, zh, ja, ko, en=None):
    return {
        "key": key, "pos": pos, "cefr": cefr, "cat": cat,
        "trans": {
            "de": de, "ru": ru, "uk": uk, "es": es, "fr": fr,
            "it": it, "pt": pt, "zh": zh, "ja": ja, "ko": ko, "en": en or key
        }
    }

sample = [
    e("to speak", "verb", "A1", "Action Verbs", "sprechen", "говорить", "говорити", "hablar", "parler", "parlare", "falar", "说话", "話す", "말하다"),
    e("water", "noun", "A1", "Everyday Life", "das Wasser", "вода", "вода", "el agua", "l'eau", "l'acqua", "a água", "水", "水", "물"),
    e("decision", "noun", "B1", "Thoughts & Emotions", "die Entscheidung", "решение", "рішення", "la decisión", "la décision", "la decisione", "a decisão", "决定", "決定", "결정"),
]
print("Loaded concise sample entries:", len(sample))
for s in sample:
    print(s["key"], "-> DE:", s["trans"]["de"], "RU:", s["trans"]["ru"], "UK:", s["trans"]["uk"])
