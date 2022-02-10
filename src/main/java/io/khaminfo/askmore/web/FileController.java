package io.khaminfo.askmore.web;

import java.io.DataInputStream;


import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/files")
public class FileController {
	
	@GetMapping("/pdfs/{fileName}")
	public void getPdf(@PathVariable String  fileName ,  HttpServletResponse response) throws Exception {
	
//		 Path path = Paths.get("src/main/resources/static"+"/pdfs/" + fileName);
//		 
//		 DataInputStream in = new DataInputStream(new FileInputStream(path.toFile().getAbsolutePath()));
		
		
		response.setHeader("Content-disposition: ", "attachment; filename="+fileName+".pdf");
		response.setContentType("application/pdf");
		response.setHeader("Content-Transfer-Encoding", "download");
	
	OutputStream output = new DataOutputStream(response.getOutputStream());

//		long reset = path.toFile().length();
//		int buffer_size = 5*1024*1024;
//		byte[] buffer;
//		while( reset > 0 ){
//			if( buffer_size < reset ){
//			}else{
//				buffer_size = (int)reset;
//			}
//			reset -= buffer_size;
//			buffer = new byte[buffer_size];
//			in.readFully(buffer);
//			output.write(buffer);
//		}
		
	}
	
	@GetMapping("/profile/{fileName}")
	public void getImage(@PathVariable String  fileName ,  HttpServletResponse response) throws IOException {

		System.out.println("We are here" + fileName);

		 Path path = Paths.get("src/main/resources/static"+"/profile_picture/" + fileName);
		 DataInputStream in = new DataInputStream(new FileInputStream(path.toFile().getAbsolutePath()));
		
		response.setContentType("image/png");
		response.setHeader("Content-Transfer-Encoding", "download");
		
		DataOutputStream output = new DataOutputStream(response.getOutputStream());
		long reset = path.toFile().length();
		int buffer_size = 5*1024*1024;
		byte[] buffer;
		while( reset > 0 ){
			if( buffer_size < reset ){
			}else{
				buffer_size = (int)reset;
			}
			reset -= buffer_size;
			buffer = new byte[buffer_size];
			in.readFully(buffer);
			output.write(buffer);
		}
		
	}

}
