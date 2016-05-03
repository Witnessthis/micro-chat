/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package relay;

import brugerautorisation.Diverse;
import java.rmi.Naming;
import brugerautorisation.data.Bruger;
import brugerautorisation.transport.rmi.*;
import java.util.HashMap;
import java.util.Map;
import token.*;

/**
 *
 * @author emilj
 */
public class Authentication {

    /**
     * This method authenticates the user and returns a token upon success.
     * @param user
     * @param psw
     * @return token
     * @throws Exception 
     */
    public String authenticateUser(String user, String psw) throws Exception {
        Brugeradmin u = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");

        try {
            Bruger usr = u.hentBruger(user, psw);
            //System.out.println("Fik bruger = " + usr);
            //System.out.println("Data: " + Diverse.toString(usr));
        } catch (Exception ex) {
            ex.printStackTrace();
            //System.err.println("Bad username or password.");
            return null;
        }
        
        return generateToken(user, psw); // return a token.
    }

    /**
     * Generates a firebase token.
     * @param user
     * @param psw
     * @return Token
     */
    private String generateToken(String user, String psw) {

        Map<String, Object> payload = new HashMap<String, Object>();

        payload.put("uid", user); // "uid" is needed for firebase, it identifies the user.
        payload.put("psw", psw); // users password.
        
        TokenGenerator tokenGenerator = new TokenGenerator("jsmUjCi94i5xcHmW1iznhZHtX2oEv5amVtwRfGx8"); // create token object with firebase secret.

        return tokenGenerator.createToken(payload); // create a token and return it. (encrypt with firebase secret)
    }
}
