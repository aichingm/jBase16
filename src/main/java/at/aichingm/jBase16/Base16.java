package at.aichingm.jBase16;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Encode/Decode base16 (RFC 4648)
 *
 * @author Mario Aichinger (aichingm@gmail.com)
 */
public class Base16 {
    /**
     * Encode byte to a base16 String
     *
     * @param bytes The bytes to decode
     * @return An in base16 encodeed String
     */
    public static String encode(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder(bytes.length * 2);
        byte quartet1, quartet2;
        for (int i = 0; i < bytes.length; i++) {
            quartet1 = (byte) ((bytes[i] >>> 4) & 0xF);
            quartet2 = (byte) (bytes[i] & 0xF);
            stringBuilder.append(Integer.toHexString(quartet1)); //Integer.toHexString returns lower case
            stringBuilder.append(Integer.toHexString(quartet2)); //Integer.toHexString returns lower case
        }
        return stringBuilder.toString().toUpperCase();
    }

    /**
     * Decode a base16 String to a byte array. Not performing any checks on the given data!
     *
     * @param str The string to decode
     * @return An array of bytes
     */
    public static byte[] decode(String str) {
        int len = str.length() / 2;
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            bytes[i] = (byte) (((Character.getNumericValue(str.charAt(i << 1))) << 4)
                    + Character.getNumericValue(str.charAt((i << 1) + 1)));
        }
        return bytes;
    }

    /**
     * Decode a base16 String to a byte array. Not performing any checks on the given data! (tolerate lower case characters)
     * @param str The string to decode
     * @return An array of bytes
     */
    public static byte[] decodeTolerant(String str) {
        return decode(str.toUpperCase());
    }

    /**
     * Verify that a given string is valid base16 encoded
     * @param base16str The string in question
     * @return  Returns true if the string is valid and false otherwise
     */
    public static boolean verifyStrict(String base16str) {
        int len = base16str.length();
        if(len % 2 != 0){
            return false;
        }
        for (int i = 0; i < len; i++) {
            switch (base16str.charAt(i)) {
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case 'A':
                case 'B':
                case 'C':
                case 'D':
                case 'E':
                case 'F':
                    continue;
                default:
                    return false;
            }
        }
        return true;
    }
    /**
     * Verify that a given string is valid base16 encoded (tolerate lower case characters)
     * @param base16str The string in question
     * @return  Returns true if the string is valid and false otherwise
     */
    public static boolean verifyTolerant(String base16str) {
        int len = base16str.length();
        for (int i = 0; i < len; i++) {
            switch (base16str.charAt(i)) {
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case 'A':
                case 'B':
                case 'C':
                case 'D':
                case 'E':
                case 'F':
                case 'a':
                case 'b':
                case 'c':
                case 'd':
                case 'e':
                case 'f':
                    continue;
                default:
                    return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        tests();
    }

    public static void tests() {
        test("A".getBytes(), "41");
        test("a".getBytes(), "61");
        test("test".getBytes(), "74657374");
        test("TeSt".getBytes(), "54655374");
        test("TEST".getBytes(), "54455354");
        test("!\"§$%&/()=?".getBytes(), "2122C2A72425262F28293D3F");
        test(" \n\t\r".getBytes(), "200A090D");

        //  tests from https://tools.ietf.org/html/rfc4648#page-13

        test("".getBytes(), "");
        test("f".getBytes(), "66");
        test("fo".getBytes(), "666F");
        test("foo".getBytes(), "666F6F");
        test("foob".getBytes(), "666F6F62");
        test("fooba".getBytes(), "666F6F6261");
        test("foobar".getBytes(), "666F6F626172");
    }

    public static void test(byte[] in, String expectedOut) {
        String encoded;
        byte[] decoded;
        if (!verifyStrict(encoded = encode(in))) {
            System.out.println("Verifying \"" + encoded + "\" failed");
        } else if (!encoded.equals(expectedOut)) {
            System.out.println(
                    "Encoding {" + new String(in, StandardCharsets.UTF_8) + "} failed, expecting: \"" + expectedOut + "\", got: \"" + encoded + "\"");
        } else if (!Arrays.equals((decoded = decode(encoded)), in)) {
            System.out.println("Decoding \"" + encoded + "\" failed, expecting: {" + new String(in, StandardCharsets.UTF_8) + "}, got: {" + new String(decoded, StandardCharsets.UTF_8) + "}");
        } else {
            System.out.println("\"" + expectedOut + "\": ✓");
        }
    }

}
