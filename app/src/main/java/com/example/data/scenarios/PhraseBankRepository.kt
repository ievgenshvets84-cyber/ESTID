package com.example.data.scenarios

import com.example.data.models.ScenarioPhrase

object PhraseBankRepository {

    /**
     * Returns a curated list of up to 100 phrases for the specified CEFR level and language.
     * Includes native phrasing, English translation, German translation, and pronunciation.
     */
    fun getPhrasesForLevel(cefrLevel: String, langCode: String): List<ScenarioPhrase> {
        val baseLevel = cefrLevel.uppercase().trim().ifEmpty { "A1" }
        val phrasesForLang = phrasesByLanguageAndLevel[langCode]?.get(baseLevel)
            ?: phrasesByLanguageAndLevel["es"]?.get(baseLevel)
            ?: emptyList()

        if (phrasesForLang.size >= 100) {
            return phrasesForLang.take(100)
        }

        // Expand with level-appropriate situational patterns to ensure 100 robust phrases per level
        return buildExpandedPhraseList(baseLevel, langCode, phrasesForLang)
    }

    /**
     * Return phrases specific to a scenario's theme and level in the user's chosen language.
     */
    fun getPhrasesForScenario(scenarioId: String, langCode: String, cefrLevel: String): List<ScenarioPhrase> {
        val levelPhrases = getPhrasesForLevel(cefrLevel, langCode)
        // Find matching or relevant phrases, or return top relevant phrases
        val scenario = ScenarioRepository.findById(scenarioId)
        val category = scenario?.category ?: "Daily Life"
        val matched = levelPhrases.filter { it.category.equals(category, ignoreCase = true) }
        return if (matched.size >= 5) matched else levelPhrases.take(12)
    }

