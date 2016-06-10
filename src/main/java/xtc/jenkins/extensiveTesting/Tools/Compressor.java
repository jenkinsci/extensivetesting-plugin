/**
 * The MIT License

 Copyright (c) 2016, Gfi Informatique, Inc., Blaise Cador

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.
 */
package xtc.jenkins.extensiveTesting.Tools;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;


/**
 * Inflate and Deflate Strings
 */
public class Compressor {

    /**
     * Deflate byte array
     *
     * @param data
     * @return
     * @throws IOException
     */
    public byte[] deflater(byte[] data) throws IOException {

        String string = new String(data);
        System.out.println("method deflater : input data = " + string);
        Deflater deflater = new Deflater();
        deflater.setInput(data);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);

        deflater.finish();
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer); // returns the generated code... index
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        byte[] output = outputStream.toByteArray();

        deflater.end();

        return output;
    }


    /**
     * Inflate encodedString
     *
     * @return Inflated string
     */
    public static String inflater(String inputString) {
        String outputString = "NOTHING";
        try {
            byte[] input = Base64.decodeBase64(inputString);
            int inputLen = input.length;

            // Decompress the bytes
            Inflater decompresser = new Inflater();
            decompresser.setInput(input, 0, inputLen);
            byte[] result = new byte[100000];
            int resultLength = decompresser.inflate(result);
            decompresser.end();

            // Decode the bytes into a String
            outputString = new String(result, 0, resultLength, "UTF-8");
        } catch (Exception ex) {
            System.out.println("B64.INFLATER.ERROR = " + ex);
        }
        return outputString;
    }

}