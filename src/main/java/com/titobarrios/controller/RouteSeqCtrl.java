package com.titobarrios.controller;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;

import com.titobarrios.constants.VType;
import com.titobarrios.model.Company;
import com.titobarrios.model.RouteSequence;
import com.titobarrios.utils.Converter;
import com.titobarrios.utils.LaboralDays;
import com.titobarrios.view.Console;
import com.titobarrios.view.company.route_seqs.RSMainMenu;

public class RouteSeqCtrl {
    private Company company;

    public RouteSeqCtrl(Company company) {
        this.company = company;
    }

    public RouteSequence selectRouteSeq(RouteSequence[] routeSeqs) {
        RouteSequence selected = null;
        Console.log("Seleccione una secuencia de rutas");
        for (int i = 0; i < routeSeqs.length; i++)
            Console.log((i + 1) + ". " + routeSeqs[i].getName());
        int option = 0;
        do {
            option = Console.readNumber();
            if (option == 0)
                new RSMainMenu(company);
        } while (option < 0 || option > routeSeqs.length);
        selected = routeSeqs[option - 1];
        Console.log("Secuencia: " + selected.getName());
        for (int i = 0; i < selected.getRoutes().length; i++)
            Console.log((i + 1) + ". " + selected.getRoutes()[i].info());
        Console.log("1. Seleccionar     0. Volver");
        do {
            option = Console.readNumber();
            if (option == 0)
                selectRouteSeq(routeSeqs);
            if (option != 1)
                Console.log("Opción inválida");
        } while (option != 1);
        return selected;
    }

    public VType selectType() {
        Console.log("Seleccione el tipo de vehículo al que se le asignará esta secuencia de rutas\n" + VType.menu());
        int option = Console.readNumber();
        if (option == 0)
            new RSMainMenu(company);
        if (option < 0 || option > 4)
            selectType();
        return Converter.fromInt(option);
    }

    public String selectName() {
        Console.log("Escriba el nombre de la secuencia de rutas");
        String name = Console.readData();
        if (name.equals("0"))
            new RSMainMenu(company);
        return name;
    }

    public DayOfWeek[] selectLaboralDays() {
        Console.log(
                "Seleccione los días que estará activa la secuencias de rutas\n1. Lunes  2. Martes  3. Miércoles  4. Jueves  5. Viernes  6. Sábado 7. Domingo\n8. Todos los días  -1. Terminar de leer");
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        int option = 0;
        do {
            option = Console.readNumber();
            if (option == -1) {
                Console.log("Se terminó de leer");
                break;
            }
            if (option == 0)
                new RSMainMenu(company);
            if (option < -1 || option > 8)
                continue;
            numbers.add(option);
            if (option == 8) {
                Console.log("Ha seleccionado todos los días");
                break;
            }
        } while (option != -1);
        return LaboralDays
                .fromInt(Arrays.stream(numbers.toArray(Integer[]::new)).mapToInt(Integer::intValue).toArray());
    }

    public LocalTime selectStartingTime() {
        Console.log("Desde qué hora inicia la ruta (hh:mm:ss)");
        String startingTime = Console.readData();
        if (startingTime.equals("0"))
            new RSMainMenu(company);
        try {
            return LocalTime.parse(startingTime);
        } catch (DateTimeParseException e) {
            Console.log("El formato de la ruta se ha digitado mal, por favor, inténtelo de nuevo\n");
            selectStartingTime();
        }
        return null;
    }

    public int selectStopsQuantity() {
        Console.log("¿Cuántas paradas tendrá la ruta?");
        int stops = Console.readNumber();
        if (stops == 0)
            new RSMainMenu(company);
        if (stops < 2 && stops > 0)
            Console.log("Debe crear al menos 2 paradas\n");
        if (stops < 2)
            selectStopsQuantity();
        return stops;
    }

    public int[] selectIntervals(int stops) {
        Console.log("Digite cada intervalo en minutos");
        int[] intervals = new int[stops];
        for (int i = 1; i < stops; i++) {
            Console.log("Intervalo entre la parada " + i + " a la " + (i + 1));
            intervals[i - 1] = Console.readNumber();
            if (intervals[i - 1] == 0)
                new RSMainMenu(company);
            if (intervals[i - 1] < 0) {
                Console.log("El intervalo no puede ser negativo, inténtelo de nuevo");
                i--;
            }
        }
        return intervals;
    }
}