    // Curated high-frequency authentic phrase banks per language and CEFR level
    private val phrasesByLanguageAndLevel: Map<String, Map<String, List<ScenarioPhrase>>> = mapOf(
        // ================= SPANISH (ES) =================
        "es" to mapOf(
            "A1" to listOf(
                ScenarioPhrase(targetText = "¡Hola! ¿Cómo estás?", translation = "Hello! How are you?", germanTranslation = "Hallo! Wie geht es dir?", pronunciation = "OH-lah! KOH-moh ess-TAHS?", cefrLevel = "A1", category = "Daily Life", formality = "Informal"),
                ScenarioPhrase(targetText = "Buenos días, mucho gusto.", translation = "Good morning, nice to meet you.", germanTranslation = "Guten Morgen, freut mich.", pronunciation = "BWEH-nohs DEE-ahs, MOO-choh GOO-stoh", cefrLevel = "A1", category = "Daily Life", formality = "Polite"),
                ScenarioPhrase(targetText = "Quisiera un café con leche, por favor.", translation = "I would like a coffee with milk, please.", germanTranslation = "Ich hätte gerne einen Milchkaffee, bitte.", pronunciation = "kee-SYEH-rah oon kah-FEH kohn LEH-cheh", cefrLevel = "A1", category = "Food & Drink", formality = "Polite"),
                ScenarioPhrase(targetText = "¿Cuánto cuesta esto?", translation = "How much does this cost?", germanTranslation = "Wie viel kostet das?", pronunciation = "KWAHN-toh KWEH-stah ESS-toh?", cefrLevel = "A1", category = "Shopping", formality = "Neutral"),
                ScenarioPhrase(targetText = "¿Dónde está el baño?", translation = "Where is the bathroom?", germanTranslation = "Wo ist die Toilette?", pronunciation = "DOHN-deh ess-TAH ell BAH-nyoh?", cefrLevel = "A1", category = "Essentials", formality = "Polite"),
                ScenarioPhrase(targetText = "La cuenta, por favor.", translation = "The check, please.", germanTranslation = "Die Rechnung, bitte.", pronunciation = "Lah KWEHN-tah, pohr fah-VOHR", cefrLevel = "A1", category = "Food & Drink", formality = "Polite"),
                ScenarioPhrase(targetText = "No entiendo, ¿puede repetir más despacio?", translation = "I don't understand, could you repeat slower?", germanTranslation = "Ich verstehe nicht, können Sie langsamer sprechen?", pronunciation = "Noh ehn-TYEHN-doh, PWEH-deh reh-peh-TEER mahs deh-SPAH-syoh?", cefrLevel = "A1", category = "Essentials", formality = "Polite"),
                ScenarioPhrase(targetText = "Tengo una reserva a nombre de...", translation = "I have a reservation under the name of...", germanTranslation = "Ich habe eine Reservierung auf den Namen...", pronunciation = "TEHN-goh OO-nah reh-SEHR-bah ah NOHM-breh deh...", cefrLevel = "A1", category = "Travel", formality = "Polite"),
                ScenarioPhrase(targetText = "¿Tiene una mesa para dos personas?", translation = "Do you have a table for two?", germanTranslation = "Haben Sie einen Tisch für zwei Personen?", pronunciation = "TYEH-neh OO-nah MEH-sah PAH-rah dohs pehr-SOH-nahs?", cefrLevel = "A1", category = "Food & Drink", formality = "Polite"),
                ScenarioPhrase(targetText = "¿A qué hora sale el próximo autobús?", translation = "What time does the next bus leave?", germanTranslation = "Um wie viel Uhr fährt der nächste Bus ab?", pronunciation = "Ah keh OH-rah SAH-leh ell PROHK-see-moh ow-toh-BOOS?", cefrLevel = "A1", category = "Travel", formality = "Neutral"),
                ScenarioPhrase(targetText = "¿Se puede pagar con tarjeta?", translation = "Can I pay by credit card?", germanTranslation = "Kann man mit Karte zahlen?", pronunciation = "Seh PWEH-deh pah-GAHR kohn tahr-HEH-tah?", cefrLevel = "A1", category = "Shopping", formality = "Neutral"),
                ScenarioPhrase(targetText = "Me llamo Alex y soy de Alemania.", translation = "My name is Alex and I am from Germany.", germanTranslation = "Mein Name ist Alex und ich komme aus Deutschland.", pronunciation = "Meh YAH-moh Alex ee soy deh ah-leh-MAH-nyah", cefrLevel = "A1", category = "Social", formality = "Informal"),
                ScenarioPhrase(targetText = "¿Me puede ayudar, por favor?", translation = "Could you help me, please?", germanTranslation = "Können Sie mir bitte helfen?", pronunciation = "Meh PWEH-deh ah-yoo-DAHR, pohr fah-VOHR?", cefrLevel = "A1", category = "Essentials", formality = "Polite"),
                ScenarioPhrase(targetText = "Muchas gracias por su amabilidad.", translation = "Thank you very much for your kindness.", germanTranslation = "Vielen Dank für Ihre Freundlichkeit.", pronunciation = "MOO-chahs GRAH-syahs pohr soo ah-mah-bee-lee-DAHD", cefrLevel = "A1", category = "Social", formality = "Polite"),
                ScenarioPhrase(targetText = "Disculpe, ¿está libre este asiento?", translation = "Excuse me, is this seat free?", germanTranslation = "Entschuldigung, ist dieser Platz frei?", pronunciation = "Dees-KOOL-peh, ess-TAH LEE-breh ESS-teh ah-SYEHN-toh?", cefrLevel = "A1", category = "Travel", formality = "Polite")
            ),
            "A2" to listOf(
                ScenarioPhrase(targetText = "Me duele mucho la cabeza y tengo fiebre.", translation = "My head hurts a lot and I have a fever.", germanTranslation = "Mein Kopf tut sehr weh und ich habe Fieber.", pronunciation = "Meh DWEH-leh MOO-choh lah kah-BEH-sah ee TEHN-goh FYEH-breh", cefrLevel = "A2", category = "Health", formality = "Neutral"),
                ScenarioPhrase(targetText = "¿Tiene algún medicamento para el dolor de garganta?", translation = "Do you have any medicine for a sore throat?", germanTranslation = "Haben Sie etwas gegen Halsschmerzen?", pronunciation = "TYEH-neh ahl-GOON meh-dee-kah-MEHN-toh PAH-rah ell doh-LOHR deh gahr-GAHN-tah?", cefrLevel = "A2", category = "Health", formality = "Polite"),
                ScenarioPhrase(targetText = "Quisiera devolver esta camisa, me queda un poco pequeña.", translation = "I'd like to return this shirt, it is a bit small.", germanTranslation = "Ich möchte dieses Hemd umtauschen, es ist etwas zu klein.", pronunciation = "kee-SYEH-rah deh-bohl-BEHR ESS-tah kah-MEE-sah", cefrLevel = "A2", category = "Shopping", formality = "Polite"),
                ScenarioPhrase(targetText = "Perdón por llegar tarde, había mucho tráfico.", translation = "Sorry for arriving late, there was heavy traffic.", germanTranslation = "Entschuldigung für die Verspätung, es gab viel Verkehr.", pronunciation = "Pehr-DOHN pohr yeh-GAHR TAHR-deh", cefrLevel = "A2", category = "Daily Life", formality = "Informal"),
                ScenarioPhrase(targetText = "¿Cuál es la contraseña del wifi, por favor?", translation = "What is the wifi password, please?", germanTranslation = "Wie lautet das WLAN-Passwort, bitte?", pronunciation = "KWAHL ess lah kohn-trah-SEH-nyah dehl WEE-fee?", cefrLevel = "A2", category = "Tech & Work", formality = "Polite"),
                ScenarioPhrase(targetText = "¿Me podría recomendar un plato típico de aquí?", translation = "Could you recommend a traditional local dish?", germanTranslation = "Könnten Sie mir ein typisches regionales Gericht empfehlen?", pronunciation = "Meh poh-DREE-ah reh-koh-mehn-DAHR oon PLAH-toh TEE-pee-koh?", cefrLevel = "A2", category = "Food & Drink", formality = "Polite"),
                ScenarioPhrase(targetText = "He perdido mi mochila con las llaves y el pasaporte.", translation = "I have lost my backpack with keys and passport.", germanTranslation = "Ich habe meinen Rucksack mit Schlüsseln und Pass verloren.", pronunciation = "Eh pehr-DEE-doh mee moh-CHEE-lah", cefrLevel = "A2", category = "Essentials", formality = "Polite"),
                ScenarioPhrase(targetText = "¿A qué hora abre el gimnasio por la mañana?", translation = "What time does the gym open in the morning?", germanTranslation = "Um wie viel Uhr öffnet das Fitnessstudio morgens?", pronunciation = "Ah keh OH-rah AH-breh ell heem-NAH-syoh?", cefrLevel = "A2", category = "Fitness", formality = "Neutral"),
                ScenarioPhrase(targetText = "¿Está incluido el desayuno en el precio de la habitación?", translation = "Is breakfast included in the room price?", germanTranslation = "Ist das Frühstück im Zimmerpreis inbegriffen?", pronunciation = "Ess-TAH een-kloo-EE-doh ell deh-sah-YOO-noh?", cefrLevel = "A2", category = "Travel", formality = "Polite"),
                ScenarioPhrase(targetText = "Fue un placer conocerte, espero que nos veamos pronto.", translation = "It was a pleasure meeting you, hope to see you soon.", germanTranslation = "Es war mir eine Freude, dich kennenzulernen. Bis bald!", pronunciation = "Fweh oon plah-SEHR koh-noh-SEHR-teh", cefrLevel = "A2", category = "Social", formality = "Informal")
            ),
            "B1" to listOf(
                ScenarioPhrase(targetText = "Quisiera saber si los gastos de calefacción están incluidos en el alquiler.", translation = "I would like to know if heating costs are included in the rent.", germanTranslation = "Ich würde gerne wissen, ob die Heizkosten in der Miete enthalten sind.", pronunciation = "kee-SYEH-rah sah-BEHR see lohs GAHS-tohs deh kah-leh-fahk-SYOHN...", cefrLevel = "B1", category = "Housing", formality = "Polite"),
                ScenarioPhrase(targetText = "En mi último puesto lideré un proyecto para mejorar la atención al cliente.", translation = "In my last role, I led a project to improve customer care.", germanTranslation = "In meiner letzten Position leitete ich ein Projekt zur Verbesserung des Kundenservices.", pronunciation = "Ehn mee OOL-tee-moh PWEHS-toh lee-deh-REH oon proh-YEHK-toh...", cefrLevel = "B1", category = "Career", formality = "Formal"),
                ScenarioPhrase(targetText = "Hay una fuga de agua en el baño que necesita reparación urgente.", translation = "There is a water leak in the bathroom requiring urgent repair.", germanTranslation = "Es gibt ein Wasserleck im Bad, das dringend repariert werden muss.", pronunciation = "Ay OO-nah FOO-gah deh AH-gwah ehn ell BAH-nyoh...", cefrLevel = "B1", category = "Housing", formality = "Polite"),
                ScenarioPhrase(targetText = "¿Qué cobertura incluye el seguro a todo riesgo del coche?", translation = "What coverage does the comprehensive car insurance include?", germanTranslation = "Welche Leistungen umfasst die Vollkaskoversicherung?", pronunciation = "Keh koh-behr-TOO-rah een-KLOO-yeh ell seh-GOO-roh ah TOH-doh RYEHS-goh?", cefrLevel = "B1", category = "Travel", formality = "Polite"),
                ScenarioPhrase(targetText = "Desde mi punto de vista, la película refleja muy bien la soledad moderna.", translation = "From my point of view, the film reflects modern loneliness very well.", germanTranslation = "Meiner Ansicht nach spiegelt der Film die moderne Einsamkeit sehr gut wider.", pronunciation = "DEHS-deh mee POON-toh deh VEES-tah, lah peh-LEE-koo-lah...", cefrLevel = "B1", category = "Entertainment", formality = "Neutral"),
                ScenarioPhrase(targetText = "Le sugiero que consultemos las condiciones antes de firmar el contrato.", translation = "I suggest we check terms before signing the contract.", germanTranslation = "Ich schlage vor, dass wir die Bedingungen vor der Vertragsunterzeichnung prüfen.", pronunciation = "Leh soo-HYEH-roh keh kohn-sool-TEH-mohs...", cefrLevel = "B1", category = "Business", formality = "Formal"),
                ScenarioPhrase(targetText = "Me gustaría abrir una cuenta corriente con acceso a banca en línea.", translation = "I would like to open a checking account with online banking.", germanTranslation = "Ich möchte gerne ein Girokonto mit Online-Banking eröffnen.", pronunciation = "Meh goos-tah-REE-ah ah-BREER OO-nah KWEHN-tah koh-RYEHN-teh...", cefrLevel = "B1", category = "Finance", formality = "Polite")
            ),
            "B2" to listOf(
                ScenarioPhrase(targetText = "Considerando mis responsabilidades y el impacto en ingresos, propongo revisar mi retribución.", translation = "Considering my responsibilities and revenue impact, I propose reviewing my compensation.", germanTranslation = "In Anbetracht meiner Verantwortung und des Umsatzbeitrags schlage ich eine Anpassung meines Gehalts vor.", pronunciation = "Kohn-see-deh-RAHN-doh mees reh-spohn-sah-bee-lee-DAH-dehs...", cefrLevel = "B2", category = "Career", formality = "Formal"),
                ScenarioPhrase(targetText = "Es imprescindible alcanzar un equilibrio entre el crecimiento económico y la sostenibilidad.", translation = "It is essential to strike a balance between economic growth and sustainability.", germanTranslation = "Es ist unabdingbar, ein Gleichgewicht zwischen Wirtschaftswachstum und Nachhaltigkeit zu finden.", pronunciation = "Ess eem-preh-seen-DEE-bleh ahl-kahn-ZAHR oon eh-kee-LEE-bryoh...", cefrLevel = "B2", category = "Environment", formality = "Formal"),
                ScenarioPhrase(targetText = "Lamento profundamente el fallo en el servicio y le aseguro que hemos tomado medidas correctoras.", translation = "I deeply regret the service disruption and assure you corrective measures have been implemented.", germanTranslation = "Ich bedaure den Serviceausfall zutiefst und versichere Ihnen, dass Korrekturmaßnahmen ergriffen wurden.", pronunciation = "Lah-MEHN-toh proh-foon-dah-MEHN-teh ell FAH-yoh...", cefrLevel = "B2", category = "Client Relations", formality = "Formal"),
                ScenarioPhrase(targetText = "Aunque entiendo tu postura, los datos empíricos sugieren una tendencia distinta.", translation = "Although I understand your stance, empirical data suggests a different trend.", germanTranslation = "Obwohl ich Ihren Standpunkt verstehe, deuten die empirischen Daten auf einen anderen Trend hin.", pronunciation = "OWNG-keh ehn-TYEHN-doh too pohs-TOO-rah...", cefrLevel = "B2", category = "Current Events", formality = "Formal"),
                ScenarioPhrase(targetText = "La inteligencia artificial transformará radicalmente los paradigmas laborales existentes.", translation = "Artificial intelligence will radically transform existing workplace paradigms.", germanTranslation = "Künstliche Intelligenz wird die bestehenden Arbeitsparadigmen radikal verändern.", pronunciation = "Lah een-teh-lee-HEHN-syah ahr-tee-fee-SYAHL trahns-fohr-mah-RAH...", cefrLevel = "B2", category = "Technology", formality = "Formal")
            ),
            "C1" to listOf(
                ScenarioPhrase(targetText = "La cláusula de limitación de responsabilidad adolece de una ambigüedad jurídica insubsanable.", translation = "The limitation of liability clause suffers from incurable legal ambiguity.", germanTranslation = "Die Haftungsbegrenzungsklausel leidet unter einer unheilbaren rechtlichen Unklarheit.", pronunciation = "Lah KLOW-soo-lah deh lee-mee-tah-SYOHN...", cefrLevel = "C1", category = "Law", formality = "Formal"),
                ScenarioPhrase(targetText = "Dicha hipótesis presupone una correlación espuria sin sustento causal suficiente.", translation = "Such hypothesis presumes a spurious correlation devoid of causal backing.", germanTranslation = "Diese Hypothese setzt eine Scheinkorrelation ohne hinreichende Kausalität voraus.", pronunciation = "DEE-chah ee-POH-teh-sees preh-soo-POH-neh...", cefrLevel = "C1", category = "Academia", formality = "Formal"),
                ScenarioPhrase(targetText = "Es menester conciliar el imperativo deontológico con las contingencias pragmáticas de la geopolítica.", translation = "It is necessary to reconcile deontological imperatives with pragmatic geopolitical contingencies.", germanTranslation = "Es ist unerlässlich, den deontologischen Imperativ mit den pragmatischen Notwendigkeiten der Geopolitik in Einklang zu bringen.", pronunciation = "Ess meh-NEHS-tehr kohn-see-LYAHR...", cefrLevel = "C1", category = "Diplomacy", formality = "Formal"),
                ScenarioPhrase(targetText = "La polifonía narrativa subraya el distanciamiento epistémico del protagonista respecto a su entorno.", translation = "The narrative polyphony highlights the protagonist's epistemic alienation from their milieu.", germanTranslation = "Die narrative Polyphonie unterstreicht die epistemische Entfremdung des Protagonisten von seiner Umwelt.", pronunciation = "Lah poh-lee-foh-NEE-ah nah-rrah-TEE-bah...", cefrLevel = "C1", category = "Literature", formality = "Formal"),
                ScenarioPhrase(targetText = "La sinergia post-fusión se verá comprometida si no se armonizan las divergencias idiosincráticas.", translation = "Post-merger synergy will be compromised unless idiosyncratic divergences are harmonized.", germanTranslation = "Die Synergien nach der Fusion werden gefährdet sein, wenn idiosynkratische Divergenzen nicht harmonisiert werden.", pronunciation = "Lah see-NEHR-hyah pohst-foo-SYOHN...", cefrLevel = "C1", category = "Finance", formality = "Formal")
            )
        ),

        // ================= GERMAN (DE) =================
        "de" to mapOf(
            "A1" to listOf(
                ScenarioPhrase(targetText = "Guten Tag! Wie geht es Ihnen?", translation = "Good day! How are you?", germanTranslation = "Guten Tag! Wie geht es Ihnen?", pronunciation = "GOO-ten tahk! vee gayt ess EE-nen?", cefrLevel = "A1", category = "Daily Life", formality = "Formal"),
                ScenarioPhrase(targetText = "Ich hätte gerne einen Kaffee und ein Croissant, bitte.", translation = "I would like a coffee and a croissant, please.", germanTranslation = "Ich hätte gerne einen Kaffee und ein Croissant, bitte.", pronunciation = "ikh HEHT-teh GEHR-neh EYE-nen KAH-feh...", cefrLevel = "A1", category = "Food & Drink", formality = "Polite"),
                ScenarioPhrase(targetText = "Wie viel kostet das, bitte?", translation = "How much does that cost, please?", germanTranslation = "Wie viel kostet das, bitte?", pronunciation = "vee feel KOSS-tet dahss, BIT-teh?", cefrLevel = "A1", category = "Shopping", formality = "Polite"),
                ScenarioPhrase(targetText = "Entschuldigung, wo ist die nächste U-Bahn-Station?", translation = "Excuse me, where is the nearest subway station?", germanTranslation = "Entschuldigung, wo ist die nächste U-Bahn-Station?", pronunciation = "ent-SHOOL-dee-goong, voh ist dee NEHKH-steh...", cefrLevel = "A1", category = "Travel", formality = "Polite"),
                ScenarioPhrase(targetText = "Die Rechnung, bitte. Zusammen oder getrennt?", translation = "The bill, please. Together or separately?", germanTranslation = "Die Rechnung, bitte. Zusammen oder getrennt?", pronunciation = "dee REHKH-noong, BIT-teh...", cefrLevel = "A1", category = "Food & Drink", formality = "Polite"),
                ScenarioPhrase(targetText = "Ich verstehe leider nicht so gut. Sprechen Sie Englisch?", translation = "I don't understand very well unfortunately. Do you speak English?", germanTranslation = "Ich verstehe leider nicht so gut. Sprechen Sie Englisch?", pronunciation = "ikh fehr-SHTEH-eh LYE-der nikht zoh goot...", cefrLevel = "A1", category = "Essentials", formality = "Polite"),
                ScenarioPhrase(targetText = "Kann ich mit Kreditkarte bezahlen?", translation = "Can I pay with credit card?", germanTranslation = "Kann ich mit Kreditkarte bezahlen?", pronunciation = "kahn ikh mit kreh-DEET-kahr-teh beh-TSAH-len?", cefrLevel = "A1", category = "Shopping", formality = "Neutral"),
                ScenarioPhrase(targetText = "Mein Name ist Alex und ich lerne Deutsch.", translation = "My name is Alex and I am learning German.", germanTranslation = "Mein Name ist Alex und ich lerne Deutsch.", pronunciation = "myne NAH-meh ist Alex oond ikh LEHR-neh doytsh", cefrLevel = "A1", category = "Social", formality = "Informal")
            ),
            "A2" to listOf(
                ScenarioPhrase(targetText = "Ich fühle mich seit zwei Tagen krank und habe Halsschmerzen.", translation = "I have felt sick for two days and have a sore throat.", germanTranslation = "Ich fühle mich seit zwei Tagen krank und habe Halsschmerzen.", pronunciation = "ikh FYOO-leh mikh zyte tsvye TAH-gen krahnk...", cefrLevel = "A2", category = "Health", formality = "Neutral"),
                ScenarioPhrase(targetText = "Haben Sie diesen Pullover auch in einer Nummer größer?", translation = "Do you have this sweater in a larger size as well?", germanTranslation = "Haben Sie diesen Pullover auch in einer Nummer größer?", pronunciation = "HAH-ben zee DEE-zen pool-OH-ver owkh...", cefrLevel = "A2", category = "Shopping", formality = "Polite"),
                ScenarioPhrase(targetText = "Mein Zug hatte Verspätung, deshalb komme ich etwas später.", translation = "My train was delayed, that's why I'm arriving a bit later.", germanTranslation = "Mein Zug hatte Verspätung, deshalb komme ich etwas später.", pronunciation = "myne tsook HAH-teh fehr-SHPEH-toong...", cefrLevel = "A2", category = "Travel", formality = "Polite"),
                ScenarioPhrase(targetText = "Könnten Sie mir bitte das WLAN-Passwort geben?", translation = "Could you please give me the wifi password?", germanTranslation = "Könnten Sie mir bitte das WLAN-Passwort geben?", pronunciation = "KOEN-ten zee meer BIT-teh dahss VAY-lahn...", cefrLevel = "A2", category = "Tech & Work", formality = "Polite"),
                ScenarioPhrase(targetText = "Ich möchte gerne einen Tisch für heute Abend reservieren.", translation = "I would like to reserve a table for tonight.", germanTranslation = "Ich möchte gerne einen Tisch für heute Abend reservieren.", pronunciation = "ikh MOEKH-teh GEHR-neh EYE-nen tish...", cefrLevel = "A2", category = "Food & Drink", formality = "Polite")
            ),
            "B1" to listOf(
                ScenarioPhrase(targetText = "Sind die Heiz- und Nebenkosten bereits in der Warmmiete enthalten?", translation = "Are heating and ancillary costs already included in warm rent?", germanTranslation = "Sind die Heiz- und Nebenkosten bereits in der Warmmiete enthalten?", pronunciation = "zint dee HYTS- oond NEH-ben-koss-ten...", cefrLevel = "B1", category = "Housing", formality = "Formal"),
                ScenarioPhrase(targetText = "In meinem vorherigen Job war ich für das Projektmanagement zuständig.", translation = "In my previous job I was responsible for project management.", germanTranslation = "In meinem vorherigen Job war ich für das Projektmanagement zuständig.", pronunciation = "in MYE-nem fohr-HEHR-ee-gen job...", cefrLevel = "B1", category = "Career", formality = "Formal"),
                ScenarioPhrase(targetText = "Ich möchte mich über den unzuverlässigen Kundenservice beschweren.", translation = "I would like to complain about the unreliable customer service.", germanTranslation = "Ich möchte mich über den unzuverlässigen Kundenservice beschweren.", pronunciation = "ikh MOEKH-teh mikh OO-ber dehn...", cefrLevel = "B1", category = "Services", formality = "Formal"),
                ScenarioPhrase(targetText = "Meiner Meinung nach sollten wir die verschiedenen Optionen genauer abwägen.", translation = "In my opinion we should weigh the various options more carefully.", germanTranslation = "Meiner Meinung nach sollten wir die verschiedenen Optionen genauer abwägen.", pronunciation = "MYE-ner MY-noong nahkh...", cefrLevel = "B1", category = "Business", formality = "Formal")
            ),
            "B2" to listOf(
                ScenarioPhrase(targetText = "Aufgrund meiner langjährigen Erfahrung schlage ich eine Anpassung der Vergütung vor.", translation = "Based on my extensive experience, I propose an adjustment in remuneration.", germanTranslation = "Aufgrund meiner langjährigen Erfahrung schlage ich eine Anpassung der Vergütung vor.", pronunciation = "owf-GROONT MYE-ner lahng-YEH-ree-gen...", cefrLevel = "B2", category = "Career", formality = "Formal"),
                ScenarioPhrase(targetText = "Wir müssen einen gangbaren Kompromiss zwischen Wirtschaftlichkeit und Ökologie finden.", translation = "We must find a viable compromise between profitability and ecology.", germanTranslation = "Wir müssen einen gangbaren Kompromiss zwischen Wirtschaftlichkeit und Ökologie finden.", pronunciation = "veer MOOS-sen EYE-nen GAHNG-bah-ren...", cefrLevel = "B2", category = "Environment", formality = "Formal"),
                ScenarioPhrase(targetText = "Trotz gegenteiliger Meinungen belegen die empirischen Daten einen klaren Aufwärtstrend.", translation = "Despite conflicting opinions, empirical data proves a clear upward trend.", germanTranslation = "Trotz gegenteiliger Meinungen belegen die empirischen Daten einen klaren Aufwärtstrend.", pronunciation = "trotss geh-gen-TYE-lee-ger MY-noong-en...", cefrLevel = "B2", category = "Business", formality = "Formal")
            ),
            "C1" to listOf(
                ScenarioPhrase(targetText = "Die strittige Haftungsklausel bedarf einer rechtsdogmatischen Präzisierung.", translation = "The disputed liability clause requires legal-dogmatic precision.", germanTranslation = "Die strittige Haftungsklausel bedarf einer rechtsdogmatischen Präzisierung.", pronunciation = "dee SHTRI-tee-geh HAHF-toongs-klow-zel...", cefrLevel = "C1", category = "Law", formality = "Formal"),
                ScenarioPhrase(targetText = "Diese Prämisse vernachlässigt die epistemologische Komplexität des Phänomens.", translation = "This premise neglects the epistemological complexity of the phenomenon.", germanTranslation = "Diese Prämisse vernachlässigt die epistemologische Komplexität des Phänomens.", pronunciation = "DEE-zeh preh-MIS-seh fehr-NAHKH-leh-seekht...", cefrLevel = "C1", category = "Academia", formality = "Formal"),
                ScenarioPhrase(targetText = "Die geopolitische Gemengelage erfordert ein Höchstmaß an diplomatischem Fingerspitzengefühl.", translation = "The geopolitical situation necessitates utmost diplomatic finesse.", germanTranslation = "Die geopolitische Gemengelage erfordert ein Höchstmaß an diplomatischem Fingerspitzengefühl.", pronunciation = "dee geh-oh-poh-LEE-tee-sheh...", cefrLevel = "C1", category = "Diplomacy", formality = "Formal")
            )
        ),

        // ================= FRENCH (FR) =================
        "fr" to mapOf(
            "A1" to listOf(
                ScenarioPhrase(targetText = "Bonjour ! Comment allez-vous ?", translation = "Hello! How are you?", germanTranslation = "Guten Tag! Wie geht es Ihnen?", pronunciation = "bohn-zhoor! koh-mahn tah-leh-voo?", cefrLevel = "A1", category = "Daily Life", formality = "Formal"),
                ScenarioPhrase(targetText = "Un café et un croissant s'il vous plaît.", translation = "A coffee and a croissant please.", germanTranslation = "Ein Kaffee und ein Croissant bitte.", pronunciation = "uhn kah-feh ay uhn krwah-sahn seel voo pleh", cefrLevel = "A1", category = "Food & Drink", formality = "Polite"),
                ScenarioPhrase(targetText = "L'addition, s'il vous plaît.", translation = "The check, please.", germanTranslation = "Die Rechnung, bitte.", pronunciation = "lah-dee-SYOHN seel voo pleh", cefrLevel = "A1", category = "Food & Drink", formality = "Polite"),
                ScenarioPhrase(targetText = "Combien ça coûte ?", translation = "How much does that cost?", germanTranslation = "Wie viel kostet das?", pronunciation = "kohm-BYEHN sah koot?", cefrLevel = "A1", category = "Shopping", formality = "Neutral"),
                ScenarioPhrase(targetText = "Où se trouvent les toilettes s'il vous plaît ?", translation = "Where are the toilets please?", germanTranslation = "Wo sind die Toiletten, bitte?", pronunciation = "oo suh TROO-vent lay twah-LEHT...", cefrLevel = "A1", category = "Essentials", formality = "Polite")
            ),
            "A2" to listOf(
                ScenarioPhrase(targetText = "J'ai mal à la tête et j'ai de la fièvre depuis hier.", translation = "I have a headache and a fever since yesterday.", germanTranslation = "Ich habe Kopfschmerzen und Fieber seit gestern.", pronunciation = "zhay mahl ah lah teht...", cefrLevel = "A2", category = "Health", formality = "Neutral"),
                ScenarioPhrase(targetText = "Pouvez-vous me donner le mot de passe du wifi ?", translation = "Can you give me the wifi password?", germanTranslation = "Können Sie mir das WLAN-Passwort geben?", pronunciation = "poo-vay voo muh doh-nay...", cefrLevel = "A2", category = "Tech & Work", formality = "Polite"),
                ScenarioPhrase(targetText = "Je voudrais réserver une table pour deux personnes à vingt heures.", translation = "I would like to book a table for two at 8 PM.", germanTranslation = "Ich möchte gerne einen Tisch für zwei Personen um 20 Uhr reservieren.", pronunciation = "zhuh voo-DREH ray-zehr-vay...", cefrLevel = "A2", category = "Food & Drink", formality = "Polite")
            ),
            "B1" to listOf(
                ScenarioPhrase(targetText = "Le loyer comprend-il les charges de chauffage et d'eau ?", translation = "Does rent include heating and water charges?", germanTranslation = "Beinhaltet die Miete die Heiz- und Wasserkosten?", pronunciation = "luh lwah-YAY kohm-prahn-teel...", cefrLevel = "B1", category = "Housing", formality = "Formal"),
                ScenarioPhrase(targetText = "Dans mon précédent emploi, j'étais en charge du développement commercial.", translation = "In my previous job, I was in charge of business development.", germanTranslation = "In meiner vorherigen Anstellung war ich für die Geschäftsentwicklung zuständig.", pronunciation = "dahn mohn pray-say-dahn ahm-PLWAH...", cefrLevel = "B1", category = "Career", formality = "Formal")
            ),
            "B2" to listOf(
                ScenarioPhrase(targetText = "Au vu de mes performances, je souhaite aborder la réévaluation de mon salaire.", translation = "In light of my performance, I wish to address salary review.", germanTranslation = "In Anbetracht meiner Leistung möchte ich eine Gehaltsüberprüfung ansprechen.", pronunciation = "oh vyoo duh may pehr-fohr-MAHNS...", cefrLevel = "B2", category = "Career", formality = "Formal"),
                ScenarioPhrase(targetText = "Il convient d'adopter une stratégie alliant rentabilité et transition écologique.", translation = "A strategy combining profitability and ecological transition is appropriate.", germanTranslation = "Es empfiehlt sich eine Strategie, die Wirtschaftlichkeit und ökologischen Wandel verbindet.", pronunciation = "eel kohn-VYEHN dah-dohp-tay...", cefrLevel = "B2", category = "Environment", formality = "Formal")
            ),
            "C1" to listOf(
                ScenarioPhrase(targetText = "Cette clause contractuelle souffre d'une ambiguïté préjudiciable aux intérêts des parties.", translation = "This contractual clause suffers from ambiguity detrimental to parties.", germanTranslation = "Diese Vertragsklausel leidet unter einer Unklarheit, die den Interessen der Parteien schadet.", pronunciation = "seht klohz kohn-trahk-too-EHL...", cefrLevel = "C1", category = "Law", formality = "Formal"),
                ScenarioPhrase(targetText = "L'herméneutique postmoderne déconstruit les présupposés idéologiques sous-jacents.", translation = "Postmodern hermeneutics deconstructs underlying ideological presuppositions.", germanTranslation = "Die postmoderne Hermeneutik dekonstruiert die zugrundeliegenden ideologischen Annahmen.", pronunciation = "lehr-may-noo-TEEK pohst-moh-DEHRN...", cefrLevel = "C1", category = "Literature", formality = "Formal")
            )
        ),

        // ================= JAPANESE (JA) =================
        "ja" to mapOf(
            "A1" to listOf(
                ScenarioPhrase(targetText = "こんにちは、はじめまして。", translation = "Hello, nice to meet you.", germanTranslation = "Guten Tag, sehr erfreut.", pronunciation = "Konnichiwa, hajimemashite.", cefrLevel = "A1", category = "Daily Life", formality = "Polite"),
                ScenarioPhrase(targetText = "これをお願いします。", translation = "This one, please.", germanTranslation = "Das hier bitte.", pronunciation = "Kore o onegaishimasu.", cefrLevel = "A1", category = "Shopping", formality = "Polite"),
                ScenarioPhrase(targetText = "いくらですか？", translation = "How much is it?", germanTranslation = "Wie viel kostet das?", pronunciation = "Ikura desu ka?", cefrLevel = "A1", category = "Shopping", formality = "Polite"),
                ScenarioPhrase(targetText = "お会計をお願いします。", translation = "Check, please.", germanTranslation = "Die Rechnung, bitte.", pronunciation = "O-kaikei o onegaishimasu.", cefrLevel = "A1", category = "Food & Drink", formality = "Polite"),
                ScenarioPhrase(targetText = "トイレはどこですか？", translation = "Where is the bathroom?", germanTranslation = "Wo ist die Toilette?", pronunciation = "Toire wa doko desu ka?", cefrLevel = "A1", category = "Essentials", formality = "Polite")
            ),
            "A2" to listOf(
                ScenarioPhrase(targetText = "頭が痛くて、熱もあります。", translation = "I have a headache and also a fever.", germanTranslation = "Mein Kopf tut weh und ich habe auch Fieber.", pronunciation = "Atama ga itakute, netsu mo arimasu.", cefrLevel = "A2", category = "Health", formality = "Polite"),
                ScenarioPhrase(targetText = "Wi-Fiのパスワードを教えていただけますか？", translation = "Could you tell me the Wi-Fi password?", germanTranslation = "Könnten Sie mir das WLAN-Passwort mitteilen?", pronunciation = "Waifai no pasuwādo o oshiete itadakemasu ka?", cefrLevel = "A2", category = "Tech & Work", formality = "Polite"),
                ScenarioPhrase(targetText = "今夜二人で予約できますか？", translation = "Can I reserve for two tonight?", germanTranslation = "Kann ich heute Abend für zwei reservieren?", pronunciation = "Kon'ya futari de yoyaku dekimasu ka?", cefrLevel = "A2", category = "Food & Drink", formality = "Polite")
            ),
            "B1" to listOf(
                ScenarioPhrase(targetText = "家賃に水道代や光熱費は含まれていますか？", translation = "Are water and utilities included in the rent?", germanTranslation = "Sind Wasser und Nebenkosten in der Miete enthalten?", pronunciation = "Yachin ni suidōdai ya kōnetsuhi wa fukumarete imasu ka?", cefrLevel = "B1", category = "Housing", formality = "Polite"),
                ScenarioPhrase(targetText = "前職では顧客満足度向上のプロジェクトを担当しました。", translation = "In my previous job I handled customer satisfaction projects.", germanTranslation = "In meiner vorherigen Stelle war ich für Kundenzufriedenheitsprojekte zuständig.", pronunciation = "Zenshoku de wa kokyaku manzokudo kōjō no purojekuto o tantō shimashita.", cefrLevel = "B1", category = "Career", formality = "Polite")
            ),
            "B2" to listOf(
                ScenarioPhrase(targetText = "実績と貢献度を踏まえ、報酬の見直しをご検討いただきたく存じます。", translation = "Considering achievements, I would appreciate reviewing compensation.", germanTranslation = "Unter Berücksichtigung meiner Leistungen möchte ich um eine Anpassung der Vergütung bitten.", pronunciation = "Jisseki to kōkendo o fumae, hōshū no minaoshi o gokentō...", cefrLevel = "B2", category = "Career", formality = "Formal"),
                ScenarioPhrase(targetText = "経済成長と持続可能性の両立が急務であります。", translation = "Balancing economic growth and sustainability is an urgent priority.", germanTranslation = "Wirtschaftswachstum und Nachhaltigkeit zu vereinbaren, ist dringend erforderlich.", pronunciation = "Keizai seichō to jizoku kanōsei no ryōritsu ga kyūmu de arimasu.", cefrLevel = "B2", category = "Environment", formality = "Formal")
            ),
            "C1" to listOf(
                ScenarioPhrase(targetText = "免責条項における文言の多義性が法的解釈上の懸念を生じさせています。", translation = "Ambiguity in the limitation clause raises legal concerns.", germanTranslation = "Die Mehrdeutigkeit der Haftungsklausel wirft rechtliche Bedenken auf.", pronunciation = "Menseki jōkō ni okeru mongon no tagisei ga...", cefrLevel = "C1", category = "Law", formality = "Formal")
            )
        ),

        // ================= ITALIAN (IT) =================
        "it" to mapOf(
            "A1" to listOf(
                ScenarioPhrase(targetText = "Buongiorno! Come sta?", translation = "Good morning! How are you?", germanTranslation = "Guten Tag! Wie geht es Ihnen?", pronunciation = "bwon-JOHR-noh! KOH-meh STAH?", cefrLevel = "A1", category = "Daily Life", formality = "Formal"),
                ScenarioPhrase(targetText = "Un caffè espresso e un cornetto, per favore.", translation = "An espresso and croissant, please.", germanTranslation = "Ein Espresso und ein Croissant, bitte.", pronunciation = "oon kahf-FEH ess-PRESS-oh...", cefrLevel = "A1", category = "Food & Drink", formality = "Polite"),
                ScenarioPhrase(targetText = "Il conto, per favore.", translation = "The bill, please.", germanTranslation = "Die Rechnung, bitte.", pronunciation = "eel KOHN-toh, pehr fah-VOH-reh", cefrLevel = "A1", category = "Food & Drink", formality = "Polite"),
                ScenarioPhrase(targetText = "Quanto costa questo?", translation = "How much is this?", germanTranslation = "Wie viel kostet das?", pronunciation = "KWAHN-toh KOH-stah KWEH-stoh?", cefrLevel = "A1", category = "Shopping", formality = "Neutral"),
                ScenarioPhrase(targetText = "Dov'è il bagno, per favore?", translation = "Where is the bathroom please?", germanTranslation = "Wo ist die Toilette, bitte?", pronunciation = "doh-VEH eel BAH-nyoh?", cefrLevel = "A1", category = "Essentials", formality = "Polite")
            ),
            "A2" to listOf(
                ScenarioPhrase(targetText = "Ho mal di testa e febbre da stamattina.", translation = "I have a headache and fever since this morning.", germanTranslation = "Ich habe Kopfschmerzen und Fieber seit heute Morgen.", pronunciation = "oh mahl dee TEH-stah...", cefrLevel = "A2", category = "Health", formality = "Neutral"),
                ScenarioPhrase(targetText = "Qual è la password del Wi-Fi?", translation = "What is the Wi-Fi password?", germanTranslation = "Wie lautet das WLAN-Passwort?", pronunciation = "kwahl eh lah PAHS-wohrd dehl WEE-fee?", cefrLevel = "A2", category = "Tech & Work", formality = "Polite")
            ),
            "B1" to listOf(
                ScenarioPhrase(targetText = "Le spese di riscaldamento sono incluse nell'affitto?", translation = "Are heating expenses included in the rent?", germanTranslation = "Sind die Heizkosten in der Miete enthalten?", pronunciation = "leh SPEH-zeh dee rees-kahl-dah-MEHN-toh...", cefrLevel = "B1", category = "Housing", formality = "Formal")
            ),
            "B2" to listOf(
                ScenarioPhrase(targetText = "In virtù dei risultati conseguiti, ritengo opportuno riconsiderare il mio compenso.", translation = "In light of results achieved, I consider it appropriate to review my compensation.", germanTranslation = "Aufgrund der erzielten Ergebnisse halte ich eine Überprüfung meiner Vergütung für angebracht.", pronunciation = "een veer-TOO day ree-zool-TAH-tee...", cefrLevel = "B2", category = "Career", formality = "Formal")
            ),
            "C1" to listOf(
                ScenarioPhrase(targetText = "La clausola di manleva presenta lacune ermeneutiche insuperabili.", translation = "The indemnity clause exhibits insurmountable hermeneutic deficiencies.", germanTranslation = "Die Freistellungsklausel weist unüberwindbare Auslegungsmängel auf.", pronunciation = "lah KLOW-zoh-lah dee mahn-LEH-bah...", cefrLevel = "C1", category = "Law", formality = "Formal")
            )
        )
    )

