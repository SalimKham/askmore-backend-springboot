package io.khaminfo.askmore.web;

import java.io.IOException;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.khaminfo.askmore.domain.Crop;
import io.khaminfo.askmore.domain.UserInfo;
import io.khaminfo.askmore.services.MapValidationErrorService;
import io.khaminfo.askmore.services.ProfileService;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
	@Autowired
	ProfileService profileService;
	@Autowired
	private MapValidationErrorService mapErrorService;

	@PostMapping("/uploadPhoto/")
	public ResponseEntity<?> singleFileUpload(@RequestParam("idInfo") long profileId , @RequestParam("cropString") String cropString, @RequestParam("file") MultipartFile file
		) throws JsonParseException, JsonMappingException, IOException {
		
		String[] cropValue = cropString.split(":");
		Crop crop = new Crop();
		if( cropValue[0].indexOf(".") != -1)
			cropValue[0] = cropValue[0].substring(0 , cropValue[0].indexOf("."));
		if( cropValue[1].indexOf(".") != -1)
			cropValue[1] = cropValue[1].substring(0 , cropValue[1].indexOf("."));
		crop.setX(Integer.parseInt(cropValue[0]));
		crop.setY(Integer.parseInt(cropValue[1]));
		crop.setWith(Integer.parseInt(cropValue[2]));
		crop.setHeight(Integer.parseInt(cropValue[3]));
		crop.setDisplayWidth(Integer.parseInt(cropValue[4]));
		crop.setDisplayHeight(Integer.parseInt(cropValue[5]));

		return new ResponseEntity<String>(
				profileService.updateUserPhoto( profileId,file, crop) , HttpStatus.OK);

	}

	@PostMapping("/updateAccount/{oldPassword}/{newPassword}")
	public ResponseEntity<?> registerUser(@PathVariable String oldPassword,@PathVariable String newPassword, Principal principal) {
		profileService.changePassword(oldPassword,newPassword, principal);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PostMapping("/updateProfile/")
	public ResponseEntity<?> updateInfo(@Valid @RequestBody UserInfo userinfo, Principal principal,BindingResult result)
			throws JsonParseException, JsonMappingException, IOException {
		// validator.validate(user, result);
		ResponseEntity<?> mappErr = mapErrorService.MapValidationService(result);
		if (mappErr != null)
			return mappErr;

		return new ResponseEntity<UserInfo>(profileService.updateInfo(userinfo,principal), HttpStatus.CREATED);
	}
	
	@GetMapping("/getProfile/")
	public ResponseEntity<?> getProfile(Principal principal){
	

		return new ResponseEntity<UserInfo>(profileService.getUserInfo(principal), HttpStatus.OK);
	}

}
