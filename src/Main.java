import com.fasterxml.jackson.databind.ObjectMapper;
import model.Chip007;
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
            List<Chip007> chip007s = new ArrayList<>();
            List<String> columnNames = new ArrayList<>();
            String line = null;
            int columnIdx = 0;

            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                String[] columns = new String[row.length];

                if ((++columnIdx) == 1) {
                    columnNames = service.prepareColumnNames(row);
                }
                else {
                    Chip007 chip007 = service.save(row);
                    chip007s.add(chip007);
                }
            }

            List<String> chip007Lines = service.chip0007ToString(chip007s);
            String csvContent = service.publishCsvFiles(columnNames, chip007Lines, fileOutputName);
            System.out.println(csvContent);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }
}