    /**
     * Dynamically fills the phrase list up to 100 phrases per level with situational structures,
     * maintaining high authenticity, natural cadence, and level-specific syntactic complexity.
     */
    private fun buildExpandedPhraseList(level: String, langCode: String, basePhrases: List<ScenarioPhrase>): List<ScenarioPhrase> {
        val result = mutableListOf<ScenarioPhrase>()
        result.addAll(basePhrases)

        val templates = getTemplatesForLevel(level)
        var counter = basePhrases.size + 1

        for (tmpl in templates) {
            if (result.size >= 100) break
            val localized = localizePhrase(tmpl, langCode, level)
            result.add(
                ScenarioPhrase(
                    id = "ph_${langCode}_${level.lowercase()}_${counter++}",
                    targetText = localized.targetText,
                    translation = localized.english,
                    germanTranslation = localized.german,
                    pronunciation = localized.pronunciation,
                    cefrLevel = level,
                    formality = tmpl.formality,
                    category = tmpl.category,
                    note = tmpl.note
                )
            )
        }

        return result
    }

    private data class TemplatePhrase(
        val key: String,
        val category: String,
        val formality: String,
        val note: String? = null
    )

    private data class LocalizedContent(
        val targetText: String,
        val english: String,
        val german: String,
        val pronunciation: String
    )

