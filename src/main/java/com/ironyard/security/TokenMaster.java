package com.ironyard.security;

import com.ironyard.data.CUser;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Created by matthewhug on 3/24/17.
 */
public class TokenMaster {
    private String token;
    private final String SECRET = "CMS";

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSECRET() {
        return SECRET;
    }

    public String generateToken(CUser user) throws Exception{
        SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET.getBytes(), "Blowfish");
        Cipher cipher = Cipher.getInstance("Blowfish");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        // build my secret message
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar cal = Calendar.getInstance();
        String date = dateFormat.format(cal.getTime());
        String mySecretMessage = String.format("%s:%s:%s", date, SECRET, user.getId());

        // do the encrypt message
        byte[] hasil = cipher.doFinal(mySecretMessage.getBytes());

        return new BASE64Encoder().encode(hasil);
    }

    public String decrypt(String encryptedToken){
        String decrypted = null;

        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET.getBytes(), "Blowfish");
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] hasil = cipher.doFinal(new BASE64Decoder().decodeBuffer(encryptedToken));
            decrypted = new String(hasil);
        }catch (Throwable t){
            //ignore
        }
        return decrypted;
    }

    public Long getUserIdFromToken(String encryptedToken){
        Long userId = null;
        try {
            // step 1: decrypt this thing
            String decrypted = this.decrypt(encryptedToken);
            StringTokenizer st = new StringTokenizer(decrypted, ":");
            String dateAsString = st.nextToken();
            String secret = st.nextToken();

            if(secret.equals(SECRET)){
                DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                Date dateFromToken = dateFormat.parse(dateAsString);
                userId = Long.parseLong(st.nextToken());
            }
        }catch(Exception e)
        {

        }

        return userId;
    }

}
