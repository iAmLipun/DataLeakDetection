/*

        @Author : iAmLipun (Rudraprasad Prradhan),
        @CreatedON : 30 Jul, 2019, 9:20 AM,
        @File-Name : Dbops.java

 */

package org.dld.datahandling;

import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;

public class Dbops extends Rdbms implements Serializable {

    private static final long serialVersionUID = 1L;

    private static Dbops fi = null; private static Connection conn = null;

    private Dbops () { super(); conn = Rdbms.getConn(); }

    public synchronized static Dbops getFi () {

        if ( fi == null ) { fi = new Dbops (); }
        return ( fi );

    }

    public synchronized boolean insInter( int c, String ... vals  ) {

        String res = ""; String query = "";

        switch ( c ) {

            case 1 : query = "INSERT INTO USER_DETAILS ( UID , UNAME , UEMAILID, UPASS ) VALUES ( ? , ? , ? , ? )";
                     return ( insert( query, vals ) );
            case 2 : query = "INSERT INTO USER_DATA ( RUID , USUB , UFILENAME, USECKEY ) VALUES ( ? , ? , ? , ? )";
                     return ( insert( query, vals ) );
            default : return (false);

        }

    }

    private synchronized boolean insert( String ins_query, String ... vals ) {

        PreparedStatement smt = null; boolean res = false;
        try {

            smt = conn.prepareStatement( ins_query );
            smt.setString( 1 , vals[0] );
            smt.setString( 2 , vals[1] );
            smt.setString( 3 , vals[2] );
            smt.setString( 4, vals[3] );
            smt.execute();
            smt.close();
            smt.close();
            res = true;

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

    public synchronized Object fetch () {

        Statement smt = null; ResultSet rs = null; ArrayList <String []> lst = null;
        try {

            smt = conn.createStatement();
            rs = smt.executeQuery( "SELECT * FROM USER_DATA" );
            lst = new ArrayList <> ();
            if ( rs.isBeforeFirst() ) {

                    while ( rs.next() ) {

                            String arr [] = new String [4];
                            arr [0] = rs.getString( "RUID" );
                            arr [1] = rs.getString( "USUB" );
                            arr [2] = rs.getString( "UFILENAME" );
                            arr [3] = rs.getString( "USECKEY" );
                            lst.add( arr );

                    }

            }
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
        return ( lst );

    }

}
