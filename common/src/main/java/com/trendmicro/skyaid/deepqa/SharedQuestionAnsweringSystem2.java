package com.trendmicro.skyaid.deepqa;

import org.apdplat.qa.datasource.BaiduDataSource;
import org.apdplat.qa.system.CommonQuestionAnsweringSystem;
import org.apdplat.qa.system.QuestionAnsweringSystem;

/**
 * Created by herbert_yin on 2016/7/25.
 */

public class SharedQuestionAnsweringSystem2 {
    private static final QuestionAnsweringSystem QUESTION_ANSWERING_SYSTEM = new CommonQuestionAnsweringSystem();
    static{
        QUESTION_ANSWERING_SYSTEM.setDataSource(new BaiduDataSource());
    }
    public static QuestionAnsweringSystem getInstance(){
        return QUESTION_ANSWERING_SYSTEM;
    }
    public static void main(String[] args){

    }
}