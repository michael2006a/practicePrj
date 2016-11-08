package com.trendmicro.skyaid.deepqa;

import org.apdplat.qa.SharedQuestionAnsweringSystem;
import org.apdplat.qa.model.CandidateAnswer;
import org.apdplat.qa.model.Question;

import java.io.IOException;
import java.util.List;

/**
 * Created by herbert_yin on 2016/7/25.
 */
public class WatsonBrother {

    public static void main(String[] args) throws IOException {
        String questionStr = "这孩子到底是谁的？";
        Question question = SharedQuestionAnsweringSystem.getInstance().answerQuestion(questionStr);
        if (question != null) {
            List<CandidateAnswer> candidateAnswers = question.getAllCandidateAnswer();
            int i = 1;
            for (CandidateAnswer candidateAnswer : candidateAnswers) {
                System.out.println((i++) + "、" + candidateAnswer.getAnswer() + ":" + candidateAnswer.getScore());
            }
        }
    }

}