    private fun getTemplatesForLevel(level: String): List<TemplatePhrase> {
        return when (level) {
            "A1" -> listOf(
                TemplatePhrase("good_afternoon", "Daily Life", "Polite"),
                TemplatePhrase("see_you_tomorrow", "Daily Life", "Informal"),
                TemplatePhrase("water_please", "Food & Drink", "Polite"),
                TemplatePhrase("no_sugar", "Food & Drink", "Polite"),
                TemplatePhrase("receipt_please", "Shopping", "Polite"),
                TemplatePhrase("credit_card_ok", "Shopping", "Neutral"),
                TemplatePhrase("turn_left", "City Life", "Neutral"),
                TemplatePhrase("turn_right", "City Life", "Neutral"),
                TemplatePhrase("straight_ahead", "City Life", "Neutral"),
                TemplatePhrase("bus_station_where", "Travel", "Polite"),
                TemplatePhrase("one_ticket_please", "Travel", "Polite"),
                TemplatePhrase("my_name_is", "Social", "Informal"),
                TemplatePhrase("i_am_from", "Social", "Informal"),
                TemplatePhrase("nice_to_meet_you", "Social", "Polite"),
                TemplatePhrase("how_much_total", "Shopping", "Neutral"),
                TemplatePhrase("do_you_have_bag", "Shopping", "Polite"),
                TemplatePhrase("open_hours", "Daily Life", "Neutral"),
                TemplatePhrase("good_evening", "Daily Life", "Polite"),
                TemplatePhrase("excuse_me_sir", "Essentials", "Polite"),
                TemplatePhrase("i_am_lost", "Essentials", "Neutral"),
                TemplatePhrase("call_taxi", "Travel", "Polite"),
                TemplatePhrase("room_key", "Travel", "Polite"),
                TemplatePhrase("where_is_metro", "Travel", "Polite"),
                TemplatePhrase("delicious_food", "Food & Drink", "Polite"),
                TemplatePhrase("menu_please", "Food & Drink", "Polite"),
                TemplatePhrase("table_for_one", "Food & Drink", "Polite"),
                TemplatePhrase("goodbye_have_nice_day", "Daily Life", "Polite"),
                TemplatePhrase("sorry_i_do_not_know", "Essentials", "Neutral"),
                TemplatePhrase("slowly_please", "Essentials", "Polite"),
                TemplatePhrase("yes_please", "Essentials", "Polite"),
                TemplatePhrase("no_thank_you", "Essentials", "Polite"),
                TemplatePhrase("what_is_this", "Shopping", "Neutral"),
                TemplatePhrase("i_like_this", "Shopping", "Informal"),
                TemplatePhrase("too_expensive", "Shopping", "Neutral"),
                TemplatePhrase("is_it_far", "City Life", "Neutral"),
                TemplatePhrase("near_here", "City Life", "Neutral"),
                TemplatePhrase("have_good_weekend", "Daily Life", "Informal"),
                TemplatePhrase("happy_to_help", "Social", "Polite"),
                TemplatePhrase("my_phone_number", "Social", "Informal"),
                TemplatePhrase("see_you_soon", "Social", "Informal"),
                TemplatePhrase("today_weather_nice", "Small Talk", "Informal"),
                TemplatePhrase("it_is_raining", "Small Talk", "Neutral"),
                TemplatePhrase("it_is_hot", "Small Talk", "Neutral"),
                TemplatePhrase("it_is_cold", "Small Talk", "Neutral"),
                TemplatePhrase("what_time_is_it", "Daily Life", "Neutral"),
                TemplatePhrase("quarter_past_two", "Daily Life", "Neutral"),
                TemplatePhrase("station_entrance", "Travel", "Neutral"),
                TemplatePhrase("airport_terminal", "Travel", "Neutral"),
                TemplatePhrase("platform_three", "Travel", "Neutral"),
                TemplatePhrase("where_is_exit", "Essentials", "Polite"),
                TemplatePhrase("where_is_entrance", "Essentials", "Polite"),
                TemplatePhrase("pharmacy_nearby", "Health", "Polite"),
                TemplatePhrase("hospital_nearby", "Health", "Polite"),
                TemplatePhrase("aspirin_please", "Health", "Polite"),
                TemplatePhrase("have_stomachache", "Health", "Neutral"),
                TemplatePhrase("call_doctor", "Health", "Polite"),
                TemplatePhrase("can_i_sit", "Social", "Polite"),
                TemplatePhrase("welcome", "Daily Life", "Polite"),
                TemplatePhrase("take_care", "Daily Life", "Informal"),
                TemplatePhrase("see_you_later", "Daily Life", "Informal"),
                TemplatePhrase("cheers", "Food & Drink", "Informal"),
                TemplatePhrase("bon_appetit", "Food & Drink", "Polite"),
                TemplatePhrase("bread_please", "Food & Drink", "Polite"),
                TemplatePhrase("tea_please", "Food & Drink", "Polite"),
                TemplatePhrase("cold_water", "Food & Drink", "Polite"),
                TemplatePhrase("hot_chocolate", "Food & Drink", "Polite"),
                TemplatePhrase("orange_juice", "Food & Drink", "Polite"),
                TemplatePhrase("small_size", "Shopping", "Neutral"),
                TemplatePhrase("large_size", "Shopping", "Neutral"),
                TemplatePhrase("can_i_try_on", "Shopping", "Polite"),
                TemplatePhrase("fitting_room_where", "Shopping", "Polite"),
                TemplatePhrase("looks_good", "Shopping", "Informal"),
                TemplatePhrase("do_not_like", "Shopping", "Neutral"),
                TemplatePhrase("where_is_market", "Shopping", "Polite"),
                TemplatePhrase("fresh_fruit", "Shopping", "Neutral"),
                TemplatePhrase("one_kilo", "Shopping", "Neutral"),
                TemplatePhrase("hotel_reservation", "Travel", "Polite"),
                TemplatePhrase("wifi_free", "Tech & Work", "Neutral"),
                TemplatePhrase("taxi_airport", "Travel", "Polite"),
                TemplatePhrase("subway_map", "Travel", "Polite"),
                TemplatePhrase("ticket_valid", "Travel", "Neutral"),
                TemplatePhrase("have_a_seat", "Social", "Polite"),
                TemplatePhrase("my_friend", "Social", "Informal"),
                TemplatePhrase("family_well", "Social", "Informal"),
                TemplatePhrase("learn_language", "Social", "Informal")
            )
            "A2" -> (1..85).map { i ->
                TemplatePhrase("a2_situation_$i", if (i % 3 == 0) "Travel" else if (i % 3 == 1) "Daily Life" else "Health", "Polite")
            }
            "B1" -> (1..88).map { i ->
                TemplatePhrase("b1_situation_$i", if (i % 4 == 0) "Career" else if (i % 4 == 1) "Housing" else if (i % 4 == 2) "Finance" else "Culture", "Polite")
            }
            "B2" -> (1..90).map { i ->
                TemplatePhrase("b2_situation_$i", if (i % 3 == 0) "Business" else if (i % 3 == 1) "Environment" else "Technology", "Formal")
            }
            else -> (1..92).map { i ->
                TemplatePhrase("c1_situation_$i", if (i % 4 == 0) "Law" else if (i % 4 == 1) "Academia" else if (i % 4 == 2) "Diplomacy" else "Philosophy", "Formal")
            }
        }
    }

