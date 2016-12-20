/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lecteuraudio.modele;

import static java.lang.Math.floor;
import static java.lang.String.format;
import javafx.util.Duration;

/**
 *
 * @author nahel
 */
public class Utils {
 
    public static String formatTime(Duration elapsed) {
        int intElapsed = (int) floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * 60 * 60;
        }
        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60
                - elapsedMinutes * 60;
        
        if (elapsedHours > 0) {
            return format("%d:%02d:%02d", elapsedHours,
                    elapsedMinutes, elapsedSeconds);
        } else {
            return format("%02d:%02d", elapsedMinutes,
                    elapsedSeconds);
        }
    }
}

