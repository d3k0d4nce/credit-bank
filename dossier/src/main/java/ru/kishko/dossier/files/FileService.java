package ru.kishko.dossier.files;

import java.io.File;
import java.io.IOException;

public interface FileService {
    File createCreditFile(String statementId) throws IOException;
}
