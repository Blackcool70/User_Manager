/*
 * This file is part of the QuickServer library
 * Copyright (C) 2003-2005 QuickServer.org
 *
 * Use, modification, copying and distribution of this software is subject to
 * the terms and conditions of the GNU Lesser General Public License.
 * You should have received a copy of the GNU LGP License along with this
 * library; if not, you can download a copy from <http://www.quickserver.org/>.
 *
 * For questions, suggestions, bug-reports, enhancement-requests etc.
 * visit http://www.quickserver.org
 *
 */

package com.usrmngr.server.core.model.QuickServer.UMServer;

import org.quickserver.net.server.ClientCommandHandler;
import org.quickserver.net.server.ClientHandler;

import java.io.IOException;

public class RequestCommandHandler implements ClientCommandHandler {

    public void gotConnected(ClientHandler handler) {
        handler.sendSystemMsg("Connection opened : " +
                handler.getSocket().getInetAddress());

    }

    public void lostConnection(ClientHandler handler) {
        handler.sendSystemMsg("Connection lost : " +
                handler.getSocket().getInetAddress());
    }

    public void closingConnection(ClientHandler handler) {
        handler.sendSystemMsg("Connection closed : " +
                handler.getSocket().getInetAddress());
    }

    public void handleCommand(ClientHandler handler, String command)
            throws IOException {
        System.out.println("Msg:" + command);
        handler.sendClientMsg("OK");
    }
}
