let url = "http://localhost:9999";
let token = localStorage.getItem('token');
let user = [];
const API_URL = 'http://localhost:9999/'
const sizes = 10;
var currentPage = 0;
var listOfTableContent = []
var savedData
var highlightedRow = null;
var idSelecionado = -1;
var actualId;


$(document).ready(function() {

    searchAllUsers()

});
function searchAllUsers(){

    let path = 'user/page/0/size/10/'
    get(path,'UserTable');
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
                case 2: //LIVROS

                    savedData = data.responseJSON
                    showContentBookSearch(data.responseJSON, elementId)
                    showPaginationBookSeach(data.responseJSON, elementId, path)


                    break;
                case 3:

            }


        }
    });
}

//----------------------------------HTML CALLS -------------------------------------------------------------------------------------------------------------------

function get(path, elementId){


    callRoute(API_URL+path, 'GET', undefined, elementId,1)

}

function post(path, body, elementId){
    callRoute(API_URL+path, 'POST', body)
}

function callRouteDeleteUser(path){


    callRoute(API_URL+path, 'DELETE', undefined, elementId,1)

}


//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
// Montar tabela no HTML com JS para usuarios
function showContentIn(data, elementId){
    table =
        "	<div>\
              <table class=\"table align-middle text-center table-hover table-responsive table-bordered table-striped mt-lg-4\">\
                <thead>\
                  <tr>\
                    <th scope=\"col\">#</th>\
                    <th scope=\"col\">Nome</th>\
                    <th scope=\"col\">Email</th>\
                    <th scope=\"col\">Documento Legal</th>\
                    <th scope=\"col\">Telefone</th>\
                    <th scope=\"col\">Deletar Usuario</th>\
                    <th scope=\"col\">Editar Cadastro</th>\
                  </tr>\
                </thead>\
                <tbody>"+
        getTableBody(data.content)
        +
        "</tbody>\
      </table>\
    </div>\
"

    $("#"+elementId)[0].innerHTML = table
}


function getTableBody(content){
    tableBody = "";
    listOfTableContent = content
    for(i = 0; i < content.length; i++){
        let idUser = content[i]['idUser']
        let email  = content[i]['email']
        console.log(idUser)

        /*onclick='PickedUser("+content[i]['idUser']+", this)'>*/

        tableBody +=  "<tr onclick='PickedUser("+content[i]['idUser']+", this)'>\
            <td class=\"align-middle fw-bolder\" >"+content[i]['idUser']+"</td>\
            <td>"+content[i]['name']+"</td>\
			<td>"+content[i]['email']+"</td>\
			<td>"+content[i]['legal_document']+"</td>\
			<td>"+content[i]['phone1']+"</td>\
			<td><button type=\"button\" class=\"page-link\" onclick=\"deleteUser('"+content[i]['email']+"')\">Deletar</button>\</td>\
			<td><a class=\"page-link\" data-bs-toggle=\"collapse\" href=\"#CreateUser\" role=\"button\" aria-expanded=\"false\" aria-controls=\"CreateUser\">Editar Usuário</a>\
			</tr>"
    }
    return tableBody;
}

async function PickedUser(id ,row){
    if (highlightedRow) {
        highlightedRow.classList.remove("highlight");
      }
      row.classList.add("highlight");
      highlightedRow = row;
      idSelecionado = id;
      getUserById(id);
}

//Montar botoes de paginação
function showPagination(data, elementId, path){
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
            " type=\"button\" class=\"page-link"+(isOnPage? " disabled":"")+"\" onclick=\"get('user/page/"+i+"/size/"+sizes+"', 'UserTable')\">"+i+"</button></li>"
    }


    pagination = "<nav aria-label=\"Page navigation example\">\
	  <ul class=\"pagination\">\
		<li class=\"page-item disable\">\
		  <button "+(previousPage < 0? "disabled": "")+" type=\"button\" class=\"page-link "+(previousPage < 0? "disabled": "")+"\" onclick=\"get('user/page/"+previousPage+"/size/"+sizes+"', 'UserTable')\">Anterior</button>\
		</li>"
        +pagesBtn+
        "<li class=\"page-item\">\
          <button "+(totalPages-1 == currentPage? "disabled": "")+" type=\"button\" class=\"page-link "+(totalPages-1 == currentPage? "disabled": "")+"\" onclick=\"get('user/page/"+nextPage+"/size/"+sizes+"', 'UserTable')\">Próximo</button>\
		</li>\
	  </ul>\
	</nav>"

    $("#"+elementId)[0].innerHTML = pagination
}


