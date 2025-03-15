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
    public static void main(String[] args)
    {
        //Check for possible arg counts
        if (args.length == 2)
        {
            //Check if the file exists at all, if not, error out
            File file = new File(args[1]);
            if (!file.exists())
            {
                System.err.println("File does not exist!");
                return;
            }
            try
            {
                switch(args[0])
                {
                    case "-D":
                    case "-d":
                        //DES encryption case
                        //General format of encryption/decryption taken from (and slightly modified) https://medium.com/@amit28amical/data-encryption-standard-des-code-in-java-4a45ad692bae
                        byte[] asBytes = Files.readAllBytes(file.toPath());
                        SecretKey desKey;

                        //Check to see if we are inputting a custom key or not, generate as necessary
                        if (args.length == 4)
                        {
                            SecretKeyFactory MyKeyFactory = SecretKeyFactory.getInstance("DES");
                            //Turn our string argument into bytes for key usage
                            byte[] keyBytes = args[3].getBytes();
                            DESKeySpec myMaterial = new DESKeySpec(keyBytes);
                            desKey = MyKeyFactory.generateSecret(myMaterial);
                            //If the key is not a valid one, the program will throw an error and will be caught
                        }
                        else
                        {
                            KeyGenerator Mygenerator = KeyGenerator.getInstance("DES");
                            desKey = Mygenerator.generateKey();
                        }                        

                        //Create a cipher of the instance DES and initiailize with our key
                        //Encrypt the info
                        Cipher cip = Cipher.getInstance("DES");
                        cip.init(cip.ENCRYPT_MODE, desKey);
                        byte[] myEncryptedBytes = cip.doFinal(asBytes);

                        //Print it out in hex format (borrowed from genMD5.java)
                        for (byte b : myEncryptedBytes)
                        {
                            System.out.print(String.format("%02x", b));
                        }
                        
                        break;
                    case "-C":
                    case "-c":
                        //Case for Caesar Cipher encryption
                        //Method readAllBytes re-used from genMD5 hashing source https://stackoverflow.com/questions/858980/file-to-byte-in-java
                        String content = new String(Files.readAllBytes(file.toPath()));
                        String res = rot13(content);
                        //Commented out check for decryption working properly
                        //String res2 = rot13(res);
                        //System.out.println(res2);
                        System.out.println(res);
                        break;
                    case "-A":
                    case "-a":
                        //AES encryption case
                        //General format of encryption/decryption taken from (and slightly modified) https://medium.com/@amit28amical/data-encryption-standard-des-code-in-java-4a45ad692bae
                        byte[] aesAsBytes = Files.readAllBytes(file.toPath());
                        Cipher aesCip = Cipher.getInstance("AES");

                        KeyGenerator AESgenerator = KeyGenerator.getInstance("AES");
                        SecretKey aesKey = AESgenerator.generateKey();
                        aesCip.init(aesCip.ENCRYPT_MODE, aesKey);
                        
                        //Create a cipher of the instance AES and initiailize with our key
                        //Encrypt the info
                        byte[] aesEncryptedBytes = aesCip.doFinal(aesAsBytes);

                        //Print it out in hex format (borrowed from genMD5.java)
                        for (byte b : aesEncryptedBytes)
                        {
                            System.out.print(String.format("%02x", b));
                        }
                        System.out.println("");                        
                }
            }
            catch (Exception e)
            {
                System.err.println("Error occurred in file reading or incorrect key length");
            }
            
        }
        else
        {
            System.err.println("Wrong number of arguments provided");
        }
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
