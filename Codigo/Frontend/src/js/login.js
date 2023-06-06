let url = "http://localhost:9999";
const API_URL = 'http://localhost:9999'


function login() {

    var loginBody = {
        email:  String(document.getElementById("Username").value),
        password: String(document.getElementById("Password").value)
    }

    requestUser(loginBody);

    //postLogin(loginBody)

}

function postLogin(body){

    var path = "/auth/login";
    callRoute(API_URL+path, 'POST', body)

}



async function requestUser(User) {
    console.log('entrou script no login')
    urlBook = url + "/auth/login";
    console.log('actual url')
    console.log(url)
    console.log('------------------')
    const response = await fetch(urlBook, {
        method: "POST",
        headers: {
            'Access-Control-Allow-Origin' : '*',
            'Access-Control-Allow-Methods' : 'GET, POST, PUT, DELETE, GET, OPTIONS',
            'Access-Control-Request-Method' : '*',
            'Access-Control-Allow-Headers' : 'Origin, X-Requested-With, Content-Type, Accept, Authorization',
            'Content-Type': 'application/JSON'
        },
        body: JSON.stringify(User)

    });
    console.log('fez a call')


    var data = await response.json();


    window.localStorage.setItem("token", data.token);
    window.localStorage.setItem("UserID", data.user.id)
    console.log(data);
    if(localStorage.token != null){
        if (confirm("Usuario logado com sucesso!") == true) {
            text = "ok!";
            window.location.href = 'index.html'
        } else {
            text = "Falha ao Logar";
            window.location.href = 'index.html';
        }
    }

}

function callRoute(path, route_type, body){
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
            'Access-Control-Allow-Origin' : '*',
            'Access-Control-Allow-Methods' : 'GET, POST, PUT, DELETE, GET, OPTIONS',
            'Access-Control-Request-Method' : '*',
            'Access-Control-Allow-Headers' : 'Origin, X-Requested-With, Content-Type, Accept, Authorization',
            'Content-Type': 'application/JSON'

        },
        success: function (data) {
            console.log("rota executada com sucesso")
            console.log(data)
        },
        // Error handling
        error: function (error) {

            console.log(error)
            console.log("rota executada com erro")
        },
        beforeSend: function (xhr) {

        },
        complete: function (data) {
            console.log("rota finalizada")
            console.log(data)
        }
    });
}


