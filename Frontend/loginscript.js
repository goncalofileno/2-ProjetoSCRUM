//Ao clicar no botão de login, o username é armazenado na sessionStorage e o usuário é redirecionado para a página de interface
document
  .getElementById("loginForm")
  .addEventListener("submit", function (event) {
    event.preventDefault();
    //Armazena o username na sessionStorage
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    console.log(username);  
    console.log(password);

    async function loginUser(username, password) {
      await fetch("http://localhost:8080/demo-1.0-SNAPSHOT/rest/user/login", {
        method: "POST",
        headers: {
          Accept: "*/*",
          "Content-Type": "application/json",
          "username": username,
          "password": password
        },
        credentials: 'include',
      })
      .then((response) => {
        if (response.ok) {
          return response.text(); // read the response body as text
        } else {
          return response.text(); // read the response body as text
        }
      })
      .then((data) => {
        if (data == "done") {
          // Store the username in localStorage
          localStorage.setItem("username", username);
          // Redirect to the interface page
          window.location.href = "interface.html";
        } else if (data == "username") {
          alert("username does not exist :(");
        } else if (data == "password") {
          alert("wrong password :(");
        } else {
          console.log(data);
        }
      })
      .catch((error) => {
        console.error("There was a problem with the fetch operation:", error);
      });
    }
    loginUser(username, password);
  });

document
  .getElementById("registerButton")
  .addEventListener("click", function (event) {
    event.preventDefault();
    //Redireciona para a página de registro
    window.location.href = "register.html";
  });
