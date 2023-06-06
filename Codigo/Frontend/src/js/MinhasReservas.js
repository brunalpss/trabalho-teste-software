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
var RentID


$(document).ready(function() {

    searchAllRents()

});
function searchAllRents(){

    let path = 'rents/page/0/size/10/'
    get(path,'rentsTable');
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
                    console.log(savedData)
                    showContentInRents(data.responseJSON, elementId)
                    showPaginationRentsSearch(data.responseJSON, 'rentsPagination', path)


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
function showContentInRents(data, elementId){
    let carTable ="	<div class=\"row \">\
                        <div class=\"col-auto\">\
                            <div>\
                            <table class=\"table align-middle text-center table-hover table-responsive table-bordered table-striped\">\
                                <thead>\
                                <tr>\
                                    <th scope=\"col\">#</th>\
                                    <th scope=\"col\">Nome Criador</th>\
                                    <th scope=\"col\">Veiculo</th>\
                                    <th scope=\"col\">Data Retirada</th>\
                                    <th scope=\"col\">Data Entrega</th>\
                                    <th scope=\"col\">Status</th>\
                                    <th scope=\"col\">Preço</th>\
                                    <th scope=\"col\">Status de Pagamento</th>\
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
        carTableBody +=  "<tr onclick='PickedRent("+content[i]['idRent']+", this)'>\
            <td class=\"align-middle fw-bolder\" >"+content[i]['idRent']+"</td>\
            <td id=\"creatorName\">"+content[i]['idCreator']['name']+"</td>\
            <td>"+content[i]['idVehicle']['name']+"</td>\
            <td>"+content[i]['withdrawDate']+"</td>\
			<td>"+content[i]['returnDate']+"</td>\
			<td>"+content[i]['status']+"</td>\
			<td>"+content[i]['price']+"</td>\
			<td id=\"paymentStatus\">"+content[i]['paymentStatus']+"</td>\
		</tr>"
    }
    return carTableBody;
}

async function PickedRent(id, row){
    if (highlightedRow) {
        highlightedRow.classList.remove("highlight");
    }
        row.classList.add("highlight");
        highlightedRow = row;
        idSelecionado = id;
        getRentById(id);
}
async function getRentById(id) {
    let urlIdUser = url + "/getRentByid/idRent/" + id;

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

    RentID = await response.json();
}
//Montar botoes de paginação
function showPaginationRentsSearch(data, elementId, path){
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
            " type=\"button\" class=\"page-link"+(isOnPage? " disabled":"")+"\" onclick=\"get('rents/page/"+i+"/size/"+sizes+"', 'rentsTable')\">"+i+"</button></li>"
    }


    rentspagination = "<nav aria-label=\"Page navigation example\">\
	  <ul class=\"pagination\">\
		<li class=\"page-item disable\">\
		  <button "+(previousPage < 0? "disabled": "")+" type=\"button\" class=\"page-link "+(previousPage < 0? "disabled": "")+"\" onclick=\"get('rents/page/"+previousPage+"/size/"+sizes+"', 'rentsTable')\">Anterior</button>\
		</li>"
        +pagesBtn+
        "<li class=\"page-item\">\
          <button "+(totalPages-1 == currentPage? "disabled": "")+" type=\"button\" class=\"page-link "+(totalPages-1 == currentPage? "disabled": "")+"\" onclick=\"get('rents/page/"+nextPage+"/size/"+sizes+"', 'rentsTable')\">Próximo</button>\
		</li>\
	  </ul>\
	</nav>"

    $("#"+elementId)[0].innerHTML = rentspagination
}


