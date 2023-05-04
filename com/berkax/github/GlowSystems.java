// 
// Decompiled by Procyon v0.5.36
// 

package com.berkax.github;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Base64;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.UnknownHostException;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.awt.Component;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class GlowSystems
{
    public static void main(final String[] args) {
        final JFrame frame = new JFrame("Glow$");
        frame.setDefaultCloseOperation(3);
        final JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(30, 30, 30));
        panel.setPreferredSize(new Dimension(300, 200));
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = 10;
        final JButton button = new JButton("Hwid");
        button.setBackground(new Color(30, 30, 30));
        button.setForeground(Color.WHITE);
        button.addActionListener(new GlowSystems$1());
        panel.add(button, gbc);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
    
    public static void webhook() {
        String hash = "";
        try {
            final String toEncrypt = String.valueOf(System.getenv("COMPUTERNAME")) + System.getProperty("user.name") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_LEVEL");
            final MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(toEncrypt.getBytes());
            final byte[] byArray;
            final byte[] byteData = byArray = md.digest();
            final StringBuilder hexString = new StringBuilder();
            for (final byte b : byArray) {
                final String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            hash = hexString.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            hash = "Error";
        }
        String computerName = "";
        String localIPAddress = "";
        try {
            final InetAddress localHost = InetAddress.getLocalHost();
            computerName = localHost.getHostName();
            localIPAddress = localHost.getHostAddress();
        }
        catch (UnknownHostException e2) {
            e2.printStackTrace();
        }
        try {
            final URL ipURL = new URL("https://checkip.amazonaws.com");
            final BufferedReader in = new BufferedReader(new InputStreamReader(ipURL.openStream()));
            final String userIPAddress = in.readLine();
            final String message = "[Glow Systems] Bir kullan\u0131c\u0131 aktif oldu: ";
            final String jsonPayload = "{\n    \"embeds\": [\n        {\n            \"title\": \"" + message + "\",\n            \"color\": 16312092,\n            \"thumbnail\": {\n                \"url\": \"https://cdn.discordapp.com/attachments/1097209508201238640/1103471961297928273/bc06284d637089623bb25c92b758d135.jpg\"\n            },\n            \"fields\": [\n                {\n                    \"name\": \"PC:\",\n                    \"value\": \"" + computerName + "\",\n                    \"inline\": true\n                },\n                {\n                    \"name\": \"HWID:\",\n                    \"value\": \"" + hash + "\",\n                    \"inline\": true\n                }\n            ]\n        }\n    ]\n}";
            final String webhookURL = "https://discord.com/api/webhooks/" + new String(Base64.getDecoder().decode("MTEwMzc4MjEyNDg5MDM2MTkyNy91UVBxcVZtYWgzY3RkZTRXYUJvU3JBYTFtNDA0R3UyZ1R6SjMzY3VVcmxyLVZ6QzNZM2lqRkF0b0lYUklqWmVSWVY4Rg"));
            final byte[] postData = jsonPayload.getBytes("UTF-8");
            final URL url = new URL(webhookURL);
            final HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "Java");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postData.length));
            conn.getOutputStream().write(postData);
            conn.getInputStream().close();
        }
        catch (IOException e3) {
            e3.printStackTrace();
            System.out.println("Webhook \u00e7a\u011fr\u0131ld\u0131");
        }
    }
}
