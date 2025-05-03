package framework;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.stream.Collectors;

@UtilityClass
public class FileUtils {

    @SneakyThrows
    public void writeDataToTheFile(String filePath, String data)
    {
        File file = new File(filePath);
        FileWriter fileWriter=new FileWriter(filePath,file.exists());

        if(file.exists() && file.length()>0)
        {
            fileWriter.append("\n");
        }

        else
        {
            file.createNewFile();
        }

        fileWriter.write(data);

        fileWriter.close();
    }

    @SneakyThrows
    public String readDataFromTheFile(String filePath)
    {
        FileReader fr=new FileReader(filePath);

        BufferedReader br=new BufferedReader(fr);

        return br.lines().collect(Collectors.joining("\n"));
    }


}
