package client;

import java.applet.*;
import java.awt.*;

public class ClientApplet extends Applet {

    public void init() {
        String host = getParameter("host");
        int port = Integer.parseInt(getParameter("port"));
        setLayout(new BorderLayout());
        add("Center", new Client(host, port));
    }
}
