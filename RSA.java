//  RSA.java
//  RSA Encryption
//  Copyright © 2019 Stephen Alger. All rights reserved.

import java.util.Scanner;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;
import javax.crypto.Cipher;
import java.security.NoSuchAlgorithmException;

public class RSA
{
    
    public static void main(String[] args) throws Exception
    {
        
        //Instantiate scanner so we can read the command-line input
        Scanner scanner = new Scanner(System.in);
        
        //Get user input - message to encrypt
        System.out.print("Enter your message to be encrypted using RSA Encryption: ");

        //Take in User input from console
        String originalMsg = scanner.nextLine();
        
        //Instantiate the RSA key generator with specified byte size 
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        
        //Use keyPairGenerator to generate the KeyPair (public & private)
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        
        // Get the public and private key
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        
        //Output user input in plain text 
        System.out.println(String.format("Your un-encrpyted message is: [ %s ] - PUBLIC KEY IS: [%s]", originalMsg, publicKey));
        
        //Perform encryption process using public key
        byte[] cipherByteArray = encryptFunc(originalMsg, publicKey);
        
        //Perform decryption using private key
        String decryptedText = decryptFunc(cipherByteArray, privateKey);
        
        //Convert btye array to string
        String encryptedText = Base64.getEncoder().encodeToString(cipherByteArray);
        
        //Output encrypted message
        System.out.println(String.format("Your encrypted message is: [ %s ]", encryptedText));
        
        //Output the cycled message back to user from plaintext to RSA encryption back to plaintext
        System.out.println(String.format("Your decrypted message is: [ %s ] - PRIVATE KEY IS: [%s]", decryptedText, privateKey));
        
    }
    
    public static byte[] encryptFunc (String originalMsg, PublicKey publicKey ) throws Exception
    {
        //Get required Cipher Instance & initialise stating mode and public Key
        Cipher cipherInstance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipherInstance.init(Cipher.ENCRYPT_MODE, publicKey);
        
        //Use cipherInstance doFinal function to encrypt & return byteArray
        byte[] cipherByteArray = cipherInstance.doFinal(originalMsg.getBytes()) ;
        return cipherByteArray;
    }
    
    public static String decryptFunc (byte[] cipherByteArray, PrivateKey privateKey) throws Exception
    {
        ////Get required Cipher Instance & initialise stating mode and private Key
        Cipher cipherInstance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipherInstance.init(Cipher.DECRYPT_MODE, privateKey);
        
        //Use cipherInstance doFinal function to encrypt
        byte[] decipherByteArray = cipherInstance.doFinal(cipherByteArray);
        
        //Convert byteArray to string
        return new String(decipherByteArray);
    }
}