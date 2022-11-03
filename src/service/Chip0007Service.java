package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Chip0007Json;
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
import java.util.Objects;
import java.util.stream.Collectors;

import static utils.Chip0007Utils.*;

public class Chip0007Service {

    private static Chip0007Service instance = null;

    private Chip0007Service() {
    }

    public Chip0007Json save(String[] row, String lineConvertedToRow) {
        RowValue rowValue = setRowValue(row, lineConvertedToRow);
        Chip007 chip007 = new Chip007();

        chip007.setSeriesNumber(numberOrZero(rowValue.getSeriesNumber()));
        chip007.setName(stringOrEmpty(rowValue.getFileName()));
        chip007.setDescription(stringOrEmpty(rowValue.getDescription()));

        Chip007.Attribute chip007Attribute = new Chip007.Attribute();
        chip007Attribute.setTraitType("Gender");
        chip007Attribute.setValue(stringOrEmpty(rowValue.getGender()));

        chip007.getAttributes().add(chip007Attribute);

        setAttributes(chip007, rowValue);

        Chip007.Collection collection = new Chip007.Collection();
        collection.setId(uuidOrNew(rowValue.getUuid()));

        Chip007.CollectionAttr collectionAttr = new Chip007.CollectionAttr();
        collection.getAttributes().add(collectionAttr);
        chip007.setCollection(collection);

        return new Chip0007Json(rowValue.getFileName(), chip007);
    }

    public List<String> prepareColumnNames(String[] columnRow) {
        List<String> columnNames = new ArrayList<>();
        String seriesNumber = columnRow[0];
        String fileName = columnRow[1];
        String name = columnRow[2];
        String desc = columnRow[3];
        String gender = columnRow[4];
        String attributes = columnRow[5];
        String uuid = columnRow[6];

        columnNames.add(Objects.isNull(seriesNumber) ? "Series Number" : seriesNumber.trim());
        columnNames.add(Objects.isNull(fileName) ? "File Name" : fileName.trim());
        columnNames.add(Objects.isNull(name) ? "Name" : name.trim());
        columnNames.add(Objects.isNull(desc) ? "Description" : desc.trim());
        columnNames.add(Objects.isNull(gender) ? "Gender" : gender.trim());
        columnNames.add(Objects.isNull(attributes) ? "Attributes" : attributes.trim());
        columnNames.add(Objects.isNull(uuid) ? "UUID" : uuid.trim());
        columnNames.add("Hash");

        return columnNames;
    }

    public RowValue setRowValue(String[] row, String lineConvertedToRow) {

        String seriesNumber = row[0];
        String fileName = row[1];
        String name = row[2];
        String description = row[3];
        String gender = row[4];
        String attributes = row[5];
        String uuid = row[6];

        RowValue rowValue = RowValue
                .builder()
                .seriesNumber(seriesNumber)
                .fileName(fileName)
                .name(name)
                .description(description)
                .gender(gender)
                .attributes(attributes)
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

    public String publishCsvFiles(String teamName, List<String> columnNames, List<String> rowValues, String fileOutputName) throws IOException {
        String csvContent = "";

        String columnNamesStr = columnNames.stream().collect(Collectors.joining(","));

        csvContent += teamName.concat("\n");
        csvContent += columnNamesStr.concat("\n");

        for (String line: rowValues) {
            csvContent += line.concat("\n");
        }

        fileOutputName = fileOutputName.concat(".output.csv");
        FileWriter writer = new FileWriter(fileOutputName);
        writer.write(csvContent);
        writer.close();

        return csvContent;
    }

    public List<String> chip0007ToString(List<Chip0007Json> chip0007Jsons) throws IOException, NoSuchAlgorithmException {
        List<String> chipString = new ArrayList<>();
        List<String> lines = new ArrayList<>();

        for (Chip0007Json chip007Json : chip0007Jsons) {
            Chip007 chip007 = chip007Json.getChip007();
            String fileName = chip007Json.getFileName();

            chipString.add(String.valueOf(chip007.getSeriesNumber()));
            chipString.add(fileName);
            chipString.add(chip007.getName());
            chipString.add(chip007.getDescription());
            chipString.add(chip007.getAttributes().get(0).getValue());

            String tempFileName = "nft_".concat(fileName).concat(".json");

            createJsonFileAndSave(chip007, tempFileName);
            String checksum = generateSHA256Hash(tempFileName);
            removeTempFile(tempFileName);

            if (chip007.getAttributes() != null && chip007.getAttributes().get(0) != null) {
                chip007.getAttributes().remove(0);
            }

            List<String> attributeList = chip007
                    .getAttributes()
                    .stream()
                    .map(attr -> attr.getTraitType().concat(":").concat(attr.getValue()))
                    .collect(Collectors.toList());

            String joinedAttributes = attributeList.stream().collect(Collectors.joining(";"));

            chipString.add(joinedAttributes);
            chipString.add(chip007.getCollection().getId());
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
