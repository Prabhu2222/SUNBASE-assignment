let form = document.getElementById("form");

let firstNameField = form.children[0].children[0];
let lastNameField = form.children[0].children[1];
let streetField = form.children[1].children[0];
let addressField = form.children[1].children[1];
let cityField = form.children[2].children[0];
let stateField = form.children[2].children[1];
let emailField = form.children[3].children[0];
let phoneField = form.children[3].children[1];

//if localStorage has a key called customer whose length is no 0,then populate the input field
function populateFormField() {
    if (localStorage.getItem("customer") != null && localStorage.getItem("customer").length != 0) {
        let customerObj = JSON.parse(localStorage.getItem("customer"));
        // console.log(customerObj)
        firstNameField.value = customerObj.firstName;
        lastNameField.value = customerObj.lastName;
        streetField.value = customerObj.street;
        addressField.value = customerObj.address;
        cityField.value = customerObj.city;
        stateField.value = customerObj.state;
        emailField.value = customerObj.email;
        phoneField.value = customerObj.phone;
       
    }
}


populateFormField();


let submitBtn = document.getElementById("submit-btn");


submitBtn.addEventListener("click", async (event) => {
    //prevent default behaviour
    event.preventDefault();
   
    if (firstNameField.value.length == 0 || lastNameField.value.length == 0 || emailField.value.length == 0) {
        window.alert("firstName,lastName,email are the required field");
        return;
       
    }
   //create customerObject to be sent to backen as esponse body
    let customerObj = {
        "first_name": `${firstNameField.value}`,
        "last_name": `${lastNameField.value}`,
        "street": `${streetField.value}`,
        "address": `${addressField.value}`,
        "city": `${cityField.value}`,
        "state": `${stateField.value}`,
        "email": `${emailField.value}`,
        "phone":`${phoneField.value}`
    }


     //if local storage has already a cusotmer obj already then its an edit/updaterequest
    if (localStorage.getItem("customer") != null) {
        let updateUrl = "http://localhost:8080/customer/update";
        let response = await fetch(updateUrl, {
            method: "PUT",
            headers: {
                'Content-Type': 'application/json',
                "Authorization":`Bearer ${localStorage.getItem("jwtToken")}`
            },
            body: JSON.stringify(customerObj)
        })
        if (response.status == 401 || response.status == 403) {
            console.log(response)
            alert("your SESSION EXPIRED!!!please login again...")
            window.location.href = "index.html"
            return;
        }
        let responseMessage = await response.json();
        window.alert(`${responseMessage.message}`)
        // localStorage.removeItem("customer");
        return;
     }
     //chenk for the required field
     //fetch the addcustomer api in the backend
    let addUrl="http://localhost:8080/customer/add"
    let response =await fetch(addUrl, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            "Authorization":`Bearer ${localStorage.getItem("jwtToken")}`
        },
        body: JSON.stringify(customerObj)
    }) 

    // console.log(response);
    if (response.status == 401 || response.status == 403) {
        console.log(response)
        alert("your SESSION EXPIRED!!!please login again...")
        window.location.href = "index.html"
        return;
    }
    let responseMessage = await response.json();
    window.alert(`${responseMessage.message}`);
    if (responseMessage.error === true) { 
        return;
    } else {
        form.reset();
    }
    
    
})

window.addEventListener('beforeunload', function(event) {
    // Clear localStorage when the user navigates away from the page
    localStorage.removeItem("customer");
  });