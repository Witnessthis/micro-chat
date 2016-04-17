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

    public String authenticateUser(String user, String psw) throws Exception {
        Brugeradmin u = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");

        try {
            Bruger usr = u.hentBruger(user, psw);
            System.out.println("Fik bruger = " + usr);
            System.out.println("Data: " + Diverse.toString(usr));
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Bad username or password.");
            return null;
        }
        
        return generateToken(user, psw);
    }

    private String generateToken(String user, String psw) {

        Map<String, Object> payload = new HashMap<String, Object>();

        payload.put("uid", user);
        payload.put("psw", psw);
        
        TokenGenerator tokenGenerator = new TokenGenerator("jsmUjCi94i5xcHmW1iznhZHtX2oEv5amVtwRfGx8");

        return tokenGenerator.createToken(payload);
    }
}
