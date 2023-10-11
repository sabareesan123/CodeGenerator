package com.dynamiccodegenerator.codeandtablegenerator.service;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaveDynamicClassToFile {
	
	
	final private RuntimeClassService runtimeClassService;
    public static void main(String[] args) throws IOException {
        // Assume you have dynamically generated class bytes in a byte array
        byte[] byteCode = new byte[22];

        // Save the bytes to a .class file
        saveClassToFile(byteCode, "Person.class");
        
        // Generate the corresponding .java source code and save it
        String javaCode = generateJavaSourceCode();
        saveJavaToFile(javaCode, "Person.java");
    }

    private static void saveClassToFile(byte[] byteCode, String fileName) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(byteCode);
        }
    }
//static
    private  String generateJavaSourceCode() {
    	String javacode2 = runtimeClassService.get(1).getBody();
        String javaCode = "public class Person {\n" +
                "    private long id;\n" +
                "    private String name;\n" +
                "    private String email;\n" +
                "    private String address;\n" +
                "\n" +
                "    // Getters and setters here\n" +
                "}";
        return javaCode;
    }

    private static void saveJavaToFile(String javaCode, String fileName) throws IOException {
        try (FileWriter writer = new FileWriter("src/main/java/com/dynamiccodegenerator/codeandtablegenerator/service/" + fileName)) {
            writer.write(javaCode);
        }
    }
}
