package util;

import java.io.*;
import java.util.StringTokenizer;


/**
 * Helpful classes set to parse input at programming contests.
 *
 * @author Serj Sintsov
 */
public class ContestIO {

    public static ContestIn fromStdIn() {
        return new ContestIn(System.in);
    }

    public static ContestIn fromFile(String name) {
        try {
            return new ContestIn(new FileInputStream(name));
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static ContestOut toStdOut() {
        return new ContestOut(System.out);
    }

    public static ContestOut toFile(String name) {
        try {
            return new ContestOut(new FileOutputStream(name));
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    // test it
    public static void main(String[] args) throws IOException {
        testStdIn();
        testFileIn();
        testStdOut();
        testFileOut();
    }

    private static void testStdIn() throws IOException {
        String stdInData = "23 34 54 12\n 34\n\n34";

        InputStream origIn = System.in;
        System.setIn(new ByteArrayInputStream(stdInData.getBytes()));

        ContestIn stdIn = fromStdIn();
        while (stdIn.hasMoreInput())
            System.out.println(stdIn.nextInt());

        stdIn.close();
        System.setIn(origIn);
    }

    private static void testFileIn() throws IOException {
        ContestIn fileIn = fromFile("input.txt");
        while (fileIn.hasMoreInput())
            System.out.println(fileIn.nextInt());

        fileIn.close();
    }

    private static void testStdOut() throws IOException {
        ContestOut stdOut = toStdOut();
        stdOut.printInt(34);
        stdOut.printInt(55);
        stdOut.newLine();
        stdOut.printInt(55);
        stdOut.close();
    }

    private static void testFileOut() throws IOException {
        ContestOut fileOut = toFile("output.txt");
        fileOut.printInt(34);
        fileOut.printInt(55);
        fileOut.newLine();
        fileOut.printInt(55);
        fileOut.close();
    }
}


class ContestIn implements Closeable {

    private static final StringTokenizer EMPTY_TOKENIZER = new StringTokenizer("");

    private BufferedReader  reader;
    private StringTokenizer currLine;

    public ContestIn(InputStream in) {
        reader   = new BufferedReader(new InputStreamReader(in));
        currLine = EMPTY_TOKENIZER;
    }

    public int nextInt() {
        return Integer.parseInt(nextToken());
    }

    public boolean hasMoreInput() {
        return moreTokens();
    }

    private String nextToken() {
        moreTokens();
        return currLine.nextToken();
    }

    private boolean moreTokens() {
        if (currLine.hasMoreTokens())
            return true;

        try {
            String nextLine = reader.readLine();

            if (nextLine != null) {
                currLine = new StringTokenizer(nextLine);
                return true;
            }
            else {
                currLine = EMPTY_TOKENIZER;
                return false;
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}


class ContestOut implements Closeable {

    private BufferedWriter writer;

    public ContestOut(OutputStream out) {
        writer = new BufferedWriter(new OutputStreamWriter(out));
    }

    public void printInt(int i) {
        try {
            writer.write(String.valueOf(i));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void newLine() {
        try {
            writer.write("\n");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}
