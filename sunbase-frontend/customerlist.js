
let tableBody;
let prevBtn;
let nextBtn;
let searchBtn;
let searchInput;
let selectElemet;
let addBtn;
let syncBtn;

let dataBaseAuthUrl = "https://qa.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp"
let dataBaseDataUrl = "https://qa.sunbasedata.com/sunbase/portal/api/assignment.jsp"


tableBody = document.getElementsByTagName("table")[0].children[1];
prevBtn = document.getElementById("prev");
nextBtn = document.getElementById("next");
searchBtn = document.getElementById("search-btn");
searchInput = document.getElementById("search-bar");
addBtn = document.getElementById("add-btn");
selectElemet = document.getElementById("selection");
syncBtn=document.getElementById("sync-btn")
// console.log(nextBtn);
// console.log(sessionStorage.getItem("pageInfo"));

addBtn.addEventListener("click", (event) => {
  window.location.href = "addcustomer.html";
});

prevBtn.addEventListener("click", (event) => {
  // console.log("prev is clicked");

  //retrive pageInfo from session storage
  let pageInfoObj = JSON.parse(sessionStorage.getItem("pageInfo"));
  if (pageInfoObj.isFirstPage) {
    window.alert("already on the first page");
    return;
  }

  //if not the first page then make reeuest to back end to load the previous page
  //and delete the current table rows;
  let currPageNo = pageInfoObj.currentPageNo;

  let valueSelected = selectElemet.options[selectElemet.selectedIndex].value;
  let inputSearchValue = searchInput.value;
  let commonUrl = "http://localhost:8080/customer";
  let postUrl = "";
  if (valueSelected == "Search By" || inputSearchValue.length == 0)
    postUrl = `/get_all?pageNo=${currPageNo - 1}`;

  if (valueSelected == "firstName")
    postUrl = `/search_by_name?pageNo=${
      currPageNo - 1
    }&pattern=${inputSearchValue}`;
  else if (valueSelected == "email")
    postUrl = `/search_by_email?pageNo=${
      currPageNo - 1
    }&pattern=${inputSearchValue}`;
  else if (valueSelected == "city")
    postUrl = `/search_by_city?pageNo=${
      currPageNo - 1
    }&pattern=${inputSearchValue}`;
  else if (valueSelected == "phone")
    postUrl = `/search_by_phone?pageNo=${
      currPageNo - 1
    }&pattern=${inputSearchValue}`;

  let comboUrl = commonUrl + postUrl;
  fetch(comboUrl, {
    method: "GET",
    headers: {
      Authorization:`Bearer ${localStorage.getItem("jwtToken")}`
    }
  })
    .then((response) => {
      if (response.status == 401 || response.status == 403) {
        console.log(response)
        alert("your SESSION EXPIRED!!!please login again...")
        window.location.href = "index.html"
        return;
      }
      return response.json()
    })
    .then((data) => {
      extractContentAndAppendToTheTable(data.dataObject);
      manupulatePageInSessionStorage(data.dataObject);
    });

  let tableBodyElement = document
    .getElementsByTagName("table")[0]
    .getElementsByTagName("tbody")[0];
  while (tableBodyElement.rows.length > 0) {
    tableBodyElement.deleteRow(0);
  }
});

