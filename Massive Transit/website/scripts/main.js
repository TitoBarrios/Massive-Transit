import {routes, buses, users, addUser, addBus, createRoutes, userLogIn} from './functions.js';

(() => {
    addUser('admin', '0000');
})();

(() => {
    let option = 0;
    do{let userName;
        let userPassword;
        option = prompt("Escriba la opción que más le convenga\n1. LogIn\n2. Ver rutas\n3. Registrarse\n4. Ingresar como administrador");
        switch(option){
            case '1': userName = prompt("Escriba su usuario");
                userPassword = prompt("Escriba su contraseña");
                if(userLogIn(userName, userPassword)){
                    do{
                        option = prompt("1. Comprar Ticket\n2. Agregar fondos a la billetera");
                        switch(option){
                            case '1': alert("Función aún no disponible");
                                break;
                            case '2': option = prompt("Su saldo actual es de: " + users[userNumber].wallet + "\n1. Agregar más fondos\n0. Volver");
                                switch(option){
                                    case 1: option = prompt("Digite la cantidad de dinero que desea agregar a su cuenta");
                                    users[userNumber].wallet += option;
                                    alert("Se ha agregado correctamente " + option + " pesos\nSu saldo acutal es de: " + users[userNumber].wallet);
                                    break;
                                }
                        }
                    }while(option != 0);
                    option = 1;
                } else{
                    alert("Ha ocurrido un error, verifique sus datos e inténtelo de nuevo");
                }
                break;
            case '2': 
            alert("La fecha actual es: " + currentDate);
            break;
            case '3': userName = prompt("Escriba su nuevo usuario");
                userPassword = prompt("Escriba su contraseña");
                userNumber = users.length;
                addUser(userName, userPassword);
                    break;
            case '4': miliseconds = prompt("Escriba la fecha en milisegundos");
            date = currentDate.setMilliseconds(miliseconds);
            alert("La fecha es:  " + date)
            break;  
        }
    }while(option != 0);
})();