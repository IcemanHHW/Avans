package nl.kampmeijer.fxoef031112;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Oef031112 {
    private final Button groterknop, kleinerknop, rechtsknop, linksknop;
    private final Circle cirkel;
    private int straal;
    private int xpositie;

    public Oef031112(Pane p) {
        groterknop = new Button("Groter");
        kleinerknop = new Button("Kleiner");
        linksknop = new Button("Naar links");
        rechtsknop = new Button("Naar rechts");
        straal = 10;
        xpositie = 160;
        cirkel = new Circle(xpositie, 125, straal, Color.CHOCOLATE);

        groterknop.setOnAction(e -> {
            straal++;
            cirkel.setRadius(straal);
        });

        kleinerknop.setOnAction(e -> {
            straal--;
            cirkel.setRadius(straal);
        });

        linksknop.setOnAction(e -> {
            xpositie--;
            cirkel.relocate(xpositie, 125);
        });

        rechtsknop.setOnAction(e -> {
            xpositie++;
            cirkel.relocate(xpositie, 125);
        });

        p.getChildren().addAll(groterknop, kleinerknop, linksknop, rechtsknop, cirkel);
    }
}
