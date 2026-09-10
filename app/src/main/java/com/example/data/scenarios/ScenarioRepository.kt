package com.example.data.scenarios

import com.example.data.models.PracticeScenario
import com.example.data.models.ProficiencyLevel

object ScenarioRepository {

    val allScenarios: List<PracticeScenario> by lazy {
        scenariosA1 + scenariosA2 + scenariosB1 + scenariosB2 + scenariosC1
    }

    // ==========================================
    // 20 SCENARIOS - A1: BEGINNER (ESSENTIALS)
    // ==========================================
    val scenariosA1 = listOf(
        PracticeScenario(
            id = "sc_a1_01_free_chat",
            title = "Casual Meetup",
            category = "Daily Life",
            iconEmoji = "☕",
            description = "Chat naturally about your day, hobbies, music, and favorite food.",
            promptContext = "You are a warm, friendly local chatting with the user at a cozy neighborhood cafe. Keep it natural, welcoming, and relaxed.",
            goals = listOf("Share what you did today", "Talk about your favorite hobby", "Ask the partner a question"),
            cefrLevel = "A1",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a1_02_cafe_ordering",
            title = "Café & Bakery",
            category = "Food & Drink",
            iconEmoji = "🥐",
            description = "Order a drink and pastry, ask for recommendations, and request the bill.",
            promptContext = "You are an attentive barista and baker at a popular local café. Greet the customer, recommend a specialty, answer their inquiries, and finalize their order.",
            goals = listOf("Order your beverage of choice", "Ask for a pastry recommendation", "Request the check/bill"),
            cefrLevel = "A1",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a1_03_hotel_checkin",
            title = "Hotel Check-In",
            category = "Travel",
            iconEmoji = "🏨",
            description = "Check into a hotel, confirm room amenities, and ask for local sightseeing tips.",
            promptContext = "You are a professional, helpful front desk concierge at a central boutique hotel. Assist the guest with checking in, explaining breakfast times, and giving neighborhood recommendations.",
            goals = listOf("Provide your name and reservation", "Ask about Wi-Fi or breakfast hours", "Request a nearby recommendation"),
            cefrLevel = "A1",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a1_04_market_produce",
            title = "Open Street Market",
            category = "Shopping",
            iconEmoji = "🍎",
            description = "Shop for fresh produce or souvenirs, ask prices, and practice polite negotiation.",
            promptContext = "You are an enthusiastic merchant at a bustling open-air market with fresh fruits, spices, and handmade goods. Engage with the shopper cheerily.",
            goals = listOf("Inquire about the price of an item", "Ask about product freshness or origin", "Complete the purchase politely"),
            cefrLevel = "A1",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a1_05_directions_simple",
            title = "Finding Your Way",
            category = "City Life",
            iconEmoji = "🗺️",
            description = "Ask a pedestrian for directions to the train station or city landmark.",
            promptContext = "You are a knowledgeable local pedestrian who stops to help a visitor navigate the city streets. Use clear, simple directional terms.",
            goals = listOf("Politely catch attention and ask where something is", "Confirm if it's within walking distance", "Thank them warmly"),
            cefrLevel = "A1",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a1_06_fast_food",
            title = "Fast Food Counter",
            category = "Food & Drink",
            iconEmoji = "🍔",
            description = "Order a quick burger or meal set, choose sides and drinks, pay at the counter.",
            promptContext = "You work at a busy burger and fries eatery. Ask the customer what combo they'd like, drink size, and dine-in or takeaway.",
            goals = listOf("Choose your combo meal", "Specify drink and side size", "State whether eating here or to go"),
            cefrLevel = "A1",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a1_07_introductions",
            title = "Introducing Yourself",
            category = "Social",
            iconEmoji = "👋",
            description = "Introduce your name, country of origin, occupation, and languages spoken.",
            promptContext = "You are a friendly student at an international language meetup welcoming a newcomer. Ask simple questions about where they are from.",
            goals = listOf("Say your name and nationality", "Mention what language you want to learn", "Ask the other person's name"),
            cefrLevel = "A1",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a1_08_bus_ticket",
            title = "Buying a Bus Ticket",
            category = "Travel",
            iconEmoji = "🚌",
            description = "Purchase a single or day-pass bus ticket from the kiosk or driver.",
            promptContext = "You are a bus station kiosk attendant. Tell the traveler route options, prices, and validate their ticket.",
            goals = listOf("Ask for a ticket to city center", "Inquire how much it costs", "Ask what time the next bus arrives"),
            cefrLevel = "A1",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a1_09_supermarket",
            title = "Supermarket Checkout",
            category = "Shopping",
            iconEmoji = "🛒",
            description = "Ask where items are located, interact with cashier, ask for a receipt and bag.",
            promptContext = "You are a polite supermarket cashier scanning items. Ask if they have a loyalty card, need a shopping bag, and take cash or card.",
            goals = listOf("Ask if you need a bag", "Pay with credit card or cash", "Request the receipt politely"),
            cefrLevel = "A1",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a1_10_telling_time",
            title = "Asking What Time It Is",
            category = "Daily Life",
            iconEmoji = "⏰",
            description = "Ask the current time, store opening hours, and appointment schedules.",
            promptContext = "You are a passerby outside a store. Answer what time it is and when the store opens or closes.",
            goals = listOf("Ask what time it is right now", "Ask when the shop closes", "Say thank you and wish them a good day"),
            cefrLevel = "A1",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a1_11_weather_chat",
            title = "Talking About the Weather",
            category = "Small Talk",
            iconEmoji = "☀️",
            description = "Chat about today's sunshine, rain, temperature, and tomorrow's forecast.",
            promptContext = "You are an elder neighbor in the garden elevator making friendly small talk about the chilly wind and sunny weekend forecast.",
            goals = listOf("Describe today's weather", "Express whether you like the temperature", "Comment on the weekend forecast"),
            cefrLevel = "A1",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a1_12_ice_cream",
            title = "Gelato & Ice Cream Shop",
            category = "Food & Drink",
            iconEmoji = "🍨",
            description = "Choose flavors, cup or cone, toppings, and taste sample spoons.",
            promptContext = "You work at an artisanal ice cream parlour. Recommend seasonal fruit sorbets and chocolate flavors with enthusiasm.",
            goals = listOf("Ask to taste a flavor sample", "Order two scoops in a cone", "Ask for a napkin"),
            cefrLevel = "A1",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a1_13_clothing_store",
            title = "Clothing Boutique",
            category = "Shopping",
            iconEmoji = "👕",
            description = "Ask for your size, different colors, and where the fitting room is.",
            promptContext = "You are a stylish retail assistant at a high-street clothing shop. Assist the customer with finding sizes and checking fit.",
            goals = listOf("Ask if this shirt is in Medium", "Ask where the fitting room is", "Say whether it fits comfortably"),
            cefrLevel = "A1",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a1_14_taxi_ride",
            title = "Taking a Taxi",
            category = "Travel",
            iconEmoji = "🚕",
            description = "Give the taxi driver your destination address, confirm price, and ask to stop.",
            promptContext = "You are a seasoned taxi driver navigating city traffic. Ask the passenger where to go and confirm the route.",
            goals = listOf("State the destination address clearly", "Ask how long the drive will take", "Ask for the total fare"),
            cefrLevel = "A1",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a1_15_restroom",
            title = "Finding the Restroom",
            category = "Essentials",
            iconEmoji = "🚻",
            description = "Politely locate the nearest public restroom in a mall, station, or restaurant.",
            promptContext = "You are a security guard in a large train station helping a traveler find the restrooms and elevators.",
            goals = listOf("Politely ask where the bathroom is", "Clarify if it's on the upper or lower floor", "Thank them for the help"),
            cefrLevel = "A1",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a1_16_phone_numbers",
            title = "Exchanging Contact Info",
            category = "Social",
            iconEmoji = "📱",
            description = "Exchange phone numbers, spell out email address, and plan to stay in touch.",
            promptContext = "You are a new classmate after your first language lesson exchanging WhatsApp numbers and email.",
            goals = listOf("Say your phone number digit by digit", "Spell your email address correctly", "Suggest messaging each other later"),
            cefrLevel = "A1",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a1_17_airport_baggage",
            title = "Airport Baggage Claim",
            category = "Travel",
            iconEmoji = "🧳",
            description = "Locate luggage carousel, describe suitcase color, and report a delayed bag.",
            promptContext = "You are an airport ground staff agent at the baggage service counter helping passengers locate their luggage carousel.",
            goals = listOf("Ask which carousel your flight's bags arrive at", "Describe the color and shape of your bag", "Thank the agent"),
            cefrLevel = "A1",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a1_18_neighbor_greeting",
            title = "Greeting the Neighbor",
            category = "Daily Life",
            iconEmoji = "🏡",
            description = "Say good morning, exchange pleasantries in the hallway or garden.",
            promptContext = "You are a friendly neighbor collecting your morning mail. Chat briefly about how the morning is going.",
            goals = listOf("Say good morning politely", "Ask how their week has been", "Wish them a pleasant day"),
            cefrLevel = "A1",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a1_19_library_card",
            title = "Local Public Library",
            category = "Culture",
            iconEmoji = "📚",
            description = "Sign up for a library card, ask where language learning books are kept.",
            promptContext = "You are a quiet, helpful community librarian guiding visitors to fiction and language reference sections.",
            goals = listOf("Ask how to register for a card", "Inquire where foreign language books are", "Ask loan duration for books"),
            cefrLevel = "A1",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a1_20_phone_call_simple",
            title = "Answering the Phone",
            category = "Communication",
            iconEmoji = "📞",
            description = "Answer a friendly incoming call, confirm who is speaking, leave a simple message.",
            promptContext = "You are calling a friend's home and asking if they are available to talk for two minutes.",
            goals = listOf("Identify yourself on the phone", "Ask if the person is available", "Say you will call back later"),
            cefrLevel = "A1",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        )
    )

