package com.xm.commerce;

import com.xm.commerce.system.util.MapStringUtil;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class Test2 {

    @Test
    void test2(){
            String a = "{\n" +
                    "    \"verson\": 1,\n" +
                    "    \"person\": {\n" +
                    "        \"age\": 22,\n" +
                    "        \"name\": \"aaa\"\n" +
                    "    },\n" +
                    "    \"status\": \"ok\",\n" +
                    "    \"book\": [\n" +
                    "        {\n" +
                    "            \"price\": 0,\n" +
                    "            \"bookname\": \"name0\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"price\": 1,\n" +
                    "            \"bookname\": \"name1\"\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"price\": 2,\n" +
                    "            \"bookname\": \"name2\"\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";

        JSONObject jsonObject = JSONObject.fromObject(a);
        Iterator keys = jsonObject.keys();
        while (keys.hasNext()) {
            String teams = (String) keys.next();
            System.out.println("teams= " + teams);
            String teamsInfo = jsonObject.optString(teams);
            System.out.println("teamsInfo= " + teamsInfo);
//            generateListsData(teamsInfo,teams);
        }

        System.out.println(jsonObject.get("person").toString());
    }

    @Test
    void test3(){
        String a = "{\n" +
                "    \"verson\": 1,\n" +
                "    \"person\": {\n" +
                "        \"age\": 22,\n" +
                "        \"name\": \"aaa\"\n" +
                "    },\n" +
                "    \"status\": \"ok\",\n" +
                "    \"book\": [\n" +
                "        {\n" +
                "            \"price\": 0,\n" +
                "            \"bookname\": \"name0\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"price\": 1,\n" +
                "            \"bookname\": \"name1\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"price\": 2,\n" +
                "            \"bookname\": \"name2\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        Map<String, Object> stringToMap = MapStringUtil.getStringToMap(a);
        System.out.println(stringToMap.get("person"));

    }

}
