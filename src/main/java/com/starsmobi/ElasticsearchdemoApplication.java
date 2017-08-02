package com.starsmobi;

import com.starsmobi.domain.GoodsEs;
import com.starsmobi.domain.StoreEs;
import com.starsmobi.elasticsearchclient.ElasticsearchClient;
import com.starsmobi.service.IGoodService;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

@SpringBootApplication
public class ElasticsearchdemoApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchdemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ElasticsearchdemoApplication.class, args);
	}

}
