package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Chip007;
import model.RowValue;
import utils.CheckSumGenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static utils.Chip0007Utils.*;

public class Chip0007Service {

    private static Chip0007Service instance = null;

    private Chip0007Service() {
    }

    public Chip007 save(String[] row) {
        RowValue rowValue = setRowValues(row);
        Chip007 chip007 = new Chip007();

        chip007.setSeriesNumber(numberOrZero(rowValue.getSeriesNumber()));
        chip007.setName(stringOrEmpty(rowValue.getFileName()));
        chip007.setDescription(stringOrEmpty(rowValue.getDescription()));

        Chip007.Attribute attribute = new Chip007.Attribute();
        attribute.setTraitType("Gender");
        attribute.setValue(stringOrEmpty(rowValue.getGender()));
        chip007.getAttributes().add(attribute);

        Chip007.Collection collection = new Chip007.Collection();
        collection.setId(uuidOrNew(rowValue.getUuid()));

        Chip007.CollectionAttr collectionAttr = new Chip007.CollectionAttr();
        collection.getAttributes().add(collectionAttr);
        chip007.setCollection(collection);

        return chip007;
    }

    public List<String> prepareColumnNames(String[] columnRow) {
        List<String> columnNames = new ArrayList<>();
        String seriesNumber = columnRow[0];
        String fileName = columnRow[1];
        String desc = columnRow[2];
        String gender  = columnRow[3];
        String uuid = columnRow[4];

        columnNames.add(seriesNumber != null ? seriesNumber.trim() : "Series Number");
        columnNames.add(fileName != null ? fileName.trim() : "File Name");
        columnNames.add(desc != null ? desc.trim() : "Description");
        columnNames.add(gender != null ? gender.trim() : "gender");
        columnNames.add(uuid != null ? uuid.trim() : "UUID");
        columnNames.add("Hash");

        return columnNames;
    }

    public RowValue setRowValues(String[] row) {
        String seriesNumber = row[0];
        String fileName = row[1];
        String description = row[2];
        String gender = row[3];
        String uuid = row[4];

        RowValue rowValue = RowValue
                .builder()
                .seriesNumber(seriesNumber)
                .fileName(fileName)
                .description(description)
                .gender(gender)
                .uuid(uuid).build();

        return rowValue;
    }

    public void createJsonFileAndSave(Chip007 chip007, String fileNameOutput) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(chip007);

        FileWriter writer = new FileWriter(fileNameOutput);
        writer.write(jsonStr);
        writer.close();
    }

    public String generateSHA256Hash(String fileName) throws NoSuchAlgorithmException, IOException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        String checksum = CheckSumGenerator.getFileChecksum(fileName, messageDigest);
        return checksum;
    }

    public void removeTempFile(String fileName) {
        File file = new File(fileName);
        file.delete();
    }

    public String publishCsvFiles(List<String> columnNames, List<String> rowValues, String fileOutputName) throws IOException {
        String csvContent = "";

        String columnNamesStr = columnNames.stream().collect(Collectors.joining(","));
        csvContent += columnNamesStr.concat("\n");

        for (String line: rowValues) {
            csvContent += line.concat("\n");
        }
        FileWriter writer = new FileWriter(fileOutputName);
        writer.write(csvContent);
        writer.close();

        return csvContent;
    }

    public List<String> chip0007ToString(List<Chip007> chip007s) throws IOException, NoSuchAlgorithmException {
        List<String> chipString = new ArrayList<>();
        List<String> lines = new ArrayList<>();
        String tempFileName = "file.json";

        for (Chip007 chip007 : chip007s) {
            chipString.add(String.valueOf(chip007.getSeriesNumber()));
            chipString.add(chip007.getName());
            chipString.add(chip007.getDescription());
            chipString.add(chip007.getAttributes().get(0).getValue());
            chipString.add(chip007.getCollection().getId());

            createJsonFileAndSave(chip007, tempFileName);
            String checksum = generateSHA256Hash(tempFileName);
            removeTempFile(tempFileName);

            chipString.add(checksum);
            String line = chipString.stream().collect(Collectors.joining(","));
            chipString = new ArrayList<>();

            lines.add(line);
        }
        return lines;
    }

    public static Chip0007Service getInstance() {
        if (instance == null) {
            instance = new Chip0007Service();
        }
        return instance;
    }
}
