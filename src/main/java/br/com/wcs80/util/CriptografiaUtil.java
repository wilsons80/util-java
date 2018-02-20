/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.wcs80.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.jboss.resteasy.util.Hex;

/**
 *
 * @author fernando.colombo
 */
public class CriptografiaUtil {
   /**
    * Criptografa o valor passado com parametro
    * utilizando o algoritimo MD5. E retorna uma
    * String.
    * @param vl
    * @return valor criptografado.
    * @throws NoSuchAlgorithmException
    */
   public static String getMD5H(String vl) throws NoSuchAlgorithmException {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(vl.getBytes());
      return stringHexa(md.digest());
   }

   /**
    * String digest = getDigest(new FileInputStream(file));
    * 
    * @param is
    * @return
    * @throws NoSuchAlgorithmException
    * @throws IOException
    */
	public static String getMD5H(InputStream in) throws NoSuchAlgorithmException, IOException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.reset();
		
		byte[] bytes = new byte[2048];
		int numBytes;
		while ((numBytes = in.read(bytes)) != -1) {
			md.update(bytes, 0, numBytes);
		}
		byte[] digest = md.digest();
		String result = new String(Hex.encodeHex(digest));
		return result;
	}   
   
   /**
    * Criptografa o valor passado com parametro
    * utilizando o algoritimo MD5. E retorna uma
    * String.
    * @param vl
    * @return valor criptografado.
    * @throws NoSuchAlgorithmException
 * @throws UnsupportedEncodingException 
    */
   public static String getSHA512(String vl) throws NoSuchAlgorithmException, UnsupportedEncodingException {
      MessageDigest md = MessageDigest.getInstance("SHA-512");
      
      byte messageDigestSenhaAdmin[] = md.digest(vl.getBytes("UTF-8"));
      
      StringBuilder hexStringSenhaAdmin = new StringBuilder();
      for (byte b : messageDigestSenhaAdmin) {
               hexStringSenhaAdmin.append(String.format("%02X", 0xFF & b));
      }
      //String senha = hexStringSenhaAdmin.toString();
      return stringHexa(md.digest());
   }

   
   /**
    * Transforma o array de bytes em uma String
    * @param Array bytes
    * @return String
    */
   private static String stringHexa(byte[] bytes) {
      StringBuilder s = new StringBuilder();
      for (int i = 0; i < bytes.length; i++) {
         int parteAlta = ((bytes[i] >> 4) & 0xf) << 4;
         int parteBaixa = bytes[i] & 0xf;
         if (parteAlta == 0) {
            s.append('0');
         }
         s.append(Integer.toHexString(parteAlta | parteBaixa));
      }
      return s.toString();
   }
   
   
   private static final String FRASE_SECRETA = "617c1597c328026fc80b8031d2b02c66f62f5a1882007f0b09c51d293c42d186046a2b8993ad336ce315e14513b2fa92";
   /**
    * Gerador de hash para senha de usuarios externos ao LDAP
    * @param txt
    * @return
    * @throws NoSuchAlgorithmException 
    */
   public static String trt10Hash(String txt){
      try {
         return CriptografiaUtil.getMD5H(FRASE_SECRETA.concat(txt));
      } catch (NoSuchAlgorithmException ex) {
         throw  new RuntimeException("Erro ao carregar algoritmo de criptografia.");
      }
   }
}
