package com.xm.commerce.regex;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {

    @Test
    public void test() {
        String link = "<https://bestrylife.myshopify.com/admin/api/2020-07/products.json?fields=id&limit=50&page_info=eyJsYXN0X2lkIjo1MzU3MDcyNjc5MDY4LCJsYXN0X3ZhbHVlIjoiNjNwY3NcL3NldCAyMDIwIGJ1Y2sgYmFsbCIsImRpcmVjdGlvbiI6Im5leHQifQ>; rel=\"next\"";
        String pattern = "(.*)<(https://.*com.*)>; rel=\"next\"";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(link);
        if (matcher.find()) {
            System.out.println(matcher.group(2));
        }
    }
}