    // ==========================================
    // 20 SCENARIOS - A2: ELEMENTARY (DAILY LIFE)
    // ==========================================
    val scenariosA2 = listOf(
        PracticeScenario(
            id = "sc_a2_01_pharmacy",
            title = "At the Pharmacy",
            category = "Health",
            iconEmoji = "💊",
            description = "Describe symptoms like headache, sore throat, or allergies and ask for medicine.",
            promptContext = "You are a licensed pharmacist in a downtown chemist. Listen to symptoms and recommend lozenges or pain relievers.",
            goals = listOf("Explain your headache or allergy symptom", "Ask about dosage and how often to take it", "Ask if it causes drowsiness"),
            cefrLevel = "A2",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a2_02_doctor_general",
            title = "Doctor's Appointment",
            category = "Health",
            iconEmoji = "🩺",
            description = "Explain how long you have felt unwell, temperature, and answer medical history questions.",
            promptContext = "You are a caring general practitioner conducting a routine examination. Ask when symptoms began.",
            goals = listOf("Describe when your pain started", "State if you have fever or fatigue", "Ask if you need a prescription"),
            cefrLevel = "A2",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a2_03_bike_rental",
            title = "Renting a City Bike",
            category = "Travel",
            iconEmoji = "🚲",
            description = "Rent a bicycle or e-scooter, inquire about deposit, helmet, and return points.",
            promptContext = "You manage a city bike rental stand near the river. Explain hourly rates, lock codes, and helmet safety.",
            goals = listOf("Ask for a bike for three hours", "Check helmet and lock availability", "Ask where you can return the bike"),
            cefrLevel = "A2",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a2_04_train_delays",
            title = "Train Station & Delays",
            category = "Travel",
            iconEmoji = "🚆",
            description = "Check track numbers, handle a delayed train, and exchange your ticket.",
            promptContext = "You are an information desk officer at the central railway station during rush hour.",
            goals = listOf("Inquire which platform the train departs from", "Ask how long the delay will be", "Request ticket rebooking"),
            cefrLevel = "A2",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a2_05_restaurant_reservation",
            title = "Reserving a Table",
            category = "Food & Drink",
            iconEmoji = "🍽️",
            description = "Call a restaurant to book an outdoor table for 4 people with dietary preferences.",
            promptContext = "You are the host at a popular bistro taking reservations over the phone.",
            goals = listOf("Book a table for four on Friday evening", "Request an outdoor terrace seat", "Mention one vegetarian guest"),
            cefrLevel = "A2",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a2_06_clothing_return",
            title = "Exchanging an Item",
            category = "Shopping",
            iconEmoji = "🔄",
            description = "Return a sweater with a receipt, explain it was the wrong size, and pick an exchange.",
            promptContext = "You are a customer service representative at a department store processing returns.",
            goals = listOf("Show receipt and explain size issue", "Ask if store credit or refund is possible", "Select a replacement size"),
            cefrLevel = "A2",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a2_07_gym_signup",
            title = "Joining a Gym",
            category = "Fitness",
            iconEmoji = "🏋️",
            description = "Inquire about membership plans, locker rooms, classes, and sign up for a trial pass.",
            promptContext = "You are a fitness club advisor giving a gym tour and detailing month-to-month plans.",
            goals = listOf("Ask about monthly subscription costs", "Inquire what group fitness classes are included", "Ask for a 1-day trial workout"),
            cefrLevel = "A2",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a2_08_dinner_invitation",
            title = "Inviting a Friend to Dinner",
            category = "Social",
            iconEmoji = "🍝",
            description = "Invite a coworker or friend over to your place for homemade pasta or tacos.",
            promptContext = "You are a friendly friend flattered by an invitation to dinner. Inquire what to bring.",
            goals = listOf("Propose a day and time for dinner", "Ask if they have food allergies", "Tell them what you plan to cook"),
            cefrLevel = "A2",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a2_09_running_late",
            title = "Apologizing for Delay",
            category = "Daily Life",
            iconEmoji = "⏳",
            description = "Send a text or call explaining traffic or train breakdown and give your ETA.",
            promptContext = "You are waiting at the cinema entrance for a friend who is running 15 minutes late.",
            goals = listOf("Apologize sincerely for being late", "Explain the transport reason", "Give an accurate expected arrival time"),
            cefrLevel = "A2",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a2_10_coworking_wifi",
            title = "Working at a Café & Wi-Fi",
            category = "Tech & Work",
            iconEmoji = "💻",
            description = "Ask for the Wi-Fi password, locate a power outlet, and order tea.",
            promptContext = "You run a laptop-friendly coffee shop. Tell patrons the network password and socket spots.",
            goals = listOf("Ask for the Wi-Fi network and password", "Find a table near an electrical outlet", "Order a refill beverage"),
            cefrLevel = "A2",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a2_11_lost_property",
            title = "Lost & Found Office",
            category = "Essentials",
            iconEmoji = "🎒",
            description = "Describe a lost backpack or umbrella left on the metro line.",
            promptContext = "You are a lost-and-found station clerk checking storage logs for passenger property.",
            goals = listOf("Report where and when you lost the item", "Give brand, color, and contents", "Leave your phone number for updates"),
            cefrLevel = "A2",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a2_12_hair_salon",
            title = "At the Hair Salon / Barber",
            category = "Personal Care",
            iconEmoji = "✂️",
            description = "Explain how much to trim, style preferences, and shampoo preferences.",
            promptContext = "You are an experienced hairstylist consulting with a client before washing and cutting.",
            goals = listOf("Specify how short to cut the sides", "Ask for a wash and light styling", "Confirm if the water temperature is good"),
            cefrLevel = "A2",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a2_13_post_office",
            title = "Sending a Parcel at Post Office",
            category = "Services",
            iconEmoji = "📦",
            description = "Weigh a package, choose standard vs express shipping, and fill out customs label.",
            promptContext = "You are a postal worker weighing boxes and offering insurance and delivery speed options.",
            goals = listOf("Ask the shipping rate for airmail", "Choose between tracking and standard mail", "Buy packing tape or stamps"),
            cefrLevel = "A2",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a2_14_sim_card",
            title = "Buying a Prepaid SIM Card",
            category = "Tech",
            iconEmoji = "📶",
            description = "Choose mobile data allowance, check roaming coverage, and activate the SIM.",
            promptContext = "You work at a telecom kiosk helping international travelers set up local eSIMs or physical cards.",
            goals = listOf("Ask for a 20GB tourist data plan", "Confirm if tethering/hotspot works", "Verify that phone is unlocked"),
            cefrLevel = "A2",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a2_15_museum_tour",
            title = "Visiting an Art Museum",
            category = "Culture",
            iconEmoji = "🎨",
            description = "Buy admission tickets, rent an audio guide, and locate the impressionist gallery.",
            promptContext = "You are an informative museum ticketing docent providing floor plans and exhibition highlights.",
            goals = listOf("Ask for student or youth discount ticket", "Request an audio guide in your language", "Ask which floor the special exhibit is on"),
            cefrLevel = "A2",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a2_16_food_complaint",
            title = "Addressing a Restaurant Issue",
            category = "Food & Drink",
            iconEmoji = "🍲",
            description = "Politely inform the waiter your soup is lukewarm or that the wrong side dish arrived.",
            promptContext = "You are an attentive waiter eager to ensure customers have an enjoyable meal and correct mistakes immediately.",
            goals = listOf("Politely call the server over", "Explain that the soup is cold", "Request a warm replacement gracefully"),
            cefrLevel = "A2",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a2_17_picnic_planning",
            title = "Organizing a Park Picnic",
            category = "Social",
            iconEmoji = "🧺",
            description = "Coordinate with friends who brings snacks, blanket, speakers, and drinks.",
            promptContext = "You are a friend enthusiastic about a sunny Saturday afternoon picnic at the botanical garden.",
            goals = listOf("Suggest meeting at 2 PM by the fountain", "Volunteer to bring cheese and fruit", "Ask what games or music to prepare"),
            cefrLevel = "A2",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a2_18_pet_vet",
            title = "Veterinary Clinic Checkup",
            category = "Pets & Care",
            iconEmoji = "🐾",
            description = "Bring a cat or dog for vaccination, describe eating habits and weight.",
            promptContext = "You are a gentle veterinary technician weighing a pet and recording vaccine history.",
            goals = listOf("State your pet's age and breed", "Describe changes in their appetite", "Ask when the next booster shot is due"),
            cefrLevel = "A2",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a2_19_airbnb_host",
            title = "Airbnb Host Instructions",
            category = "Travel",
            iconEmoji = "🔑",
            description = "Ask your host for the apartment lockbox code, garbage rules, and heater settings.",
            promptContext = "You are an accommodating Airbnb host texting your guest welcoming instructions.",
            goals = listOf("Confirm your arrival time", "Ask how to operate the air conditioner/heater", "Ask where recycling bins are located"),
            cefrLevel = "A2",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        ),
        PracticeScenario(
            id = "sc_a2_20_daily_routine",
            title = "Describing Your Daily Routine",
            category = "Daily Life",
            iconEmoji = "🌅",
            description = "Share what time you wake up, your commute, work habits, and evening relaxation.",
            promptContext = "You are a tandem language exchange buddy chatting about morning habits and evening hobbies.",
            goals = listOf("Describe what time you start your day", "Explain how you commute to work or university", "Mention what you like to do to unwind"),
            cefrLevel = "A2",
            proficiencyLevel = ProficiencyLevel.BEGINNER
        )
    )

