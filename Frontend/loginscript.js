//Ao clicar no botão de login, o username é armazenado na sessionStorage e o usuário é redirecionado para a página de interface
document
  .getElementById("loginForm")
  .addEventListener("submit", function (event) {
    event.preventDefault();
    //Armazena o username na sessionStorage
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    loginUser(username, password);
  });

document
  .getElementById("registerButton")
  .addEventListener("click", function (event) {
    event.preventDefault();
    //Redireciona para a página de registro
    window.location.href = "register.html";
  });

  function loginUser(username, password) {
    localStorage.removeItem("username");
    localStorage.removeItem("password");
    fetch("http://localhost:8080/demo-1.0-SNAPSHOT/rest/user/login", {
      method: "POST",
      headers: {
        Accept: "*/*",
        "Content-Type": "application/json",
        username: username,
        password: password,
      },
      credentials: "include",
    }).then(async (response) => {
      if (response.status == 200) {
        localStorage.setItem("username", username);
        localStorage.setItem("password", password);
        window.location.href = "interface.html";
      } else {
        alert("Invalid Credentials");
      }
    });
  }
