document
  .getElementById("cancelButton")
  .addEventListener("click", function (event) {
    event.preventDefault();
    //Redireciona para a página de login
    window.location.href = "index.html";
  });

document
  .getElementById("registerForm")
  .addEventListener("submit", function (event) {
    
    async function addUser() {

      let username = document.getElementById("username").value;
      let password = document.getElementById("password").value;
      let email = document.getElementById("email").value;
      let firstname = document.getElementById("firstname").value;
      let lastname = document.getElementById("lastname").value;
      let phone = document.getElementById("phone").value;
      let photoURL = document.getElementById("photo").value;

      let user = {
        username: username,
        password: password,
        email: email,
        firstname: firstname,
        lastname: lastname,
        phone: phone,
        photoURL: photoURL,
      };

      console.log(JSON.stringify(user));
      

      await fetch("http://localhost:8080/demo-1.0-SNAPSHOT/rest/user/add", {
          method: "POST",
          headers: {
            Accept: "*/*",
            "Content-Type": "application/json",
          },
          body: JSON.stringify(user),
        }).then(response => {
          if (response.ok) {
            alert("user is added successfully :)");
          } else {
            return response.text(); // read the response body as text
          }
        })
        .then(text => {
          if (text == 'username') {
            alert("user already exists :(");
            document.getElementById("username").value = "";
          } else if (text == 'mail') {
            alert("email already exists :(");
          } else {
            alert("Something went wrong :(");
          }
        })
        .catch(error => {
          console.error('There was a problem with the fetch operation:', error);
        });
    }

    event.preventDefault();

    addUser();  
    //Redireciona para a página de login
    //window.location.href = "index.html";
  });
