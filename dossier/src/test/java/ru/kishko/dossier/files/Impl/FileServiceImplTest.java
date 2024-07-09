package ru.kishko.dossier.files.Impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileServiceImplTest {

    @Mock
    private FileServiceImpl fileService;

    @Test
    void createCreditFile() throws IOException {
        String statementId = "12345";
        File expectedFile = new File("document.txt");
        when(fileService.createCreditFile(statementId)).thenReturn(expectedFile);

        File actualFile = fileService.createCreditFile(statementId);

        assertEquals(expectedFile, actualFile);
    }
}