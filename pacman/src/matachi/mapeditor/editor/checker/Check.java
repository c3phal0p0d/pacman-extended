package src.matachi.mapeditor.editor.checker;

import java.io.FileWriter;
import java.io.IOException;

public interface Check {
    default void logCheckFailure(FileWriter fileWriter, String str) {
        try {
            fileWriter.write(str);
            fileWriter.write("\n");
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
