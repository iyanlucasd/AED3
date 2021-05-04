import java.util.logging.*;
import java.io.File;
import java.io.IOException;

public class Log {
    public Logger logger;
    FileHandler fh;

    public Log(String file_name) throws SecurityException, IOException{
        File arq = new File(file_name);
        if(!arq.exists())
            arq.createNewFile();
        
        fh = new FileHandler(file_name, true);
        logger = Logger.getLogger("teste");
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
    }
}