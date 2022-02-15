package io.khaminfo.askmore.services;

import java.awt.image.BufferedImage;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.Principal;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.WriteMode;
import com.dropbox.core.v2.sharing.RequestedVisibility;
import com.dropbox.core.v2.sharing.SharedLinkSettings;

import io.khaminfo.askmore.domain.Crop;
import io.khaminfo.askmore.domain.Person;
import io.khaminfo.askmore.domain.UserInfo;
import io.khaminfo.askmore.exceptions.AccessException;
import io.khaminfo.askmore.payload.ScriptToDropBox;
import io.khaminfo.askmore.repositories.ProfileRepository;
import io.khaminfo.askmore.repositories.UserRepository;

@Service
public class ProfileService {
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProfileRepository profileRepository;
    private static final String ACCESS_TOKEN = "z62njhqXOzEAAAAAAAAAAZML1_3JqgmU_SOJ8J0KP-xZUDt0ON0CSBMHWWqMNhpC";
	DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/ask-more").build();
    DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
	public void changePassword(String oldPassword , String newPassword , Principal principal) {
		
		
		newPassword = bCryptPasswordEncoder.encode(newPassword);
		oldPassword = bCryptPasswordEncoder.encode(oldPassword);
	
		Person user = userRepository.findByUsername(principal.getName());
		if(bCryptPasswordEncoder.matches(user.getPassword(),oldPassword)) {
			throw new AccessException("wrong password.");
		}
		
		if(userRepository.updateUserPassword(newPassword, principal.getName())!=1)
		{
			throw new AccessException("Wrong password!!");
		}
		ScriptToDropBox.change = true;
	}
	
	
	public UserInfo updateInfo(UserInfo userInfo,Principal principal) {
	
		 UserInfo info = profileRepository.findById(userInfo.getId_Info()).get();
		 info.setFirstName(userInfo.getFirstName());
		 info.setLastName(userInfo.getLastName());
		 info.setAddress(userInfo.getAddress());
		 info.setBirthday(userInfo.getBirthday());
		 info.setBirthday_type(userInfo.getBirthday_type());
		 info.setSex(userInfo.getSex());
		 
		try {
			UserInfo result = profileRepository.save(info);
			ScriptToDropBox.change = true;
		 return result;
		}catch(Exception e) {
			e.printStackTrace();
			throw new AccessException(e.getMessage());
		}
	}
	public UserInfo getUserInfo(Principal principal) {
		Person user = userRepository.findByUsername(principal.getName());
	
		return user.getUserInfo();
	}
	
	
	public String updateUserPhoto(long profileId,MultipartFile file, Crop crop) {
		
		 try {

	        
	            byte[] bytes = file.getBytes();
	            ImageIcon img = new ImageIcon(bytes);
	   
	            float x_ratio = (float) img.getIconWidth() /crop.getDisplayWidth();
	            float y_ratio = (float) img.getIconHeight()/crop.getDisplayHeight();
	            String image_url="";
	            crop.setRatio(x_ratio, y_ratio);
				BufferedImage b = ImageUtils.crop(img.getImage(),crop.getX(),crop.getY(), crop.getWith(),crop.getHeight()	);
			    BufferedImage b2 = ImageUtils.resize(b, crop.getWith(), crop.getHeight(), 200,200);

			     String imageName = "profileimage"+profileId;
			 
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write( b2, "png", baos );
				baos.flush();
				client.files().uploadBuilder("/images/"+imageName+".png").withMode(WriteMode.OVERWRITE)
		                .uploadAndFinish(new ByteArrayInputStream(baos.toByteArray()));
	            image_url = client.sharing().createSharedLinkWithSettings("/images/"+imageName+".png", SharedLinkSettings.newBuilder().withRequestedVisibility(RequestedVisibility.PUBLIC).build()).getUrl();
	            image_url = image_url.substring(0,image_url.lastIndexOf('.')) +".png?raw=1";
	            b2 = ImageUtils.resize(b, crop.getWith(), crop.getHeight(), 56,56);
	            baos = new ByteArrayOutputStream();
	            ImageIO.write( b2, "png", baos );
				baos.flush();

				client.files().uploadBuilder("/images/"+imageName+"2.png").withMode(WriteMode.OVERWRITE)
                .uploadAndFinish(new ByteArrayInputStream(baos.toByteArray()));
        String image_url2 = client.sharing().createSharedLinkWithSettings("/images/"+imageName+"2.png", SharedLinkSettings.newBuilder().withRequestedVisibility(RequestedVisibility.PUBLIC).build()).getUrl();
        image_url2 = image_url2.substring(0,image_url2.lastIndexOf('.')) +".png?raw=1";
                image_url =  image_url2+"& "+image_url+"&";
				profileRepository.updateProfilePicture(profileId, image_url);
				ScriptToDropBox.change = true;
	            return image_url;

			    


	        } catch (Exception e) {
	        	e.printStackTrace();
	        	//throw new AccessException("SomeThing went Wrong!");
	        }
		 return "";
		
	}
	
	

}
