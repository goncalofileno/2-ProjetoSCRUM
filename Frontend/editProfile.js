/**************************************/
/* on load , fill out the form with placeholders*/
/**************************************/
window.onload = async function () {
  await getUser();
  const user = JSON.parse(sessionStorage.getItem("user"));
  let n_stars = 12;
  document.getElementById("username").placeholder = user.username;
  document.getElementById("oldpassword").placeholder = "*".repeat(n_stars); //.repeat(user.password.length);
  document.getElementById("newpassword").placeholder = "*".repeat(n_stars); //.repeat(user.password.length);
  document.getElementById("confirmnewpassword").placeholder = "*".repeat(
    n_stars
  ); //.repeat(user.password.length);
  document.getElementById("email").placeholder = user.email;
  document.getElementById("firstname").placeholder = user.firstname;
  document.getElementById("lastname").placeholder = user.lastname;
  document.getElementById("phone").placeholder = user.phone;
  document.getElementById("photo").placeholder = user.photoURL;
};

// /**************************************/
// /* function getUser - obtains the user infos from the backend*/
// //(need generalization from sessiionstorage or authenticated)
// /**************************************/
// async function getUser(){
//     //http://localhost:8080/demo-1.0-SNAPSHOT/rest/user/add -> but with the variable or info there
//     let response = await fetch('http://localhost:8080/demo-1.0-SNAPSHOT/rest/user/0',
//     {
//         method: 'GET',
//         headers: {
//         'Accept': 'application/json'
//         },
//     });

//     let user = await response.json(); // convert to JSON
//     // console.log(user);
//     return user; // return
// }
// /**************************************/
// /* function getUser().then() - places the user infos on placeholders after the fetch */
// /**************************************/
// getUser().then(user => {’

/* example:
{"email":"123@google.com","id":0,"password":"123","phone":"123","photoURL":"http://localhost:8080/frontend/register.html","username":"Hannah"}
*/

// });

//getUser().response --- aqui segunda à tarde
/**************************************/
/* document listener - for the editProfileForm and submit "Save Changes" button */
/**************************************/
document
  .getElementById("editProfileForm")
  .addEventListener("submit", function (event) {
    event.preventDefault(); //*** dp a pull fix things and remove the prevent default */
    // generalize for multiple users
    // *** check if we don't need another UserfromForm class in backend
    updateUser();
  });

/**************************************/
/* function updateUser -  */
/**************************************/
async function updateUser() {
  // aqui fazer o POST! tal como no add
  let userFromForm = getUserfromUpdatedFormValues();
  console.log(userFromForm);
  console.log("This data was in the form:\n" + JSON.stringify(userFromForm));

  //http://localhost:8080/demo-1.0-SNAPSHOT/rest/user/0
  await fetch("http://localhost:8080/demo-1.0-SNAPSHOT/rest/user/update", {
    method: "POST",
    headers: {
      Accept: "*/*",
      "Content-Type": "application/json",
      username: localStorage.getItem("username"),
      password: localStorage.getItem("password"),
    },
    body: JSON.stringify(userFromForm),
  })
    .then((response) => {
      if (response.ok) {
        alert("user was edited successfully :)");
      } else {
        alert(response.text());
      }
    })
    .catch((error) => {
      console.log("There was a problem with the fetch operation", error);
    });

  /* 1st attempt -> above is now a simplification
    
    */

  // verificação do email, caso alguém já o tenha
  // verificar todos os users, excepto este, se alguém já o possui
  // response de erro406?. com texto email

  //, eu mandaria sempre os 3 valores para o backend
  //e o backend apenas me alertaria
  //caso 1: password antiga nao corresponde
  //caso2: novas passwords não correspondem
  //caso 3: password nova igual a antiga
  //caso4: update feito com sucesso
}
/**************************************/
/* function getUserfromUpdatedFormValues -  */
/**************************************/
function getUserfromUpdatedFormValues() {
  // gets values from Form
  let username = document.getElementById("username").placeholder; // unique case sends the placeholder
  let oldpassword = getValueOrPlaceholder("oldpassword");
  let newpassword = getValueOrPlaceholder("newpassword");
  let confirmnewpassword = getValueOrPlaceholder("confirmnewpassword");
  let email = getValueOrPlaceholder("email");
  let firstname = getValueOrPlaceholder("firstname");
  let lastname = getValueOrPlaceholder("lastname");
  let phone = getValueOrPlaceholder("phone");
  let photoURL = getValueOrPlaceholder("photo");

  // turns it into a User
  let userFromForm = {
    username: username,
    oldpassword: oldpassword,
    newpassword: newpassword,
    confirmnewpassword: confirmnewpassword,
    email: email,
    firstname: firstname,
    lastname: lastname,
    phone: phone,
    photoURL: photoURL,
  };

  return userFromForm;
}
/**************************************/
/* function getValueOrPlaceholder(elementId) - checks if the elementId has an updated value, otherwise returns "" since it was only placeholder  */
/**************************************/
function getValueOrPlaceholder(elementId) {
  // ternary syntax javascript = condition ? exprIfTrue : exprIfFalse
  const element = document.getElementById(elementId);
  return element.value === "" ? "" : element.value;
}

async function getUser() {
  const response = await fetch(
    "http://localhost:8080/demo-1.0-SNAPSHOT/rest/user/get",
    {
      method: "GET",
      headers: {
        Accept: "*/*",
        "Content-Type": "application/json",
        username: localStorage.getItem("username"),
        password: localStorage.getItem("password"),
      },
    }
  );

  if (!response.ok) {
    alert("Failed to fetch user");
    return;
  }

  const user = await response.json();
  console.log(user);

  // Store the user in the session storage
  sessionStorage.setItem("user", JSON.stringify(user));
}
