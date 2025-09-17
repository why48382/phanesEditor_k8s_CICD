package org.example.coding_convention.file.service;

import java.io.IOException;
import java.sql.SQLException;

public interface UploadService {
    public String upload(String filename, String fileContents) throws SQLException, IOException;
}
