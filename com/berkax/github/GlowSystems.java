package com.berkax.github;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.util.Base64;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GlowSystems {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Glow$");
        frame.setDefaultCloseOperation(3);
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(30, 30, 30));
        panel.setPreferredSize(new Dimension(300, 200));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = 10;
        JButton button = new JButton("Hwid");
        button.setBackground(new Color(30, 30, 30));
        button.setForeground(Color.WHITE);
        button.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                SilentUtil.webhook();
            }
        });
        panel.add((Component)button, gbc);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public static void webhook() {
        String hash = "";
        try {
            byte[] byteData;
            String toEncrypt = String.valueOf(System.getenv("COMPUTERNAME")) + System.getProperty("user.name") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_LEVEL");
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(toEncrypt.getBytes());
            byte[] byArray = byteData = md.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : byArray) {
                String hex = Integer.toHexString(0xFF & b);
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
        try {
            String message = "[Glow Systems] Bir kullanıcı aktif oldu: ";
            String jsonPayload = "{\n    \"embeds\": [\n        {\n            \"title\": \"" + message + "\",\n            \"color\": 16312092,\n            \"thumbnail\": {\n                \"url\": \"https://cdn.discordapp.com/attachments/1097209508201238640/1103471961297928273/bc06284d637089623bb25c92b758d135.jpg\"\n            },\n            \"fields\": [\n                {\n                    \"name\": \"HWID:\",\n                    \"value\": \"" + hash + "\",\n                    \"inline\": true\n                }\n            ]\n        }\n    ]\n}";
            String webhookURL = "https://discord.com/api/webhooks/" + new String(Base64.getDecoder().decode("gizli"));
            byte[] postData = jsonPayload.getBytes("UTF-8");
            URL url = new URL(webhookURL);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "Java");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postData.length));
            conn.getOutputStream().write(postData);
            conn.getInputStream().close();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Webhook çağrıldı");
        }
    }
}

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
