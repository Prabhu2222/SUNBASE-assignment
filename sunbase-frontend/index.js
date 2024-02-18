let loginIdInputField=document.getElementById("login-id")
let passwordInputField = document.getElementById("password")


let submitBtn = document.getElementById("submit-btn");

submitBtn.addEventListener("click",async (event) => {
    let loginId = loginIdInputField.value;
    let password = passwordInputField.value;

    if (!loginId || !password) {
        alert("please enter all the details");
        return;
    }
    let authObj=createAuthRequestObject(loginId, password);

    let authenticateAndGetTokenUrl = "http://localhost:8080/api/authenticate"
    let response = await fetch(authenticateAndGetTokenUrl, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(authObj)
    })
    console.log(response);
    if (response.ok == false) {
        window.alert("Unauthorized access!!!Access Revoked")
        window.location.reload();
        return;
    }
    let token = await response.text();
    console.log(token);
    localStorage.setItem("jwtToken", token);
    window.location.href = "customerlist.html";

})

function createAuthRequestObject(loginId, password) {
    let obj = {
        username: loginId,
        password:password
    }
    return obj;
    
}