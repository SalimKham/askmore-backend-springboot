package io.khaminfo.askmore.payload;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.files.WriteMode;


@Component
public class ScriptToDropBox implements Runnable {
	private static final String ACCESS_TOKEN = "z62njhqXOzEAAAAAAAAAAZML1_3JqgmU_SOJ8J0KP-xZUDt0ON0CSBMHWWqMNhpC";
	private static DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/ask-more").build();
	private static DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
	private static Object monitor = new Object();
	public static boolean change  = false;
	private static JdbcTemplate jdbcTemplate;

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(60000);
             if(!change)
            	 continue;
       		 File dump = new File("schema2.sql");
				synchronized (monitor) {

					if (dump.exists()) {
						dump.delete();
					}
					
					jdbcTemplate.execute("script to '" + dump.getPath() + "'");
					
					FileInputStream file2 = new FileInputStream(dump);
                    //client.files().delete("/sql/schema.sql");
                   
					client.files().uploadBuilder("/sql/schema.sql").withMode(WriteMode.OVERWRITE)

							.uploadAndFinish(new ByteArrayInputStream(new FileInputStream(dump).readAllBytes()));
					file2.close();
					
					System.out.println("upload finish");
					change = false;
				}

			} catch (InterruptedException e) {
				
			} catch (UploadErrorException e) {
				
			} catch (DbxException e) {
				System.out.println(e.getMessage());
			} catch (IOException e) {
				
			}

		}

	}
	public static void setJdbc(JdbcTemplate jdbc) {
		jdbcTemplate = jdbc;
	}

}