    // ==========================================
    // 20 SCENARIOS - B1: INTERMEDIATE (INDEPENDENT)
    // ==========================================
    val scenariosB1 = listOf(
        PracticeScenario(
            id = "sc_b1_01_apartment_viewing",
            title = "Apartment Viewing & Tenancy",
            category = "Housing",
            iconEmoji = "🏢",
            description = "Tour a rental flat, inspect light and heating, discuss utilities and lease duration.",
            promptContext = "You are a real estate agent showing an unfurnished 2-room apartment. Answer questions about building noise and lease terms.",
            goals = listOf("Ask whether heating and water are included in rent", "Inquire about minimum lease duration and deposit", "Ask if pets or balcony plants are allowed"),
            cefrLevel = "B1",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b1_02_bank_account",
            title = "Opening a Local Bank Account",
            category = "Finance",
            iconEmoji = "🏦",
            description = "Submit proof of address, discuss checking account fees, debit cards, and online banking.",
            promptContext = "You are a retail bank personal banker guiding an international resident through KYC documentation.",
            goals = listOf("State the purpose of opening the account", "Compare monthly fee structures and transfer limits", "Set up mobile app two-factor authentication"),
            cefrLevel = "B1",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b1_03_job_interview",
            title = "Job Interview: Experience & Strengths",
            category = "Career",
            iconEmoji = "💼",
            description = "Introduce your professional background, past project achievements, and team collaboration style.",
            promptContext = "You are a hiring manager interviewing a candidate for a cross-functional role. Ask about teamwork and problem-solving.",
            goals = listOf("Highlight a key project success from your past role", "Explain how you handle workplace deadlines", "Ask thoughtful questions about company culture"),
            cefrLevel = "B1",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b1_04_creative_hobbies",
            title = "Discussing Creative Passions",
            category = "Culture",
            iconEmoji = "🎨",
            description = "Discuss photography, painting, music production, or pottery techniques and inspiration.",
            promptContext = "You are a fellow creative artist at a workshop discussing artistic processes and favorite tools.",
            goals = listOf("Describe what inspired you to start your craft", "Explain a technical challenge you recently overcame", "Compare traditional vs digital methods"),
            cefrLevel = "B1",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b1_05_car_rental_insurance",
            title = "Car Rental & Collision Insurance",
            category = "Travel",
            iconEmoji = "🚗",
            description = "Select vehicle tier, inspect existing scratches, evaluate collision damage waiver options.",
            promptContext = "You are an airport car rental desk supervisor presenting insurance tiers and fuel return policies.",
            goals = listOf("Choose between automatic or manual transmission", "Evaluate full coverage insurance vs basic deductible", "Document pre-existing dents on vehicle inspection sheet"),
            cefrLevel = "B1",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b1_06_landlord_repairs",
            title = "Requesting Urgent Home Repairs",
            category = "Housing",
            iconEmoji = "🔧",
            description = "Contact your property manager regarding a leaking pipe or broken heating unit.",
            promptContext = "You are a property manager fielding a maintenance request from a worried tenant.",
            goals = listOf("Explain the urgency and location of the water leak", "Describe steps you took to mitigate damage", "Coordinate a time slot for the plumber visit"),
            cefrLevel = "B1",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b1_07_surprise_party",
            title = "Organizing a Surprise Party",
            category = "Social",
            iconEmoji = "🎉",
            description = "Conspire with friends on venue selection, guest list secrecy, budget pooling, and decor.",
            promptContext = "You are a close mutual friend planning a 30th birthday surprise for a colleague.",
            goals = listOf("Propose a covert schedule so the guest doesn't find out", "Assign catering and music responsibilities", "Calculate estimated per-person cost share"),
            cefrLevel = "B1",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b1_08_lost_passport_police",
            title = "Reporting Stolen Items to Police",
            category = "Emergency",
            iconEmoji = "👮",
            description = "File an official police report for a stolen wallet and passport for insurance and embassy purposes.",
            promptContext = "You are a police station intake officer recording a sworn statement regarding a pickpocketing incident.",
            goals = listOf("Narrate the chronological sequence of events", "List lost ID cards, currency amount, and credit cards", "Request a stamped crime reference report for embassy"),
            cefrLevel = "B1",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b1_09_medical_specialist",
            title = "Consulting a Medical Specialist",
            category = "Health",
            iconEmoji = "🩻",
            description = "Discuss chronic knee pain or back stiffness with an orthopedic specialist and explore therapy.",
            promptContext = "You are an orthopedic physician reviewing mobility and recommending physical therapy.",
            goals = listOf("Describe pain triggers (walking stairs, running)", "Detail treatments you previously attempted", "Inquire about physiotherapy and recovery timeline"),
            cefrLevel = "B1",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b1_10_movie_review",
            title = "Film & Cinema Critique",
            category = "Entertainment",
            iconEmoji = "🎬",
            description = "Analyze cinematography, plot twists, character arcs, and compare director styles.",
            promptContext = "You are an enthusiastic cinephile discussing a newly premiered psychological thriller.",
            goals = listOf("Summarize the overarching narrative without major spoilers", "Evaluate the lead actor's dramatic performance", "Explain whether the ending felt earned or forced"),
            cefrLevel = "B1",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b1_11_university_courses",
            title = "Academic Advising & Electives",
            category = "Education",
            iconEmoji = "🎓",
            description = "Discuss credit prerequisites, seminar workload, and thesis topics with an academic counselor.",
            promptContext = "You are a university academic counselor advising an undergraduate on course registration.",
            goals = listOf("Explain your career ambitions and preferred electives", "Ask about prerequisite credits for advanced seminars", "Plan a manageable semester workload balance"),
            cefrLevel = "B1",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b1_12_cooking_recipe",
            title = "Discussing Cooking & Diets",
            category = "Food & Drink",
            iconEmoji = "🍲",
            description = "Exchange authentic family recipes, discuss plant-based substitutions, and cooking techniques.",
            promptContext = "You are an experienced home chef exchanging culinary tricks and spice secrets with a friend.",
            goals = listOf("Explain step-by-step preparation of a signature dish", "Suggest healthier or vegetarian ingredient swaps", "Discuss the cultural history behind the dish"),
            cefrLevel = "B1",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b1_13_mountain_hiking",
            title = "Booking a Guided Mountain Hike",
            category = "Adventure",
            iconEmoji = "🏔️",
            description = "Inquire about trail elevation, required gear, weather hazards, and fitness requirements.",
            promptContext = "You are an alpine mountain guide organizing a full-day summit expedition.",
            goals = listOf("Describe your physical hiking endurance and experience", "Check if crampons, poles, or waterproof boots are needed", "Review emergency rendezvous points and weather protocols"),
            cefrLevel = "B1",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b1_14_isp_tech_support",
            title = "Internet Provider Technical Support",
            category = "Tech",
            iconEmoji = "🌐",
            description = "Troubleshoot persistent packet loss, router firmware updates, and scheduled maintenance.",
            promptContext = "You are a technical support agent diagnosing fiber optic connectivity issues.",
            goals = listOf("Report specific blinking router LED status and error codes", "Describe troubleshooting steps already attempted (restart, cables)", "Schedule an on-site technician dispatch if required"),
            cefrLevel = "B1",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b1_15_flea_market_negotiation",
            title = "Vintage Antiques Negotiation",
            category = "Shopping",
            iconEmoji = "🏺",
            description = "Examine provenance of a mid-century lamp or antique watch, negotiate a fair bundle price.",
            promptContext = "You are a passionate flea market antique dealer who appreciates knowledgeable collectors.",
            goals = listOf("Point out authentic patina and craftsmanship details", "Make a polite, well-reasoned counteroffer", "Agree on a bundle deal for multiple vintage items"),
            cefrLevel = "B1",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b1_16_literature_club",
            title = "Book Club Discussion",
            category = "Culture",
            iconEmoji = "📖",
            description = "Debate a classic novel's recurring motifs, moral ambiguity, and historical context.",
            promptContext = "You are a thoughtful member of a monthly book salon discussing a newly finished novel.",
            goals = listOf("Analyze the protagonist's underlying motivations", "Identify symbolic motifs (weather, mirrors, journey)", "Contrast the novel's themes with contemporary society"),
            cefrLevel = "B1",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b1_17_community_meeting",
            title = "Town Hall & Neighborhood Council",
            category = "Civic",
            iconEmoji = "🏛️",
            description = "Participate in a district council meeting regarding bike lanes, recycling, and public parks.",
            promptContext = "You are the community council chairperson facilitating public comments from residents.",
            goals = listOf("Express support or concern regarding proposed infrastructure", "Suggest constructive compromises to address traffic noise", "Advocate for greener public community spaces"),
            cefrLevel = "B1",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b1_18_roadside_assistance",
            title = "Car Breakdown on Highway",
            category = "Travel",
            iconEmoji = "⚠️",
            description = "Call auto club dispatch after a flat tire or engine overheating, describe mile marker.",
            promptContext = "You are an automobile club emergency road dispatch operator recording highway breakdown details.",
            goals = listOf("State your exact highway exit and safe hazard stance", "Describe engine noises, smoke, or flat tire damage", "Confirm membership number and dispatch ETA"),
            cefrLevel = "B1",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b1_19_travel_memories",
            title = "Sharing Travel & Culture Shocks",
            category = "Culture",
            iconEmoji = "✈️",
            description = "Exchange amusing anecdotes about travel mishaps, unexpected hospitality, and customs.",
            promptContext = "You are a fellow backpacker sharing stories over tea in a cozy hostel lounge.",
            goals = listOf("Narrate an unforgettable cultural miscommunication", "Describe unexpected warmth shown by locals", "Reflect on how travel broadened your perspective"),
            cefrLevel = "B1",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b1_20_booking_mixup",
            title = "Resolving a Hotel Booking Error",
            category = "Travel",
            iconEmoji = "🛎️",
            description = "Arrive at a fully booked hotel where your confirmation is missing; negotiate an upgrade.",
            promptContext = "You are a hotel duty manager attempting to resolve an overbooking discrepancy gracefully.",
            goals = listOf("Present booking confirmation email and transaction voucher", "Maintain calm, assertive professional demeanor", "Negotiate complimentary upgrade or partner hotel suite"),
            cefrLevel = "B1",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        )
    )