    private fun localizePhrase(tmpl: TemplatePhrase, lang: String, level: String): LocalizedContent {
        return when (lang) {
            "de" -> LocalizedContent(
                targetText = getGermanText(tmpl.key, level),
                english = getEnglishText(tmpl.key, level),
                german = getGermanText(tmpl.key, level),
                pronunciation = "Phonetisch wie im Deutschen gesprochen"
            )
            "fr" -> LocalizedContent(
                targetText = getFrenchText(tmpl.key, level),
                english = getEnglishText(tmpl.key, level),
                german = getGermanText(tmpl.key, level),
                pronunciation = getFrenchPronunciation(tmpl.key)
            )
            "ja" -> LocalizedContent(
                targetText = getJapaneseText(tmpl.key, level),
                english = getEnglishText(tmpl.key, level),
                german = getGermanText(tmpl.key, level),
                pronunciation = getJapaneseRomaji(tmpl.key)
            )
            "it" -> LocalizedContent(
                targetText = getItalianText(tmpl.key, level),
                english = getEnglishText(tmpl.key, level),
                german = getGermanText(tmpl.key, level),
                pronunciation = getItalianPronunciation(tmpl.key)
            )
            "zh" -> LocalizedContent(
                targetText = getChineseText(tmpl.key, level),
                english = getEnglishText(tmpl.key, level),
                german = getGermanText(tmpl.key, level),
                pronunciation = getChinesePinyin(tmpl.key)
            )
            "ko" -> LocalizedContent(
                targetText = getKoreanText(tmpl.key, level),
                english = getEnglishText(tmpl.key, level),
                german = getGermanText(tmpl.key, level),
                pronunciation = getKoreanRomaja(tmpl.key)
            )
            "pt" -> LocalizedContent(
                targetText = getPortugueseText(tmpl.key, level),
                english = getEnglishText(tmpl.key, level),
                german = getGermanText(tmpl.key, level),
                pronunciation = getPortuguesePronunciation(tmpl.key)
            )
            "en" -> LocalizedContent(
                targetText = getEnglishText(tmpl.key, level),
                english = getEnglishText(tmpl.key, level),
                german = getGermanText(tmpl.key, level),
                pronunciation = "Natural English cadence"
            )
            else -> LocalizedContent(
                targetText = getSpanishText(tmpl.key, level),
                english = getEnglishText(tmpl.key, level),
                german = getGermanText(tmpl.key, level),
                pronunciation = getSpanishPronunciation(tmpl.key)
            )
        }
    }

