package com.yangzhuo.dozer;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class DozerTest {
    
    @Data
    @AllArgsConstructor
    class Score {
        String name;
        int age;
        String sex;
        String subject;
        int score;
    }

    @Data
    @AllArgsConstructor
    class ScoreStatus {
        String field;
        boolean enable;
    }
   
    @Test
    public void test() {
        List<Score> scores = createList();
        Map<String, IntSummaryStatistics> stringScoreMap = scores.stream()
                .collect(Collectors.groupingBy(Score::getName, Collectors.summarizingInt(Score::getScore)));
        List<ScoreStatus> scoreStatusList = createtScoreStatus();
        List<String> groupByFields = new ArrayList<>();
        for (int i = 0; i < scoreStatusList.size(); i++) {
            if (scoreStatusList.get(i).enable) {
                groupByFields.add(scoreStatusList.get(i).getField());
            }
        }
        String[] groupByFieldNames = groupByFields.toArray(new String[0]);
        
        Map<List<String>, List<Score>> map = DynamicGroupListByFiled(scores, groupByFieldNames);
        for(List<Score> scoreList : map.values()){
            int scoreCR = 0;
            int scoreDR = 0;
            for (int i = 0; i < scoreList.size(); i++) {
                scoreCR += scoreList.get(i).getScore();
                scoreDR += scoreList.get(i).getScore();
                if ("zz".equals(scoreList.get(i).getName())) {
                    scoreCR += 1;
                }
            }
            if (scoreCR != scoreDR) {
                System.out.println(scoreList.get(0).getName() + "分数报错");
            } else {
                System.out.println(scoreList.get(0).getName() + "分数正确");
            }
        }
        System.out.println();
    }

    //多字段排序
    //多字段排序
    public  Map<List<String>, List<Score>> DynamicGroupListByFiled(List<Score> data, String[] groupByFieldNames) {
        final MethodHandles.Lookup lookup = MethodHandles.lookup();
        List<MethodHandle> handles =
                Arrays.stream(groupByFieldNames)
                        .map(field -> {
                            try {
                                return lookup.findGetter(Score.class, field, String.class);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }).collect(Collectors.toList());
        return data.stream().collect(Collectors.groupingBy(
                d -> handles.stream()
                        .map(handle -> {
                            try {
                                return (String) handle.invokeExact(d);
                            } catch (Throwable e) {
                                throw new RuntimeException(e);
                            }
                        }).collect(Collectors.toList())
        ));
    }

    //按某字段分组后统计数量
    public Map<String, Integer> DynamicGroupListByFiled(List<Score> data, String fieldName) {
        final MethodHandles.Lookup lookup = MethodHandles.lookup();
        try {
            MethodHandle handle = lookup.findGetter(Score.class, fieldName, String.class);
             return data.stream().collect(Collectors.groupingBy(
                    d -> {
                        try {
                            return (String) handle.invokeExact(d);
                        } catch (Throwable e) {
                            throw new RuntimeException(e);
                        }
                    } ,Collectors.summingInt(Score::getScore)));
        }
        catch (Throwable e)
        {
            throw new RuntimeException();
        }


    }
    
    
    private List<Score> createList() {
        List<Score> scoreList = new ArrayList<>();
        Score score1 = new Score("yz", 24, "male", "math",90);
        Score score2 = new Score("yz", 24, "male", "chinese",89);
        Score score3 = new Score("yz", 24, "male", "english",79);
        Score score4 = new Score("lr", 25, "female", "math",70);
        Score score5 = new Score("lr", 25, "female", "chinese",90);
        Score score6 = new Score("lr", 25, "female", "english",90);
        Score score7 = new Score("zz", 20, "male", "math",57);
        Score score8 = new Score("zz", 20, "male", "chinese",59);
        Score score9 = new Score("zz", 20, "male", "english",58);
        scoreList.add(score1);
        scoreList.add(score2);
        scoreList.add(score3);
        scoreList.add(score4);
        scoreList.add(score5);
        scoreList.add(score6);
        scoreList.add(score7);
        scoreList.add(score8);
        scoreList.add(score9);
        return scoreList;
    }
    
    List<ScoreStatus> createtScoreStatus() {
        List<ScoreStatus> scoreStatuses = new ArrayList<>();
        ScoreStatus scoreStatus1 = new ScoreStatus("name", true);
        ScoreStatus scoreStatus2 = new ScoreStatus("age", false);
        ScoreStatus scoreStatus3 = new ScoreStatus("sex", true);
        ScoreStatus scoreStatus4 = new ScoreStatus("subject", false);
        ScoreStatus scoreStatus5 = new ScoreStatus("score", false);
        scoreStatuses.add(scoreStatus1);
        scoreStatuses.add(scoreStatus2);
        scoreStatuses.add(scoreStatus3);
        scoreStatuses.add(scoreStatus4);
        scoreStatuses.add(scoreStatus5);
        return scoreStatuses;
    }

    private static String getFiledType(Object object, String fieldName) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            if (fieldName.equals(fields[i].getName())) {
                return fields[i].getType().getName();
            }
        }
        return null;
    }
}

