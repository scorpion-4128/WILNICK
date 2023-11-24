package com.nusantara.xyz.tunnel;

import com.nusantara.xyz.logger.*;
import com.trilead.ssh2.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;

public class Pinger extends Thread {
    private final Connection a;
    private final String b;
    private LocalPortForwarder c;
    private boolean d;
    private Socket f;

    public Pinger(Connection aVar, String str) {
        this.a = aVar;
        this.b = str;
    }

    private int b() {
        return (new Random().nextInt(6) + 5) * 1000;
    }

    public synchronized void close() {
        this.d = false;
        interrupt();
    }

    public void run() {
	 SkStatus.logWarning("Ping : " + this.b);
        try {
			//VpnStatus.logWarning("pinger started");
			this.c = this.a.createLocalPortForwarder(9395, this.b, 80);
            this.d = true;
            while (this.d) {
                try {
                    this.f = new Socket("127.0.0.1", 9395);
                    this.f.setSoTimeout(20000);
                    OutputStream outputStream = this.f.getOutputStream();
                    outputStream.write(("GET http://" + this.b + "/ HTTP/1.1\r\nHost: " + this.b + "\r\n\r\n").getBytes());
                    outputStream.flush();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.f.getInputStream()));
                    String readLine = bufferedReader.readLine();
                    if (readLine != null) {
						SkStatus.logWarning("Respon : "+ "<font color='#5cf55d'>" + readLine + "</font>");
                    } else {
						SkStatus.logWarning("<strong><font color='#D50000'>No Data</font></strong>");
                    }
                    bufferedReader.close();
                    outputStream.close();
                    this.f.close();
                } catch (Exception e) {
					SkStatus.logWarning("<strong><font color='#d50000'>Server Timeout</font></strong>");
                }
                try {
                    sleep((long) b());
                } catch (Exception e2) {
					//VpnStatus.logWarning("pinger stopped");
					this.c.close(); // fixed (address already in use) bug
					this.c = null; // This is required
                    return;
                }
            }
        } catch (Exception e3) {
			SkStatus.logWarning("Pinger: " + e3.toString());
		}
    }
}