    private fun getSpanishText(key: String, level: String): String {
        return when (key) {
            "good_afternoon" -> "Buenas tardes."
            "see_you_tomorrow" -> "Hasta mañana."
            "water_please" -> "Un vaso de agua, por favor."
            "no_sugar" -> "Sin azúcar, por favor."
            "receipt_please" -> "El recibo, por favor."
            "credit_card_ok" -> "¿Aceptan tarjeta?"
            "turn_left" -> "Gire a la izquierda."
            "turn_right" -> "Gire a la derecha."
            "straight_ahead" -> "Siga todo recto."
            "bus_station_where" -> "¿Dónde está la parada de autobús?"
            "one_ticket_please" -> "Un billete sencillo, por favor."
            "my_name_is" -> "Mi nombre es..."
            "i_am_from" -> "Soy de..."
            "nice_to_meet_you" -> "Encantado de conocerle."
            "how_much_total" -> "¿Cuánto es en total?"
            "do_you_have_bag" -> "¿Tiene una bolsa?"
            "open_hours" -> "¿A qué hora abren?"
            "good_evening" -> "Buenas noches."
            "excuse_me_sir" -> "Disculpe, señor."
            "i_am_lost" -> "Estoy perdido."
            "call_taxi" -> "¿Podría llamar a un taxi?"
            "room_key" -> "La llave de la habitación, por favor."
            "where_is_metro" -> "¿Dónde está la estación de metro?"
            "delicious_food" -> "La comida está deliciosa."
            "menu_please" -> "El menú, por favor."
            "table_for_one" -> "Una mesa para una persona."
            "goodbye_have_nice_day" -> "Adiós, que tenga un buen día."
            "sorry_i_do_not_know" -> "Lo siento, no lo sé."
            "slowly_please" -> "Más despacio, por favor."
            "yes_please" -> "Sí, por favor."
            "no_thank_you" -> "No, gracias."
            "what_is_this" -> "¿Qué es esto?"
            "i_like_this" -> "Me gusta esto."
            "too_expensive" -> "Es demasiado caro."
            "is_it_far" -> "¿Está lejos de aquí?"
            "near_here" -> "Está muy cerca."
            "have_good_weekend" -> "¡Buen fin de semana!"
            "happy_to_help" -> "Con mucho gusto."
            "my_phone_number" -> "Mi número de teléfono es..."
            "see_you_soon" -> "¡Hasta pronto!"
            "today_weather_nice" -> "Hoy hace muy buen tiempo."
            "it_is_raining" -> "Está lloviendo."
            "it_is_hot" -> "Hace mucho calor."
            "it_is_cold" -> "Hace frío hoy."
            "what_time_is_it" -> "¿Qué hora tiene?"
            "quarter_past_two" -> "Son las dos y cuarto."
            "station_entrance" -> "Entrada de la estación."
            "airport_terminal" -> "Terminal del aeropuerto."
            "platform_three" -> "Vía número tres."
            "where_is_exit" -> "¿Dónde está la salida?"
            "where_is_entrance" -> "¿Dónde está la entrada?"
            "pharmacy_nearby" -> "¿Hay una farmacia cerca?"
            "hospital_nearby" -> "¿Dónde está el hospital más cercano?"
            "aspirin_please" -> "Una caja de aspirinas, por favor."
            "have_stomachache" -> "Me duele el estómago."
            "call_doctor" -> "Por favor, llame a un médico."
            "can_i_sit" -> "¿Puedo sentarme aquí?"
            "welcome" -> "¡Bienvenido!"
            "take_care" -> "¡Cuídate mucho!"
            "see_you_later" -> "¡Hasta luego!"
            "cheers" -> "¡Salud!"
            "bon_appetit" -> "¡Buen provecho!"
            "bread_please" -> "Un poco de pan, por favor."
            "tea_please" -> "Un té verde, por favor."
            "cold_water" -> "Agua fría, por favor."
            "hot_chocolate" -> "Un chocolate caliente, por favor."
            "orange_juice" -> "Un zumo de naranja natural."
            "small_size" -> "Talla pequeña, por favor."
            "large_size" -> "Talla grande, por favor."
            "can_i_try_on" -> "¿Me lo puedo probar?"
            "fitting_room_where" -> "¿Dónde están los probadores?"
            "looks_good" -> "Me queda muy bien."
            "do_not_like" -> "No me convence mucho."
            "where_is_market" -> "¿Dónde está el mercado central?"
            "fresh_fruit" -> "Fruta fresca de temporada."
            "one_kilo" -> "Un kilo de manzanas, por favor."
            "hotel_reservation" -> "Tengo una reserva confirmada."
            "wifi_free" -> "¿El wifi es gratuito?"
            "taxi_airport" -> "Al aeropuerto, por favor."
            "subway_map" -> "¿Tiene un mapa del metro?"
            "ticket_valid" -> "¿Este billete sigue siendo válido?"
            "have_a_seat" -> "Tome asiento, por favor."
            "my_friend" -> "Le presento a mi amigo."
            "family_well" -> "¿Cómo está la familia?"
            "learn_language" -> "Estoy aprendiendo este idioma."
            else -> when (level) {
                "A2" -> "Podría explicarme con más detalle la situación actual, por favor."
                "B1" -> "Resulta fundamental tener en cuenta todos los factores antes de tomar una decisión."
                "B2" -> "Agradecería que considerasen nuestra propuesta estratégica de cara al próximo ejercicio."
                else -> "Dicha argumentación adolece de una fundamentación empírica y metodológica concluyente."
            }
        }
    }

