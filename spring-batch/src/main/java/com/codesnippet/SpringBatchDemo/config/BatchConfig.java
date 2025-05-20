package com.codesnippet.SpringBatchDemo.config;

import com.codesnippet.SpringBatchDemo.Person;
import com.codesnippet.SpringBatchDemo.config.PersonProcessor;
import com.codesnippet.SpringBatchDemo.config.PersonCsvItemReader;
import com.codesnippet.SpringBatchDemo.config.PersonJsonItemReader;
import com.codesnippet.SpringBatchDemo.config.PersonItemWriter;
import com.codesnippet.SpringBatchDemo.config.PersonSkipListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

	@Autowired
	private PersonCsvItemReader csvItemReader; // CSV Reader

	@Autowired
	private PersonJsonItemReader jsonItemReader; // JSON Reader

	@Autowired
	private PersonProcessor personProcessor;

	@Autowired
	private PersonItemWriter personItemWriter;

	@Autowired
	private PersonSkipListener skipListener;

	private static final String DATA_SOURCE = "csv"; // Change to "csv" to process CSV

	@Bean
	public Job job(JobRepository jobRepository, Step step) {
		return new JobBuilder("importPersons", jobRepository)
				.start(step)
				.build();
	}

	@Bean
	public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("data-import-step", jobRepository)
				.<Person, Person>chunk(10, transactionManager)
				.reader(selectReader())
				.processor(personProcessor)
				.writer(personItemWriter.writer())
				.faultTolerant()
				.retry(Exception.class)
				.retryLimit(3)
//				.listener(retryListener)
				.skip(Exception.class)
				.skipLimit(5)
				.listener(skipListener)
				.build();
	}

	// Dynamically select the reader (CSV or JSON)
	private ItemReader<Person> selectReader() {
		return DATA_SOURCE.equalsIgnoreCase("json") ? jsonItemReader.reader() : csvItemReader.reader();
	}
}
