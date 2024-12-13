package org.scoreservice.client;

/*source: https://github.com/bogdanmarculescu/microservices2024/blob/main/ongoing/src/main/java/org/cards/ongoinground/clients/RecorderClient.java*/
import lombok.extern.slf4j.Slf4j;
import org.scoreservice.dtos.quizDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class QuizServiceClient {

    private final String restServiceUrl;
    private final RestTemplate restTemplate;

    public QuizServiceClient(
            RestTemplateBuilder restTemplateBuilder,
            @Value("${quizservice.url}") final String url
    ) {
        this.restTemplate = restTemplateBuilder.build();
        this.restServiceUrl = url;
    }

    public quizDTO getQuizById(Long quizId) {
        String url = restServiceUrl + "/" + quizId;
        quizDTO quizDetails = null;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            quizDetails = restTemplate.getForObject(url, quizDTO.class);
        } catch (Exception e) {
            log.error("Error fetching quiz details for quizId {}: {}", quizId, e.getMessage());
            e.printStackTrace();
        }

        log.info("Fetched quiz details: {}", quizDetails);
        return quizDetails;
    }
}
