package com.nusantara.xyz.tunnel;

import com.nusantara.xyz.logger.SkStatus;
import com.trilead.ssh2.ProxyData;
import java.io.*;
import java.io.IOException;
import java.net.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.net.ssl.*;
import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SSLTunnelProxy implements ProxyData {
  @Override
  public void close() {
    if (mSocket == null) return;
    try {
      mSocket.close();
    } catch (IOException e) {
    }
  }

  public static void Killer() {
    try {
      mSocket.close();
    } catch (IOException e) {
    }
  }

  class HandshakeTunnelCompletedListener implements HandshakeCompletedListener {
    private final String val$host;
    private final int val$port;
    private final SSLSocket val$sslSocket;

    HandshakeTunnelCompletedListener(String str, int i, SSLSocket sSLSocket) {
      this.val$host = str;
      this.val$port = i;
      this.val$sslSocket = sSLSocket;
    }

    public void handshakeCompleted(HandshakeCompletedEvent handshakeCompletedEvent) {
      SkStatus.logDebug(
          new StringBuffer()
              .append("Enable protocols : ")
              .append(Arrays.toString(val$sslSocket.getSupportedProtocols()))
              .toString());
      SkStatus.logWarning("ChiperSuite : " + handshakeCompletedEvent.getSession().getCipherSuite());
    }
  }

  private String stunnelServer;
  private int stunnelPort = 443;
  private String stunnelHostSNI;
  public static Socket mSocket;

  public SSLTunnelProxy(String server, int port, String hostSni) {
    this.stunnelServer = server;
    this.stunnelPort = port;
    this.stunnelHostSNI = hostSni;
  }

  @Override
  public Socket openConnection(String hostname, int port, int connectTimeout, int readTimeout)
      throws IOException {
    try {
      SkStatus.logWarning("Starting SSL Handshake...");
      URL url = new URL("https://" + stunnelHostSNI);
      HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
      // conn.connect();
      conn.setConnectTimeout(3000);
      conn.setReadTimeout(1000);
      conn.setRequestMethod("GET");
      int code = conn.getResponseCode();
      String ressult = Integer.toString(code);
      conn.connect();
      Certificate[] certs = conn.getServerCertificates();
      X509Certificate xnxx = (X509Certificate) certs[0];
      SkStatus.logWarning("Principal : " + xnxx.getIssuerDN().toString());

      if (!ressult.isEmpty()) {
        try {
          mSocket = SocketChannel.open().socket();
          mSocket.connect(new InetSocketAddress(stunnelServer, stunnelPort), 3000);

          if (mSocket.isConnected()) {
            mSocket = doSSLHandshake(hostname, stunnelHostSNI, port);
            mSocket.setKeepAlive(true);
            mSocket.setTcpNoDelay(true);
            SkStatus.logWarning("SSL KEEP ALIVE : " + mSocket.getKeepAlive());
            SkStatus.logWarning("SSL TCP DELAY : " + mSocket.getTcpNoDelay());
            SkStatus.logWarning("<b>Connection established</b>");
          }
          return mSocket;
        } catch (Exception e) {
          SkStatus.logWarning("mSock: " + e);
          return null;
        }
      } else {
        conn.disconnect();
        return mSocket;
      }
    } catch (Exception e) {
    }
    return mSocket;
  }

  private SSLSocket doSSLHandshake(String host, String sni, int port) throws IOException {
    TrustManager[] trustAllCerts =
        new TrustManager[] {
          new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
              return null;
            }

            public void checkClientTrusted(
                java.security.cert.X509Certificate[] certs, String authType) {}

            public void checkServerTrusted(
                java.security.cert.X509Certificate[] certs, String authType) {}
          }
        };
    try {
      KeyManager[] keyManagerArr = null;
      TLSSocketFactory tsf = new TLSSocketFactory();
      SSLSocket socket = (SSLSocket) tsf.createSocket(host, port);
      try {
        socket.getClass().getMethod("setHostname", String.class).invoke(socket, sni);
      } catch (Throwable e) {
      }
      socket.addHandshakeCompletedListener(
          new HandshakeTunnelCompletedListener(host, port, socket));
      // VpnStatus.logWarning("Starting SSL Handshake...");
      socket.startHandshake();
      return socket;
    } catch (Exception e) {
      IOException iOException =
          new IOException(
              new StringBuffer().append("Could not do SSL handshake: ").append(e).toString());
      throw iOException;
    }
  }
}

