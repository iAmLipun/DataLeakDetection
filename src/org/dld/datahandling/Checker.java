/*

        @Author : iAmLipun (Rudraprasad Pradhan ),
        @CreatedON : 30 Jul, 2019, 1:20 AM,
        @File-Name : Checker.java

 */

package org.dld.datahandling;

import java.io.Serializable;
import java.sql.*;

public class Checker extends Rdbms implements Serializable
{

        private static final long serialVersionUID = 1L;

        private static Checker chk = null; private static Connection conn = null;

        private Checker () { super(); conn = Rdbms.getConn(); }

        public synchronized static Checker getChk () {

            if ( chk == null ) { chk = new Checker (); }
            return ( chk );

        }

        public synchronized boolean checkEml( String email ) {

            return ( isExists( "SELECT UEMAILID FROM USER_DETAILS WHERE UEMAILID=\'" + email + "\'" ) );

        }

        public synchronized boolean checkPass( String passw ) {

            return ( isExists( "SELECT UPASS FROM USER_DETAILS WHERE UPASS=\'" + passw + "\'" ) );

        }

        public synchronized boolean checkLogin( String uid, String pass ) {

            return ( isExists( "SELECT * FROM USER_DETAILS WHERE UID=\'" + uid + "\' AND UPASS=\'" + pass + "\'" ) );

        }

        private synchronized boolean isExists( String chk_query ) {

            Statement smt = null; ResultSet rs = null; boolean res = true;
            try {

                smt = conn.createStatement();
                rs=smt.executeQuery( chk_query );
                if( !(rs.isBeforeFirst()) ) { res = false; }
                rs.close();
                smt.close();

            } catch ( SQLException se ) {

                se.printStackTrace();

            } catch ( Exception e ) {

                e.printStackTrace();

            } finally {

                try {

                    if ( smt != null ) { smt.close(); }

                }
                catch ( SQLException se2 ) { se2.printStackTrace(); }
            }
            return ( res );

        }

}
