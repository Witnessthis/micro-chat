/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package relay;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
                out.println("<br>resources:</br>");
                out.println("<br>/auth?user=[username]&psw=[password]</br>");
//                out.println("<br>/fileserver/download</br>");
//                out.println("<br>/fileserver/upload</br>");
//                out.println("<br>/fileserver/delete</br>");
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
                out.println("<h1>resources:</h1>");
                out.println("<br>/auth?user=[username]&psw=[password]</br>");
//                out.println("<br>/fileserver/download</br>");
//                out.println("<br>/fileserver/upload</br>");
//                out.println("<br>/fileserver/delete</br>");
                out.println("</body>");
                out.println("</html>");

                break;
            }

            case "/RelayServer/auth": {

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

                            if (j.get("username").equals(user)) {
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
                        out.print("Something went wrong. Please try again."); // if token is null (Not properly generated)
                    }

                } catch (Exception ex) {
                    Logger.getLogger(RelayServer.class.getName()).log(Level.SEVERE, null, ex);
                    System.err.println("Bad username or password.");
                    ex.printStackTrace();
                }
                break;
            }

//            case "/RelayServer/fileserver/upload": {
//                String user = request.getParameter("user");
//                String psw = request.getParameter("psw");
//                String payload = request.getParameter("payload");
//
//                out.println("<html>");
//                out.println("<head>");
//                out.println("<title>Upload</title>");
//                out.println("</head>");
//                out.println("<body>");
//                out.println("<br>" + user + "</br>");
//                out.println("<br>" + psw + "</br>");
//                out.println("<br>" + payload + "</br>");
//                out.println("</body>");
//                out.println("</html>");
//
//                break;
//            }
//
//            case "/RelayServer/fileserver/download": {
//
//                String URI = request.getParameter("URI");
//
//                break;
//            }
//
//            case "/RelayServer/fileserver/delete": {
//                String user = request.getParameter("user");
//                String psw = request.getParameter("psw");
//                String URI = request.getParameter("URI");
//
//                out.println("<html>");
//                out.println("<head>");
//                out.println("<title>Delete</title>");
//                out.println("</head>");
//                out.println("<body>");
//                out.println("<br>" + user + "</br>");
//                out.println("<br>" + psw + "</br>");
//                out.println("<br>" + URI + "</br>");
//                out.println("</body>");
//                out.println("</html>");
//
//                break;
//            }

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
