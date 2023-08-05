const currentDate = new Date();

//Name es un String, disponibility un booleano stops un array, se usan solo 2 casillas del array
function Route(name, stops){
    this.name = name;
    this.stops = stops;
    this.disponibility = false;
}

function Bus(plate, routes, price, maxCapacity){
    this.plate = plate;
    this.routes = routes;
    this.price = price;
    this.maxCapacity = maxCapacity;
    this.tickets = [];
    this.currentCapacity;
    this.disponibility;
}

//name y password son un String, wallet un entero, tickets un array del objeto tickets representando el historial de tickets comparados
function User(name, password){
    this.name = name;
    this.password = password;
    this.wallet = 0;
    this.tickets = [];
}

//user es un objeto User, bus un objeto Bus, routeArrayNumber es un array de enteros de 2 casillas que representa el número de la ruta que tomará el usuario con ese ticket, name es un String, el cual es un recibo y disponibilidad nos dice si el ticket sigue vigente o ya expiró
function Ticket(user, bus, routeArrayNumber){
    this.user = user;
    this.bus = bus;
    this.dates[0] = bus.routes[routeArrayNumber[0]].date[0];
    this.dates[1] = bus.routes[routeArrayNumber[1]].date[1];
    this.routeArrayNumber = routeArrayNumber;
    this.name = String;
    this.disponibility = false;
}

export{currentDate, Route, Bus, User, Ticket};