package org.quizapp.loginservice.client;

import org.quizapp.loginservice.dtos.FlashcardDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FlashcardClient {

    private final RestTemplate restTemplate;
    private final String flashcardServiceUrl = "http://flashcardservice:8085/flashcards/quiz";

    public FlashcardClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<FlashcardDTO> getFlashcards(Long quizId) {
        String url = flashcardServiceUrl + "/" + quizId;
        return List.of(restTemplate.getForObject(url, FlashcardDTO[].class));
    }
}
