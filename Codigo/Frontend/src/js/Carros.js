let url = "http://localhost:9999";
let token = localStorage.getItem('token');
let cars = [];
const API_URL = 'http://localhost:9999/'
const sizes = 10;
var currentPage = 0;
var listOfTableContent = []
var savedData
var highlightedRow = null;
var idSelecionado = -1;
var VehicleID


$(document).ready(function() {

    searchAllVehicles()

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
                case 3:

            }


        }
    });
}

//----------------------------------HTML CALLS -------------------------------------------------------------------------------------------------------------------

function get(path, elementId){


    callRoute(API_URL+path, 'GET', undefined, elementId,2)

}

function post(path, body, elementId){
    callRoute(API_URL+path, 'POST', body)
}

function callRouteDeleteUser(path){


    callRoute(API_URL+path, 'DELETE', undefined, elementId,2)

}


//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
// Montar tabela no HTML com JS para usuarios
function showContentInCars(data, elementId){
    let carTable ="	<div class=\"row \">\
                        <div class=\"col-auto\">\
                            <div>\
                            <table class=\"table align-middle text-center table-hover table-responsive table-bordered table-striped\">\
                                <thead>\
                                <tr>\
                                    <th scope=\"col\">#</th>\
                                    <th scope=\"col\">Nome</th>\
                                    <th scope=\"col\">Chassi</th>\
                                    <th scope=\"col\">Placa</th>\
                                    <th scope=\"col\">Documento Legal</th>\
                                    <th scope=\"col\">Ano de Fabricação</th>\
                                    <th scope=\"col\">Fabricante</th>\
                                    <th scope=\"col\">Verifica Solicitações</th>\
                                    <th scope=\"col\">Deletar</th>\
                                </tr>\
                                </thead>\
                                <tbody>"+
                        getTableBody(data.content)
                        +
                        "</tbody>\
                    </table>\
                </div>\
            </div>\
        </div>\
"

    $("#"+elementId)[0].innerHTML = carTable
}


function getTableBody(content){
     let carTableBody = "";
    //listOfTableContent = content
    for(i = 0; i < content.length; i++){
        let idCar = content[i]['idVehicle']
        carTableBody +=  "<tr onclick='PickedVehicle("+content[i]['idVehicle']+", this)'>\
            <td class=\"align-middle fw-bolder\" >"+content[i]['idVehicle']+"</td>\
            <td id=\"Name\">"+content[i]['name']+"</td>\
            <td>"+content[i]['chassi']+"</td>\
            <td>"+content[i]['licensePlate']+"</td>\
			<td>"+content[i]['legalDocument']+"</td>\
			<td id=\"ManufacturedYear\">"+content[i]['manufacturedYear']+"</td>\
			<td>"+content[i]['manufacturer']+"</td>\
			<td><button type=\"button\" class=\"page-link\" onclick=\"VerifyAccessToAlugar(" + content[i]['idVehicle'] +")\">Alugar</button>\</td>\
            <td><button type=\"button\" class=\"page-link\" onclick=\"DeleteVehicle(" + content[i]['idVehicle'] +")\">Delete</button>\</td>\
		  </tr>"
    }
    return carTableBody;
}

async function PickedVehicle(id, row){
    if (highlightedRow) {
        highlightedRow.classList.remove("highlight");
    }
        row.classList.add("highlight");
        highlightedRow = row;
        idSelecionado = id;
        getVehicleById(id);
}

//Montar botoes de paginação
function showPaginationCarsSearch(data, elementId, path){
    console.log("paginação")
    console.log(data, elementId, path)

    let currentPage = data['number']
    let previousPage = currentPage-1
    let totalPages = data['totalPages']
    let nextPage = totalPages-1 == currentPage? currentPage : currentPage+1


    pagesBtn = ""

    for(i = 0; i < totalPages; i++){
        isOnPage = currentPage == i

        pagesBtn += "<li class=\"page-item\"><button "+(isOnPage? "disabled": "")+
            " type=\"button\" class=\"page-link"+(isOnPage? " disabled":"")+"\" onclick=\"get('vehicles/page/"+i+"/size/"+sizes+"', 'carTableRegister')\">"+i+"</button></li>"
    }


    carpagination = "<nav aria-label=\"Page navigation example\">\
	  <ul class=\"pagination\">\
		<li class=\"page-item disable\">\
		  <button "+(previousPage < 0? "disabled": "")+" type=\"button\" class=\"page-link "+(previousPage < 0? "disabled": "")+"\" onclick=\"get('vehicles/page/"+previousPage+"/size/"+sizes+"', 'carTableRegister')\">Anterior</button>\
		</li>"
        +pagesBtn+
        "<li class=\"page-item\">\
          <button "+(totalPages-1 == currentPage? "disabled": "")+" type=\"button\" class=\"page-link "+(totalPages-1 == currentPage? "disabled": "")+"\" onclick=\"get('vehicles/page/"+nextPage+"/size/"+sizes+"', 'carTableRegister')\">Próximo</button>\
		</li>\
	  </ul>\
	</nav>"

    $("#"+elementId)[0].innerHTML = carpagination
}


