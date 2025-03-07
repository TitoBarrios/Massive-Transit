package com.titobarrios.view.home;

import com.titobarrios.services.JsonServices.JsonService;
import com.titobarrios.view.Console;

public class Exit {
    public Exit() {
        JsonService.save();
        Console.log("Se ha detenido la ejecución del programa");
        System.exit(0);
    }
}
