package com.backend.MyBackend.restaurant.agent;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface RestaurantAgent{

    /**
     * The system message gives the AI its "personality" and rules. It tells the model exactly how it should behave.
     */
    @SystemMessage({
            "You are an intelligent, polite AI assistant for our restaurant platform.",
            "Your job is to answer user questions about restaurants, their operational status, cuisines, and menus.",
            "You must always use your provided tools to look up the data from the database. Do not make up facts.",
            "If a user asks about a restaurant or a dish that isn't in the database, politely explain that you couldn't find it.",
            "Keep your answers friendly, concise, and helpful."
    })
    String chat(String userMessage);
}
