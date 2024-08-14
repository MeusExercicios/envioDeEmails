package send.emails;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.SimpleEmail;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) {

        String nomeRemetente = JOptionPane.showInputDialog(null, "Digite o seu nome");
        String emailRemetente = JOptionPane.showInputDialog(null, "Digite seu email");
        String assuntoRemetente = JOptionPane.showInputDialog(null, "Digite o assunto do email");
        String mensagemRemetente = JOptionPane.showInputDialog(null, "Digite sua mensagem");

        List<String> list = new ArrayList<>();

        String quantidadeEmails = JOptionPane.showInputDialog(null, "Digite a quantidade de emails que deseja enviar: ");
        for(int i = 0; i < Integer.parseInt(quantidadeEmails); i++){
            String emailDestinatario = JOptionPane.showInputDialog(null, "Digite o email para quem deseja enviar: ");

            list.add(emailDestinatario);
        }

        JPasswordField jPasswordField = new JPasswordField();
        int option = JOptionPane.showConfirmDialog(null, jPasswordField, "Para finalizar, digite sua senha",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (option == JOptionPane.OK_OPTION) {
            // O usuário clicou em OK
            char[] password = jPasswordField.getPassword();
            String senha = new String(password);
            jPasswordField.setText(""); // limpa o campo de senha

            String emailCorreto = System.getenv("EMAIL_USER");
            String senhaCorreta = System.getenv("EMAIL_PASSWORD");

            if (emailRemetente.equals(emailCorreto) && senha.equals(senhaCorreta)) {
                try {
                    // Criei um objeto email para poder configurar
                    SimpleEmail email = new SimpleEmail();
                    email.setHostName("smtp.gmail.com");
                    email.setSmtpPort(465);
                    email.setAuthenticator(new DefaultAuthenticator(emailRemetente, senha));
                    email.setSSLOnConnect(true);// conexão segura

                    email.setFrom(emailRemetente);
                    email.setSubject(assuntoRemetente);
                    email.setMsg(mensagemRemetente);

                    for (String emailDestino : list) {
                        email.addTo(emailDestino);
                    }

                    email.send();

                    JOptionPane.showMessageDialog(null, "Email enviado com sucesso!");
                    JOptionPane.showMessageDialog(null, "Obrigado por usar nosso serviço " + nomeRemetente + " !");
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Falha ao enviar email: " + e.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Email ou senha inválido");
            }

        } else {
            // O usuário clicou em Cancelar ou fechou o diálogo
            JOptionPane.showMessageDialog(null, "Cadastro de senha cancelado.");
        }
    }
}
