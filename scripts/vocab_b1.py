from vocab_a1 import e

B1_WORDS = [
    # Verbs
    e("to decide", "verb", "B1", "Thoughts & Emotions", "entscheiden", "решать", "вирішувати", "decidir", "décider", "decidere", "decidir", "决定", "決める", "결정하다"),
    e("to develop", "verb", "B1", "Action Verbs", "entwickeln", "развивать", "розвивати", "desarrollar", "développer", "sviluppare", "desenvolver", "发展", "発展させる", "발전시키다"),
    e("to achieve", "verb", "B1", "Action Verbs", "erreichen", "достигать", "досягати", "lograr", "atteindre", "raggiungere", "alcançar", "达到", "達成する", "달성하다"),
    e("to support", "verb", "B1", "Society & Culture", "unterstützen", "поддерживать", "підтримувати", "apoyar", "soutenir", "supportare", "apoiar", "支持", "支える", "지지하다"),
    e("to consider", "verb", "B1", "Thoughts & Emotions", "überlegen", "рассматривать", "розглядати", "considerar", "considérer", "considerare", "considerar", "考虑", "考慮する", "고려하다"),
    e("to suggest", "verb", "B1", "Action Verbs", "vorschlagen", "предлагать", "пропонувати", "sugerir", "suggérer", "suggerire", "sugerir", "建议", "提案する", "제안하다"),
    e("to compare", "verb", "B1", "Action Verbs", "vergleichen", "сравнивать", "порівнювати", "comparar", "comparer", "confrontare", "comparar", "比较", "比較する", "비교하다"),
    e("to agree", "verb", "B1", "Thoughts & Emotions", "zustimmen", "соглашаться", "погоджуватися", "estar de acuerdo", "être d'accord", "essere d'accordo", "concordar", "同意", "同意する", "동의하다"),
    e("to discuss", "verb", "B1", "Society & Culture", "diskutieren", "обсуждать", "обговорювати", "discutir", "discuter", "discutere", "discutir", "讨论", "議論する", "토론하다"),
    e("to avoid", "verb", "B1", "Action Verbs", "vermeiden", "избегать", "уникати", "evitar", "éviter", "evitare", "evitar", "避免", "避ける", "피하다"),
    e("to improve", "verb", "B1", "Action Verbs", "verbessern", "улучшать", "покращувати", "mejorar", "améliorer", "migliorare", "melhorar", "改进", "改善する", "개선하다"),
    e("to protect", "verb", "B1", "Nature & Environment", "schützen", "защищать", "захищати", "proteger", "protéger", "proteggere", "proteger", "保护", "守る", "보호하다"),
    e("to discover", "verb", "B1", "Travel & Exploration", "entdecken", "открывать", "відкривати", "descubrir", "découvrir", "scoprire", "descobrir", "发现", "発見する", "발견하다"),
    e("to expect", "verb", "B1", "Thoughts & Emotions", "erwarten", "ожидать", "очікувати", "esperar", "attendre", "aspettarsi", "esperar", "期待", "期待する", "기대하다"),
    e("to participate", "verb", "B1", "Society & Culture", "teilnehmen", "участвовать", "брати участь", "participar", "participer", "partecipare", "participar", "参加", "参加する", "참여하다"),
    e("to manage", "verb", "B1", "Professional & Tech", "verwalten", "руководить", "керувати", "gestionar", "gérer", "gestire", "gerenciar", "管理", "管理する", "관리하다"),
    e("to organize", "verb", "B1", "Professional & Tech", "organisieren", "организовывать", "організовувати", "organizar", "organiser", "organizzare", "organizar", "组织", "組織する", "조직하다"),
    e("to allow", "verb", "B1", "Society & Culture", "erlauben", "разрешать", "дозволяти", "permitir", "permettre", "permettere", "permitir", "允许", "許可する", "허용하다"),
    e("to share", "verb", "B1", "Society & Culture", "teilen", "делиться", "ділитися", "compartir", "partager", "condividere", "compartilhar", "分享", "共有する", "공유하다"),
    e("to trust", "verb", "B1", "Thoughts & Emotions", "vertrauen", "доверять", "довіряти", "confiar", "faire confiance", "fidarsi", "confiar", "信任", "信頼する", "신뢰하다"),
    e("to appreciate", "verb", "B1", "Thoughts & Emotions", "schätzen", "ценить", "цінувати", "apreciar", "apprécier", "apprezzare", "apreciar", "欣赏", "感謝する", "인정하다"),

    # Nouns
    e("decision", "noun", "B1", "Thoughts & Emotions", "die Entscheidung", "решение", "рішення", "la decisión", "la décision", "la decisione", "a decisão", "决定", "決定", "결정"),
    e("development", "noun", "B1", "Professional & Tech", "die Entwicklung", "развитие", "розвиток", "el desarrollo", "le développement", "lo sviluppo", "o desenvolvimento", "发展", "開発", "개발"),
    e("experience", "noun", "B1", "Thoughts & Emotions", "die Erfahrung", "опыт", "досвід", "la experiencia", "l'expérience", "l'esperienza", "a experiência", "经验", "経験", "경험"),
    e("relationship", "noun", "B1", "Society & Culture", "die Beziehung", "отношения", "відносини", "la relación", "la relation", "la relazione", "o relacionamento", "关系", "関係", "관계"),
    e("possibility", "noun", "B1", "Thoughts & Emotions", "die Möglichkeit", "возможность", "можливість", "la posibilidad", "la possibilité", "la possibilità", "a possibilidade", "可能性", "可能性", "가능성"),
    e("opinion", "noun", "B1", "Thoughts & Emotions", "die Meinung", "мнение", "думка", "la opinión", "l'avis", "l'opinione", "a opinião", "意见", "意見", "의견"),
    e("goal", "noun", "B1", "Thoughts & Emotions", "das Ziel", "цель", "мета", "el objetivo", "l'objectif", "l'obiettivo", "o objetivo", "目标", "目標", "목표"),
    e("reason", "noun", "B1", "Thoughts & Emotions", "der Grund", "причина", "причина", "la razón", "la raison", "il motivo", "a razão", "原因", "理由", "이유"),
    e("result", "noun", "B1", "Professional & Tech", "das Ergebnis", "результат", "результат", "el resultado", "le résultat", "il risultato", "o resultado", "结果", "結果", "결과"),
    e("future", "noun", "B1", "Everyday Life", "die Zukunft", "будущее", "майбутнє", "el futuro", "l'avenir", "il futuro", "o futuro", "未来", "未来", "미래"),
    e("culture", "noun", "B1", "Society & Culture", "die Kultur", "культура", "культура", "la cultura", "la culture", "la cultura", "a cultura", "文化", "文化", "문화"),
    e("economy", "noun", "B1", "Professional & Tech", "die Wirtschaft", "экономика", "економіка", "la economía", "l'économie", "l'economia", "a economia", "经济", "経済", "경제"),
    e("education", "noun", "B1", "Society & Culture", "die Bildung", "образование", "освіта", "la educación", "l'éducation", "l'istruzione", "a educação", "教育", "教育", "교육"),
    e("success", "noun", "B1", "Professional & Tech", "der Erfolg", "успех", "успіх", "el éxito", "le succès", "il successo", "o sucesso", "成功", "成功", "성공"),
    e("society", "noun", "B1", "Society & Culture", "die Gesellschaft", "общество", "суспільство", "la sociedad", "la société", "la società", "a sociedade", "社会", "社会", "사회"),
    e("truth", "noun", "B1", "Thoughts & Emotions", "die Wahrheit", "правда", "правда", "la verdad", "la vérité", "la verità", "a verdade", "真理", "真実", "진실"),
    e("advantage", "noun", "B1", "Everyday Life", "der Vorteil", "преимущество", "перевага", "la ventaja", "l'avantage", "il vantaggio", "a vantagem", "优势", "利点", "장점"),
    e("gratitude", "noun", "B1", "Thoughts & Emotions", "die Dankbarkeit", "благодарность", "вдячність", "la gratitud", "la gratitude", "la gratitudine", "a gratidão", "感激", "感謝", "감사"),
    e("freedom", "noun", "B1", "Society & Culture", "die Freiheit", "свобода", "свобода", "la libertad", "la liberté", "la libertà", "a liberdade", "自由", "自由", "자유"),
    e("knowledge", "noun", "B1", "Thoughts & Emotions", "das Wissen", "знания", "знання", "el conocimiento", "la connaissance", "la conoscenza", "o conhecimento", "知识", "知識", "지식"),
    e("challenge", "noun", "B1", "Professional & Tech", "die Herausforderung", "вызов", "виклик", "el desafío", "le défi", "la sfida", "o desafio", "挑战", "挑戦", "도전"),

    # Adjectives
    e("necessary", "adjective", "B1", "Descriptions & Quality", "notwendig", "необходимый", "необхідний", "necesario", "nécessaire", "necessario", "necessário", "必要", "必要な", "필요한"),
    e("responsible", "adjective", "B1", "Thoughts & Emotions", "verantwortlich", "ответственный", "відповідальний", "responsable", "responsable", "responsabile", "responsável", "负责", "責任ある", "책임감 있는"),
    e("independent", "adjective", "B1", "Society & Culture", "unabhängig", "независимый", "незалежний", "independiente", "indépendant", "indipendente", "independente", "独立", "独立した", "독립적인"),
    e("patient", "adjective", "B1", "Thoughts & Emotions", "geduldig", "терпеливый", "терплячий", "paciente", "patient", "paziente", "paciente", "耐心", "忍耐強い", "인내심 있는"),
    e("flexible", "adjective", "B1", "Descriptions & Quality", "flexibel", "гибкий", "гнучкий", "flexible", "flexible", "flessibile", "flexível", "灵活", "柔軟な", "유연한"),
    e("honest", "adjective", "B1", "Thoughts & Emotions", "ehrlich", "честный", "чесний", "honesto", "honnête", "onesto", "honesto", "诚实", "正直な", "정직한"),
    e("creative", "adjective", "B1", "Descriptions & Quality", "kreativ", "творческий", "творчий", "creativo", "créatif", "creativo", "criativo", "有创意", "創造的な", "창의적인"),
    e("reliable", "adjective", "B1", "Descriptions & Quality", "zuverlässig", "надёжный", "надійний", "fiable", "fiable", "affidabile", "confiável", "可靠", "信頼できる", "신뢰할 수 있는"),
    e("calm", "adjective", "B1", "Thoughts & Emotions", "ruhig", "спокойный", "спокійний", "tranquilo", "calme", "calmo", "calmo", "平静", "穏やかな", "차분한"),
    e("brave", "adjective", "B1", "Thoughts & Emotions", "mutig", "смелый", "сміливий", "valiente", "courageux", "coraggioso", "corajoso", "勇敢", "勇敢な", "용감한"),

    # Adverbs
    e("certainly", "adverb", "B1", "Everyday Life", "sicherlich", "безусловно", "безумовно", "ciertamente", "certainement", "certamente", "certamente", "必定", "確かに", "분명히"),
    e("finally", "adverb", "B1", "Everyday Life", "schließlich", "наконец", "нарешті", "finalmente", "finalement", "finalmente", "finalmente", "终于", "ついに", "마침내"),
    e("however", "adverb", "B1", "Everyday Life", "jedoch", "однако", "проте", "sin embargo", "cependant", "tuttavia", "no entanto", "然而", "しかしながら", "그러나"),
    e("definitely", "adverb", "B1", "Everyday Life", "definitiv", "определённо", "безумовно", "definitivamente", "définitivement", "decisamente", "definitivamente", "绝对", "絶対に", "확실히"),
    e("completely", "adverb", "B1", "Everyday Life", "völlig", "полностью", "повністю", "completamente", "complètement", "completamente", "completamente", "完全", "完全に", "완전히")
]

print("B1 Words count:", len(B1_WORDS))