nextBtn.addEventListener("click", (event) => {
  console.log("next is clicked");
  console.log(sessionStorage.getItem("pageInfo"));

  //retrive pageInfo from session storage
  let pageInfoObj = JSON.parse(sessionStorage.getItem("pageInfo"));
  if (pageInfoObj.isLastPage) {
    window.alert("already on the last page");
    return;
  }

  //if not the last page then make reeuest to back end to load the next page
  //and delete all the current table rows;
  let currPageNo = pageInfoObj.currentPageNo;

  let valueSelected = selectElemet.options[selectElemet.selectedIndex].value;
  let inputSearchValue = searchInput.value;
  let commonUrl = "http://localhost:8080/customer";
  let postUrl = "";
  if (valueSelected == "Search By" || inputSearchValue.length == 0)
    postUrl = `/get_all?pageNo=${currPageNo + 1}`;

  if (valueSelected == "firstName")
    postUrl = `/search_by_name?pageNo=${
      currPageNo + 1
    }&pattern=${inputSearchValue}`;
  else if (valueSelected == "email")
    postUrl = `/search_by_email?pageNo=${
      currPageNo + 1
    }&pattern=${inputSearchValue}`;
  else if (valueSelected == "city")
    postUrl = `/search_by_city?pageNo=${
      currPageNo + 1
    }&pattern=${inputSearchValue}`;
  else if (valueSelected == "phone")
    postUrl = `/search_by_phone?pageNo=${
      currPageNo + 1
    }&pattern=${inputSearchValue}`;

  let comboUrl = commonUrl + postUrl;

  fetch(comboUrl, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      "Authorization":`Bearer ${localStorage.getItem("jwtToken")}`
    },
  })
    .then((response) => {
      if (response.status == 401 || response.status == 403) {
        console.log(response)
        alert("your SESSION EXPIRED!!!please login again...")
        window.location.href = "index.html"
        return;
      } 
      return response.json()
    } )
    .then((data) => {
      extractContentAndAppendToTheTable(data.dataObject);
      manupulatePageInSessionStorage(data.dataObject);
    });

  let tableBodyElement = document
    .getElementsByTagName("table")[0]
    .getElementsByTagName("tbody")[0];
  while (tableBodyElement.rows.length > 0) {
    tableBodyElement.deleteRow(0);
  }
});

searchBtn.addEventListener("click", (event) => {
  let valueSelected = selectElemet.options[selectElemet.selectedIndex].value;
  let inputSearchValue = searchInput.value;
  console.log(inputSearchValue.length);
  console.log(valueSelected);
  if (valueSelected == "Search By" || inputSearchValue.length == 0) return;
  let commonUrl = "http://localhost:8080/customer";
  let postUrl = "";
  if (valueSelected == "firstName")
    postUrl = `/search_by_name?pattern=${inputSearchValue}`;
  else if (valueSelected == "email")
    postUrl = `/search_by_email?pattern=${inputSearchValue}`;
  else if (valueSelected == "city")
    postUrl = `/search_by_city?pattern=${inputSearchValue}`;
  else if (valueSelected == "phone")
    postUrl = `/search_by_phone?pattern=${inputSearchValue}`;

  let totalUrl = commonUrl + postUrl;
  fetch(totalUrl, {
    method: "GET",
    headers: {
      Authorization:`Bearer ${localStorage.getItem("jwtToken")}`
    }
  })
    .then((response) => {
      if (response.status == 401 || response.status == 403) {
        console.log(response)
        alert("your SESSION EXPIRED!!!please login again...")
        window.location.href = "index.html"
        return;
      }
      return response.json()
    })
    .then((data) => {
      console.log(data);
      let tableBodyElement = document
        .getElementsByTagName("table")[0]
        .getElementsByTagName("tbody")[0];
      
      while (tableBodyElement.rows.length > 0) {
        tableBodyElement.deleteRow(0);
      }

      extractContentAndAppendToTheTable(data.dataObject);
      manupulatePageInSessionStorage(data.dataObject);
    });
});


syncBtn.addEventListener("click", (event) => {
  getTokenFetchAndUpdateList();
})




let url = "http://localhost:8080/customer/get_all";
fetch(url, {
  method: "GET",
  headers: {
    "Content-Type": "application/json",
    "Authorization":`Bearer ${localStorage.getItem("jwtToken")}`
  },
})
  .then((response) => {
    if (response.status==401||response.status==403) {
       console.log(response)
      window.alert("your SESSION EXPIRED!!!please log in again to continue");
      window.location.href = "index.html"
      return;
      // console.log(response)
    
    }
    return response.json();
  } )
  .then((data) => {
    // console.log(data);
    console.log(data)
    extractContentAndAppendToTheTable(data.dataObject);
    manupulatePageInSessionStorage(data.dataObject);
  });


//after getting array of data you need to extract it
//convert it in the form table row or column
//then append it to the table body