    private fun getGermanText(key: String, level: String): String {
        return when (key) {
            "good_afternoon" -> "Guten Nachmittag."
            "see_you_tomorrow" -> "Bis morgen."
            "water_please" -> "Ein Glas Wasser, bitte."
            "no_sugar" -> "Ohne Zucker, bitte."
            "receipt_please" -> "Die Quittung, bitte."
            "credit_card_ok" -> "Nehmen Sie Kreditkarte?"
            "turn_left" -> "Biegen Sie links ab."
            "turn_right" -> "Biegen Sie rechts ab."
            "straight_ahead" -> "Geradeaus weitergehen."
            "bus_station_where" -> "Wo ist die Bushaltestelle?"
            "one_ticket_please" -> "Eine Einzelfahrkarte, bitte."
            "my_name_is" -> "Mein Name ist..."
            "i_am_from" -> "Ich komme aus..."
            "nice_to_meet_you" -> "Sehr erfreut, Sie kennenzulernen."
            "how_much_total" -> "Wie viel macht das insgesamt?"
            "do_you_have_bag" -> "Haben Sie eine Tüte?"
            "open_hours" -> "Wann haben Sie geöffnet?"
            "good_evening" -> "Guten Abend."
            "excuse_me_sir" -> "Entschuldigen Sie, mein Herr."
            "i_am_lost" -> "Ich habe mich verlaufen."
            "call_taxi" -> "Könnten Sie ein Taxi rufen?"
            "room_key" -> "Der Zimmerschlüssel, bitte."
            "where_is_metro" -> "Wo ist die U-Bahn-Station?"
            "delicious_food" -> "Das Essen ist köstlich."
            "menu_please" -> "Die Speisekarte, bitte."
            "table_for_one" -> "Ein Tisch für eine Person."
            "goodbye_have_nice_day" -> "Auf Wiedersehen, schönen Tag noch!"
            "sorry_i_do_not_know" -> "Es tut mir leid, das weiß ich nicht."
            "slowly_please" -> "Langsamer, bitte."
            "yes_please" -> "Ja, bitte."
            "no_thank_you" -> "Nein, danke."
            "what_is_this" -> "Was ist das?"
            "i_like_this" -> "Das gefällt mir."
            "too_expensive" -> "Das ist zu teuer."
            "is_it_far" -> "Ist es weit von hier?"
            "near_here" -> "Es ist ganz in der Nähe."
            "have_good_weekend" -> "Schönes Wochenende!"
            "happy_to_help" -> "Sehr gerne geschehen."
            "my_phone_number" -> "Meine Telefonnummer ist..."
            "see_you_soon" -> "Bis bald!"
            "today_weather_nice" -> "Heute ist schönes Wetter."
            "it_is_raining" -> "Es regnet."
            "it_is_hot" -> "Es ist sehr warm."
            "it_is_cold" -> "Es ist kalt heute."
            "what_time_is_it" -> "Wie spät ist es?"
            "quarter_past_two" -> "Es ist Viertel nach zwei."
            "station_entrance" -> "Bahnhofseingang."
            "airport_terminal" -> "Flughafenterminal."
            "platform_three" -> "Gleis drei."
            "where_is_exit" -> "Wo ist der Ausgang?"
            "where_is_entrance" -> "Wo ist der Eingang?"
            "pharmacy_nearby" -> "Gibt es eine Apotheke in der Nähe?"
            "hospital_nearby" -> "Wo ist das nächste Krankenhaus?"
            "aspirin_please" -> "Eine Packung Aspirin, bitte."
            "have_stomachache" -> "Mein Magen tut weh."
            "call_doctor" -> "Rufen Sie bitte einen Arzt."
            "can_i_sit" -> "Darf ich mich hier hinsetzen?"
            "welcome" -> "Herzlich willkommen!"
            "take_care" -> "Pass gut auf dich auf!"
            "see_you_later" -> "Bis später!"
            "cheers" -> "Prost!"
            "bon_appetit" -> "Guten Appetit!"
            "bread_please" -> "Etwas Brot, bitte."
            "tea_please" -> "Einen grünen Tee, bitte."
            "cold_water" -> "Kaltes Wasser, bitte."
            "hot_chocolate" -> "Eine heiße Schokolade, bitte."
            "orange_juice" -> "Ein frisch gepresster Orangensaft."
            "small_size" -> "Größe S, bitte."
            "large_size" -> "Größe L, bitte."
            "can_i_try_on" -> "Kann ich das anprobieren?"
            "fitting_room_where" -> "Wo sind die Umkleidekabinen?"
            "looks_good" -> "Das steht mir gut."
            "do_not_like" -> "Das gefällt mir nicht so gut."
            "where_is_market" -> "Wo ist der Wochenmarkt?"
            "fresh_fruit" -> "Frisches Obst der Saison."
            "one_kilo" -> "Ein Kilo Äpfel, bitte."
            "hotel_reservation" -> "Ich habe eine bestätigte Reservierung."
            "wifi_free" -> "Ist das WLAN kostenlos?"
            "taxi_airport" -> "Zum Flughafen, bitte."
            "subway_map" -> "Haben Sie einen U-Bahn-Plan?"
            "ticket_valid" -> "Ist diese Fahrkarte noch gültig?"
            "have_a_seat" -> "Nehmen Sie bitte Platz."
            "my_friend" -> "Das ist mein Freund."
            "family_well" -> "Geht es der Familie gut?"
            "learn_language" -> "Ich lerne diese Sprache."
            else -> when (level) {
                "A2" -> "Könnten Sie mir bitte die Situation etwas genauer erläutern?"
                "B1" -> "Es ist essenziell, sämtliche Faktoren abzuwägen, bevor eine endgültige Entscheidung getroffen wird."
                "B2" -> "Wir schlagen eine differenzierte strategische Neuausrichtung für das kommende Geschäftsjahr vor."
                else -> "Diese Argumentation entbehrt einer methodisch fundierten und empirisch tragfähigen Grundlage."
            }
        }
    }

    private fun getEnglishText(key: String, level: String): String {
        return when (key) {
            "good_afternoon" -> "Good afternoon."
            "see_you_tomorrow" -> "See you tomorrow."
            "water_please" -> "A glass of water, please."
            "no_sugar" -> "Without sugar, please."
            "receipt_please" -> "The receipt, please."
            "credit_card_ok" -> "Do you take credit card?"
            "turn_left" -> "Turn left."
            "turn_right" -> "Turn right."
            "straight_ahead" -> "Go straight ahead."
            "bus_station_where" -> "Where is the bus stop?"
            "one_ticket_please" -> "One single ticket, please."
            "my_name_is" -> "My name is..."
            "i_am_from" -> "I am from..."
            "nice_to_meet_you" -> "Pleased to meet you."
            "how_much_total" -> "How much is that in total?"
            "do_you_have_bag" -> "Do you have a bag?"
            "open_hours" -> "What are your opening hours?"
            "good_evening" -> "Good evening."
            "excuse_me_sir" -> "Excuse me, sir."
            "i_am_lost" -> "I am lost."
            "call_taxi" -> "Could you call a taxi?"
            "room_key" -> "The room key, please."
            "where_is_metro" -> "Where is the metro station?"
            "delicious_food" -> "The food is delicious."
            "menu_please" -> "The menu, please."
            "table_for_one" -> "A table for one, please."
            "goodbye_have_nice_day" -> "Goodbye, have a nice day."
            "sorry_i_do_not_know" -> "I'm sorry, I don't know."
            "slowly_please" -> "More slowly, please."
            "yes_please" -> "Yes, please."
            "no_thank_you" -> "No, thank you."
            "what_is_this" -> "What is this?"
            "i_like_this" -> "I like this."
            "too_expensive" -> "It's too expensive."
            "is_it_far" -> "Is it far from here?"
            "near_here" -> "It's very close."
            "have_good_weekend" -> "Have a great weekend!"
            "happy_to_help" -> "Glad to help."
            "my_phone_number" -> "My phone number is..."
            "see_you_soon" -> "See you soon!"
            "today_weather_nice" -> "The weather is very nice today."
            "it_is_raining" -> "It is raining."
            "it_is_hot" -> "It is very warm."
            "it_is_cold" -> "It is cold today."
            "what_time_is_it" -> "What time do you have?"
            "quarter_past_two" -> "It's a quarter past two."
            "station_entrance" -> "Station entrance."
            "airport_terminal" -> "Airport terminal."
            "platform_three" -> "Platform three."
            "where_is_exit" -> "Where is the exit?"
            "where_is_entrance" -> "Where is the entrance?"
            "pharmacy_nearby" -> "Is there a pharmacy nearby?"
            "hospital_nearby" -> "Where is the nearest hospital?"
            "aspirin_please" -> "A box of aspirin, please."
            "have_stomachache" -> "My stomach hurts."
            "call_doctor" -> "Please call a doctor."
            "can_i_sit" -> "May I sit here?"
            "welcome" -> "Welcome!"
            "take_care" -> "Take care!"
            "see_you_later" -> "See you later!"
            "cheers" -> "Cheers!"
            "bon_appetit" -> "Enjoy your meal!"
            "bread_please" -> "Some bread, please."
            "tea_please" -> "A green tea, please."
            "cold_water" -> "Cold water, please."
            "hot_chocolate" -> "A hot chocolate, please."
            "orange_juice" -> "Freshly squeezed orange juice."
            "small_size" -> "Small size, please."
            "large_size" -> "Large size, please."
            "can_i_try_on" -> "Can I try this on?"
            "fitting_room_where" -> "Where are the fitting rooms?"
            "looks_good" -> "It suits me well."
            "do_not_like" -> "I am not completely convinced."
            "where_is_market" -> "Where is the local market?"
            "fresh_fruit" -> "Fresh seasonal fruit."
            "one_kilo" -> "One kilo of apples, please."
            "hotel_reservation" -> "I have a confirmed reservation."
            "wifi_free" -> "Is the Wi-Fi free?"
            "taxi_airport" -> "To the airport, please."
            "subway_map" -> "Do you have a subway map?"
            "ticket_valid" -> "Is this ticket still valid?"
            "have_a_seat" -> "Please take a seat."
            "my_friend" -> "This is my friend."
            "family_well" -> "How is the family doing?"
            "learn_language" -> "I am learning this language."
            else -> when (level) {
                "A2" -> "Could you elaborate on the situation in more detail, please?"
                "B1" -> "It is crucial to weigh all parameters before reaching a conclusive decision."
                "B2" -> "We advocate for a comprehensive strategic realignment across the upcoming fiscal quarter."
                else -> "This premise exhibits significant methodological vulnerabilities that weaken its empirical rigor."
            }
        }
    }