async function createVehicle(){
    console.log('body prepare');

    var body = {
        chassi: String(document.getElementById("chassi").value),
        idVehicle: 0,
        legalDocument: String(document.getElementById("documentoLegal").value),
        licensePlate : String((document.getElementById("placa").value)),
        manufacturedYear: String((document.getElementById("manufacturedYear").value)),
        manufacturer: String((document.getElementById("manufacturer").value)),
        name: String(document.getElementById("nome").value),


    }


	if( ValidateCreate(	(document.getElementById("chassi").value,
						document.getElementById("documentoLegal").value,
						document.getElementById("placa").value,
                        document.getElementById("manufacturedYear").value,
                        document.getElementById("manufacturer").value,
                        document.getElementById("nome").value))
    ){

        urlUser = url + "/vehicles/create";
        const response = await fetch(urlUser, {
            method: "POST",
            headers: {
                'host': 'localhost:9999',
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/JSON',
                "Access-Control-Allow-Headers": "Origin, X-Requested-With, Content-Type, Accept",
                'authorization': 'Bearer ' + token
            },
            body: JSON.stringify(body)
        });
        alert("Cadastro registrado com sucesso!");
		console.log(data);
	 }else{
		alert("Um ou mais campos vazios");
	}

}

async function ValidateCreate(chassi, documentoLegal, placa, manufacturedYear, manufacturer, nome){
	if(chassi === null) return false;
	if(documentoLegal === null) return false;
	if(placa === null) return false;
    if(manufacturedYear === null) return false;
    if(manufacturer === null) return false;
    if(nome === null) return false;
	else return true;

}

async function getVehicleById(id) {
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
    document.getElementById("nome").value = VehicleID['name'];
    document.getElementById("chassi").value = VehicleID['chassi'];
    document.getElementById("documentoLegal").value = VehicleID['legalDocument'];
    document.getElementById("placa").value = VehicleID['licensePlate'];
    document.getElementById("manufacturedYear").value = VehicleID['manufacturedYear'];
    document.getElementById("manufacturer").value = VehicleID['manufacturer'];
}

async function Alugar(idCar){
    if(localStorage.getItem('token') !== null && localStorage.getItem('token') !== "undefined"){
        window.location.replace("aluguelCarro.html?CarID="+idCar)
    }else{
        alert("Você precisa fazer uma conta para alugar um carro")
    }
}

async function TestAlugar(){
    window.location.replace("aluguelCarro.html?CarID="+0)
}

function DeleteVehicle(id){
    let text = "Você tem certeza que deseja deletar esse veículo?";
    let SureRequest = false;
    let path = 'vehicles/delete/idVehicle/' + id;
    console.log(path);

    if (confirm(text) == true ) {
        console.log(path);
        callRoute(API_URL+path, 'DELETE', undefined, undefined,3)

    }else{

    }
}

async function editVehicle(){
        console.log('body prepare');
    
        var body = {
            chassi: String(document.getElementById("chassi").value),
            idVehicle: VehicleID.idVehicle,
            legalDocument: String(document.getElementById("documentoLegal").value),
            licensePlate : String((document.getElementById("placa").value)),
            manufacturedYear: String((document.getElementById("manufacturedYear").value)),
            manufacturer: String((document.getElementById("manufacturer").value)),
            name: String(document.getElementById("nome").value),
    
        }
    
    
        if( ValidateCreate(	(document.getElementById("chassi").value,
                            document.getElementById("documentoLegal").value,
                            document.getElementById("placa").value,
                            document.getElementById("manufacturedYear").value,
                            document.getElementById("manufacturer").value,
                            document.getElementById("nome").value))
        ){
    
            urlUser = url + "/vehicles/edit";
            const response = await fetch(urlUser, {
                method: "POST",
                headers: {
                    'host': 'localhost:9999',
                    'Access-Control-Allow-Origin': '*',
                    'Content-Type': 'application/JSON',
                    "Access-Control-Allow-Headers": "Origin, X-Requested-With, Content-Type, Accept",
                    'authorization': 'Bearer ' + token
                },
                body: JSON.stringify(body)
            });
            alert("Veículo editado com sucesso!");
         }else{
            alert("Um ou mais campos vazios");
        }
    
    
}