package com.elysium.lab;

/**
 * Created by jay on 11/29/16.
 */

public class ImageRacer {

    private ImageRacerListener listener;
    private static ImageRacer instance;

    public interface ImageRacerListener {
        void updateImages(String type);
    }

    private ImageRacer() {}

    public static ImageRacer getInstance() {
        if (instance == null) {instance = new ImageRacer();}
        return instance;
    }

    public void setImageRacerListener(ImageRacerListener listen) {
        this.listener = listen;
    }

    public void updateMainThread(String type) {
        if (listener != null) {
            listener.updateImages(type);
        }
    }
}
