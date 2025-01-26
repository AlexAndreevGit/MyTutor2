    let selectedCurrency = 'EUR'
    let amountBGN = document.getElementById('priceInBGN').value
    let priceSpan = document.getElementById('priceEUR')

    fetch('/api/convert?' + new URLSearchParams(
        {
            from: 'BGN',
            to: selectedCurrency,
            amount: amountBGN
        }
    )).then(response=> response.json())
        .then(data => {priceSpan.textContent = data.result})
    .catch(error => {
        console.log('An error occurred:' + error)
    })
