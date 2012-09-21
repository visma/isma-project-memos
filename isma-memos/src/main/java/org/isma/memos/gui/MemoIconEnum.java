package org.isma.memos.gui;

import javax.swing.*;

public enum MemoIconEnum {
    APPLICATION_ICON("images/icon.png"),
    SPLASH_SCREEN_ICON("images/splashscreen.png"),
    ATTACHMENT_ICON("images/attachment.png"),
    TAG_ICON("images/tag.png"),
    NEW_ICON("images/new.png"),
    OK_ICON("images/ok.png"),
    KO_ICON("images/ko.png");

    private String path;
    private ImageIcon imageIcon;


    MemoIconEnum(String path) {
        this.path = path;
        this.imageIcon = new ImageIcon(getClass().getClassLoader().getResource(getPath()));
    }


    public String getPath() {
        return path;
    }


    public ImageIcon getImageIcon() {
        return imageIcon;
    }


    public static Icon getIcon(String extension) {
        for (MemoIconEnum iconEnum : values()) {
            if (iconEnum.path.startsWith("images/" + extension + ".")) {
                return iconEnum.getImageIcon();
            }
        }
        return APPLICATION_ICON.getImageIcon();
    }
}