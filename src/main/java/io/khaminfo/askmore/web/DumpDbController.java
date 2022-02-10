package io.khaminfo.askmore.web;
import java.io.File;

import java.io.IOException;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RestController;


 class DumpDbController  {

    private static   Object monitor = new Object();

    private static  JdbcTemplate jdbcTemplate;
   

    @Autowired
    public DumpDbController(JdbcTemplate jdbcTemplate) {
    	System.out.println("Inside Controller");
        this.jdbcTemplate = jdbcTemplate;
        
    }

  
    public static void dumpDb() throws IOException {
        synchronized (monitor) {
        	
        	//String path = ResourceUtils.getURL("classpath:").getPath()+"schema.sql";
   		 File dump = new File("schema2.sql");
            if (dump.exists()) {
                dump.delete();
            }
            System.out.println(dump.getAbsolutePath());
            jdbcTemplate.execute("script to '" + dump.getAbsolutePath() + "'");
            System.out.println("end of Monitoring");
            
        }
    }


	
	
}