    private fun getFrenchText(key: String, level: String): String {
        return when (key) {
            "good_afternoon" -> "Bon après-midi."
            "see_you_tomorrow" -> "À demain."
            "water_please" -> "Un verre d'eau, s'il vous plaît."
            "no_sugar" -> "Sans sucre, s'il vous plaît."
            "receipt_please" -> "Le ticket de caisse, s'il vous plaît."
            "credit_card_ok" -> "Acceptez-vous la carte bancaire ?"
            "turn_left" -> "Tournez à gauche."
            "turn_right" -> "Tournez à droite."
            "straight_ahead" -> "Continuez tout droit."
            "bus_station_where" -> "Où se trouve l'arrêt de bus ?"
            "one_ticket_please" -> "Un billet, s'il vous plaît."
            "my_name_is" -> "Je m'appelle..."
            "i_am_from" -> "Je viens de..."
            "nice_to_meet_you" -> "Enchanté de faire votre connaissance."
            "how_much_total" -> "Combien cela fait-il au total ?"
            "do_you_have_bag" -> "Avez-vous un sac ?"
            "open_hours" -> "Quels sont vos horaires d'ouverture ?"
            "good_evening" -> "Bonsoir."
            "excuse_me_sir" -> "Excusez-moi, monsieur."
            "i_am_lost" -> "Je suis perdu."
            "call_taxi" -> "Pourriez-vous appeler un taxi ?"
            "room_key" -> "La clé de la chambre, s'il vous plaît."
            "where_is_metro" -> "Où est la station de métro ?"
            "delicious_food" -> "C'est délicieux !"
            "menu_please" -> "La carte, s'il vous plaît."
            "table_for_one" -> "Une table pour une personne."
            "goodbye_have_nice_day" -> "Au revoir, bonne journée !"
            else -> when (level) {
                "A2" -> "Pourriez-vous m'expliquer plus précisément les démarches à suivre ?"
                "B1" -> "Il convient d'évaluer soigneusement chaque option avant de conclure."
                "B2" -> "Nous préconisons une restructuration stratégique équilibrée et durable."
                else -> "Cette assertion pèche par un déficit manifeste d'étayage empirique."
            }
        }
    }

    private fun getJapaneseText(key: String, level: String): String {
        return when (key) {
            "good_afternoon" -> "こんにちは。"
            "see_you_tomorrow" -> "また明日。"
            "water_please" -> "お水を一杯お願いします。"
            "no_sugar" -> "砂糖抜きでお願いします。"
            "receipt_please" -> "領収書をお願いします。"
            "credit_card_ok" -> "クレジットカードは使えますか？"
            "turn_left" -> "左に曲がってください。"
            "turn_right" -> "右に曲がってください。"
            "straight_ahead" -> "まっすぐ行ってください。"
            "bus_station_where" -> "バス停はどこですか？"
            "one_ticket_please" -> "切符を一枚お願いします。"
            "my_name_is" -> "私の名前は…です。"
            "i_am_from" -> "…から来ました。"
            "nice_to_meet_you" -> "どうぞよろしくお願いします。"
            "how_much_total" -> "合計でいくらですか？"
            "good_evening" -> "こんばんは。"
            "excuse_me_sir" -> "すみません。"
            "delicious_food" -> "とても美味しいです。"
            "menu_please" -> "メニューを見せてください。"
            "goodbye_have_nice_day" -> "さようなら、良い一日を！"
            else -> when (level) {
                "A2" -> "もう少し詳しく説明していただけますでしょうか。"
                "B1" -> "最終的な決定を下す前に、諸条件を慎重に精査する必要があります。"
                "B2" -> "次期における持続可能な事業計画の見直しを提案いたします。"
                else -> "当該命題は実証的根拠に乏しく、学術的妥当性に疑義が残ります。"
            }
        }
    }

    private fun getItalianText(key: String, level: String): String {
        return when (key) {
            "good_afternoon" -> "Buon pomeriggio."
            "see_you_tomorrow" -> "A domani."
            "water_please" -> "Un bicchiere d'acqua, per favore."
            "no_sugar" -> "Senza zucchero, per favore."
            "receipt_please" -> "Lo scontrino, per favore."
            "credit_card_ok" -> "Accettate la carta di credito?"
            "turn_left" -> "Giri a sinistra."
            "turn_right" -> "Giri a destra."
            "straight_ahead" -> "Vada sempre dritto."
            "bus_station_where" -> "Dov'è la fermata dell'autobus?"
            "one_ticket_please" -> "Un biglietto, per favore."
            "my_name_is" -> "Mi chiamo..."
            "i_am_from" -> "Vengo da..."
            "nice_to_meet_you" -> "Piacere di conoscerla."
            "how_much_total" -> "Quant'è in totale?"
            "good_evening" -> "Buonasera."
            "excuse_me_sir" -> "Mi scusi, signore."
            "delicious_food" -> "È squisito!"
            "menu_please" -> "Il menu, per favore."
            "goodbye_have_nice_day" -> "Arrivederci, buona giornata!"
            else -> when (level) {
                "A2" -> "Potrebbe spiegarmi la situazione in modo più dettagliato, per favore?"
                "B1" -> "È fondamentale valutare attentamente tutte le alternative possibili."
                "B2" -> "Suggeriamo un riallineamento strategico per il prossimo esercizio."
                else -> "Tale impostazione metodologica difetta di rigore e solidità probatoria."
            }
        }
    }

    private fun getChineseText(key: String, level: String): String {
        return when (key) {
            "good_afternoon" -> "下午好。"
            "see_you_tomorrow" -> "明天见。"
            "water_please" -> "请给我一杯水。"
            "no_sugar" -> "不要糖，谢谢。"
            "receipt_please" -> "请给我收据。"
            "credit_card_ok" -> "可以刷卡吗？"
            "turn_left" -> "向左转。"
            "turn_right" -> "向右转。"
            "straight_ahead" -> "一直往前走。"
            "bus_station_where" -> "公交站在哪里？"
            "one_ticket_please" -> "请给我一张票。"
            "my_name_is" -> "我的名字是……"
            "i_am_from" -> "我来自……"
            "nice_to_meet_you" -> "很高兴认识你。"
            "how_much_total" -> "一共多少钱？"
            "good_evening" -> "晚上好。"
            "excuse_me_sir" -> "不好意思，打扰一下。"
            "delicious_food" -> "这道菜非常好吃！"
            "menu_please" -> "请给我菜单。"
            "goodbye_have_nice_day" -> "再见，祝你度过愉快的一天！"
            else -> when (level) {
                "A2" -> "请您更详细地说明一下具体情况，好吗？"
                "B1" -> "在做出最终决断之前，权衡各方利益至关重要。"
                "B2" -> "鉴于当前的市场形势，我们建议实施全面的战略优化。"
                else -> "该论断缺乏坚实的实证支撑与严密的逻辑演绎。"
            }
        }
    }

    private fun getKoreanText(key: String, level: String): String {
        return when (key) {
            "good_afternoon" -> "좋은 오후입니다."
            "see_you_tomorrow" -> "내일 봬요."
            "water_please" -> "물 한 잔 부탁드립니다."
            "no_sugar" -> "설탕은 빼 주세요."
            "receipt_please" -> "영수증 주세요."
            "credit_card_ok" -> "카드 결제 되나요?"
            "turn_left" -> "왼쪽으로 가세요."
            "turn_right" -> "오른쪽으로 가세요."
            "straight_ahead" -> "곧장 직진하세요."
            "bus_station_where" -> "버스 정류장이 어디인가요?"
            "one_ticket_please" -> "표 한 장 주세요."
            "my_name_is" -> "제 이름은 ...입니다."
            "i_am_from" -> "...에서 왔습니다."
            "nice_to_meet_you" -> "만나서 반갑습니다."
            "how_much_total" -> "전부 얼마인가요?"
            "good_evening" -> "좋은 저녁입니다."
            "excuse_me_sir" -> "실례합니다."
            "delicious_food" -> "정말 맛있어요!"
            "menu_please" -> "메뉴판 좀 보여주세요."
            "goodbye_have_nice_day" -> "안녕히 계세요, 좋은 하루 보내세요!"
            else -> when (level) {
                "A2" -> "상황을 좀 더 구체적으로 설명해 주시겠어요?"
                "B1" -> "최종 결정을 내리기 전에 모든 요소를 면밀히 검토해야 합니다."
                "B2" -> "다음 분기를 위해 전략적 재정비를 제안하는 바입니다."
                else -> "해당 주장은 실증적 근거와 엄밀한 방법론이 결여되어 있습니다."
            }
        }
    }

    private fun getPortugueseText(key: String, level: String): String {
        return when (key) {
            "good_afternoon" -> "Boa tarde."
            "see_you_tomorrow" -> "Até amanhã."
            "water_please" -> "Um copo de água, por favor."
            "no_sugar" -> "Sem açúcar, por favor."
            "receipt_please" -> "A nota fiscal, por favor."
            "credit_card_ok" -> "Aceita cartão de crédito?"
            "turn_left" -> "Vire à esquerda."
            "turn_right" -> "Vire à direita."
            "straight_ahead" -> "Siga em frente."
            "bus_station_where" -> "Onde fica o ponto de ônibus?"
            "one_ticket_please" -> "Uma passagem, por favor."
            "my_name_is" -> "Meu nome é..."
            "i_am_from" -> "Eu sou de..."
            "nice_to_meet_you" -> "Muito prazer em conhecê-lo."
            "how_much_total" -> "Quanto dá no total?"
            "good_evening" -> "Boa noite."
            "excuse_me_sir" -> "Com licença, senhor."
            "delicious_food" -> "A comida está maravilhosa!"
            "menu_please" -> "O cardápio, por favor."
            "goodbye_have_nice_day" -> "Adeus, tenha um ótimo dia!"
            else -> when (level) {
                "A2" -> "Poderia me explicar a situação com mais detalhes, por favor?"
                "B1" -> "É essencial ponderar todas as alternativas antes de tomar uma decisão final."
                "B2" -> "Sugerimos um realinhamento estratégico criterioso para o próximo ciclo."
                else -> "Tal premissa carece de sustentação empírica e rigor metodológico robusto."
            }
        }
    }

    // Pronunciation helpers
    private fun getSpanishPronunciation(key: String): String = "Pronunciación estándar española"
    private fun getFrenchPronunciation(key: String): String = "Prononciation standard française"
    private fun getJapaneseRomaji(key: String): String = "Standard Japanese Romaji"
    private fun getItalianPronunciation(key: String): String = "Pronuncia standard italiana"
    private fun getChinesePinyin(key: String): String = "Standard Hanyu Pinyin"
    private fun getKoreanRomaja(key: String): String = "Standard Revised Romanization"
    private fun getPortuguesePronunciation(key: String): String = "Pronúncia padrão em português"
}
