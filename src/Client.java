import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {

    // A socket is one end-point in a communication link. Sockets allow communication between two machines.
    // A server has a socket that is bound to a specific port number that a client can connect to


    public static void main(String[] args) throws IOException {

        // localhost is a domain name like www.google.com
        Socket socket = new Socket("localhost", 1234);
        System.out.println("Connected to server.");

        JFrame jFrame = new JFrame("Client");
        jFrame.setSize(500, 500);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // An icon is a fixed size picture the image
        ImageIcon imageIcon = new ImageIcon("/Users/chanelmorgan/IdeaProjects/TCPServerClientSendingImages/about-image.png");
        // Resize the image
        Image originalImage = imageIcon.getImage();
        int targetWidth = 200;  // Adjust as needed
        int targetHeight = 200; // Adjust as needed
        Image resizedImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        ImageIcon resizedImageIcon = new ImageIcon(resizedImage);


        JLabel jLabelPic = new JLabel(resizedImageIcon);
        JButton jButton = new JButton("Send image to Sever");

        jFrame.add(jLabelPic, BorderLayout.CENTER);
        jFrame.add(jButton, BorderLayout.SOUTH);

        jFrame.setVisible(true);

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    OutputStream outputStream = socket.getOutputStream();
                    BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

                    Image image = resizedImageIcon.getImage();
                    BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
                            BufferedImage.TYPE_INT_RGB);
                    Graphics graphics = bufferedImage.getGraphics();
                    graphics.drawImage(image, 0, 0, null);
                    graphics.dispose();

                    ImageIO.write(bufferedImage, "png", bufferedOutputStream);

                    bufferedOutputStream.close();
                    socket.close();

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

    }
}
