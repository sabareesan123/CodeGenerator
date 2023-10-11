//package com.dynamiccodegenerator.codeandtablegenerator.service;
//import net.bytebuddy.description.type.TypeDescription;
//import net.bytebuddy.pool.TypePool;
//
//import java.io.FileWriter;
//import java.io.IOException;
//
//public class GenerateJavaSourceForDynamicClass {
//    public static void main(String[] args) throws IOException {
//        TypeDescription typeDescription = TypePool.Default.ofSystemLoader()
//                .describe("com.example.Person")
//                .resolve();
//
//        String javaCode = generateJavaSourceCode(typeDescription);
//
//        saveJavaToFile(javaCode, "Person.java");
//    }
//
//    private static String generateJavaSourceCode(TypeDescription typeDescription) {
//        StringBuilder sourceCode = new StringBuilder();
//        TypeDescription.ForLoadedType load = new TypeDescription.ForLoadedType(typeDescription.);
//        // Append package and imports (if any)
//        sourceCode.append("package ").append(typeDescription.getPackage().getName()).append(";\n\n");
//        typeDescription.getGen .getActualTypeArguments()
//                .forEach(typeVariableToken -> sourceCode.append("import ").append(typeVariableToken.getTypeName()).append(";\n"));
//
//        // Append class declaration
//        sourceCode.append("\npublic class ")
//                .append(typeDescription.getSimpleName()).append(" {\n");
//
//        // Append fields
//        typeDescription.getDeclaredFields().forEach(fieldDescription ->
//                sourceCode.append("    private ")
//                        .append(fieldDescription.getType().asErasure().getSimpleName()).append(" ")
//                        .append(fieldDescription.getName()).append(";\n"));
//
//        // Append getters and setters
//        typeDescription.getDeclaredFields().forEach(fieldDescription -> {
//            String fieldName = fieldDescription.getName();
//            String capitalizedFieldName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
//
//            sourceCode.append("\n    public ")
//                    .append(fieldDescription.getType().asErasure().getSimpleName()).append(" get")
//                    .append(capitalizedFieldName).append("() {\n")
//                    .append("        return ").append(fieldName).append(";\n    }\n\n")
//                    .append("    public void set")
//                    .append(capitalizedFieldName).append("(")
//                    .append(fieldDescription.getType().asErasure().getSimpleName()).append(" ")
//                    .append(fieldName).append(") {\n")
//                    .append("        this.").append(fieldName).append(" = ").append(fieldName).append(";\n    }\n");
//        });
//
//        // Close class declaration
//        sourceCode.append("\n}\n");
//
//        return sourceCode.toString();
//    }
//
//    private static void saveJavaToFile(String javaCode, String fileName) throws IOException {
//        try (FileWriter writer = new FileWriter(fileName)) {
//            writer.write(javaCode);
//        }
//    }
//}
