/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package relay;

import brugerautorisation.data.Bruger;
import brugerautorisation.transport.rmi.Brugeradmin;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.rmi.Naming;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author lime
 */
public class RelayServer extends HttpServlet {

    Authentication auth = new Authentication();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RelayServer</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RelayServer at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        ServletOutputStream out = response.getOutputStream();//getWriter();

        String resource = request.getRequestURI();
        switch (resource) {

            case "/RelayServer": {

                out.println("<html>");
                out.println("<head>");
                out.println("<title>Index</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h2>Navigate to the following resource in order to see the micro-chat API</h2>");
                out.println("<br>/micro-chatAPI</br>");
                out.println("</body>");
                out.println("</html>");

                break;
            }

            case "/RelayServer/": {
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Index</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h2>Navigate to the following resource in order to see the micro-chat API</h2>");
                out.println("<br>/micro-chatAPI</br>");
                out.println("</body>");
                out.println("</html>");

                break;
            }

            case "/RelayServer/micro-chatAPI": {

                out.println("<html>");
                out.println("<head>");
                out.println("<title>Index</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>resources:</h1>");
                out.println("<br>AUTHENTICATE_USER: GET http://85.11.31.36:8080/RelayServer/account/auth?user=[USERNAME]&psw=[PASSWORD]</br>");
                out.println("<br>CHANGE_PASSWORD    GET http://85.11.31.36:8080/RelayServer/account/changepsw?user=[USERNAME]&oldPsw=[PASSWORD]&newPsw=[PASSWORD]</br>");
                out.println("<br>FORGOT_PASSWORD    GET http://85.11.31.36:8080/RelayServer/account/forgotpsw?user=[USERNAME]</br>");
                out.println("<br>DOWNLOAD_FILE:     GET http://130.226.195.227:8080/microChatFileServer/root/files/[USERNAME]/[FILENAME]?username=[USERNAME]&password=[PASSWORD]</br>");
                out.println("<br>LIST_FILES:        GET http://130.226.195.227:8080/microChatFileServer/root/files/[USERNAME]?username=[USERNAME]&password=[PASSWORD]</br>");
                out.println("<br>UPLOAD_FILE:       POST http://130.226.195.227:8080/microChatFileServer/root/files/[USERNAME]?username=[USERNAME]&password=[PASSWORD]   (with form-data file=somefile)\n</br>");
                out.println("<br>DELETE_FILE:       DELETE http://130.226.195.227:8080/microChatFileServer/root/files/[USERNAME]/[FILENAME]?username=[USERNAME]&password=[PASSWORD]</br>");
                out.println("<br></br>");
                out.println("<h1>Firebase rules</h1>");
                out.println("<br>{</br>");
                out.println("<br>  \"rules\": {</br>");
                out.println("<br>       \".read\": \"auth.isModerator == true\",</br>");
                out.println("<br>       \".write\": \"auth.isModerator == true\",</br>");
                out.println("<br></br>");
                out.println("<br>           \"chat-rooms\": {</br>");
                out.println("<br>               \"$chatroom\":{</br>");
                out.println("<br>               \".read\": \"(data.child('password').exists() && data.parent().parent().child('users').child(auth.uid).child('chatrooms').hasChild($chatroom) && data.parent().parent().child('users').child(auth.uid).child('chatrooms').child($chatroom).val() == data.child('password').val()) || (!data.child('password').exists() && data.parent().parent().child('users').child(auth.uid).child('chatrooms').hasChild($chatroom))\",</br>");
                out.println("<br>               \".write\": \"(data.child('password').exists() && data.parent().parent().child('users').child(auth.uid).child('chatrooms').hasChild($chatroom) && data.parent().parent().child('users').child(auth.uid).child('chatrooms').child($chatroom).val() == data.child('password').val()) || (!data.child('password').exists() && data.parent().parent().child('users').child(auth.uid).child('chatrooms').hasChild($chatroom))\"</br>");
                out.println("<br>           }</br>");
                out.println("<br>       },</br>");
                out.println("<br></br>");
                out.println("<br>       \"users\": {</br>");
                out.println("<br>           \"$user\":{</br>");
                out.println("<br>               \"personal\":{</br>");
                out.println("<br>               \".read\": \"auth.uid == data.parent().child('username').val() && auth.psw == data.parent().child('psw').val()\",</br>");
                out.println("<br>               \".write\": \"auth.uid == data.parent().child('username').val() && auth.psw == data.parent().child('psw').val()\"</br>");
                out.println("<br>               },</br>");
                out.println("<br>               \"favourites\":{</br>");
                out.println("<br>               \".read\": \"auth.uid == data.parent().child('username').val() && auth.psw == data.parent().child('psw').val()\",</br>");
                out.println("<br>               \".write\": \"auth.uid == data.parent().child('username').val() && auth.psw == data.parent().child('psw').val()\"</br>");
                out.println("<br>               },</br>");
                out.println("<br>               \"friends\":{</br>");
                out.println("<br>               \".read\": \"auth.uid == data.parent().child('username').val() && auth.psw == data.parent().child('psw').val()\",</br>");
                out.println("<br>               \".write\": \"auth.uid == data.parent().child('username').val() && auth.psw == data.parent().child('psw').val()\"</br>");
                out.println("<br>               },</br>");
                out.println("<br>               </br>");
                out.println("<br>               \"chatrooms\":{</br>");
                out.println("<br>               \".read\": \"auth.uid == data.parent().child('username').val() && auth.psw == data.parent().child('psw').val()\",</br>");
                out.println("<br>               \".write\": \"auth.uid == data.parent().child('username').val() && auth.psw == data.parent().child('psw').val()\"</br>");
                out.println("<br>               },</br>");
                out.println("<br>               \"privatechats\":{</br>");
                out.println("<br>               \".read\": \"auth.uid == data.parent().child('username').val() && auth.psw == data.parent().child('psw').val()\",</br>");
                out.println("<br>               \".write\": \"auth.uid == data.parent().child('username').val() && auth.psw == data.parent().child('psw').val()\"</br>");
                out.println("<br>               },</br>");
                out.println("<br>               \"myFiles\":{</br>");
                out.println("<br>               \".read\": \"auth.uid == data.parent().child('username').val() && auth.psw == data.parent().child('psw').val()\",</br>");
                out.println("<br>               \".write\": \"auth.uid == data.parent().child('username').val() && auth.psw == data.parent().child('psw').val()\"</br>");
                out.println("<br>               }</br>");
                out.println("<br>           }</br>");
                out.println("<br>       }</br>");
                out.println("<br>   }</br>");
                out.println("<br>}</br>");
                out.println("<br></br>");
                out.println("</body>");
                out.println("</html>");

                break;
            }

            case "/RelayServer/account/forgotpsw": {

                String user = request.getParameter("user");
                String extraText = "\nKind regards, the µ-chat team.";
                Brugeradmin ba = null;
                try {
                    ba = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");
                } catch (Exception ex) {
                    Logger.getLogger(RelayServer.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (ba != null) {
                    ba.sendGlemtAdgangskodeEmail(user, extraText);
                    out.print("A new password has been sent to your email address.");
                } else {
                    out.print("Something wen't wrong, try again.");
                }

                break;
            }
            case "/RelayServer/account/changepsw": {

                String user = request.getParameter("user");
                String oldPsw = request.getParameter("oldPsw");
                String newPsw = request.getParameter("newPsw");
                Brugeradmin ba = null;
                try {
                    ba = (Brugeradmin) Naming.lookup("rmi://javabog.dk/brugeradmin");
                } catch (Exception ex) {
                    Logger.getLogger(RelayServer.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (ba != null) {
                    Bruger br = null;
                    try {
                        br = ba.ændrAdgangskode(user, oldPsw, newPsw);
                    } catch (Exception ex) {
                        out.print("Bad username or password");
                    }
                    if (br != null) {
                        if (br.adgangskode.equals(newPsw)) {
                            out.print("Password changed.");

                            Runtime.getRuntime().exec("curl -X PATCH -d {\"psw\":\"" + newPsw + "\"} https://micro-chat.firebaseio.com/users/" + user + ".json?print=pretty&auth=jsmUjCi94i5xcHmW1iznhZHtX2oEv5amVtwRfGx8");
                        } else {
                            out.print("Password not changed. Something went wrong.");
                        }
                    }

                }

                break;
            }

            case "/RelayServer/account/auth": {

                String user = request.getParameter("user");
                String psw = request.getParameter("psw");

                String token;

                try {
                    token = auth.authenticateUser(user, psw);
                    boolean foundUser = false;
                    String getResponse = "";
                    String line = "";
                    Process p = Runtime.getRuntime().exec("curl -X GET https://micro-chat.firebaseio.com/users.json?print=pretty&auth=jsmUjCi94i5xcHmW1iznhZHtX2oEv5amVtwRfGx8");
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(p.getInputStream()));

                    while ((line = in.readLine()) != null) {
                        getResponse = getResponse + line;

                    }

                    in.close();
                    JSONObject json = new JSONObject(getResponse);

                    Iterator<String> i;
                    i = json.keys();
                    while (i.hasNext()) {
                        String key = i.next();
                        System.out.print(key + ": ");
                        try {
                            //Print første lag af nestede JSONobjecter.
                            JSONObject j = json.getJSONObject(key);

                            if (j.get("username").equals(user) && j.get("psw").equals(psw)) {
                                foundUser = true;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    if ((token != null) && (foundUser == false)) {
                        String newUser = "curl -X PATCH -d {\"username\":\"" + user + "\",\"psw\":\"" + psw + "\"} https://micro-chat.firebaseio.com/users/" + user + ".json?print=pretty&auth=jsmUjCi94i5xcHmW1iznhZHtX2oEv5amVtwRfGx8";
                        Runtime.getRuntime().exec(newUser);
                        out.print(token);
                    } else if ((token != null) && (foundUser == true)) {
                        out.print(token);
                    } else {
                        out.print("-1"); // bad username or password
                    }

                } catch (Exception ex) {
                    Logger.getLogger(RelayServer.class.getName()).log(Level.SEVERE, null, ex);
                    System.err.println("Bad username or password.");
                    ex.printStackTrace();
                }
                break;
            }

            default: {
                break;
            }

        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "µ-chat";
    }// </editor-fold>

}
