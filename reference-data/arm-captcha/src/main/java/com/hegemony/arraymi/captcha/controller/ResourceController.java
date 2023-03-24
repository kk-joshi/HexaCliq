package com.hegemony.arraymi.captcha.controller;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hegemony.arraymi.captcha.form.Response;
import com.hegemony.arraymi.captcha.model.Resource;
import com.hegemony.arraymi.captcha.service.AppException;
import com.hegemony.arraymi.captcha.service.IResourceService;

@RestController
@CrossOrigin(origins = "${allowed.origins}")
public class ResourceController {

//	private static final int IMG_WIDTH = 100;
	@Autowired
	private IResourceService resourceService;

	@GetMapping("/resource/{resourceId}")
	public void showResource(@PathVariable long resourceId, @RequestParam String token, HttpServletResponse response) {
		if (0 == resourceId) {
			throw new AppException(HttpStatus.BAD_REQUEST, "Please provide valid resourceId!!");
		}
		Resource resource = resourceService.getResource(resourceId, token);
		if (null != resource) {
			try {
				ServletOutputStream outputStream = response.getOutputStream();
				//TODO Add compression logic
//				BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(resource.getData())); // load image
//				BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_WIDTH, originalImage.getType());
//				Graphics2D g = resizedImage.createGraphics();
//				g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_WIDTH, null);
//				g.dispose();
//				ByteArrayOutputStream baos = new ByteArrayOutputStream();
//				ImageIO.write(originalImage, "jpg", baos);
//				baos.flush();
//				byte[] imageInByte = baos.toByteArray();
//				baos.close();
				response.setContentLength(resource.getData().length);
				response.setContentType("");
				outputStream.write(resource.getData());
				outputStream.flush();
			} catch (IOException e) {
				throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong!");
			}
		}
	}

	@PostMapping("/upload")
	public ResponseEntity<?> uplaodImage(@RequestParam("imageFile") MultipartFile file,
			@RequestParam("type") String type) {
		long resourceId = resourceService.uploadResource(file, type);
		return ResponseEntity.ok(new Response(resourceId + ""));
	}

}
