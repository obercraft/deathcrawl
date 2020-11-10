package net.sachau.zarrax;

import javafx.concurrent.Task;

public class InitTask extends Task<String> {

    private double width, height;

    public InitTask(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    protected String call() throws Exception {
//        String standard = "/fonts/LibreBaskerville-Regular.ttf";
//        int fontSize = 12;
//        Fonts.getInstance()
//                .load("symbol-12", Main.class.getResourceAsStream("/fonts/fontawesome-solid-900.otf"), 12).progress(this, 1)
//                .load("h1",Main.class.getResourceAsStream(standard), fontSize*4).progress(this, 2)
//                .load("h2",Main.class.getResourceAsStream(standard), fontSize*3).progress(this, 3)
//                .load("h3",Main.class.getResourceAsStream(standard), fontSize*2).progress(this, 4)
//                .load("standard",Main.class.getResourceAsStream(standard), fontSize).progress(this, 5)
//                .load("italic",Main.class.getResourceAsStream("/fonts/LibreBaskerville-Italic.ttf"), fontSize).progress(this, 6)
//                .load("bold",Main.class.getResourceAsStream("/fonts/LibreBaskerville-Bold.ttf"), fontSize).progress(this, 7)
//        ;
        //ApplicationContext.init(width, height);
        //ApplicationContext.getGameEngine().setInitialized(true);
        //this.update(10);
        return null;
    }

    public void update(int current) {
        this.updateProgress(current, 10);
    }
}
