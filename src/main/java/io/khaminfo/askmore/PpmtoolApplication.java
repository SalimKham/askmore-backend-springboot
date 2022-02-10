package io.khaminfo.askmore;

import java.io.ByteArrayOutputStream;

import java.io.IOException;

import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;

import io.khaminfo.askmore.payload.ScriptToDropBox;

@EnableAsync
@SpringBootApplication

public class PpmtoolApplication {

	private static final String ACCESS_TOKEN = "z62njhqXOzEAAAAAAAAAAZML1_3JqgmU_SOJ8J0KP-xZUDt0ON0CSBMHWWqMNhpC";
	private static DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/ask-more").build();
	private static DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
	@Autowired
	static DataSource dataSource;

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();

	}

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+3")); // set Time Zone to moscow
	}

	public static void main(String[] args) {
		try {

			ByteArrayOutputStream downloadFile = new ByteArrayOutputStream();

			try {
				 client.files().downloadBuilder("/sql/schema.sql").download(downloadFile);

			} finally {
				downloadFile.close();

				

			ApplicationContext applicationContext = SpringApplication.run(PpmtoolApplication.class, args);
			JdbcTemplate jdbc = applicationContext.getBean(JdbcTemplate.class);
			
	Resource resource = new ByteArrayResource(downloadFile.toByteArray());
		dataSource = jdbc.getDataSource();
		createDefaultDB(resource);
			ScriptToDropBox myThread = applicationContext.getBean(ScriptToDropBox.class);
			myThread.setJdbc(jdbc);
			new Thread(myThread).start();
			System.out.println("Thread started");
			}
		}
		// exception handled
		catch (DbxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getMessage());

		}

	}

	public static void createDefaultDB(Resource resource) {
		try {
			ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);

			databasePopulator.setScripts(resource);
		    
			databasePopulator.execute(dataSource);
			System.out.println("setting script done");

		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
