package com.starsmobi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;


@SpringBootApplication
public class ElasticsearchdemoApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchdemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ElasticsearchdemoApplication.class, args);


        String ff = "[[[113.839630,22.498528],[113.839274,22.499597]],[[113.839475,22.500667],[113.842763,22.503534]]]";

		Object[] rr = ToolUntils.stringPositionReplacement(ff);

		String cc = JSON.toJSONString(rr);

		System.out.print(cc);





	}

}
