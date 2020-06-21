import java.io.*;

public class HuffmanIO {
    /**
     * Deserialize an object from a file.
     *
     * @param file the file in which the object is serialized.
     * @return the object contained in the file,
     * and null if the file doesn't exists or its data cannot be used to create an object.
     */
    public static Object deserialize(File file) {
        Object object = null;
        try {
            ObjectInputStream out = new ObjectInputStream(new FileInputStream(file));
            object = out.readObject();
            out.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return object;
    }

    /**
     * Serialise an object into a file.
     *
     * @param object object to serialise into the file.
     * @param file   file in which the serialised object is going to be stored.
     */
    public static void serialize(Object object, File file) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(object);
            out.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Read a text file
     *
     * @param file The file to read.
     * @return The string read from the file.
     */
    public static String readFile(File file) {

        String text = null;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file.getPath()))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();

            while (line != null) {
                stringBuilder.append(line);
                stringBuilder.append(System.lineSeparator());
                line = bufferedReader.readLine();
            }

            text = stringBuilder.toString();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return text;
    }

    /**
     * Write text into a file.
     *
     * @param file The file where the text is going to be written.
     * @param text The text to write into the file.
     */

    public static void writeFile(File file, String text) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file.getPath()))) {
            bufferedWriter.write(text);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}