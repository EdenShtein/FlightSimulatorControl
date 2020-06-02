package interpreter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class CompLexer<V> implements Lexer {
    private Scanner scan;
    private ArrayList<String[]> lines = new ArrayList<>();
    private String[] arr=null;

    public CompLexer(String v) {
        try {
            scan = new Scanner(new BufferedReader(new FileReader(v)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public CompLexer(String[] s)
    {
        arr=s;
    }
    public CompLexer(V v) {
        scan = new Scanner((Readable) v);

    }
    public ArrayList<String[]> lexicalCheck() {
        if(arr!=null)
        {
            for (String s:arr) {
            	//regular expression to represent 1 or more white spaces
                lines.add(s.replaceFirst("=", " = ").replaceFirst("\t","").split("\\s+"));
            }

        }
        else
            while (scan.hasNextLine()) {
                lines.add(scan.nextLine().replaceFirst("=", " = ").replaceFirst("\t","").split("\\s+"));
            }
        return lines;

    }
}
