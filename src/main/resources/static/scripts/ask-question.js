// ChatBotAPI_6

function performSearch() {
    //"document" is reference to the html-document where we are right now
    const question = document.getElementById('searchInput').value.trim();

    if (question === '') {  //"===" same value and type
        alert('Please enter a question');
        return;
    }

    // Show loading indicator
    const resultsOuterContainerDiv = document.getElementById('resultsOuterContainer');
    resultsOuterContainerDiv.style.display = 'block';

    const resultsDiv = document.getElementById('searchResults');
    resultsDiv.innerHTML = '<p>Thinking about your question...</p>';

    // Make AJAX request while we are on the page we make request to teh backend without reloading the page. We show the response to the user.
    fetch('/tutorials/ask-question', {   //To this URLS send a
        method: 'POST',                            //POST-requeste
        headers: {
            'Content-Type': 'application/json',     //we say "ouer contet will be JSON" so the other machine to know what to expect
            'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]').getAttribute('content')  // Anti forgery tocen. From that element. give em the Atribute "content"
        },
        body: JSON.stringify({query: question}) // JSON.stringify will take a JS object and convert it to string. {} this mean its an object
    })
        .then(response => {       //we start the fetch and when he gets teh response we o to the next step .then // whatever comes as response we will put in the fetch
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();    // in the response we have a lot of information. also a lot of meta information that we dont need. response.json() -> resturn the body. that what we actually need.
        })
        .then(data => {     // with the return from the previous then I want to make something else
            displayResults(data);    //show the data
        })
        .catch(error => {
            resultsDiv.innerHTML = '<p class="text-danger">Error: ' + error.message + '</p>';
        });
}

function displayResults(data) {
    const resultsDiv = document.getElementById('searchResults');
    resultsDiv.innerHTML = marked.parse(data["answer"]); // arked.parse(XXX)  convert marked down(**example**) text and converts in something that you can show
}

// Add event listener for the button click
document.getElementById('searchButton').addEventListener('click', performSearch);

// Add event listener for the Enter key press in the input field
document.getElementById('searchInput').addEventListener('keypress', function (event) { //this function is declared below

    // Check if the key pressed was "Enter"
    if (event.key === 'Enter') {
        // in html when we have something in a form and press key then we leave the page and go somewhere else.
        event.preventDefault();   //dont do what you want to do
        // do this
        performSearch();
    }
});