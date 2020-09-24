package com.bala.NRSImageOperations;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.util.EC2MetadataUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;

import java.util.List;

@SpringBootApplication(exclude = ContextStackAutoConfiguration.class)
@Slf4j
public class NrsImageOperationsApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(NrsImageOperationsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		AWSCredentialsProvider provider = InstanceProfileCredentialsProvider.getInstance();
		log.info("accesskey {}",provider.getCredentials().getAWSAccessKeyId());
		AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard().withCredentials(provider).build();
		List<Bucket> bucketList = amazonS3.listBuckets();
		bucketList.stream().forEach(s -> System.out.println("Bucket Name "+s));
		log.info("Bucket List size {}",bucketList.size());
		EC2MetadataUtils.NetworkInterface networkInterface = EC2MetadataUtils.getNetworkInterfaces().stream().findAny().get();
		log.info("Host Name {}",networkInterface.getHostname());
		log.info("getLocalIPv4s {}",networkInterface.getLocalIPv4s());
		log.info("getPublicIPv4s {}",networkInterface.getPublicIPv4s());
		log.info("getOwnerId {}",networkInterface.getOwnerId());
	}
}