async function CreateUser(){
	var DataNew = (document.getElementById("ano").value) + '/' + 
    (document.getElementById("mes").value) + '/' + 
    (document.getElementById("dia").value)

    const formBirthDate = new Date(DataNew);
    console.log('body prepare');

    var body = {
        address: {
            addressId : 0,
            city: String((document.getElementById("cidade").value)),
            district:String((document.getElementById("destrito").value)),
            number: String((document.getElementById("numeroCasa").value)),
            state: String((document.getElementById("estado").value)),
            street: String((document.getElementById("rua").value)),
            zipCode: String((document.getElementById("zip").value)),
        },
        birthDate: formBirthDate, //"2022-11-30T20:23:34.319Z",
        email:String(document.getElementById("email").value),
        idUser: 0,
        legalDocument: String(document.getElementById("documentoLegal").value),
        name: String(document.getElementById("nome").value),
        password : String((document.getElementById("senha").value)),
        phone1: String((document.getElementById("phone1").value)),
        phone2: String((document.getElementById("phone2").value)),
        roles: [
            String((document.getElementById("acesso").value))
        ],
        sex: String((document.getElementById("sexo").value))

    }

	if( ValidateCreate(	(document.getElementById("nome").value,
						document.getElementById("email").value,
						document.getElementById("documentoLegal").value,
                        document.getElementById("dia").value,
                        document.getElementById("mes").value,
                        document.getElementById("ano").value,
						document.getElementById("phone1").value,
						document.getElementById("sexo").value,
						document.getElementById("estado").value,
						document.getElementById("cidade").value,
						document.getElementById("rua").value,
						document.getElementById("numeroCasa").value,
						document.getElementById("destrito").value,
						document.getElementById("zip").value,
						document.getElementById("senha").value))
    ){

        urlUser = url + "/user/create";
        const response = await fetch(urlUser, {
            method: "POST",
            headers: {
                'host': 'localhost:9999',
                'Access-Control-Allow-Origin': '*',
                'Content-Type': 'application/JSON',
                "Access-Control-Allow-Headers": "Origin, X-Requested-With, Content-Type, Accept"
                //'authorization': 'Bearer ' + token
            },
            body: JSON.stringify(body)
        });
        alert("Cadastro registrado com sucesso!");
	 }else{
		alert("Um ou mais campos vazios");
	}

}

async function ValidateCreate(nome, email, documentLegal,dia,mes,ano, aniversario, phone1, sexo, estado, cidade, rua, numeroCasa, destrito, zip, senha, acesso){
	if(nome === null) return false;
	if(email === null) return false;
	if(documentLegal === null) return false;
    if(dia === null) return false;
    if(mes === null) return false;
    if(ano === null) return false;
	if(aniversario === null) return false;
	if(phone1 === null) return false;
	if(sexo === null) return false;
	if(estado === null) return false;
	if(cidade === null) return false;
	if(rua === null) return false;
	if(numeroCasa === null) return false;
	if(destrito === null) return false;
	if(zip === null) return false;
	if(senha === null) return false;
    if(acesso === null) return false;
	else return true;

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

        actualId = await response.json();
        console.log(actualId)
        document.getElementById("nome").value = actualId.name
        document.getElementById("email").value = actualId.email
        document.getElementById("senha").value = ""
        document.getElementById("phone1").value = actualId.phone1
        document.getElementById("phone2").value = actualId.phone2
        document.getElementById("cidade").value = actualId.address.city.city
        document.getElementById("destrito").value = actualId.address.district
        document.getElementById("numeroCasa").value = actualId.address.number
        document.getElementById("rua").value = actualId.address.street
        document.getElementById("zip").value = actualId.address.zipCode
        document.getElementById("documentoLegal").value = actualId.legal_document
        document.getElementById("sexo").value = actualId.sex
        document.getElementById("estado").value = actualId.address.state.uf
        let ReadDate = new  Date(actualId.birthDate)
        document.getElementById("ano").value = ReadDate.getYear() + 1900
        document.getElementById("dia").value = ReadDate.getDate()
        document.getElementById("mes").value = (ReadDate.getMonth() + 1)
    }

function deleteUser(email){
    let text = "Você tem certeza que deseja deletar esse usuario?";
    let SureRequest = false;
    let path = 'user/delete/' + email;

    if (confirm(text) == true ) {
        console.log(path);
        callRoute(API_URL+path, 'DELETE', undefined, undefined,1)

    }else{

    }
}

async function editUser(){
    let dataNew = (document.getElementById("ano").value) + '/' +
        (document.getElementById("mes").value) + '/' +
        (document.getElementById("dia").value)

    let formBirthDate = new Date(dataNew);
    console.log('body prepare');

    var body = {
        address: {
            addressId : actualId.address.addressId,
            city: String((document.getElementById("cidade").value)),
            district:String((document.getElementById("destrito").value)),
            number: String((document.getElementById("numeroCasa").value)),
            state: String((document.getElementById("estado").value)),
            street: String((document.getElementById("rua").value)),
            zipCode: String((document.getElementById("zip").value)),
        },
        birthDate: formBirthDate, //"2022-11-30T20:23:34.319Z",
        email:String(document.getElementById("email").value),
        idUser: actualId.idUser,
        legalDocument: String(document.getElementById("documentoLegal").value),
        name: String(document.getElementById("nome").value),
        password : String((document.getElementById("senha").value)),
        phone1: String((document.getElementById("phone1").value)),
        phone2: String((document.getElementById("phone2").value)),
        roles: [
            String((document.getElementById("acesso").value))
        ],
        sex: String((document.getElementById("sexo").value))

    }

    if( ValidateCreate(	(document.getElementById("nome").value,
        document.getElementById("email").value,
        document.getElementById("documentoLegal").value,
        document.getElementById("dia").value,
        document.getElementById("mes").value,
        document.getElementById("ano").value,
        document.getElementById("phone1").value,
        document.getElementById("sexo").value,
        document.getElementById("estado").value,
        document.getElementById("cidade").value,
        document.getElementById("rua").value,
        document.getElementById("numeroCasa").value,
        document.getElementById("destrito").value,
        document.getElementById("zip").value,
        document.getElementById("senha").value))
    ){

        urlUser = url + "/user/edit";
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
        alert("Editado com sucesso!");
        console.log(data);
    }else{
        alert("Um ou mais campos vazios");
    }

}