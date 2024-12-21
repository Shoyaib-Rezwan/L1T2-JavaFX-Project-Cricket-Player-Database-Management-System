package SideWindows;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Popup;

public class ImagePopUp {
    private Image image;
    private ImageView imageView;

    public ImagePopUp(String pathToImage) {
        image = new Image(pathToImage);
        imageView = new ImageView(image);
        imageView.setFitWidth(300);
        imageView.setPreserveRatio(true);
    }

    public Popup getPopUp() {
        Popup popup = new Popup();
        popup.getContent().add(imageView);
        popup.setAutoHide(true);
        return popup;
    }
}
