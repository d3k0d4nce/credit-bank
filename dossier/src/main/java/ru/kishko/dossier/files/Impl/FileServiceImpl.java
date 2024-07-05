package ru.kishko.dossier.files.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kishko.dossier.files.FileService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Override
    public File createCreditFile(String statementId) throws IOException {
        log.info("Creating credit file for statementId: {}", statementId);
        File file = new File("document.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("Здравствуйте, ниже представлена информация о вашем кридите, " +
                    "основанном на заявке с id: " + statementId);
            log.info("Credit file created successfully: {}", file.getAbsolutePath());
            return file;
        } catch (IOException e) {
            log.error("Error creating credit file for statementId: {}", statementId, e);
            throw e;
        }
    }
}
