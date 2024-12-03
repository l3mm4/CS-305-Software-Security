package com.snhu.sslserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.MessageDigest;


@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}

@RestController
class ServerController {

    /**
     * End point to generate a hash value for the given name.
     * 
     * @param name The input name (default: "Sean Jette").
     * @return HTML response displaying the name, algorithm used, and checksum value.
     * @throws Exception If a hashing error occurs.
     */
	@GetMapping("/hash")
	public String generateHash(@RequestParam(value = "name", defaultValue = "Sean Jette") String name) throws Exception {
	    // Call method to compute hash
	    String hashValue = computeHash(name);

	    // Build dynamic HTML response
	    StringBuilder response = new StringBuilder();
	    response.append("<p>Data: ").append(name).append("</p>");
	    response.append("<p>Name of Cipher Algorithm Used: SHA-256</p>");
	    response.append("<p>Checksum Value: ").append(hashValue).append("</p>");

	    return response.toString();
	}

    /**
     * Helper method to compute the SHA-256 hash for a given string.
     * 
     * @param data The input data to hash.
     * @return The hash value as a hex string.
     * @throws Exception If a hashing error occurs.
     */
    private String computeHash(String data) throws Exception {
        // Initialize the MessageDigest object for SHA-256
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(data.getBytes());

        // Generate the digest as a byte array
        byte[] digest = md.digest();

        // Convert the byte array to a hexadecimal string
        StringBuilder hexString = new StringBuilder();
        for (byte b : digest) {
            hexString.append(String.format("%02x", b)); // Zero-padded two-character hex
        }

        return hexString.toString();
    }
}
