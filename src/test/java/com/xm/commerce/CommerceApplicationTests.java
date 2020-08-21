package com.xm.commerce;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class CommerceApplicationTests {

    @Resource
    private RestTemplate restTemplate;

    @Test
    public void contextLoads() {



    }

    private List<Object> getVariantsList(List<Object> lists){
        List<Object> result = new ArrayList<>();
        if (lists.size() == 0){
            return null;
        }else if (lists.size() == 1){
            return lists;
        }else {
            String[] options1 = (String[])lists.get(0);
            String[] options2 = (String[])lists.get(1);
            for (String option1 : options1) {
                for (String option2 : options2) {
                    Object[] temp = {option1, option2};
                    result.add(temp);
                }
            }
            lists.remove(0);
            lists.remove(1);
            lists.add(result);
            return getVariantsList(lists);
        }
    }
}