function manupulatePageInSessionStorage(data) {
  let pageInfoObj = {
    array: data.dataObject,
    currentPageNo: data.number,
    dataPerPage: data.numberOfElements,
    totalDataCount: data.totalElements,
    isFirstPage: data.first,
    isLastPage: data.last,
  };
  sessionStorage.setItem("pageInfo", JSON.stringify(pageInfoObj));
}

function extractContentAndAppendToTheTable(data) {
  let arrayOfCustomers = data.content;
  //   console.log(arrayOfCustomers);
  //steps
  //iterate over array and create a table row for each element
  //get the table body and append it
  arrayOfCustomers.forEach((object) => {
    let tableRow = document.createElement("tr");
    tableRow.innerHTML = `<td>${object.first_name}</td>
        <td>${object.last_name}</td>
        <td>${object.address}</td>
        <td>${object.street}</td>
        <td>${object.city}</td>
        <td>${object.state}</td>
        <td>${object.email}</td>
        <td>${object.phone}</td>
        <td>
            <button class="btn" id="del" onClick="deleteMe(this)">Delete</button>
            <button class="btn" id="edit" onClick="editMe(this)">Edit</button>
        </td>`;
    tableBody.append(tableRow);
  });
}

function deleteMe(button) {
  console.log(button.parentNode.parentNode.remove());
  let email = button.parentNode.parentNode.children[6].innerText;
  console.log(email);
  let deleteUrl="http://localhost:8080/customer/delete"
  let deleteResponse=fetch(`${deleteUrl}?email=${email}`, {
    method: "DELETE",
    headers: {
      Authorization:`Bearer ${localStorage.getItem("jwtToken")}`
    }
  })
  if (deleteResponse.status == 401 || deleteResponse.status == 403) {
    console.log(deleteResponse)
    alert("your SESSION EXPIRED!!!please login again...")
    window.location.href = "index.html"
    return;
  }
  window.alert("if you are deleting data.plz make sure to refresh to see the deired changes in correctly")
}

function editMe(button) {
  let cusotmerObj = {};
  let childrenArray = button.parentNode.parentNode.children;
  cusotmerObj = {
    firstName: childrenArray[0].innerText,
    lastName: childrenArray[1].innerText,
    address: childrenArray[2].innerText,
    street: childrenArray[3].innerText,
    city: childrenArray[4].innerText,
    state: childrenArray[5].innerText,
    email: childrenArray[6].innerText,
    phone: childrenArray[7].innerText
  }
  // console.log(cusotmerObj)
  localStorage.setItem("customer", JSON.stringify(cusotmerObj));
  window.location.href = "addcustomer.html";

}




async function getTokenFetchAndUpdateList() {



  let authResponse = await fetch(dataBaseAuthUrl, {
        method: "POST",
        body:JSON.stringify( {
            "login_id": "test@sunbasedata.com",
            "password":"Test@123"
        })
  });
  let authData = await authResponse.json();
  let accessToken = authData.access_token;
  console.log(accessToken)


  let cmdValue = "get_customer_list";

  let customerListResponse = await fetch(`${dataBaseDataUrl}?cmd=${cmdValue}`, {
        method: "GET",
        headers: {
            Authorization:`Bearer ${accessToken}`
        }
  })
   
  // console.log(customerListResponse)
  let cusotmerList = await customerListResponse.json();
  // console.log(cusotmerList)
  // console.log(cusotmerList[0])
  //call to the backend to update the list int the backend database
  let backendUrl = "http://localhost:8080/customer/sync";
  let backendResponse = await fetch(`${backendUrl}`, {
    method: "POST",
    headers: {
      'Content-Type': 'application/json', // Set the appropriate Content-Type
      Authorization:`Bearer ${localStorage.getItem("jwtToken")}`
  },
    body: JSON.stringify(cusotmerList)
  })
  // console.log(backendResponse);

  if (backendResponse.status == 401 || backendResponse.status == 403) {
    console.log(backendResponse);
    alert("your SESSION EXPIRED!!!please login again...")
    window.location.href = "index.html"
    return;
  }
  let backendResponseMessage = await backendResponse.json();
  alert (`${backendResponseMessage.message}`)
 
}
