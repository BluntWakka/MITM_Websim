import java.security.*;
import java.util.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.charset.*;
import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec; 
import javax.crypto.spec.PBEKeySpec; 
import javax.crypto.spec.SecretKeySpec; 
import java.security.spec.KeySpec; 

public class encryptPro
{
    public static String encrypt(String[] args)
    {

        //Check for possible arg counts
        if (args.length == 2)
        {    
            try
            {
                switch(args[0])
                {
                    case "-D":
                    case "-d":
                        //DES encryption case
                        //General format of encryption/decryption taken from (and slightly modified) https://medium.com/@amit28amical/data-encryption-standard-des-code-in-java-4a45ad692bae
                        byte[] asBytes = args[1].getBytes();
                        SecretKey desKey;

                        //Generate key as necessary
                        KeyGenerator Mygenerator = KeyGenerator.getInstance("DES");
                        desKey = Mygenerator.generateKey();                    

                        //Create a cipher of the instance DES and initiailize with our key
                        //Encrypt the info
                        Cipher cip = Cipher.getInstance("DES");
                        cip.init(cip.ENCRYPT_MODE, desKey);
                        byte[] myEncryptedBytes = cip.doFinal(asBytes);

                        StringBuilder sbd = new StringBuilder();
                        for (byte b : myEncryptedBytes)
                        {
                            sbd.append(String.format("%02x", b));
                        }
                        
                        return sbd.toString();
                    case "-C":
                    case "-c":
                        //Case for Caesar Cipher encryption
                        //Method readAllBytes re-used from genMD5 hashing source https://stackoverflow.com/questions/858980/file-to-byte-in-java
                        String res = rot13(args[1]);
                        //Commented out check for decryption working properly
                        //String res2 = rot13(res);
                        //System.out.println(res2);
                        return res;
                    case "-A":
                    case "-a":
                        //AES encryption case
                        //General format of encryption/decryption taken from (and slightly modified) https://medium.com/@amit28amical/data-encryption-standard-des-code-in-java-4a45ad692bae
                        byte[] aesAsBytes = args[1].getBytes();
                        Cipher aesCip = Cipher.getInstance("AES");

                        KeyGenerator AESgenerator = KeyGenerator.getInstance("AES");
                        SecretKey aesKey = AESgenerator.generateKey();
                        aesCip.init(aesCip.ENCRYPT_MODE, aesKey);
                        
                        //Create a cipher of the instance AES and initiailize with our key
                        //Encrypt the info
                        byte[] aesEncryptedBytes = aesCip.doFinal(aesAsBytes);
                        StringBuilder sba = new StringBuilder();
                        //Print it out in hex format (borrowed from genMD5.java)
                        for (byte b : aesEncryptedBytes)
                        {
                            sba.append(String.format("%02x", b));
                        }
                        return sba.toString();                    
                }
            }
            catch (Exception e)
            {
                System.err.println("Error occurred in file reading or incorrect key length");
                return null;
            }
            
        }
        else
        {
            System.err.println("Wrong number of arguments provided");
            return null;
        }
        return null;
    }

    //This entire function was taken from https://stackoverflow.com/questions/8981296/rot-13-function-in-java by user georgiecasey!
    public static String rot13(String input) 
    {
        StringBuilder sb = new StringBuilder();
        //Builds string on a stringbuilder instance one character at a time and shifts the location of each char by 13 for lowercase and captial
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if       (c >= 'a' && c <= 'm') c += 13;
            else if  (c >= 'A' && c <= 'M') c += 13;
            else if  (c >= 'n' && c <= 'z') c -= 13;
            else if  (c >= 'N' && c <= 'Z') c -= 13;
            sb.append(c);
        }
        return sb.toString();
    }

}
