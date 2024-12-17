package org.quizapp.quizservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quizapp.quizservice.eventdriven.QuizEventPublisher;
import org.quizapp.quizservice.model.Question;
import org.quizapp.quizservice.model.QuestionRepository;
import org.quizapp.quizservice.model.Quiz;
import org.quizapp.quizservice.model.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final QuizEventPublisher quizEventPublisher;

    @Override
    public Quiz retrieveQuiz(int quizId) {
        return quizRepository.findById(quizId).orElse(null);
    }

   /* @Override
    public Quiz addQuiz(Quiz quiz) {
        Quiz savedQuiz = quizRepository.save(quiz);
        return savedQuiz;
    }*/

    @Override
    public Quiz addQuiz(Quiz quiz) {
        for (Question question : quiz.getQuestions()) {
            question.setQuiz(quiz);
            log.info("Setting quiz for question: " + question.getId());
        }
        Quiz savedQuiz = quizRepository.save(quiz);
        log.info("Generated quiz ID: " + savedQuiz.getQuizId());

        if (savedQuiz.getQuizId() == null) {
            throw new RuntimeException("Quiz ID was not generated correctly.");
        }

        questionRepository.saveAll(quiz.getQuestions());

        return savedQuiz;
    }

    public void trackQuizStart(int quizId, String userId) {
        Quiz quiz = getQuizById(quizId);
        if (quiz == null) {
            throw new IllegalArgumentException("Quiz not found with ID: " + quizId);
        }

        log.info("User {} started quiz with ID {}", userId, quizId);

    }


    @Override
    public List<Quiz> getAllQuizzes() {
        return (List<Quiz>) quizRepository.findAll();
    }

    @Override
    public Quiz getQuizById(int quizId) {
        return quizRepository.findById(quizId).orElse(null);
    }

    @Override
    public void deleteQuiz(int quizId) {
        quizRepository.deleteById(quizId);
    }

    @Override
    public void saveQuiz(Quiz quiz) {
        for (Question question : quiz.getQuestions()) {
            question.setQuiz(quiz);
        }
        quizRepository.save(quiz);
    }

    public boolean validateAnswer(int quizId, int questionId, String userAnswer) {
        Quiz quiz = getQuizById(quizId);
        if (quiz == null) return false;

        return quiz.getQuestions().stream()
                .anyMatch(q -> q.getId().equals(questionId) && q.getAnswer().equalsIgnoreCase(userAnswer));
    }


}
