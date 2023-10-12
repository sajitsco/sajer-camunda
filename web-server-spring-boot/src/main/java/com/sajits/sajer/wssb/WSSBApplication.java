package com.sajits.sajer.wssb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.sajits.sajer.core.Test;

import org.apache.commons.io.FileUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@SpringBootApplication
public class WSSBApplication   extends SpringBootServletInitializer {
    public static void main(String[] args) {
		SpringApplication.run(WSSBApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WSSBApplication.class);
	}

	@CrossOrigin("*")
	@RestController
	class RestTest {

		@GetMapping("/test")
		public String test() {
			com.sajits.sajer.camunda.Test.Test1 ts1 = new com.sajits.sajer.camunda.Test.Test1();
			Test ts = new Test();
			
			return ts.test() + "  " + ts1.test();
		}

		@PostMapping("/upload")
		public String handleFileUpload(@RequestPart(value = "file") final MultipartFile uploadfile) throws IOException {
			return saveUploadedFiles(uploadfile);
		}

		private String saveUploadedFiles(final MultipartFile file) throws IOException {
			final byte[] bytes = file.getBytes();
			URL sqlScriptUrl = RestTest.class.getClassLoader().getResource("/");
			String resourcesPath = sqlScriptUrl.getPath();
			resourcesPath = resourcesPath.replaceAll("/C:/", "C:/") + "temp/";
			File directory = new File(resourcesPath);
			if (! directory.exists()){
				directory.mkdir();
			}
			final Path path = Paths.get(resourcesPath  + file.getOriginalFilename());
			System.out.println(path);
			Files.write(path, bytes);

			String encodedString = new String(Base64.getEncoder().encode(path.toString().getBytes()));
			return encodedString;
		}

		public File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
			File destFile = new File(destinationDir, zipEntry.getName());
		
			String destDirPath = destinationDir.getCanonicalPath();
			String destFilePath = destFile.getCanonicalPath();
		
			if (!destFilePath.startsWith(destDirPath + File.separator)) {
				throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
			}
		
			return destFile;
		}

		@PostMapping("/uploadapk")
		public String handleAPKUpload(@RequestPart(value = "file") final MultipartFile uploadfile, @RequestHeader Map<String, String> headers) throws IOException {
			String envPassCode = System.getenv("SAJER_PASSCODE");
			if( envPassCode == null ){
				envPassCode = "123456";
			}
			String passCode = headers.get("sajer-passcode");
			if( passCode == null ){
				passCode = "";
			}
			if( !passCode.equals(envPassCode)) {
				return "Forbidden";
			}

			final byte[] bytes = uploadfile.getBytes();
			URL sqlScriptUrl = RestTest.class.getClassLoader().getResource("/");
			String resourcesPath = sqlScriptUrl.getPath();
			resourcesPath = resourcesPath.replaceAll("/C:/", "C:/") + "static/apps/";
			File directory = new File(resourcesPath);

			FileUtils.deleteDirectory(directory);

			if (! directory.exists()){

				directory.mkdir();
			}
			final Path path = Paths.get(resourcesPath  + uploadfile.getOriginalFilename());
			System.out.println(path);
			Files.write(path, bytes);

			return new String("File Uploaded");
		}

		@PostMapping("/uploadfe")
		public String handleFEFileUpload(@RequestPart(value = "file") final MultipartFile uploadfile, @RequestHeader Map<String, String> headers) throws IOException {
			String envPassCode = System.getenv("SAJER_PASSCODE");
			if( envPassCode == null ){
				envPassCode = "123456";
			}
			String passCode = headers.get("sajer-passcode");
			if( passCode == null ){
				passCode = "";
			}
			if( !passCode.equals(envPassCode)) {
				return "Forbidden";
			}

			String filePath = saveFEUploadedFiles(uploadfile);

			return new String(Base64.getEncoder().encode(filePath.getBytes()));
		}

		private String saveFEUploadedFiles(final MultipartFile file) throws IOException {
			final byte[] bytes = file.getBytes();
			URL sqlScriptUrl = RestTest.class.getClassLoader().getResource("/");
			String resourcesPath = sqlScriptUrl.getPath();
			resourcesPath = resourcesPath.replaceAll("/C:/", "C:/") + "static/";
			File directory = new File(resourcesPath);

			FileUtils.deleteDirectory(directory);

			if (! directory.exists()){
				directory.mkdir();
			}
			final Path path = Paths.get(resourcesPath  + file.getOriginalFilename());
			System.out.println(path);
			Files.write(path, bytes);

						File destDir = new File(resourcesPath);
			byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(path.toString()));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
           File newFile = newFile(destDir, zipEntry);
    if (zipEntry.isDirectory()) {
        if (!newFile.isDirectory() && !newFile.mkdirs()) {
            throw new IOException("Failed to create directory " + newFile);
        }
    } else {
        // fix for Windows-created archives
        File parent = newFile.getParentFile();
        if (!parent.isDirectory() && !parent.mkdirs()) {
            throw new IOException("Failed to create directory " + parent);
        }

        // write file content
        FileOutputStream fos = new FileOutputStream(newFile);
        int len;
        while ((len = zis.read(buffer)) > 0) {
            fos.write(buffer, 0, len);
        }
        fos.close();
    }
    zipEntry = zis.getNextEntry();
        }

        zis.closeEntry();
        zis.close();


		File fileToDelete = new File(path.toString());
		fileToDelete.delete();
	
			return path.toString();
		}


	}
}
