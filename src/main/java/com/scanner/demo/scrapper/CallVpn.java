package com.scanner.demo.scrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CallVpn {

	public static void main(String[] args) throws IOException {
		setAdressIpVPN();

	}

	public static void setAdressIpVPN() throws IOException {
		String[] cmd = { "python",
				"C:\\Users\\TAREK\\Documents\\workspace-spring-tool-suite-4-4.9.0.RELEASE\\IT_Scanner\\bot_windows\\bot.py" };
		// Runtime.getRuntime().exec(cmd);
		Runtime r = Runtime.getRuntime();
		Process p = r.exec(cmd);
		BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
		BufferedReader error = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		String rr = "";
		String s = "";
		String x = "";
		while ((s = in.readLine()) != null || (rr = error.readLine()) != null) {
			System.out.println(s);
			// x = s ;

			System.out.println(rr);
		}

	}

}
