import model.Chip0007Json;
import service.Chip0007Service;

import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final Chip0007Service service = Chip0007Service.getInstance();

    public static void main(String[] args) {
        if (args.length < 2) {
            throw new RuntimeException("No .csv file or file output name was provided.");
        }

        String fileStr = args[0];
        String fileOutputName = args[1];
        Path filePath = Paths.get(fileStr);
        File file = filePath.toFile();
        boolean fileExists = file.isFile();
        if (!fileExists) {
            throw new RuntimeException("CSV file does not exists");
        }

        try (BufferedReader br = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            List<Chip0007Json> chip0007Jsons = new ArrayList<>();
            List<String> columnNames = new ArrayList<>();
            String teamName = null;
            String line = null;
            int rowIdx = 1;

            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                if ((rowIdx) == 1) {
                    columnNames = service.prepareColumnNames(row);
                }
                else if ((rowIdx) == 2) {
                    teamName = row[0];
                }
                else {
                    Chip0007Json chip0007Json = service.save(row, line);
                    chip0007Jsons.add(chip0007Json);
                }
                ++rowIdx;
            }

            List<String> chip007Lines = service.chip0007ToString(chip0007Jsons);
            service.publishCsvFiles(teamName, columnNames, chip007Lines, fileOutputName);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }
}