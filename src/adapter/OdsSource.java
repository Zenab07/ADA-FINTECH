package adapter;

import org.odftoolkit.simple.SpreadsheetDocument;
import org.odftoolkit.simple.table.Row;
import org.odftoolkit.simple.table.Table;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OdsSource {
    private final String filePath;

    public OdsSource(String filePath) {
        this.filePath = filePath;
    }

    public List<List<String>> getOdsData() {
        List<List<String>> data = new ArrayList<>();
        try {
            SpreadsheetDocument document = SpreadsheetDocument.loadDocument(new File(filePath));
            Table sheet = document.getSheetByIndex(0);

            for (Row row : sheet.getRowList()) {
                List<String> rowData = new ArrayList<>();
                row.getCellList().forEach(cell -> rowData.add(cell.getStringValue().trim()));
                data.add(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