    // ==========================================
    // 20 SCENARIOS - B2: UPPER INTERMEDIATE (PROFESSIONAL)
    // ==========================================
    val scenariosB2 = listOf(
        PracticeScenario(
            id = "sc_b2_01_salary_negotiation",
            title = "Salary & Compensation Negotiation",
            category = "Career",
            iconEmoji = "💼",
            description = "Negotiate base salary, performance bonuses, remote work flexibility, and equity.",
            promptContext = "You are a senior vice president of talent conducting a formal offer package negotiation.",
            goals = listOf("Benchmark your market value and key deliverables", "Propose a balanced compensation package with performance incentives", "Negotiate flexible work-from-home allowances"),
            cefrLevel = "B2",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b2_02_current_affairs",
            title = "Debating Global News & Geopolitics",
            category = "Current Events",
            iconEmoji = "📰",
            description = "Examine economic trends, geopolitical developments, and media objectivity with nuance.",
            promptContext = "You are an analytical foreign affairs journalist hosting a lively roundtable discussion.",
            goals = listOf("Articulate nuanced perspectives without partisan bias", "Cite statistical evidence or economic indicators", "Acknowledge valid counterarguments before concluding"),
            cefrLevel = "B2",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b2_03_startup_pitch",
            title = "Pitching a Startup to Investors",
            category = "Business",
            iconEmoji = "🚀",
            description = "Deliver an elevator pitch highlighting problem statement, TAM, unit economics, and moat.",
            promptContext = "You are a venture capital partner probing the viability and defensibility of a new tech startup.",
            goals = listOf("Define the core market friction clearly in two sentences", "Explain customer acquisition channels and CAC-to-LTV ratio", "Defend your competitive moat against established incumbents"),
            cefrLevel = "B2",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b2_04_performance_review",
            title = "Constructive Performance Appraisal",
            category = "Career",
            iconEmoji = "📈",
            description = "Provide and receive constructive feedback on quarterly OKRs and leadership milestones.",
            promptContext = "You are an empathetic engineering director conducting a bi-annual review with a team lead.",
            goals = listOf("Celebrate tangible cross-functional achievements", "Frame areas for growth constructively with actionable steps", "Establish collaborative goals for the next quarter"),
            cefrLevel = "B2",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b2_05_business_dinner",
            title = "Executive Business Dinner Etiquette",
            category = "Networking",
            iconEmoji = "🥂",
            description = "Navigate high-stakes executive small talk, table manners, and subtle partnership overtures.",
            promptContext = "You are a prospective corporate partner dining at a Michelin-starred restaurant.",
            goals = listOf("Engage in sophisticated cultural and culinary conversation", "Steer the dialogue smoothly toward strategic synergies", "Toast to mutual prosperity with polished toast etiquette"),
            cefrLevel = "B2",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b2_06_climate_change",
            title = "Renewable Energy & Sustainability",
            category = "Environment",
            iconEmoji = "🌱",
            description = "Debate carbon taxation, nuclear power, solar microgrids, and circular economy strategies.",
            promptContext = "You are an environmental policy advisor presenting at a clean energy sustainability summit.",
            goals = listOf("Compare cost-benefit tradeoffs between wind, solar, and nuclear", "Analyze corporate supply chain carbon disclosure mandates", "Propose viable municipal incentives for circular recycling"),
            cefrLevel = "B2",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b2_07_vip_client_crisis",
            title = "De-escalating a VIP Client Crisis",
            category = "Client Relations",
            iconEmoji = "🛡️",
            description = "Handle a furious enterprise client whose major software release failed on launch day.",
            promptContext = "You are the frustrated COO of an enterprise client demanding immediate answers for an outage.",
            goals = listOf("Acknowledge business impact empathetically without defensiveness", "Present a transparent incident post-mortem timeline", "Offer comprehensive SLA credits and preventative remediation"),
            cefrLevel = "B2",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b2_08_ai_future_of_work",
            title = "Artificial Intelligence & Future of Work",
            category = "Technology",
            iconEmoji = "🤖",
            description = "Debate generative AI automation, workforce reskilling, and intellectual property rights.",
            promptContext = "You are a tech futurist and labor economist discussing workforce displacement.",
            goals = listOf("Evaluate jobs most vulnerable to cognitive automation", "Discuss ethical frameworks for AI training data attribution", "Advocate for human-in-the-loop collaborative intelligence"),
            cefrLevel = "B2",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b2_09_cultural_adaptation",
            title = "Expat Life & Cultural Integration",
            category = "Sociology",
            iconEmoji = "🌍",
            description = "Discuss the psychological phases of culture shock, reverse culture shock, and belonging.",
            promptContext = "You are an intercultural psychologist moderating a panel of long-term expatriates.",
            goals = listOf("Reflect on personal cognitive adaptations living abroad", "Contrast high-context vs low-context communication styles", "Examine strategies for building authentic local roots"),
            cefrLevel = "B2",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b2_10_wedding_toast",
            title = "Giving a Wedding Toast & Speeches",
            category = "Social",
            iconEmoji = "💒",
            description = "Deliver a heartwarming, humorous, and respectful toast for your best friend's wedding.",
            promptContext = "You are an honored wedding guest listening attentively to the Maid of Honor / Best Man speech.",
            goals = listOf("Open with an engaging, affectionate personal anecdote", "Balance tasteful humor with heartfelt emotional depth", "Deliver a memorable closing blessing to the newlyweds"),
            cefrLevel = "B2",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b2_11_project_crisis",
            title = "Project Management Crisis Protocol",
            category = "Management",
            iconEmoji = "📊",
            description = "Restructure project scope, reallocate budget, and mediate engineering vs marketing bottlenecks.",
            promptContext = "You are a seasoned Scrum Master facilitating an urgent risk-rebalancing sprint review.",
            goals = listOf("Identify critical path blockers transparently", "Cut non-essential features to guarantee on-time MVP delivery", "Realign cross-team sprint deliverables effectively"),
            cefrLevel = "B2",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b2_12_urban_architecture",
            title = "Architecture & Urban Master Planning",
            category = "Design",
            iconEmoji = "🏙️",
            description = "Discuss pedestrianization, biophilic skyscraper design, and historical preservation laws.",
            promptContext = "You are an urban planning architect debating zoning regulations and green corridors.",
            goals = listOf("Compare modernist brutalism with traditional facades", "Advocate for pedestrian plazas over four-lane boulevards", "Discuss climate resilience in coastal civil engineering"),
            cefrLevel = "B2",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b2_13_financial_markets",
            title = "Financial Markets & Asset Allocation",
            category = "Finance",
            iconEmoji = "💹",
            description = "Discuss inflation hedging, ETF diversification, bond yields, and central bank interest rates.",
            promptContext = "You are an investment portfolio manager conducting an asset allocation briefing.",
            goals = listOf("Analyze how interest rate hikes impact equity multiples", "Contrast active hedge fund alpha with passive index investing", "Explain diversification across asset classes during volatility"),
            cefrLevel = "B2",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b2_14_team_conflict",
            title = "Workplace Conflict Mediation",
            category = "Human Resources",
            iconEmoji = "🤝",
            description = "Mediate an interpersonal dispute between two senior designers regarding credit and vision.",
            promptContext = "You are an experienced HR dispute resolution mediator facilitating neutral ground.",
            goals = listOf("Separate subjective emotional grievances from factual deliverables", "Foster empathy by having each party summarize the other's perspective", "Draft an agreed collaborative protocol moving forward"),
            cefrLevel = "B2",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b2_15_supplier_negotiation",
            title = "Supplier Contract & SLA Negotiation",
            category = "Procurement",
            iconEmoji = "📋",
            description = "Negotiate volume tiered pricing, warranty turnaround times, and penalty clauses for delays.",
            promptContext = "You are a manufacturing procurement lead securing high-precision hardware components.",
            goals = listOf("Leverage multi-year volume commitments to secure discounted unit costs", "Enforce strict delivery penalty clauses for shipping delays", "Establish quarterly quality assurance audit milestones"),
            cefrLevel = "B2",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b2_16_keynote_qa",
            title = "Conference Keynote Q&A Session",
            category = "Public Speaking",
            iconEmoji = "🎤",
            description = "Deliver crisp answers to challenging, skeptical questions from industry conference attendees.",
            promptContext = "You are a conference session chair fielding questions from a packed auditorium.",
            goals = listOf("Reframe aggressive questions neutrally before answering", "Provide empirical case study examples supporting your hypothesis", "Invite the questioner to continue the debate during networking"),
            cefrLevel = "B2",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b2_17_work_life_balance",
            title = "Workplace Mental Health & Burnout",
            category = "Wellbeing",
            iconEmoji = "🧘",
            description = "Advocate for asynchronous communication, reasonable boundaries, and mental health sabbaticals.",
            promptContext = "You are an organizational psychologist advising corporate leaders on burnout prevention.",
            goals = listOf("Identify insidious symptoms of chronic occupational burnout", "Propose company policies limiting after-hours email communication", "Normalize seeking professional mental wellness support at work"),
            cefrLevel = "B2",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b2_18_wine_critique",
            title = "Sommelier Wine Tasting & Pairing",
            category = "Gastronomy",
            iconEmoji = "🍷",
            description = "Evaluate terroir, acidity, tannins, oak aging notes, and pair wines with multi-course dining.",
            promptContext = "You are a master sommelier guiding a blind tasting session across European wine regions.",
            goals = listOf("Articulate aroma profiles (blackberry, flint, cedar, leather)", "Explain how soil minerality and climate define vintage characteristics", "Pair crisp whites or robust reds with contrasting food flavors"),
            cefrLevel = "B2",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b2_19_tech_ethics",
            title = "Ethical Dilemmas in Bio-tech & AI",
            category = "Ethics",
            iconEmoji = "⚖️",
            description = "Debate algorithmic bias, genetic editing with CRISPR, and data sovereignty regulations.",
            promptContext = "You are a member of an institutional ethics committee reviewing a high-impact research proposal.",
            goals = listOf("Analyze potential dual-use risks of emerging bio-technologies", "Apply utilitarian vs deontological moral frameworks to automated decisions", "Formulate governance safeguards that protect vulnerable demographics"),
            cefrLevel = "B2",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        ),
        PracticeScenario(
            id = "sc_b2_20_corporate_sustainability",
            title = "ESG & Corporate Social Responsibility",
            category = "Business",
            iconEmoji = "♻️",
            description = "Propose eliminating single-use plastics, fair-trade supply chains, and transparent emissions audits.",
            promptContext = "You are an executive committee member presenting a 5-year ESG overhaul to the board of directors.",
            goals = listOf("Demonstrate that sustainable operations yield long-term cost reductions", "Outline verifiable milestones to combat greenwashing accusations", "Align company metrics with international ESG standards"),
            cefrLevel = "B2",
            proficiencyLevel = ProficiencyLevel.INTERMEDIATE
        )
    )

