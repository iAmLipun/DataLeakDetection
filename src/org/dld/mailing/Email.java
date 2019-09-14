/*  -> Designed for testing and development purposes.
 *  -> Project to design a 'SecurityPatching' prototype.
 *  -> Development Phase -- Intermediate.
 *  -> Project Type -- Educational.
 *  -> Owner/Designer of code file :
 *             @ Name - Rudraprasad Pradhan.
 *             @ Email - rudraprasadpradhan4@gmail.com.
 *  -> Copyright Norms - Every piece of code given below has been written by 'Palash Sarkar (Tj07)'©,
 *                       and he holds the rights to the file. Not meant to be
 *                       copied or tampered with, without prior permission from the author.
 *  -> Guide - Mukesh Kumar.
 */

package org.dld.mailing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Properties;
import java.util.Date;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class Email implements Serializable
{
        
       private static final long serialVersionUID = 1L;
       private static String tkn_URL = "https://www.googleapis.com/oauth2/v4/token";
       private static String acc_tkn = "";
       private static long expire_time = 0L; 
       private static JSONObject cred = new JSONObject ();
       private static String user = "";
       private static String pass = "";
       private static String file_path = "";
       
       synchronized public static void buildEmail ( String flp ) {

               try {

                      file_path = flp;
                      readfrombinencrypted ();

               } catch ( Exception e ) { System.out.println ( "Exception caught in static block - " + e ); }

       }
       
       
       synchronized public static void sendUserRegEmail ( String ... vals ) {

               try {

	                    checknresetAccTkn ();
	                    user = cred.get( "EMAIL_ID" ).toString();
	                    pass = cred.get( "EMAIL_PASSWORD" ).toString();
	                    String to = vals[2];
	                    String from = cred.get( "EMAIL_ID" ).toString();
	                    String subject = "User Registration Details---";
	                    boolean sessionDebug = false;
	                    Properties props = System.getProperties();
	                    props.put( "mail.smtp.starttls.enable", "true" );
	                    props.put( "mail.smtp.host", "smtp.gmail.com" );
	                    props.put( "mail.smtp.port", "587" );
	                    props.put( "mail.smtp.auth", "true" );
	                    props.put( "mail.smtp.auth.mechanisms", "XOAUTH2" );
	                    props.put( "mail.smtp.starttls.required", "true" );
	                    props.put( "mail.smtp.starttls.enable", "true" );
	                    props.put( "mail.transport.protocol", "smtp" );
	                    props.put( "mail.user", user );
	                    props.put( "mail.password", pass );
	                    java.security.Security.addProvider( new com.sun.net.ssl.internal.ssl.Provider () );
	                    props.put( "mail.smtp.socketFactory.port", "587" );
	                    props.put( "mail.smtp.socketFactory.fallback","true" );
	                    props.put( "mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory" );
	                    System.out.println("Sending Message Through GMail .....");
	                    Session mailSession = Session.getDefaultInstance( props, new javax.mail.Authenticator () {

                                                                                    @Override
                                                                                    protected PasswordAuthentication getPasswordAuthentication() {

                                                                                       final String ur = user;
                                                                                       final String ps = pass;
                                                                                       return ( new PasswordAuthentication( ur, ps ) );

                                                                                    }

                                                                                } );
	                    mailSession.setDebug( sessionDebug );
	                    Message msg = new MimeMessage ( mailSession );
	                    msg.setFrom( new InternetAddress ( from ) );
	                    InternetAddress[] address = { new InternetAddress ( to ) };
	                    msg.setRecipients( Message.RecipientType.TO, address );
	                    msg.setSubject( subject ); msg.setSentDate( new Date() );
	                    msg.setContent( "<h2>You Have Been Successfully Registered. Given below are your registration details --</h2>"
	            		                + "<h4>" + "USERID : - " + vals[0] + "</h4>"
	            		                + "<h4>" + "USERNAME : - " + vals[1] + "</h4>"
	            		                + "<h4>" + "USER_EMAIL_ID : - " + vals[2] + "</h4>"
	            		                + "<h4>" + "PASSWORD : - " + vals[3] + "</h4>"
                                        + "<h4>" + "SECRETKEY : - " + vals[4] + "</h4>", "text/html" );
	                    Transport transport = mailSession.getTransport( "smtp" );
	                    transport.connect( "smtp.gmail.com", user, acc_tkn );
	                    transport.sendMessage( msg, msg.getAllRecipients() );
	                    transport.close();
	                    System.out.println("Message sent successfully!!");
	          
               } catch ( Exception ex ) { System.out.println( ex ); }

	   }
              
       synchronized private static void checknresetAccTkn () {

               if( System.currentTimeMillis() > expire_time ) {

                         try {

                                String request = "client_id=" + URLEncoder.encode( cred.get( "CLIENT_ID" ).toString(), "UTF-8" )
                                                              + "&client_secret=" + URLEncoder.encode( cred.get( "CLIENT_SECRET" ).toString(), "UTF-8" )
                                                              + "&refresh_token=" + URLEncoder.encode( cred.get( "REFRESH_TOKEN" ).toString(), "UTF-8" )
                                                              + "&grant_type=refresh_token";
                                HttpURLConnection conn = (HttpURLConnection) new URL( tkn_URL ).openConnection();
                                conn.setDoOutput( true );
                                conn.setRequestMethod( "POST" );
                                PrintWriter out = new PrintWriter( conn.getOutputStream() );
                                out.print( request );
                                out.flush();
                                out.close();
                                conn.connect();
                                try {

                                        HashMap <String, Object> result;
                                        result = new ObjectMapper().readValue( conn.getInputStream(), new TypeReference <HashMap <String, Object>> () {} );
                                        acc_tkn = (String) result.get( "access_token" );
                                        //System.out.println ( "AccessToken is - " + acc_tkn );
                                        expire_time = System.currentTimeMillis() + ( ((Number)result.get( "expires_in" )).intValue() * 1000 );

                                } catch ( IOException e ) {

                                        String line;
                                        BufferedReader in = new BufferedReader ( new InputStreamReader ( conn.getErrorStream() ) );
                                        while ( (line = in.readLine()) != null ) { System.out.println( line ); }
                                        System.out.flush();

                                } finally { conn.disconnect(); }

                         } catch ( Exception e ) { System.out.println ( "Token Error !!! " + e ); }

               }

       }
               
       @SuppressWarnings("unchecked")
       synchronized public static void writetobinencrypted ( String ... vals ) throws Exception {

               JSONObject det = new JSONObject();
               det.put( "EMAIL_ID", vals[0] );
               det.put( "EMAIL_PASSWORD", vals[1] );
               det.put( "GPROJ_ID", vals[2] );
               det.put( "CLIENT_ID", vals[3] );
               det.put( "CLIENT_SECRET", vals[4] );
               det.put( "REFRESH_TOKEN", vals[5] );
               File fl = new File ( file_path );
               if ( fl.exists() ) { fl.delete(); fl = new File ( file_path ); }
               OutputStream ots = new FileOutputStream ( fl );
               byte [] bt = (new AES()).encrypt( det.toJSONString() ).getBytes( "UTF-8" );
               ots.write( bt );
               ots.flush();
               ots.close();
               readfrombinencrypted();

       }
               
       synchronized private static void readfrombinencrypted() throws Exception {

               File fl = new File ( file_path );
               System.out.println( fl.exists() + " <-FlExists & CanRdFl-> " + fl.canRead() );
               InputStream in = new FileInputStream ( fl );
               byte [] r = new byte [(int)fl.length()];
               in.read( r );
               String [] str = (new String ( r )).split( "\n" );
               String js = (new AES ()).decrypt( str[0], str[1] );
               try { cred = (JSONObject)(new JSONParser ()).parse( js ); }
               catch ( ParseException pe ) { System.out.println( "Json Parse Exception" + pe ); }
               /*System.out.println(cred.get("EMAIL_ID"));
               System.out.println(cred.get("EMAIL_PASSWORD"));
               System.out.println(cred.get("GPROJ_ID"));
               System.out.println(cred.get("CLIENT_ID"));
               System.out.println(cred.get("CLIENT_SECRET"));
               System.out.println(cred.get("REFRESH_TOKEN"));*/
                      
       }

       /*public static void main (String args[]){

           try{
               Email.writetobinencrypted("team.eworld97@gmail.com", "e_wrld9977#", "e-world-188519", "529792984638-u177obukg7ne2tvbilbng8rg8h7ju2l0.apps.googleusercontent.com", "eP3h8TXiuHsR6HgdWYzCz0Bo", "1/_9k577AxIqgpU5eX3dtL97qJxOL_g0s-BOChW0AKY18");
               Email.readfrombinencrypted();
           }catch (Exception e) {e.printStackTrace();}

       }*/

}
