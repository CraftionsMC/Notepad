/*
 * Copyright (c) 2021 Ben Siebert. All rights reserved.
 */
package net.craftions.notepad.main;

import net.craftions.notepad.gui.Editor;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Editor.showEditor();
        if(args.length > 0){
            String p = "";
            for(int i = 0; i < args.length; i++){
                p+=args;
                if(!((i + 1) == args.length)){
                    p+=" ";
                }
            }
            File f = new File(p);
            if(!f.exists()){
               f.createNewFile();
            }
            Editor.loadFile(f);
        }
    }
}
