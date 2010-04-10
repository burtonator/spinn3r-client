package com.spinn3r.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;

/**
 * The LogReader reads a log file.
 * 
 * @author jwu
 * 
 */
public class LogReader {
    public void read(File file, LogReaderAdapter adapter) throws Exception {
        read(new FileInputStream(file), adapter);
    }

    public void read(InputStream inputStream, LogReaderAdapter adapter)
            throws Exception {
        read(new InputStreamReader(inputStream), adapter);
    }

    public void read(Reader reader, LogReaderAdapter adapter) throws Exception {
        read(new LineNumberReader(reader), adapter);
    }

    public void read(LineNumberReader inputStream, LogReaderAdapter adapter)
            throws Exception {
        String line;
        while ((line = inputStream.readLine()) != null) {
            try {
                read(line, adapter);
            } catch (Exception e) {
                throw new Exception("Exception on line "
                        + inputStream.getLineNumber(), e);
            }
        }
    }

    private void read(String line, LogReaderAdapter adapter) throws Exception {
        String[] parts = line.split(" ");

        if (parts[0].equals("start")) {
            if (parts.length < 3)
                throw new Exception();

            if (Integer.parseInt(parts[1]) != parts[2].length())
                throw new Exception();

            adapter.onStart(parts[2]);
        } else if (parts[0].equals("end")) {
            if (parts.length < 3)
                throw new Exception();

            if (Integer.parseInt(parts[1]) != parts[2].length())
                throw new Exception();

            adapter.onEnd(parts[1]);
        } else {
            if (parts.length < 5)
                throw new Exception();

            if (Integer.parseInt(parts[1]) != parts[2].length())
                throw new Exception();

            if (Integer.parseInt(parts[3]) != parts[4].length())
                throw new Exception();

            adapter.onError(parts[1], parts[2]);
        }
    }
}
