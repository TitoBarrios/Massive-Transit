import{currentDate, Route, Bus, User, Ticket} from './objects.js';

let routes = []; //Es un array tridimensional, routes[Array de Rutas][Ruta][Nro de Parada]
let buses = [];
let users = [];

const addBus = (plate, routesArrayNumber, price, maxCapacity) => buses.push(new Bus(plate, routes[routesArrayNumber], price, maxCapacity));
//Separar función para poderle decir al usuario que el nombre no está disponible
const addUser = (name, password) => {
    if(isUserNameAvailable(name)){
        users.push(new User(name, password));
    }
};

const isUserNameAvailable = userName => {
    let userAvailable = true;

    for(let i = 0; i < users.length; i++){
        if(users[i].name == userName){
            userAvailable = false;
        }
    }
    return userAvailable;
}

function createRoutes(startingHour, minutesDifference, daysOfWeek){
    let currentWeekDay = false;

    for(let i = 0; i < daysOfWeek.length; i++){
        daysOfWeek[i] = +daysOfWeek[i];
        if(daysOfWeek[i] == 8){
            daysOfWeek = [7];
            for(let j = 0; j < daysOfWeek.length; j++){
                daysOfWeek[i] = j;
            }
            currentWeekDay = true;
            break;
        }
        if(daysOfWeek[i] == +currentDate.getDay()){
            currentWeekDay = true;
        }
    }

    let dateForRoute = currentDate;
    while(!currentWeekDay){
        dateForRoute = dateForRoute.setDate(dateForRoute.getDate() + 1);
        for(let i = 0; i < daysOfWeek.length; i++){
            if(dateForRoute.getDay().parseInt() == daysOfWeek[i]){
                currentWeekDay = true;
            }
        }
    }
    
    let routeArray = [];
    for (let i = 0; i < minutesDifference.length; i++) {
        let startTime = new Date(dateForRoute.getTime() + startingHour * 60 * 60 * 1000);
        let endTime = new Date(startTime.getTime() + minutesDifference[i] * 60 * 1000);
        let route = new Route(i + " a " + (i+1),[startTime, endTime]);
        console.log(route.stops[0] + " a " + route.stops[1]);
        routeArray.push(route);
        startingHour += minutesDifference[i];
    }
    routes.push(routeArray);
}

createRoutes('5:00:00', [30, 25], [0, 1, 2, 3, 4, 5, 6])

//Después agregar función para conocer si el problema es el usuario o la contraseña
let userNumber; //no dejar varialbes random afuera de una función
function userLogIn(userName, password){
    let isCorrect = false;
    for(let i = 0; i < users.length; i++){
        if(users[i] != null){
            if(users[i].name == userName && users[i].password == password){
            userNumber = i;
            isCorrect = true;
            break;
            }
        }
    }
    return isCorrect;
}

export{routes, buses, users, addUser, addBus, createRoutes, userLogIn};