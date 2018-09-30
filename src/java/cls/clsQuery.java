/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cls;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import static javax.servlet.SessionTrackingMode.URL;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

/**
 *
 * @author Metalheadfk
 */
public class clsQuery {

    final String url = "http://127.0.0.1:8069",
            db = "mydb",
            username = "boyadd@gmail.com",
            password = "65239632";

    final XmlRpcClient client = new XmlRpcClient();
    final XmlRpcClientConfigImpl common_config = new XmlRpcClientConfigImpl();
 

    private void CrateConnection() {
        try {
            common_config.setServerURL(
                    new URL(String.format("%s/xmlrpc/2/common", url)));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getlogin(String type) {
        String times = "";
        try {
            
            this.CrateConnection();

            client.execute(common_config, "version", emptyList());
            int uid = (int) client.execute(
                    common_config, "authenticate", asList(
                            db, username, password, emptyMap()));

            times = String.valueOf(uid);

        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }

        return times;

    }

    public boolean checkaccess(int uid, String acctype, XmlRpcClient models) {

        try {
            
            this.CrateConnection();
            
            models.execute("execute_kw", asList(
                    db, uid, password,
                    "res.partner", "check_access_rights",
                    asList(acctype),
                    new HashMap() {
                {
                    put("raise_exception", false);
                }
            }
            ));

            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

    public List callpartner(int uid) {
        String times = "";
        try {
            
            this.CrateConnection();

            if (checkaccess(uid, "read", client)) {

                List listData = null;

                listData = asList((Object[]) client.execute("execute_kw", asList(
                        db, uid, password,
                        "sale.order", "search_read",
                        asList(),
                        new HashMap() {
                    {
                        put("fields", asList("name", "date_order", "partner_id"));
                        put("limit", 5);
                    }
                }
                )));

                return listData;

            } else {
                return null;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public String createcust(int uid, int compid) {
        try {

            this.CrateConnection();
            
            if (checkaccess(uid, "create", client)) {

                Integer id = (Integer) client.execute("execute_kw", asList(
                        db, uid, password,
                        "res.partner", "create",
                        asList(new HashMap() {
                            {
                                put("name", "New Partner test");
                                put("parent_id", compid);
                            }
                        })
                ));

                return id.toString();

            } else {
                return null;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    public List updatecust(int uid, int custid) {
        String times = "";
        try {

            this.CrateConnection();
            
            if (checkaccess(uid, "write", client)) {

                List listData = null;

                client.execute("execute_kw", asList(
                        db, uid, password,
                        "res.partner", "write",
                        asList(
                                asList(custid),
                                new HashMap() {
                            {
                                put("name", "Newer Partner boy");
                                put("street", "79/599");
                                put("street2", "บางแก้ว");
                                put("city", "สมุทรปราการ");
                            }
                        }
                        )
                )); 
                
                listData = asList((Object[]) client.execute("execute_kw", asList(
                        db, uid, password,
                        "res.partner", "name_get",
                        asList(asList(custid))
                )));

                return listData;

            } else {
                return null;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

}