    // ==========================================
    // 20 SCENARIOS - C1: ADVANCED (MASTERY & NUANCE)
    // ==========================================
    val scenariosC1 = listOf(
        PracticeScenario(
            id = "sc_c1_01_legal_contract",
            title = "Contract Dispute & Liability Consultation",
            category = "Law",
            iconEmoji = "⚖️",
            description = "Analyze indemnification clauses, breach of fiduciary duty, and force majeure interpretations.",
            promptContext = "You are a senior corporate litigation partner evaluating an intellectual property breach claim.",
            goals = listOf("Scrutinize ambiguous wording in liability limitation clauses", "Assess jurisdictional precedents regarding force majeure events", "Draft a rigorous settlement positioning strategy"),
            cefrLevel = "C1",
            proficiencyLevel = ProficiencyLevel.ADVANCED
        ),
        PracticeScenario(
            id = "sc_c1_02_thesis_defense",
            title = "Doctoral Thesis Defense Inquisition",
            category = "Academia",
            iconEmoji = "🎓",
            description = "Defend quantitative methodology, regression models, epistemological assumptions, and findings.",
            promptContext = "You are a distinguished faculty committee dean interrogating a PhD candidate's dissertation.",
            goals = listOf("Defend methodological validity against theoretical critique", "Deconstruct counter-hypotheses with empirical robustness tests", "Articulate scholarly contributions to peer-reviewed literature"),
            cefrLevel = "C1",
            proficiencyLevel = ProficiencyLevel.ADVANCED
        ),
        PracticeScenario(
            id = "sc_c1_03_merger_acquisition",
            title = "Cross-Border M&A Strategy & Synergies",
            category = "Finance",
            iconEmoji = "🤝",
            description = "Structure leveraged buyout valuations, antitrust regulatory hurdles, and post-merger culture.",
            promptContext = "You are a managing director at an investment bank advising on a 4-billion-dollar acquisition.",
            goals = listOf("Quantify revenue and cost synergies across international jurisdictions", "Anticipate antitrust scrutiny from competition authorities", "Plan organizational integration to mitigate talent attrition"),
            cefrLevel = "C1",
            proficiencyLevel = ProficiencyLevel.ADVANCED
        ),
        PracticeScenario(
            id = "sc_c1_04_medical_grand_rounds",
            title = "Medical Grand Rounds Diagnostic Case",
            category = "Medicine",
            iconEmoji = "🩺",
            description = "Diagnose a complex multi-system autoimmune disorder through differential lab evaluations.",
            promptContext = "You are the chief of internal medicine leading differential diagnosis with clinical fellows.",
            goals = listOf("Synthesize atypical hematological and radiological markers", "Rule out mimic pathologies methodically using clinical evidence", "Formulate a personalized multi-disciplinary therapeutic protocol"),
            cefrLevel = "C1",
            proficiencyLevel = ProficiencyLevel.ADVANCED
        ),
        PracticeScenario(
            id = "sc_c1_05_philosophical_discourse",
            title = "Epistemology, Consciousness & Free Will",
            category = "Philosophy",
            iconEmoji = "🧠",
            description = "Debate determinism, compatibilism, Chalmers' hard problem of consciousness, and AI sentience.",
            promptContext = "You are a philosophy professor moderating an advanced seminar on mind-body dualism.",
            goals = listOf("Critique reductionist materialist accounts of qualia", "Examine compatibilist definitions of moral responsibility", "Formulate thought experiments challenging Cartesian boundaries"),
            cefrLevel = "C1",
            proficiencyLevel = ProficiencyLevel.ADVANCED
        ),
        PracticeScenario(
            id = "sc_c1_06_literary_hermeneutics",
            title = "Literary Semiotics & Narrative Subversion",
            category = "Literature",
            iconEmoji = "📜",
            description = "Deconstruct postmodern narrative unreliability, intertextuality, and socio-political satire.",
            promptContext = "You are a comparative literature scholar examining 20th-century avant-garde prose.",
            goals = listOf("Explicate how syntactic dissonance reflects existential alienation", "Trace intertextual allusions to classical mythology", "Critique ideological undercurrents using post-structuralist lens"),
            cefrLevel = "C1",
            proficiencyLevel = ProficiencyLevel.ADVANCED
        ),
        PracticeScenario(
            id = "sc_c1_07_diplomatic_summit",
            title = "Multilateral Diplomatic Treaty Negotiations",
            category = "Diplomacy",
            iconEmoji = "🌐",
            description = "Draft diplomatic communiqués balancing national sovereignty, territorial accords, and sanctions.",
            promptContext = "You are an ambassadorial chief negotiator brokering a ceasefire and trade accord at the UN.",
            goals = listOf("Employ constructive ambiguity to reconcile conflicting national interests", "Safeguard fundamental national red lines without derailing consensus", "Draft enforceable dispute-settlement dispute arbitration mechanisms"),
            cefrLevel = "C1",
            proficiencyLevel = ProficiencyLevel.ADVANCED
        ),
        PracticeScenario(
            id = "sc_c1_08_press_conference_crisis",
            title = "High-Stakes Crisis Press Conference",
            category = "Public Relations",
            iconEmoji = "🎙️",
            description = "Field blistering inquiries from investigative journalists regarding an industrial safety failure.",
            promptContext = "You are a veteran press corps reporter relentlessly pressing for accountability.",
            goals = listOf("Communicate transparent empathy while safeguarding organizational legal standing", "Counter speculative misinformation with verified operational data", "Delineate independent third-party audit commitments decisively"),
            cefrLevel = "C1",
            proficiencyLevel = ProficiencyLevel.ADVANCED
        ),
        PracticeScenario(
            id = "sc_c1_09_patent_litigation",
            title = "Intellectual Property & Patent Infringement",
            category = "Law",
            iconEmoji = "📑",
            description = "Debate claim construction, doctrine of equivalents, prior art validity, and royalty damages.",
            promptContext = "You are a senior patent attorney presenting oral arguments before an appellate tribunal.",
            goals = listOf("Demonstrate non-obviousness of the patented claim over cited prior art", "Argue literal vs functional equivalence of contested micro-architecture", "Quantify reasonable royalty calculations under Georgia-Pacific factors"),
            cefrLevel = "C1",
            proficiencyLevel = ProficiencyLevel.ADVANCED
        ),
        PracticeScenario(
            id = "sc_c1_10_cognitive_neuroscience",
            title = "Neurobiology & Neuroplasticity Frontiers",
            category = "Science",
            iconEmoji = "🔬",
            description = "Discuss synaptic pruning, optogenetics, connectome mapping, and memory consolidation.",
            promptContext = "You are a principal investigator leading an international neurobiology consortium.",
            goals = listOf("Analyze fMRI signal artifacts vs genuine hemodynamic responses", "Discuss cellular mechanisms underlying long-term potentiation", "Evaluate translational roadblocks in neurodegenerative therapeutics"),
            cefrLevel = "C1",
            proficiencyLevel = ProficiencyLevel.ADVANCED
        ),
        PracticeScenario(
            id = "sc_c1_11_vc_due_diligence",
            title = "Venture Capital Deep-Tech Due Diligence",
            category = "Finance",
            iconEmoji = "💎",
            description = "Interrogate technical feasibility, burn rate sustainability, and enterprise moat.",
            promptContext = "You are a technical general partner at a tier-1 venture fund scrutinizing a Series B round.",
            goals = listOf("Stress-test hardware development timelines against thermodynamic limits", "Scrutinize cohort retention curves and enterprise expansion revenue", "Structure liquidation preferences and protective minority voting rights"),
            cefrLevel = "C1",
            proficiencyLevel = ProficiencyLevel.ADVANCED
        ),
        PracticeScenario(
            id = "sc_c1_12_environmental_treaty",
            title = "International Maritime & Climate Law",
            category = "Policy",
            iconEmoji = "🌊",
            description = "Negotiate high-seas biodiversity conventions, exclusive economic zones, and deep-sea mining.",
            promptContext = "You are a special rapporteur advising the International Tribunal for the Law of the Sea.",
            goals = listOf("Reconcile resource extraction rights with the common heritage of mankind doctrine", "Establish binding transboundary environmental impact assessments", "Draft compliance enforcement protocols for international waters"),
            cefrLevel = "C1",
            proficiencyLevel = ProficiencyLevel.ADVANCED
        ),
        PracticeScenario(
            id = "sc_c1_13_art_provenance",
            title = "Art Provenance & Repatriation Controversy",
            category = "Culture",
            iconEmoji = "🏛️",
            description = "Debate museum repatriation claims for colonial antiquities, archival evidence, and legal ethics.",
            promptContext = "You are an international cultural heritage legal expert arbitrating restitution claims.",
            goals = listOf("Trace historical chains of ownership through fractured colonial archives", "Balance universal heritage preservation arguments against indigenous sovereignty", "Structure bilateral long-term cultural loan and restitution frameworks"),
            cefrLevel = "C1",
            proficiencyLevel = ProficiencyLevel.ADVANCED
        ),
        PracticeScenario(
            id = "sc_c1_14_cross_cultural_psychology",
            title = "Cross-Cultural Psychology & Cognitive Bias",
            category = "Psychology",
            iconEmoji = "👥",
            description = "Examine WEIRD psychological samples, collectivist vs individualist self-construal, and linguistic relativity.",
            promptContext = "You are a cross-cultural psychology researcher chairing a symposium on global cognition.",
            goals = listOf("Critique over-generalization of Western psychological constructs worldwide", "Analyze linguistic determinism vs universal cognitive primitives", "Design culturally invariant behavioral psychometric instruments"),
            cefrLevel = "C1",
            proficiencyLevel = ProficiencyLevel.ADVANCED
        ),
        PracticeScenario(
            id = "sc_c1_15_supply_chain_hedging",
            title = "Global Supply Chain Geopolitical Risk Hedging",
            category = "Operations",
            iconEmoji = "🚢",
            description = "Model choke points in maritime straits, dual-sourcing strategies, and nearshoring resilience.",
            promptContext = "You are a chief supply chain officer presenting geopolitical risk contingencies to the executive board.",
            goals = listOf("Quantify revenue exposure to maritime transit chokepoint blockades", "Model cost implications of friendshoring manufacturing hubs", "Establish real-time multimodal inventory visibility dashboards"),
            cefrLevel = "C1",
            proficiencyLevel = ProficiencyLevel.ADVANCED
        ),
        PracticeScenario(
            id = "sc_c1_16_quantum_cryptography",
            title = "Post-Quantum Cryptography & Security",
            category = "Technology",
            iconEmoji = "🔐",
            description = "Assess lattice-based cryptography, Shor's algorithm threat models, and zero-knowledge proofs.",
            promptContext = "You are a chief information security officer advising on national infrastructure migration.",
            goals = listOf("Evaluate vulnerability of existing RSA and ECC encryption against quantum adversaries", "Plan migration pathways to NIST-approved post-quantum algorithms", "Assess performance overheads of homomorphic and zero-knowledge systems"),
            cefrLevel = "C1",
            proficiencyLevel = ProficiencyLevel.ADVANCED
        ),
        PracticeScenario(
            id = "sc_c1_17_constitutional_liberties",
            title = "Constitutional Jurisprudence & Surveillance",
            category = "Law",
            iconEmoji = "🏛️",
            description = "Debate algorithmic surveillance, biometric data privacy, and proportional state intrusion.",
            promptContext = "You are a constitutional court justice questioning counsel in a landmark civil liberties case.",
            goals = listOf("Apply the proportionality doctrine to state bulk-data interception", "Define the constitutional scope of privacy in decentralized digital realms", "Balance public security imperatives with inviolable human dignity rights"),
            cefrLevel = "C1",
            proficiencyLevel = ProficiencyLevel.ADVANCED
        ),
        PracticeScenario(
            id = "sc_c1_18_cinematic_auteur",
            title = "Cinematic Auteur Philosophy & Aesthetics",
            category = "Arts",
            iconEmoji = "🎥",
            description = "Dissect non-linear temporality, French New Wave ethos, Tarkovsky's sculpting in time, and mise-en-scène.",
            promptContext = "You are a veteran film festival programmer interviewing a visionary auteur director.",
            goals = listOf("Elucidate how pacing and long takes alter perceptual temporality", "Analyze diegetic vs non-diegetic sound design as psychological extensions", "Debate whether digital rendering compromises analog celluloid authenticity"),
            cefrLevel = "C1",
            proficiencyLevel = ProficiencyLevel.ADVANCED
        ),
        PracticeScenario(
            id = "sc_c1_19_labor_strike_mediation",
            title = "Labor Union Collective Bargaining & Strike",
            category = "Labor Relations",
            iconEmoji = "✊",
            description = "Mediate high-stakes negotiations between transit union leadership and government ministers.",
            promptContext = "You are a neutral national labor arbitrator tasked with averting a nationwide transit shutdown.",
            goals = listOf("Reconcile real-wage inflation erosion with state municipal budget caps", "Address safety concerns regarding automated train operations", "Draft a mutually binding compromise accord preventing industrial action"),
            cefrLevel = "C1",
            proficiencyLevel = ProficiencyLevel.ADVANCED
        ),
        PracticeScenario(
            id = "sc_c1_20_semiotics_linguistics",
            title = "Linguistic Evolution, Semiotics & AI",
            category = "Linguistics",
            iconEmoji = "🗣️",
            description = "Discuss Saussurean structuralism, pragmatic semantic drift, and large language model syntactics.",
            promptContext = "You are a keynote speaker at the World Congress of Theoretical Linguistics.",
            goals = listOf("Contrast formal Chomskyan grammar with emergent statistical language models", "Trace historical phonological shifts and lexical grammaticalization", "Argue whether statistical token prediction encompasses genuine semantic understanding"),
            cefrLevel = "C1",
            proficiencyLevel = ProficiencyLevel.ADVANCED
        )
    )

    fun getScenariosByLevel(cefr: String): List<PracticeScenario> {
        return when (cefr.uppercase()) {
            "A1" -> scenariosA1
            "A2" -> scenariosA2
            "B1" -> scenariosB1
            "B2" -> scenariosB2
            "C1" -> scenariosC1
            else -> allScenarios
        }
    }

    fun findById(id: String): PracticeScenario? {
        return allScenarios.find { it.id == id }
    }
}
