package com.sajits.sajer.acesa.archive.infrastructure;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.auth.http.HttpCredentialsAdapter;
//import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import java.nio.file.Files;

public class GoogleDrive {
    public static String listFiles() {
        String result1 = "";
        
		try {
            //AccessToken at = new AccessToken(gat.getAccess_token(), new Date(System.currentTimeMillis() + 3600 * 1000));
            //GoogleCredentials credentials = GoogleCredentials.create(at);
            GoogleCredentials credentials = GoogleCredentials.fromStream(GoogleDrive.class.getResourceAsStream("/key-sajgroupco.json"));
            credentials = credentials.createScoped("https://www.googleapis.com/auth/drive");
            HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials); 
            JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
            NetHttpTransport HTTP_TRANSPORT;
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, requestInitializer).setApplicationName("app1").build();
			FileList result = service.files().list().setFields("*").execute();
			List<File> files = result.getFiles();
			if (files == null || files.isEmpty()) {
				result1 += "No files found.\n";
			} else {
				result1 += "Files:\n";
				for (File file : files) {
					result1 += (file.getName() + " (" + file.getId() + ") " +  file.getDescription() + "\n") ;
				}
			}
			//final File fileMetadata = new File();
			//fileMetadata.setName("aaa");
			//fileMetadata.setDescription("RoboZonky aktualizuje obsah tohoto adresáře jednou denně brzy ráno.");
			//fileMetadata.setMimeType("application/vnd.google-apps.folder");
			//service.files().create(fileMetadata).execute();

			//File f = service.files().get("1DvFpa2dHXUEoZ78UPCl7ww6N6gCWE-Ed").setFields("*").execute();
			//System.out.printf("%s (%s)\n", f.getName(), f.getDescription());
		} catch (GeneralSecurityException | IOException e) {
			System.out.println("Error on Google Drive API Connection : " + e.getMessage());
		}
        return result1;
    }

	public static String saveFile(com.sajits.sajer.acesa.archive.domain.File ufile){
		try {
			GoogleCredentials credentials = GoogleCredentials.fromStream(GoogleDrive.class.getResourceAsStream("/key-sajgroupco.json"));
            credentials = credentials.createScoped("https://www.googleapis.com/auth/drive");
            HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials); 
            JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
            NetHttpTransport HTTP_TRANSPORT;
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, requestInitializer).setApplicationName("app1").build();

			File fileMetadata = new File();
			fileMetadata.setName(ufile.getName());
			fileMetadata.setParents(Collections.singletonList("1KOq8tr-4oUYtIZoI4E0C9c-Rk1JEDKND"));

			java.io.File filePath = new java.io.File(ufile.getPath());
			String mimeType = Files.probeContentType(filePath.toPath());
			FileContent mediaContent = new FileContent(mimeType, filePath);

			File file = service.files().create(fileMetadata, mediaContent)
			.setFields("id")
			.execute();
			System.out.println("File ID: " + file.getId());
			return file.getId();

		} catch (GeneralSecurityException | IOException e) {
			System.out.println("Error on Google Drive API Connection : " + e.getMessage());
		}
		return "";
	}
}
