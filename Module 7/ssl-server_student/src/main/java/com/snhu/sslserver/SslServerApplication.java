package com.snhu.sslserver;

import java.security.MessageDigest;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SslServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SslServerApplication.class, args);
    }
}

@RestController
class SslServerController {

    /**
     * End point to generate a hash value for the given name.
     * 
     * @param string The input name (default: "Hello World Check Sum!").
     * @return HTML response displaying the name, algorithm used, and checksum value.
     * @throws Exception If a hashing error occurs.
     */
    
    @GetMapping("/hash")
    public String generateHash(@RequestParam(value = "name", defaultValue = "Hello World Check Sum!") String name) {
        try {
            // Sanitize input to prevent potential XSS
            String sanitizedInput = StringEscapeUtils.escapeHtml4(name);

            // Call method to compute hash
            String hashValue = computeHash(sanitizedInput);
            
            // Build dynamic HTML response
            StringBuilder response = new StringBuilder();
            response.append("<p>This is an unencrypted string: ").append(sanitizedInput).append("</p>");
            response.append("<p>Name of Cipher Algorithm Used: SHA-256</p>");
            response.append("<p>Checksum Value for String: ").append(hashValue).append("</p>");
            
            return response.toString();
        } catch (Exception e) {
            // Log the exception details for internal purposes (use proper logging in a real app)
            e.printStackTrace(); // Ideally, use a logging framework like Log4j
            
            // Return a generic error message to the user
            return "<p>An error occurred while processing the request. Please try again later.</p>";
        }
    }
    
    /**
     * Helper method to compute the SHA-256 hash for a given string.
     * 
     * @param data - The input data to hash.
     * @return - The hash value as a hex string.
     * @throws Exception - If a hashing error occurs.
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