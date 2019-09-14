/*  -> Designed for testing and development purposes.
 *  -> Project to design a 'SecurityPatching' prototype.
 *  -> Development Phase -- Intermediate.
 *  -> Project Type -- Educational.
 *  -> Owner/Designer of code file :
 *             @ Name - Rudraprasad Pradhaan.
 *             @ Email - rudraprasadpradhan4@gmail.com.
 *  -> Copyright Norms - Every piece of code given below has been written by 'Palash Sarkar (Tj07)'©,
 *                       and he holds the rights to the file. Not meant to be
 *                       copied or tampered with, without prior permission from the author.
 *  -> Guide - Mukesh Kumar.
 */

package org.dld.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;
import java.io.File;

import org.dld.datahandling.Dbops;
import org.dld.datahandling.Checker;
import org.dld.mailing.Email;

@ WebServlet ( description = "Core Servlet For Begining all Operations", urlPatterns = { "/MainServlet" } )
public final class MainServlet extends HttpServlet
{

         private static final long serialVersionUID = 1L;

         private static  InetAddress ip ; private static  String ipadd  ;

         @ Override
         public void init () {

             Email.buildEmail( (getServletContext().getRealPath("/WEB-INF")) + File.separator + "Credentials" );
             try{

	                System.setProperty( "java.net.preferIPv4Stack", "true" );
                    ip = InetAddress.getLocalHost();
	                ipadd = ip.getHostAddress();

	         } catch ( UnknownHostException e ) { e.printStackTrace(); }

	     }

         @ Override
         protected synchronized void doGet ( HttpServletRequest request, HttpServletResponse response )throws ServletException, IOException {

                    switch ( request.getParameter( "req_origin" ) ) {

                            case "setdata" : getServletContext().getRequestDispatcher( "/setdata.jsp" ).forward( request, response );
                                             break;
                            case "getdata" : request.setAttribute( "list", (Dbops.getFi()).fetch() );
                                             getServletContext().getRequestDispatcher( "/getdata.jsp" ).forward( request, response );
                                             break;
                            case "back" : request.setAttribute( "redirloc", "./login.html" );
                                          getServletContext().getRequestDispatcher( "/loghome.jsp" ).forward( request, response );
                                          break;
                            default : break;

                    }

         }

         @ Override
         protected synchronized void doPost ( HttpServletRequest request, HttpServletResponse response )throws ServletException, IOException {

                    switch ( request.getParameter( "req_origin" ) ) {

                            case "ureg" : request.setAttribute( "redirloc", "./register.html" );
                                          if ( (Checker.getChk()).checkEml( request.getParameter( "ueml" ) ) ) {

                                                request.setAttribute( "response_body", "Either email already exists or internal sql error !!" );
                                                getServletContext().getRequestDispatcher( "/faliure.jsp" ).forward( request, response );

                                          }
                                          else if ( (Checker.getChk()).checkPass( request.getParameter( "upassw" ) ) ) {

                                                request.setAttribute( "response_body", "Either password is already in use or internal sql error !!" );
                                                getServletContext().getRequestDispatcher( "/faliure.jsp" ).forward( request, response );

                                          } else {

                                                String uid = generateUID();
                                                if ( (Dbops.getFi()).insInter( 1, uid, (request.getParameter( "uname" )).toUpperCase(), request.getParameter( "ueml" ), request.getParameter( "upassw" ) ) ) {

                                                        Email.sendUserRegEmail( uid, (request.getParameter("uname")).toUpperCase(), request.getParameter("ueml"), request.getParameter("upassw"), "123456" );
                                                        request.setAttribute( "response_body", ("Your UserId : " + uid) );
                                                        getServletContext().getRequestDispatcher( "/success.jsp" ).forward( request, response );

                                                } else {

                                                        request.setAttribute( "response_body", "Internal sql error !!" );
                                                        getServletContext().getRequestDispatcher( "/faliure.jsp" ).forward( request, response );

                                                }

                                          }
                                          break;
                            case "usrdata" : response.setContentType( "text" );
                                             response.setCharacterEncoding( "UTF-8" );
                                             response.setBufferSize( 8192 );
                                             PrintWriter out = response.getWriter();
                                             if ( (Dbops.getFi()).insInter( 2, request.getParameter( "uid" ), request.getParameter( "usub" ), request.getParameter( "ufilename" ), request.getParameter( "useckey" ) ) ) {

                                                    out.print( "Data entered successfully !!" );

                                             } else { out.print( "Internal sql error !!" ); }
                                             out.flush();
                                             out.close();
                                             break;
                            case "login" : request.setAttribute( "redirloc", "./login.html" );
                                           if ( (Checker.getChk()).checkLogin( request.getParameter( "uid" ), request.getParameter( "pass" ) ) ) {

                                                    getServletContext().getRequestDispatcher( "/loghome.jsp" ).forward( request, response );

                                           } else {

                                                    request.setAttribute( "response_body", "Either invalid uid/password or internal sql error !!" );
                                                    getServletContext().getRequestDispatcher( "/faliure.jsp" ).forward( request, response );

                                           }
                                           break;
                            default : break;

                    }

         }

         private synchronized String generateUID (  ) { return ( "USR" + ( new Random () ).nextInt( 50000 ) ); }

}