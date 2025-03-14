import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MotATrouverGUI {
    private static String motSecret;
    private static int nbEssaisMax;
    private static int nbEssais = 0;

    public static void main(String[] args) {
        // Fenêtre principale
        JFrame frame = new JFrame("Le Mot à Trouver");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLayout(new BorderLayout());

        // Panneau supérieur pour l'information
        JPanel topPanel = new JPanel();
        JLabel infoLabel = new JLabel("Joueur 1 : Saisissez un mot de minimum 5 lettres.");
        topPanel.add(infoLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        // Panneau central pour l'entrée du mot
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        JLabel motLabel = new JLabel("Entrez un mot :");
        JTextField motField = new JTextField(20);
        JButton validerButton = new JButton("Valider");
        centerPanel.add(motLabel);
        centerPanel.add(motField);
        centerPanel.add(validerButton);
        frame.add(centerPanel, BorderLayout.CENTER);

        // Panneau inférieur pour les résultats
        JPanel bottomPanel = new JPanel();
        JLabel resultLabel = new JLabel(" ");
        bottomPanel.add(resultLabel);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Action pour le bouton de validation (mot secret)
        validerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = motField.getText();
                if (motSecret == null) {
                    if (input.length() < 5) {
                        resultLabel.setText("Erreur : Le mot doit contenir au moins 5 lettres.");
                    } else {
                        motSecret = input;
                        nbEssaisMax = motSecret.length();
                        nbEssais = 0;
                        infoLabel.setText("Joueur 2 : Devinez le mot en " + nbEssaisMax + " essais maximum.");
                        motLabel.setText("Entrez votre mot :");
                        motField.setText("");
                        resultLabel.setText("");
                    }
                } else {
                    // Joueur 2 entre son mot
                    if (input.equalsIgnoreCase("SOL")) {
                        resultLabel.setText("Vous avez abandonné. Le mot était : " + motSecret);
                        motField.setEnabled(false);
                        validerButton.setEnabled(false);
                    } else if (input.length() != motSecret.length()) {
                        resultLabel.setText("Erreur : Le mot doit contenir exactement " + motSecret.length() + " lettres.");
                    } else {
                        nbEssais++;
                        int lettresBienPlacees = 0;
                        int lettresCommunes = 0;

                        for (int i = 0; i < motSecret.length(); i++) {
                            if (input.charAt(i) == motSecret.charAt(i)) {
                                lettresBienPlacees++;
                            } else if (motSecret.indexOf(input.charAt(i)) != -1) {
                                lettresCommunes++;
                            }
                        }

                        if (input.equalsIgnoreCase(motSecret)) {
                            resultLabel.setText("Félicitations ! Vous avez trouvé le mot '" + motSecret + "' en " + nbEssais + " essais !");
                            motField.setEnabled(false);
                            validerButton.setEnabled(false);
                        } else if (nbEssais >= nbEssaisMax) {
                            resultLabel.setText("Dommage ! Vous avez épuisé vos essais. Le mot était : " + motSecret);
                            motField.setEnabled(false);
                            validerButton.setEnabled(false);
                        } else {
                            resultLabel.setText("Essai " + nbEssais + "/" + nbEssaisMax + " : " +
                                    lettresBienPlacees + " bien placée(s), " +
                                    lettresCommunes + " commune(s).");
                        }
                    }
                }
            }
        });

        // Affichage de la fenêtre
        frame.setVisible(true);
    }
}
