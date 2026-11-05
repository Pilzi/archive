import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SaveFile implements Serializable{

    File saveFile;

    public void createSaveFile(File file){

        try {
            saveFile = file;
            saveFile.createNewFile();
          } catch (IOException e) {
            System.out.println("An error occurred.");
          }

    }

    public void saveFile(Object[] chessBoard, File file){
      createSaveFile(file);
      try {

        writeObjectToFile(chessBoard, file);
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }

    }

    public Object[] getLoadedSettings (GlobalSettings globalSettings, File file) {
      Object[] result = new Object[56];
      try {


        result = readObjectFromFile(file);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }

      return result;
    }

    private static void writeObjectToFile(Object[]  globalSettings, File file) throws IOException {
      try (FileOutputStream fos = new FileOutputStream(file);
           ObjectOutputStream oos = new ObjectOutputStream(fos)) {
          oos.writeObject(globalSettings);
          oos.flush();
      }
  }

  public static Object[] readObjectFromFile(File file) throws IOException, ClassNotFoundException {
    Object[] result;
    try (FileInputStream fis = new FileInputStream(file);
         ObjectInputStream ois = new ObjectInputStream(fis)) {
        result = (Object[]) ois.readObject();
    }
    return result;
}


  public boolean doesFileExist(String path) {
      File f = new File(path);
      if (f.exists()){
        return true;
      }
      return false;
  }






}
