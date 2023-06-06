async function Loged(){
    let isLoged = "";
    if(localStorage.getItem('token') !== null && localStorage.getItem('token') !== "undefined"){
        isLoged += `<div class="dropdown">
        <a
          class="dropdown-toggle d-flex align-items-center hidden-arrow"
          href="#"
          id="navbarDropdownMenuAvatar"
          role="button"
          data-mdb-toggle="dropdown"
          aria-expanded="false"
        >
          <img
            src="https://mdbcdn.b-cdn.net/img/new/avatars/2.webp"
            class="rounded-circle"
            height="25"
            alt="Black and White Portrait of a Man"
            loading="lazy"
          />
        </a>
        <ul
          class="dropdown-menu dropdown-menu-end"
          aria-labelledby="navbarDropdownMenuAvatar"
        >
          <li>
            <a class="dropdown-item" href="#">My profile</a>
          </li>
          <li>
            <a class="dropdown-item" href="#">Settings</a>
          </li>
          <li>
            <a class="dropdown-item" href="Usuarios.html">Usuarios</a>
          </li>
          <li>
            <a class="dropdown-item" href="MinhasReservas.html">Minhas Reservas</a>
          </li>
          <li>
            <a class="dropdown-item" onclick="LogOut()" href="#">Logout</a>
          </li>
        </ul>
    </div>`
        
    }else{
        isLoged += `
        <a type="button" href="login.html" class="btn btn-primary me-3">
          Login
        </a>`
    }

    document.getElementById("Loged").innerHTML = isLoged
}

async function LogOut(){
  localStorage.removeItem('token');
  localStorage.removeItem('UserID');
  location.reload();
  window.location.href("index.html");
}

async function VerifyAccessToCars(){
    if(localStorage.getItem('token') !== null && localStorage.getItem('token') !== "undefined"){
        window.location.replace("RegistrarCarros.html")
    }else{
        window.location.replace("Carros.html")
    }
}

async function VerifyAccessToAlugar(CarID){
  if(localStorage.getItem('token') !== null && localStorage.getItem('token') !== "undefined"){
      window.location.replace("aluguelCarro.html?CarID=" + CarID)
  }else{
      alert("Você precisa estar logado para fazer isso!!!!")
  }
}

