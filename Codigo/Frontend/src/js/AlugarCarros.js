let url = "http://localhost:9999";
let token = localStorage.getItem('token');
let cars = [];
const API_URL = 'http://localhost:9999/'
const sizes = 10;
var currentPage = 0;
var listOfTableContent = []
var savedData
var highlightedRow = null;
let User
let VehicleID
let rndInt
const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const IDCarro = urlParams.get('CarID')
var respBody

$(document).ready(function() {

    getVehicleById2(IDCarro)

});
function searchAllVehicles(){

    let path = 'vehicles/page/0/size/10/'
    get(path,'carTableRegister');
}

// METODOS PARA CHAMAR ROTA VIA AJAX
function callRoute(path, route_type, body, elementId, completeFunction){
    returnedData = ""
    let token = localStorage.getItem('token');

    $.ajax({
        // Our sample url to make request
        url: path,
        crossDomain: true,
        type: route_type,
        dataType: 'json',
        contentType: "application/json",
        data: JSON.stringify(body),
        headers: {
            'Access-Control-Allow-Origin': '*',
            "Access-Control-Allow-Headers": "Origin, X-Requested-With, Content-Type, Accept"
        },
        success: function (data) {
            console.log("rota: "+completeFunction+", executada")
        },
        // Error handling
        error: function (error) {
            console.log("rota: "+completeFunction+", nao executada, ERRO: ")
            console.log(error)
        },
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer '+token);
        },
        complete: function (data) {

            switch (completeFunction) {

                case 1: //USUARIOS
                    showContentIn(data.responseJSON, elementId)
                    showPagination(data.responseJSON, "Pagination", path)
                    break;
                case 2: //CARROS
                    savedData = data.responseJSON
                    showContentInCars(data.responseJSON, elementId)
                    showPaginationCarsSearch(data.responseJSON, 'carPagination', path)
                    break;
                case 3: //ALUGUEL
                    savedData = data.responseJSON
                    //showContentInRents(data.responseJSON, elementId)
                    //showPaginationRentsSearch(data.responseJSON, 'carPagination', path)
                    break;
                case 4: //criar reserva
                    savedData = data.responseJSON
                    alert("Cadastro registrado com sucesso!");


                    break;
            }


        }
    });
}

//----------------------------------HTML CALLS -------------------------------------------------------------------------------------------------------------------

function get(path, elementId){


    callRoute(API_URL+path, 'GET', undefined, elementId,3)

}

function post(path, body, elementId){
    callRoute(API_URL+path, 'POST', body)
}

function postCreateRent(path, body){
    callRoute(API_URL+path, 'POST', body,undefined,4)
}

function callRouteDeleteUser(path){


    callRoute(API_URL+path, 'DELETE', undefined, elementId,3)

}

//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

async function getVehicleById2(id) {
    let urlIdUser = url + "/vehicles/getVehicleByid/idVehicle/" + id;

    const response = await fetch(urlIdUser, {
        method: "GET",
        headers: {
            'host': 'localhost:9999',
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/JSON',
                "Access-Control-Allow-Headers": "Origin, X-Requested-With, Content-Type, Accept",
                'authorization': 'Bearer ' + token
        }
    });

    VehicleID = await response.json();
    let NomeDoCarro = "<h2 class=\"display-3 text-center mt-5 mb-2 text-dark\">"+ VehicleID['name'] +"</h2>"
    let PrecoDiaria = "";
    
    console.log(VehicleID)
    var id = localStorage.getItem('UserID')
    getUserById(id)

    rndInt = randomIntFromInterval(500, 1000)

    $('input').keyup(function(){ 
        var Valor = Number($('#dia').val());
        $('#Preco').html(rndInt * Valor)
    })
    document.getElementById("NomeCarro").innerHTML = NomeDoCarro;
}

//Gerar um valor aleatorio
function randomIntFromInterval(min, max) {
    return Math.floor(Math.random() * (max - min + 1) + min)
}
  
async function getUserById(id) {
    let urlIdUser = url + "/user/getuserbyid/userId/" + id;

    const response = await fetch(urlIdUser, {
        method: "GET",
        headers: {
            'host': 'localhost:9999',
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/JSON',
                "Access-Control-Allow-Headers": "Origin, X-Requested-With, Content-Type, Accept",
                'authorization': 'Bearer ' + token
        }
    });

    User = await response.json();
    console.log(User)
}

var actualDate = new Date();
Date.prototype.addDays=function(d){return new Date(this.valueOf()+864E5*d);};


async function createRent(){
    var dataPegar = (document.getElementById("DatePickUp").value)
    let Preco = document.getElementById("dia").value
    let temp = new Date(dataPegar)
    temp.addDays(Preco)
    let dataEntrega = dataPegar

    console.log(temp)
    Preco *= rndInt

    let body = {
        createdAt: null,
        deletedAt: null,
        idAttendant: User,
        idCreator: User,
        idRent: 0,
        idVehicle: VehicleID,
        paymentStatus: "Pending",
        price: Preco,
        returnDate: dataEntrega,
        status: "CREATED",
        withdrawDate: dataPegar
    }

    console.log(body)


    if( ValidateCreate(dataPegar, Preco) ){
        let thisUrlVehicle = url + "/rents/create";

        postCreateRent("rents/create",body)
        /*
        const response = await fetch(thisUrlVehicle, {
            method: "POST",
            headers: {
                'host': 'localhost:9999',
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/JSON',
                "Access-Control-Allow-Headers": "Origin, X-Requested-With, Content-Type, Accept",
                'authorization': 'Bearer ' + token
            },
            respBody: JSON.stringify(body)
        });

        if(respBody!=null){
            console.log(respBody)
            alert("Cadastro registrado com sucesso!");
        }else{
            alert("Um ou mais campos vazios");
        }
	 }else{
		alert("Um ou mais campos vazios");*/
	}
}

async function ValidateCreate(nome, email){
	if(nome === null) return false;
	if(email === null) return false;
	else return true;

}



/**
 * {
  "createdAt": "2023-04-04T17:58:09.076Z",
  "deletedAt": "2023-04-04T17:58:09.076Z",
  
  "idCreator": {
    "address": {
      "addressId": 0,
      "city": {
        "city": "string",
        "id_city": 0
      },
      "district": "string",
      "number": 0,
      "state": {
        "id_state": 0,
        "state": "string",
        "uf": "string"
      },
      "street": "string",
      "zipCode": "string"
    },
    "birthDate": "2023-04-04T17:58:09.076Z",
    "createdAt": "2023-04-04T17:58:09.076Z",
    "deletedAt": "2023-04-04T17:58:09.076Z",
    "email": "string",
    "idUser": 0,
    "legal_document": "string",
    "name": "string",
    "password": "string",
    "phone1": "string",
    "phone2": "string",
    "roles": [
      {
        "createdAt": "2023-04-04T17:58:09.076Z",
        "deletedAt": "2023-04-04T17:58:09.076Z",
        "id": 0,
        "name": "string"
      }
    ],
    "sex": "string"
  },
  "idRent": 0,
  "idVehicle": {
    "chassi": "string",
    "createdAt": "2023-04-04T17:58:09.076Z",
    "deletedAt": "2023-04-04T17:58:09.076Z",
    "idVehicle": 0,
    "legalDocument": "string",
    "licensePlate": "string",
    "manufacturedYear": "string",
    "manufacturer": "string",
    "name": "string"
  },
  "paymentStatus": "string",
  "price": 0,
  "returnDate": "2023-04-04T17:58:09.076Z",
  "status": "string",
  "withdrawDate": "2023-04-04T17:58:09.076Z"
}
 */